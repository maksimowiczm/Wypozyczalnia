package pb.javab.services;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import pb.javab.daos.ICarDao;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.Car;
import pb.javab.models.CarRental;
import pb.javab.models.CarStatus;
import pb.javab.models.User;

@Singleton
public class CarRentalService implements ICarRentalService {
    @Inject
    private ICarRentalDao carRentalDao;
    @Inject
    private ICarDao carDao;

    @Override
    public boolean rent(Car car, User user) {
        var carRental = new CarRental();
        carRental.setCar(car);
        carRental.setUser(user);
        carRentalDao.save(carRental);
        car.setStatus(CarStatus.UNAVAILABLE);
        carDao.update(car);
        return true;
    }

    @Override
    public boolean pay(CarRental carRental) {
        return false;
    }

    @Override
    public boolean cancel(CarStatus carRental) {
        return false;
    }

    // TODO
    @Schedule(hour = "*/3", minute = "*", second = "*", persistent = false)
    private void cancelNotPayedReservations() {
        // TODO wysylanie maila
    }
}
