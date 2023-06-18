package pb.javab.services;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import pb.javab.daos.IUserDao;
import pb.javab.models.Role;
import pb.javab.models.User;
import org.mindrot.jbcrypt.BCrypt;
@Singleton
public class UserService {
    private IUserDao userDao;

    @PostConstruct
    @Inject
    public void init(IUserDao userDao) {
        this.userDao = userDao;
    }

    public boolean authenticateAndAuthorizeUser(User user) {
        var db_user = userDao.getByEmail(user.getEmail());
        if (db_user == null) {
            return false;
        }

        if (!BCrypt.checkpw(user.getPassword(),db_user.getPassword())) {
            return false;
        }

        user.setRole(db_user.getRole());
        user.setId(db_user.getId());
        user.setCarRentals(db_user.getCarRentals());
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
