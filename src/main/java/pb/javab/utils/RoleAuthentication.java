package pb.javab.utils;

import pb.javab.models.Role;

public class RoleAuthentication {
    public static boolean authenticate(Role role, Role minimumRole) {
        // mniejszy numer -> wyższa rola
        return role.ordinal() <= minimumRole.ordinal();
    }
}
