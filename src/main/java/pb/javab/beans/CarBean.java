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
import java.util.UUID;

@Named
@ViewScoped
public class CarBean implements Serializable {
    private final ICarDao dao;
    private List<Car> cars;
    private Car car;
    private String viewParamUuidString;

    @Inject
    public CarBean(ICarDao dao) {
        this.dao = dao;
    }

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

        var id = car.getId();
        if (id == null) {
            try {
                id = UUID.fromString(viewParamUuidString);
            } catch (IllegalArgumentException ignored) {
                return car;
            }
            car.setId(id);
        }
        if (car.getModel() == null)
            car = dao.get(id).orElseThrow();

        return car;
    }

    public void setCar(Car car) {
        if (car != null) {
            this.car = car;
        }
    }

    public String getViewParamUuidString() {
        return viewParamUuidString;
    }

    public void setViewParamUuidString(String viewParamUuidString) {
        this.viewParamUuidString = viewParamUuidString;
    }

    public void delete() {
        if (car == null) return;
        dao.delete(car);
        car = null;
    }

    public void delete(UUID id) {
        var carToDelete = dao.get(id).orElse(null);
        if (carToDelete == null) return;
        dao.delete(carToDelete);
    }

    public void persist() {
        if (car == null) {
            return;
        }

        if (car.getStatus() == null) {
            car.setStatus(CarStatus.AVAILABLE);
        }
        if (car.getCarRentals() == null) {
            car.setCarRentals(new ArrayList<>());
        }

        if (car.getId() != null) {
            dao.update(car);
        } else {
            dao.save(car);
        }

        cars = null;
    }
}
