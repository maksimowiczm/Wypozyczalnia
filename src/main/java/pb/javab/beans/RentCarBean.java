package pb.javab.beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import pb.javab.daos.ICarDao;
import pb.javab.models.Car;
import pb.javab.models.CarStatus;
import pb.javab.services.CarRentalService;
import pb.javab.services.ICarRentalService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
    private Date rentStartDate;
    private Date rentEndDate;
    private BigDecimal rentPrice;


    @Inject
    public RentCarBean(ICarDao carDao, UserBean userBean, CarRentalService carRentalService) {
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
        if (id != null) {
            if (rentCar.getModel() == null)
                rentCar = carDao.get(id).orElseThrow();
        }
        return rentCar;
    }

    public void setRentCar(Car rentCar) {
        this.rentCar = rentCar;
    }

    public void rentCar() {
        if (rentCar == null) return;
        var user = userBean.getUser();
        if (user == null) return;
        carRentalService.rent(rentCar, user);
    }

    public void onStartDateSelect(SelectEvent<Date> event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.rentStartDate = event.getObject();
        updatePrice();
    }

    public void onEndDateSelect(SelectEvent<Date> event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.rentEndDate = event.getObject();
        updatePrice();
    }

    public Date getRentStartDate() {
        return rentStartDate;
    }

    public void setRentStartDate(Date rentStartDate) {
        this.rentStartDate = rentStartDate;
    }

    public Date getRentEndDate() {
        return rentEndDate;
    }

    public void setRentEndDate(Date rentEndDate) {
        this.rentEndDate = rentEndDate;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    private void updatePrice() {
        //Both dates must be selected
        if (rentStartDate == null || rentEndDate == null) return;
        //Start date must be before End date
        if (rentStartDate.compareTo(rentEndDate) > 0) return;
        int days = ((int) (rentStartDate.getTime() - rentEndDate.getTime())) / 1000 / 60 / 60 / 24;
        if (rentCar == null) return;
        this.rentPrice = rentCar.getRate().multiply(new BigDecimal(days));
    }
}
