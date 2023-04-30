package pb.javab.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

    private Date rentalStartDate;
    private Date rentalEndDate;
    private BigDecimal price;
    private CarRentalStatus status;
}
