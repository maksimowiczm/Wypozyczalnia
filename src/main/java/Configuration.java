import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import pb.javab.daos.ICarDao;
import pb.javab.daos.ICarRentalDao;
import pb.javab.daos.IUserDao;
import pb.javab.models.*;

import java.math.BigDecimal;

@Singleton
@Startup
public class Configuration {
    @Inject
    private IUserDao userDao;
    @Inject
    private ICarDao carDao;

    @Inject
    private ICarRentalDao carRentalDao;

    @PostConstruct
    public void init() {
        seedDatabase();
    }

    private void seedDatabase() {
        var admin = new User();
        admin.setRole(Role.ADMIN);
        admin.setEmail("admin@a.aa");
        admin.setPassword("admin");
        userDao.save(admin);

        var user = new User();
        user.setRole(Role.USER);
        user.setEmail("user@u.uu");
        user.setPassword("user");
        userDao.save(user);

        var car = new Car("Matiz", "Daeweoo", 40, CarStatus.AVAILABLE, Transmission.MANUAL, new BigDecimal(100));
        carDao.save(car);

        var car2 = new Car("emszesc", "Beemwu", 269, CarStatus.AVAILABLE, Transmission.AUTOMATIC, new BigDecimal(200));
        carDao.save(car2);

        var car3 = new Car("Rdzazda", "Mazda", 100, CarStatus.UNAVAILABLE, Transmission.AUTOMATIC, new BigDecimal(200));
        carDao.save(car3);
    }
}
