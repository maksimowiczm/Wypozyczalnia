package pb.javab.beans;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pb.javab.models.Role;
import pb.javab.models.User;
import pb.javab.utils.UserService;

import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet({"/login", "/register"})
public class LoginServlet extends HttpServlet {
    private final UserService userService;

    @Inject
    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.xhtml").forward(req, resp);
    }


    private enum ValidatorResult {
        EMAIL,
        PASSWORD_DOESNT_MATCH,
        BAD_PASSWORD,
        ERROR,
        SUCCESS,
    }

    private ValidatorResult validate(String email, String password) {
        // Walidacja email
        var pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        var mat = pattern.matcher(email);
        if (!mat.matches()) {
            return ValidatorResult.EMAIL;
        }
        // Walidacja hasła
        if (password.isEmpty()) {
            return ValidatorResult.BAD_PASSWORD;
        }

        return ValidatorResult.SUCCESS;
    }

    private ValidatorResult handleRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        var password1 = req.getParameter("password1");

        var valid = validate(email, password);
        if (valid != ValidatorResult.SUCCESS) {
            return valid;
        }

        // Porównanie haseł
        if (!password.equals(password1)) {
            return ValidatorResult.PASSWORD_DOESNT_MATCH;
        }

        // Rejestracja usera
        var user = new User();
        user.setEmail(email);
        user.setPassword(password);

        if (!userService.registerUser(user)) {
            return ValidatorResult.ERROR;
        }

        handleLogin(req, resp);
        return ValidatorResult.SUCCESS;
    }

    private ValidatorResult handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");

        var valid = validate(email, password);
        if (valid != ValidatorResult.SUCCESS) {
            return valid;
        }

        var user = new User();
        user.setEmail(email);
        user.setPassword(password);

        if (userService.authenticateUser(user)) {
            HttpSession session = req.getSession();
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("email", email);
            if (user.getRole() == Role.ADMIN) {
                session.setAttribute("admin", true);
            }
            resp.sendRedirect("");
        } else {
            resp.sendRedirect("login?error=true");
        }

        return ValidatorResult.SUCCESS;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var register = req.getParameter("register");

        String location;
        ValidatorResult result;
        if (register == null) {
            result = handleLogin(req, resp);
            location = "login.xhtml";
        } else {
            result = handleRegister(req, resp);
            location = "register.xhtml";
        }
        if (result == ValidatorResult.ERROR) {
            resp.sendRedirect(location + "?error=true");
        } else if (result == ValidatorResult.PASSWORD_DOESNT_MATCH) {
            resp.sendRedirect(location + "?password_doesnt_match=true");
        } else if (result == ValidatorResult.BAD_PASSWORD) {
            resp.sendRedirect(location + "?bad_password=true");
        } else if (result == ValidatorResult.EMAIL) {
            resp.sendRedirect(location + "?bad_email=true");
        }
    }
}
