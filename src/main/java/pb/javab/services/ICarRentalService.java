package pb.javab.services;

import pb.javab.models.Car;
import pb.javab.models.CarRental;
import pb.javab.models.CarStatus;
import pb.javab.models.User;

public interface ICarRentalService {
    boolean rent(Car car, User user);

    boolean pay(CarRental carRental);

    boolean cancel(CarStatus carRental);
}
