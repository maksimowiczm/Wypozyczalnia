package pb.javab.daos;

import jakarta.ejb.Stateless;
import pb.javab.models.Car;
import pb.javab.models.CarRental;

@Stateless
public class CarRentalDao extends GenericDao<CarRental> implements ICarRentalDao {
    public CarRentalDao() {
        super(CarRental.class);
    }
}
