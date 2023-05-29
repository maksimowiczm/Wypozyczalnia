package pb.javab.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pb.javab.beans.UserBean;
import pb.javab.services.UserService;
import pb.javab.models.User;
import pb.javab.utils.AuthorizationResult;
import pb.javab.utils.UserLoginValidator;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserBean userBean;
    private final UserService userService;

    @Inject
    public LoginServlet(UserService userService, UserBean userBean) {
        this.userService = userService;
        this.userBean = userBean;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.xhtml").forward(req, resp);
    }

    protected AuthorizationResult handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");

        var valid = UserLoginValidator.validate(email, password);
        if (valid != AuthorizationResult.SUCCESS) {
            return valid;
        }

        // TODO Haszowanie hasła
        var user = new User();
        user.setEmail(email);
        user.setPassword(password);

        if (userService.authenticateAndAuthorizeUser(user)) {
            userBean.setUser(user);
            return AuthorizationResult.SUCCESS;
        } else {
            return AuthorizationResult.ERROR;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var result = handleLogin(req, resp);
        if (result == AuthorizationResult.ERROR) {
            resp.sendRedirect("login.xhtml?error=true");
        } else if (result == AuthorizationResult.PASSWORD_DOESNT_MATCH) {
            resp.sendRedirect("login.xhtml?password_doesnt_match=true");
        } else if (result == AuthorizationResult.BAD_PASSWORD) {
            resp.sendRedirect("login.xhtml?bad_password=true");
        } else if (result == AuthorizationResult.BAD_EMAIL) {
            resp.sendRedirect("login.xhtml?bad_email=true");
        } else if(result == AuthorizationResult.SUCCESS) {
            resp.sendRedirect("");
        }
    }
}
