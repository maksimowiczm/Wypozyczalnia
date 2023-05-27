package pb.javab.filter;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebFilter;
import pb.javab.beans.UserBean;
import pb.javab.models.Role;

import java.util.List;

@WebFilter("*")
public class GuestFilter extends AuthenticationFilter {
    @Inject
    public GuestFilter(UserBean userBean) {
        super(userBean, List.of("/", "/index.xhtml", "/index", "/login", "/login.xhtml", "/register", "/register.xhtml"), null);
    }
}
