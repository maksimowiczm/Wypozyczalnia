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
        var query = em.createNamedQuery(User.findUserByEmail, User.class);
        query.setParameter(1, email);
        var result = query.getResultList();

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }
}
