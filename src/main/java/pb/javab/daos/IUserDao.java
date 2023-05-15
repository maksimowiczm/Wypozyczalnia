package pb.javab.daos;

import pb.javab.models.User;

public interface IUserDao extends IGenericDao<User> {
    User getByEmail(String email);
}
