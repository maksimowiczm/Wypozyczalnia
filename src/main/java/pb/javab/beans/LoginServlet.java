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

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        var password1 = req.getParameter("password1");

        //TODO walidacja danych
        var user = new User();
        user.setEmail(email);
        user.setPassword(password);

        if (!userService.registerUser(user)) {
            resp.sendRedirect("register.xhtml?error=true");
        }

        handleLogin(req, resp);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");

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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var register = req.getParameter("register");

        if (register == null) {
            handleLogin(req, resp);
        } else {
            handleRegister(req, resp);
        }
    }
}
