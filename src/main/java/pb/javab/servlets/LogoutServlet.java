package pb.javab.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pb.javab.beans.UserBean;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
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
        userBean.setUser(null);

        resp.sendRedirect("");
    }
}
