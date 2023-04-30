package pb.javab.models;

import java.math.BigDecimal;
import java.util.List;

public class Car {
    private String model;
    private String manufacturer;
    private int power;
    private CarStatus status;
    private Transimition transimition;
    private BigDecimal rate;

    private List<CarRental> carRentals;
}
