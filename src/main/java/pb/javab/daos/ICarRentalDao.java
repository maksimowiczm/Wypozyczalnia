package pb.javab.daos;

import pb.javab.models.CarRental;
import pb.javab.models.User;

import java.util.List;

public interface ICarRentalDao extends IGenericDao<CarRental> {
    List<CarRental> getByUser(User user);
}
