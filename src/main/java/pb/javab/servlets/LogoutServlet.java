package pb.javab.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.javab.beans.UserBean;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserBean userBean;

    @Inject
    public LogoutServlet(UserBean userBean) {
        this.userBean = userBean;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO logout inwalidacją sesji
//        if (req.getSession() != null) {
//            req.getSession().invalidate();
//        }
        log.info("User with email " + userBean.getUser().getEmail() + " logged out successfully.");
        userBean.setUser(null);
        resp.sendRedirect("");
    }
}
