package pb.javab.services;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.CarRental;
import pb.javab.models.CarStatus;

@Singleton
public class CarRentalService implements ICarRentalService {
    @Inject
    private ICarRentalDao carRentalDao;

    @Override
    public boolean rent(CarRental carRental) {
        carRentalDao.save(carRental);
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
