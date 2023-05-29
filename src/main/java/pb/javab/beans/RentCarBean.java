package pb.javab.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pb.javab.daos.ICarDao;
import pb.javab.models.Car;
import pb.javab.models.CarStatus;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


/*
* Bean used for renting available cars as user
* */
@Named
@ViewScoped
public class RentCarBean implements Serializable {
    private final ICarDao carDao;
    private List<Car> availableCars;
    private Car rentCar;

    @Inject
    public RentCarBean(ICarDao carDao) {
        this.carDao = carDao;
    }

    public List<Car> getAvailableCars() {
        if(availableCars == null)
            availableCars = carDao.getAll().stream()
                    .filter(x->x.getStatus()== CarStatus.AVAILABLE)
                    .collect(Collectors.toList());
        return availableCars;
    }

    public void setAvailableCars(List<Car> availableCars) {
        this.availableCars = availableCars;
    }

    public Car getRentCar() {
        if(rentCar == null)
            rentCar = new Car();

        var id = rentCar.getId();
        if (id != null) {
            if (rentCar.getModel() == null)
                rentCar = carDao.get(id).orElseThrow();
        }
        return rentCar;
    }

    public void setRentCar(Car rentCar) {
        this.rentCar = rentCar;
    }
}
