package pb.javab.services;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.javab.daos.IUserDao;
import pb.javab.models.Role;
import pb.javab.models.User;
import org.mindrot.jbcrypt.BCrypt;

@Singleton
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private IUserDao userDao;

    @PostConstruct
    @Inject
    public void init(IUserDao userDao) {
        this.userDao = userDao;
    }

    public boolean authenticateAndAuthorizeUser(User user) {
        var db_user = userDao.getByEmail(user.getEmail());
        if (db_user == null) {
            log.info("User was NOT authenticated - incorrect email");
            return false;
        }

        if (!BCrypt.checkpw(user.getPassword(), db_user.getPassword())) {
            log.info("User was NOT authenticated - incorrect password");
            return false;
        }

        user.setRole(db_user.getRole());
        user.setId(db_user.getId());
        user.setCarRentals(db_user.getCarRentals());
        user.setPassword(db_user.getPassword());
        log.info("User was authenticated with email " + db_user.getEmail() + " and id " + db_user.getId());
        return true;
    }

    public boolean registerUser(User user) {
        var db_user = userDao.getByEmail(user.getEmail());
        if (db_user != null) {
            log.info("User was NOT registered - user with email - " + db_user.getEmail() + " exists in database");
            return false;
        }

        user.setRole(Role.USER);
        // haszowanie
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userDao.save(user);
        log.info("User was successfully registered with email - " + user.getEmail());
        return true;
    }
}
