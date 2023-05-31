package pb.javab.daos;

import pb.javab.models.CarRental;
import pb.javab.models.CarRentalStatus;
import pb.javab.models.User;

import java.util.List;

public interface ICarRentalDao extends IGenericDao<CarRental> {
    List<CarRental> getByUser(User user);

    List<CarRental> getByStatus(CarRentalStatus status);
}
