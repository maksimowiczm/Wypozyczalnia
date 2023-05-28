import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import pb.javab.daos.ICarDao;
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

        var car = new Car("Matiz", "Daeweoo", 40, CarStatus.AVAILABLE, Transmission.MANUAL, new BigDecimal(100));
        carDao.save(car);
    }
}