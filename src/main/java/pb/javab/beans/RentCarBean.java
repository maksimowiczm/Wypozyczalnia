package pb.javab.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import pb.javab.daos.ICarDao;
import pb.javab.models.Car;
import pb.javab.models.CarRental;
import pb.javab.models.CarRentalStatus;
import pb.javab.models.CarStatus;
import pb.javab.services.ICarRentalService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/*
 * Bean used for renting available cars as user
 * */
@Named
@ViewScoped
public class RentCarBean implements Serializable {
    private final ICarDao carDao;
    private final UserBean userBean;
    private final ICarRentalService carRentalService;
    private List<Car> availableCars;
    private Car rentCar;
    private CarRental carRental;
    private final Date today = new Date();
    private String viewParamUuidString;


    @Inject
    public RentCarBean(ICarDao carDao, UserBean userBean, ICarRentalService carRentalService) {
        this.carDao = carDao;
        this.userBean = userBean;
        this.carRentalService = carRentalService;
    }

    public List<Car> getAvailableCars() {
        if (availableCars == null)
            availableCars = carDao.getAll().stream()
                    .filter(x -> x.getStatus() == CarStatus.AVAILABLE)
                    .collect(Collectors.toList());
        return availableCars;
    }

    public void setAvailableCars(List<Car> availableCars) {
        this.availableCars = availableCars;
    }

    public Car getRentCar() {
        if (rentCar == null)
            rentCar = new Car();

        var id = rentCar.getId();
        if (id == null) {
            try {
                id = UUID.fromString(viewParamUuidString);
            } catch (IllegalArgumentException ignored) {
                return rentCar;
            }
        }
        if (rentCar.getModel() == null)
            rentCar = carDao.get(id).orElseThrow();
        return rentCar;
    }

    public void setRentCar(Car rentCar) {
        if (rentCar == null) return;
        this.rentCar = rentCar;
    }

    public void handleRentCar() {
        carRental.setCar(rentCar);
        carRental.setUser(userBean.getUser());
        carRental.setStatus(CarRentalStatus.NOT_PAID);
        if (!validateRent()) return;
        if (carRentalService.rent(carRental)) {
            rentCar.setStatus(CarStatus.UNAVAILABLE);
            carDao.update(rentCar);
        }
    }

    public void onStartDateSelect(SelectEvent<Date> event) {
        updatePrice();
    }

    public void onEndDateSelect(SelectEvent<Date> event) {
        updatePrice();
    }

    public CarRental getCarRental() {
        if (carRental == null)
            carRental = new CarRental();
        return carRental;
    }

    public void setCarRental(CarRental carRental) {
        this.carRental = carRental;
    }

    public Date getToday() {
        return today;
    }

    public String getViewParamUuidString() {
        return viewParamUuidString;
    }

    public void setViewParamUuidString(String viewParamUuidString) {
        this.viewParamUuidString = viewParamUuidString;
    }

    private void updatePrice() {
        //Both dates must be selected
        if (carRental.getRentalStartDate() == null || carRental.getRentalEndDate() == null) return;
        //Start date must be before End date
        if (carRental.getRentalStartDate().compareTo(carRental.getRentalEndDate()) > 0) return;
        //Time between dates, extract days, add one day.
        int days = ((int) (carRental.getRentalEndDate().getTime() - carRental.getRentalStartDate().getTime())) / 1000 / 60 / 60 / 24 + 1;
        if (rentCar == null) return;
        this.carRental.setPrice(rentCar.getRate().multiply(new BigDecimal(days)));
    }

    private Boolean validateRent() {
        // If car or carRental or user does not exist
        if (rentCar == null || carRental == null || userBean.getUser() == null) return false;
        // If start date after end date
        if (carRental.getRentalStartDate().compareTo(carRental.getRentalEndDate()) > 0) return false;
        // If price null or lower/equal 0
        if (carRental.getPrice() == null || carRental.getPrice().compareTo(new BigDecimal(0)) < 1) return false;
        // If car status in other than available
        if (rentCar.getStatus() != CarStatus.AVAILABLE) return false;
        return true;
    }
}
