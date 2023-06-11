package pb.javab.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.CarRental;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CarRentalBean implements Serializable {
    @Inject
    private ICarRentalDao dao;
    private List<CarRental> carRentalList;

    public List<CarRental> getCarRentalList() {
        if (carRentalList == null) {
            carRentalList = dao.getAll();
        }
        return carRentalList;
    }

    public void setCarRentalList(List<CarRental> carRentalList) {
        this.carRentalList = carRentalList;
    }
}
