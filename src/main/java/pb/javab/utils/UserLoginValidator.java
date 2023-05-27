package pb.javab.utils;

import java.util.regex.Pattern;

public class UserLoginValidator {
    public static AuthorizationResult validate(String email, String password) {
        // Walidacja email
        var pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        var mat = pattern.matcher(email);
        if (!mat.matches()) {
            return AuthorizationResult.BAD_EMAIL;
        }
        // Walidacja hasła
        if (password.isEmpty()) {
            return AuthorizationResult.BAD_PASSWORD;
        }

        return AuthorizationResult.SUCCESS;
    }
}
