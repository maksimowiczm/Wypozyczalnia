package pb.javab.daos;

import jakarta.ejb.Stateless;
import pb.javab.models.Car;

@Stateless
public class CarDao extends GenericDao<Car> implements ICarDao {
    public CarDao() {
        super(Car.class);
    }
}
