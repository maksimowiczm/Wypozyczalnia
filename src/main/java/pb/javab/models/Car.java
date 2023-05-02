package pb.javab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "car")
public class Car extends BaseModel {
    @NotNull
    private String model;
    @NotNull
    private String manufacturer;
    @Min(1)
    @NotNull
    private int power;
    private CarStatus status;
    @NotNull
    private Transmission transmission;
    @Min(1)
    @NotNull
    private BigDecimal rate;

    @OneToMany(mappedBy = "car", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CarRental> carRentals;

    public Car() {

    }

    public Car(Car car) {
        model = car.model;
        manufacturer = car.manufacturer;
        power = car.power;
        status = car.status;
        transmission = car.transmission;
        rate = car.rate;
        carRentals = car.carRentals;
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

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
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
