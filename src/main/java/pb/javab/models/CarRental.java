package pb.javab.models;

import java.math.BigDecimal;
import java.util.Date;

public class CarRental {
    private Car car;
    private User user;

    private Date rentalStartDate;
    private Date rentalEndDate;
    private BigDecimal price;
    private CarRentalStatus status;
}
