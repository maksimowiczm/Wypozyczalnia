package pb.javab.services;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.CarRental;
import pb.javab.models.CarRentalStatus;
import pb.javab.models.CarStatus;
import pb.javab.utils.EmailSender;
import pb.javab.utils.IEmailSender;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
@Startup
public class CarRentalService implements ICarRentalService {
    private ICarRentalDao carRentalDao;

    private List<CarRental> carRentalToBePayed;

    @Inject
    private IEmailSender emailSender;

    protected void setEmailSender(IEmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostConstruct
    @Inject
    public void init(ICarRentalDao carRentalDao) {
        this.carRentalDao = carRentalDao;
        // get unpaid cars from db at startup
        carRentalToBePayed = new ArrayList<>(carRentalDao.getByStatus(CarRentalStatus.NOT_PAID));
    }

    @Override
    public boolean rent(CarRental carRental) {
        carRentalDao.save(carRental);
        carRentalToBePayed.add(carRental);
        emailSender.SendEmail(carRental.getUser(), EmailSender.reservationCreatedMessage);
        return true;
    }

    @Override
    public boolean pay(UUID id) {
        var rental = getCarRentalFromList(id);
        if (rental == null)
            return false;

        rental.setStatus(CarRentalStatus.PAID);
        carRentalDao.update(rental);

        emailSender.SendEmail(rental.getUser(), EmailSender.reservationPayedMessage);

        carRentalToBePayed.remove(rental);
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

        emailSender.SendEmail(rental.getUser(), EmailSender.reservationCancelledByUserMessage);

        carRentalToBePayed.remove(rental);
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
            carRentalDao.delete(rental);
            carRentalToBePayed.remove(rental);
            deletedCarRentals.add(rental);
        }

        return deletedCarRentals;
    }

    @Schedule(hour = "*", minute = "*/30", second = "*", persistent = false)
    private void cancelNotPayedReservations() {
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -3);
        List<CarRental> toBeEmailed = cancelAllCarRentalsThatAreNotPaidAndOlderThan(calendar.getTime());
        for (CarRental rental : toBeEmailed) {
            emailSender.SendEmail(rental.getUser(), EmailSender.reservationCancelledTimeoutMessage);
        }
    }
}
