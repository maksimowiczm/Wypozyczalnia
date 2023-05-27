package pb.javab.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pb.javab.beans.UserBean;
import pb.javab.models.Role;
import pb.javab.utils.RoleAuthentication;

import java.io.IOException;
import java.util.List;

public abstract class AuthenticationFilter implements Filter {
    private final UserBean userBean;
    private final List<String> allowedEndpoints;
    private final Role allowedRole;

    public AuthenticationFilter(UserBean userBean, List<String> allowedEndpoints, Role allowedRole) {
        this.userBean = userBean;
        this.allowedEndpoints = allowedEndpoints;
        this.allowedRole = allowedRole;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;
        var action = req.getServletPath();

        for (var i : allowedEndpoints) {
            if (action.equals(i)) {
                // Gość
                if (allowedRole == null) {
                    chain.doFilter(request, response);
                    return;
                }
                // Redirect do loginu, jeśli user może tam wejść
                if (allowedRole == Role.USER && userBean.getUser() == null) {
                    res.sendRedirect("login");
                    return;
                }
                // http 404, jeśli user nie może tam wejść
                if (userBean.getUser() == null || RoleAuthentication.authenticate(userBean.getUser().getRole(), allowedRole)) {
                    res.sendError(404);
                    return;
                }

                chain.doFilter(request, response);
                return;
            }
        }

        res.sendError(404);
    }
}
