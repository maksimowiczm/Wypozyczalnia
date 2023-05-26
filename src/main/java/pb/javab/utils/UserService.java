package pb.javab.utils;

import jakarta.ejb.Singleton;
import pb.javab.models.Role;
import pb.javab.models.User;

@Singleton
public class UserService {
    public boolean authenticateUser(User user) {
        user.setRole(Role.ADMIN);
        //TODO logowanie z bazą danych

        return true;
    }

    public boolean registerUser(User user) {
        //TODO rejestrowanie

        return true;
    }
}
