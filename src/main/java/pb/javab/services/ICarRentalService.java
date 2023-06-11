package pb.javab.services;

import pb.javab.models.CarRental;

import java.util.UUID;

public interface ICarRentalService {
    boolean rent(CarRental carRental);

    boolean pay(UUID id);

    boolean cancel(UUID id);
}
