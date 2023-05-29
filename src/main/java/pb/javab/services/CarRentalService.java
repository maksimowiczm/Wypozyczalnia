package pb.javab.services;

import jakarta.ejb.Schedule;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import pb.javab.daos.CarRentalDao;
import pb.javab.models.CarRental;
import pb.javab.models.CarStatus;

@Singleton
public class CarRentalService implements ICarRentalService {
    private final CarRentalDao carRentalDao;

    @Inject
    public CarRentalService(CarRentalDao carRentalDao) {
        this.carRentalDao = carRentalDao;
    }

    @Override
    public boolean rent(CarRental carRental) {
        return false;
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
