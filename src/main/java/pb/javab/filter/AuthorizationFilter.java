package pb.javab.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pb.javab.beans.UserBean;
import pb.javab.models.Role;
import pb.javab.utils.RoleAuthentication;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public abstract class AuthorizationFilter implements Filter {
    private final UserBean userBean;
    private final List<Pattern> allowedEndpoints;
    private final Role allowedRole;

    public AuthorizationFilter(UserBean userBean, List<Pattern> allowedEndpoints, Role allowedRole) {
        this.userBean = userBean;
        this.allowedEndpoints = allowedEndpoints;
        this.allowedRole = allowedRole;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;
        var action = req.getServletPath();

        // jeśli rola wyższa niż wymagana przekaż dalej
        var user = userBean.getUser();
        if (user != null && RoleAuthentication.authenticate(user.getRole(), allowedRole)) {
            chain.doFilter(request, response);
            return;
        }

        for (var i : allowedEndpoints) {
            var m = i.matcher(action);
            if (m.find()) {
                // Redirect do loginu, jeśli user może tam wejść
                if (allowedRole == Role.USER && user == null) {
                    // TODO naprawienie redirecta
                    res.sendRedirect("login");
                    return;
                }
                // Na guest endpointy można wejść bez autoryzacji
                if (allowedRole == Role.GUEST) {
                    chain.doFilter(request, response);
                    return;
                }
                // Autoryzacja
                if (user == null || !RoleAuthentication.authenticate(user.getRole(), allowedRole)) {
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
