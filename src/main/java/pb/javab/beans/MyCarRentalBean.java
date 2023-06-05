package pb.javab.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.*;
import pb.javab.services.ICarRentalService;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


/*
 * Bean used for search, pay and cancel car rentals.
 */
@Named
@ViewScoped
public class MyCarRentalBean implements Serializable {
    private final UserBean userBean;
    private final ICarRentalService carRentalService;
    private final ICarRentalDao carRentalDao;
    private List<CarRental> myCarRentals;
    private CarRental carRental;

    @Inject
    public MyCarRentalBean(UserBean userBean, ICarRentalService carRentalService, ICarRentalDao carRentalDao) {
        this.userBean = userBean;
        this.carRentalService = carRentalService;
        this.carRentalDao = carRentalDao;
    }

    public List<CarRental> getMyCarRentals() {
        if (myCarRentals == null)
            myCarRentals = carRentalDao.getAll().stream()
                    .filter(x -> x.getUser().equals(userBean.getUser()))
                    .collect(Collectors.toList());
        return myCarRentals;
    }

    public void setMyCarRentals(List<CarRental> myCarRentals) {
        this.myCarRentals = myCarRentals;
    }

    public CarRental getCarRental() {
        if (carRental == null)
            carRental = new CarRental();

        var id = carRental.getId();
        if (id != null) {
            if (carRental.getUser() == null)
                carRental = carRentalDao.get(id).orElseThrow();
        }
        return carRental;
    }

    public void setCarRental(CarRental carRental) {
        this.carRental = carRental;
    }

    public void makePayment() {
        var id = this.getCarRental().getId();
        carRentalService.pay(id);
    }
}
