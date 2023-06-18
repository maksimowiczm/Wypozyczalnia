package pb.javab.daos;

import pb.javab.models.CarRental;
import pb.javab.models.CarRentalStatus;

import java.util.List;

public interface ICarRentalDao extends IGenericDao<CarRental> {
    List<CarRental> getByStatus(CarRentalStatus status);
}
