package pb.javab.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "carRental")
public class CarRental extends BaseModel {
    @ManyToOne
    private Car car;
    @ManyToOne
    private User user;

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

    private Date createdAt;
    private Date rentalStartDate;
    private Date rentalEndDate;
    private BigDecimal price;
    private CarRentalStatus status;
}
