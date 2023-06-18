package pb.javab.daos;

import jakarta.ejb.Stateless;
import pb.javab.models.CarRental;
import pb.javab.models.CarRentalStatus;

import java.util.List;

@Stateless
public class CarRentalDao extends GenericDao<CarRental> implements ICarRentalDao {
    public CarRentalDao() {
        super(CarRental.class);
    }

    public List<CarRental> getByStatus(CarRentalStatus status) {
        return em.createNamedQuery(CarRental.GetCarRentalByStatus).setParameter("status", status).getResultList();
    }
}
