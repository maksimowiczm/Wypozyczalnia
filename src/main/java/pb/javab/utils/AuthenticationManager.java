package pb.javab.utils;

import jakarta.ejb.Singleton;
import pb.javab.models.Role;
import pb.javab.models.User;

@Singleton
public class AuthenticationManager {
    public boolean authenticate(User user) {
        user.setRole(Role.ADMIN);
        //TODO logowanie z bazą danych

        return true;
    }
}
