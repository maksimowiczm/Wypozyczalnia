package pb.javab.beans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import pb.javab.daos.IUserDao;
import pb.javab.models.Role;
import pb.javab.models.User;

@Singleton
public class UserService {
    private IUserDao userDao;

    @PostConstruct
    @Inject
    public void init(IUserDao userDao) {
        this.userDao = userDao;

        var admin = new User();
        admin.setRole(Role.ADMIN);
        admin.setEmail("admin@a.aa");
        admin.setPassword("admin");
        userDao.save(admin);
    }

    public boolean authenticateAndAuthorizeUser(User user) {
        var db_user = userDao.getByEmail(user.getEmail());
        if (db_user == null) {
            return false;
        }
        if (!db_user.getPassword().equals(user.getPassword())) {
            return false;
        }

        user.setRole(db_user.getRole());
        return true;
    }

    public boolean registerUser(User user) {
        var db_user = userDao.getByEmail(user.getEmail());
        if (db_user != null) {
            return false;
        }

        user.setRole(Role.USER);
        userDao.save(user);
        return true;
    }
}
