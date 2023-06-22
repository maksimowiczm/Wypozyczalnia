package pb.javab.services;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.CarRental;
import pb.javab.models.CarRentalStatus;
import pb.javab.models.CarStatus;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
@Startup
public class CarRentalService implements ICarRentalService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private ICarRentalDao carRentalDao;

    private List<CarRental> carRentalToBePayed;

    @PostConstruct
    @Inject
    public void init(ICarRentalDao carRentalDao) {
        this.carRentalDao = carRentalDao;
        // get unpaid cars from db at startup
        carRentalToBePayed = new ArrayList<>(carRentalDao.getByStatus(CarRentalStatus.NOT_PAID));
    }

    @Override
    public boolean rent(CarRental carRental) {
        carRental.setCreatedAt(new Date());
        carRentalDao.save(carRental);
        carRentalToBePayed.add(carRental);

        log.info("Car with id " + carRental.getCar().getId() + " rented by user with id " + carRental.getUser().getId());
        return true;
    }

    @Override
    public boolean pay(UUID id) {
        var rental = getCarRentalFromList(id);
        if (rental == null)
            return false;

        rental.setStatus(CarRentalStatus.PAID);
        carRentalDao.update(rental);

        carRentalToBePayed.remove(rental);
        log.info("CarRental with id " + rental.getId() + " was paid.");
        return true;
    }

    @Override
    public boolean cancel(UUID id) {
        var rental = getCarRentalFromList(id);
        if (rental == null || rental.getStatus() != CarRentalStatus.NOT_PAID)
            return false;
        rental.getCar().setStatus(CarStatus.AVAILABLE);
        rental.setStatus(CarRentalStatus.CANCELED);
        carRentalDao.update(rental);

        carRentalToBePayed.remove(rental);
        log.info("CarRental with id " + rental.getId() + " was canceled.");
        return true;
    }

    private CarRental getCarRentalFromList(UUID id) {
        return carRentalToBePayed.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    /**
     * Deletes car rentals that are older than given date
     *
     * @return deleted car rentals
     */
    protected List<CarRental> cancelAllCarRentalsThatAreNotPaidAndOlderThan(Date date) {
        var deletedCarRentals = new ArrayList<CarRental>();
        var notPaid = carRentalToBePayed.stream().filter(c -> c.getStatus() == CarRentalStatus.NOT_PAID && c.getCreatedAt().before(date)).collect(Collectors.toList());

        for (var rental : notPaid) {
            rental.getCar().setStatus(CarStatus.AVAILABLE);
            rental.setStatus(CarRentalStatus.CANCELED);
            carRentalDao.update(rental);
            carRentalToBePayed.remove(rental);
            deletedCarRentals.add(rental);
            log.info("Reservation with id" + rental.getId() + " was not paid in time and is being canceled.");
        }

        return deletedCarRentals;
    }

    @Schedule(hour = "*", minute = "*/30", second = "*", persistent = false)
    private void cancelNotPayedReservations() {
        var calendar = Calendar.getInstance();
        // TODO change to HOUR -3 and */30 in minutes
        calendar.add(Calendar.HOUR, -3);
        var toBeEmailed = cancelAllCarRentalsThatAreNotPaidAndOlderThan(calendar.getTime());
        // TODO wysylanie maila
    }
}
