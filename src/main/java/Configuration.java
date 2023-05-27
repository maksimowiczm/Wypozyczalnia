import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import pb.javab.daos.IUserDao;
import pb.javab.models.Role;
import pb.javab.models.User;

@Singleton
@Startup
public class Configuration {
    private IUserDao userDao;

    @PostConstruct
    @Inject
    public void init(IUserDao userDao) {
        this.userDao = userDao;

        seedDatabase();
    }

    private void seedDatabase() {
        var admin = new User();
        admin.setRole(Role.ADMIN);
        admin.setEmail("admin@a.aa");
        admin.setPassword("admin");
        userDao.save(admin);
    }
}
