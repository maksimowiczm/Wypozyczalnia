package pb.javab.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pb.javab.daos.ICarDao;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.Car;
import pb.javab.models.CarStatus;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class RentCarBean implements Serializable {
    private final ICarDao carDao;
    private final ICarRentalDao carRentalDao;
    private List<Car> avilableCars;
    private Car rentCar;

    @Inject
    public RentCarBean(ICarDao carDao, ICarRentalDao carRentalDao) {
        this.carDao = carDao;
        this.carRentalDao = carRentalDao;
    }

    public List<Car> getAvilableCars() {
        if(avilableCars == null)
            avilableCars = carDao.getAll().stream()
                    .filter(x->x.getStatus()== CarStatus.AVAILABLE)
                    .collect(Collectors.toList());
        return avilableCars;
    }

    public void setAvilableCars(List<Car> avilableCars) {
        this.avilableCars = avilableCars;
    }

    public Car getRentCar() {
        return rentCar;
    }

    public void setRentCar(Car rentCar) {
        this.rentCar = rentCar;
    }
}
