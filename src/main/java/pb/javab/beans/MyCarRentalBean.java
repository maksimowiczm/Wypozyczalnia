package pb.javab.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.*;
import pb.javab.services.ICarRentalService;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
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
    private String viewParamUuidString;

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
        if (id == null && viewParamUuidString != null) {
            try {
                id = UUID.fromString(viewParamUuidString);
            } catch (IllegalArgumentException ignored) {
                return carRental;
            }
            if (carRental.getUser() == null)
                carRental = carRentalDao.get(id).orElseThrow();
        }
        return carRental;
    }

    public void setCarRental(CarRental carRental) {
        this.carRental = carRental;
    }

    public String getViewParamUuidString() {
        return viewParamUuidString;
    }

    public void setViewParamUuidString(String viewParamUuidString) {
        this.viewParamUuidString = viewParamUuidString;
    }

    public String makePayment() {
        if (carRentalService.pay(this.getCarRental().getId())) {
            this.carRental = carRentalDao.get(this.carRental.getId()).orElseThrow();
        }
        return "myCarRentalList.xhtml";
    }

    public String cancelReservation() {
        if (carRentalService.cancel(this.getCarRental().getId())) {
            this.carRental = carRentalDao.get(this.carRental.getId()).orElseThrow();
        }
        return "myCarRentalList.xhtml";
    }
}
