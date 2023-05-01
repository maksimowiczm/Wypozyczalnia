package pb.javab.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "car")
public class Car extends BaseModel {
    private String model;
    private String manufacturer;
    private int power;
    private CarStatus status;
    private Transmission transmission;
    private BigDecimal rate;

    @OneToMany(mappedBy = "car", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CarRental> carRentals;

    public Car() {

    }

    public Car(String model, String manufacturer, int power, CarStatus status, Transmission transmission, BigDecimal rate) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.power = power;
        this.status = status;
        this.transmission = transmission;
        this.rate = rate;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public Transmission getTransimition() {
        return transmission;
    }

    public void setTransimition(Transmission transmission) {
        this.transmission = transmission;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public List<CarRental> getCarRentals() {
        return carRentals;
    }

    public void setCarRentals(List<CarRental> carRentals) {
        this.carRentals = carRentals;
    }
}
