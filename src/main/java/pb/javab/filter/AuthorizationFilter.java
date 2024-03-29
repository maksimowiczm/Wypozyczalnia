package pb.javab.filter;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pb.javab.beans.UserBean;
import pb.javab.models.Role;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@WebFilter("/views/*")
public class AuthorizationFilter implements Filter {
    private final UserBean userBean;
    private static final List<Pattern> guestUrls = List.of(
            Pattern.compile("^/$"),
            Pattern.compile("^/login"),
            Pattern.compile("^/register"),
            Pattern.compile("^/views/guest")
    );

    private static final List<Pattern> userUrls = List.of(
            Pattern.compile("^/index"),
            Pattern.compile("^/logout"),
            Pattern.compile("^/views/guest"),
            Pattern.compile("^/views/user")
    );

    @Inject
    public AuthorizationFilter(UserBean userBean) {
        this.userBean = userBean;
    }

    private boolean userFilter(String action) {
        return filter(action, userUrls);
    }

    private boolean guestFilter(String action) {
        return filter(action, guestUrls);
    }

    private boolean filter(String action, List<Pattern> urls) {
        for (var allowed : urls) {
            var m = allowed.matcher(action);
            if (m.find()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;
        var action = req.getServletPath();

        var user = userBean.getUser();

        if (user == null) {
            if (guestFilter(action)) {
                chain.doFilter(request, response);
            } else if (userFilter(action)) {
                res.sendRedirect("/Wypozyczalnia-1/login");
            } else {
                res.sendError(404);
            }
            return;
        }

        if (user.getRole() == Role.ADMIN) {
            chain.doFilter(request, response);
            return;
        }

        // Role.User
        if (userFilter(action)) {
            chain.doFilter(request, response);
        } else {
            res.sendError(404);
        }
    }
}
