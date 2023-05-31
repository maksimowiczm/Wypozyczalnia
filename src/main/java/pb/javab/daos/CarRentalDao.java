package pb.javab.daos;

import jakarta.ejb.Stateless;
import pb.javab.models.CarRental;
import pb.javab.models.User;

import java.util.List;

@Stateless
public class CarRentalDao extends GenericDao<CarRental> implements ICarRentalDao {
    public CarRentalDao() {
        super(CarRental.class);
    }

    @Override
    public List<CarRental> getByUser(User user) {
        // TODO
        return null;
    }
}
