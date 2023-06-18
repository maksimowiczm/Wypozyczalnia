package pb.javab.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "carRental")
@NamedQueries(
        @NamedQuery(name = CarRental.GetCarRentalByStatus,
                query = "select cr from CarRental cr where cr.status=:status")
)
public class CarRental extends BaseModel {
    public static final String GetCarRentalByStatus = "CarRental.GetCarRentalByStatus";
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Car car;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;
    private Date createdAt;
    private Date rentalStartDate;
    private Date rentalEndDate;
    private BigDecimal price;
    private CarRentalStatus status;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(Date rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public Date getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(Date rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarRentalStatus getStatus() {
        return status;
    }

    public void setStatus(CarRentalStatus status) {
        this.status = status;
    }

    public String getStartDateString() {
        var pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(this.rentalStartDate);
    }

    public String getEndDateString() {
        var pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(this.rentalEndDate);
    }
}
