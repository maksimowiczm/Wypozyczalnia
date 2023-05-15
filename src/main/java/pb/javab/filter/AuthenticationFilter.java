package pb.javab.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;
        var action = req.getServletPath();

        var allowed = List.of("/", "/index.xhtml", "/index",
                "/login", "/login.xhtml",
                "/register", "/register.xhtml");
        for (var i : allowed) {
            if (action.equals(i)) {
                chain.doFilter(request, response);
                return;
            }
        }

        HttpSession session = req.getSession();
        var email = session.getAttribute("email");

        if (email == null) {
            return;
        }

        chain.doFilter(request, response);
    }
}
