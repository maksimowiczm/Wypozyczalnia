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
    private Transimition transimition;
    private BigDecimal rate;

    @OneToMany(mappedBy = "car", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CarRental> carRentals;
}
