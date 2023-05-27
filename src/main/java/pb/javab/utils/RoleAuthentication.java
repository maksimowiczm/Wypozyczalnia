package pb.javab.utils;

import pb.javab.models.Role;

public class RoleAuthentication {
    public static boolean authenticate(Role role, Role minimumRole) {
        return role.ordinal() >= minimumRole.ordinal();
    }
}
