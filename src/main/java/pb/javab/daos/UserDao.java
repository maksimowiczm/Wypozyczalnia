package pb.javab.daos;

import jakarta.ejb.Stateless;
import pb.javab.models.User;

@Stateless
public class UserDao extends GenericDao<User> implements IUserDao {
    protected UserDao() {
        super(User.class);
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }
}
