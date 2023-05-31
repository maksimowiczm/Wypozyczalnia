package pb.javab.services;

import pb.javab.models.CarRental;
import pb.javab.models.CarStatus;

public interface ICarRentalService {
    boolean rent(CarRental carRental);

    boolean pay(CarRental carRental);

    boolean cancel(CarRental carRental);
}
