package pb.javab.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pb.javab.beans.UserBean;
import pb.javab.beans.UserService;
import pb.javab.models.User;
import pb.javab.utils.AuthorizationResult;
import pb.javab.utils.UserLoginValidator;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserBean userBean;
    private final UserService userService;

    @Inject
    public RegisterServlet(UserService userService, UserBean userBean) {
        this.userService = userService;
        this.userBean = userBean;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.xhtml").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var result = handleRegister(req, resp);
        if (result == AuthorizationResult.ERROR) {
            resp.sendRedirect("register.xhtml?error=true");
        } else if (result == AuthorizationResult.PASSWORD_DOESNT_MATCH) {
            resp.sendRedirect("register.xhtml?password_doesnt_match=true");
        } else if (result == AuthorizationResult.BAD_PASSWORD) {
            resp.sendRedirect("register.xhtml?bad_password=true");
        } else if (result == AuthorizationResult.EMAIL) {
            resp.sendRedirect("register.xhtml?bad_email=true");
        } else{
            resp.sendRedirect("");
        }
    }

    private AuthorizationResult handleRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        var password1 = req.getParameter("password1");

        var valid = UserLoginValidator.validate(email, password);
        if (valid != AuthorizationResult.SUCCESS) {
            return valid;
        }

        // Porównanie haseł
        if (!password.equals(password1)) {
            return AuthorizationResult.PASSWORD_DOESNT_MATCH;
        }

        // TODO Haszowanie hasła
        // Rejestracja usera
        var user = new User();
        user.setEmail(email);
        user.setPassword(password);

        if (!userService.registerUser(user)) {
            return AuthorizationResult.ERROR;
        }

        userBean.setUser(user);
        return AuthorizationResult.SUCCESS;
    }
}
