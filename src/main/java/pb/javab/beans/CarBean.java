package pb.javab.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pb.javab.daos.ICarDao;
import pb.javab.models.Car;
import pb.javab.models.CarStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CarBean implements Serializable {
    private final ICarDao dao;

    public List<Car> getCars() {
        if (cars == null) {
            cars = dao.getAll();
        }
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Car getCar() {
        if (car == null)
            car = new Car();
        return car;
    }

    public void setCar(Car car) {
        if (car != null) {
            this.car = car;
        }
    }

    private List<Car> cars;
    private Car car;

    @Inject
    public CarBean(ICarDao dao) {
        this.dao = dao;
    }

    public void persist() {
        if (car.getStatus() == null) {
            car.setStatus(CarStatus.AVAILABLE);
        }
        if (car.getCarRentals() == null) {
            car.setCarRentals(new ArrayList<>());
        }

        dao.save(car);
    }
}
