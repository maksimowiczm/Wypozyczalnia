package pb.javab.filter;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebFilter;
import pb.javab.beans.UserBean;
import pb.javab.models.Role;

import java.util.List;
import java.util.regex.Pattern;

@WebFilter("/views/admin/*")
public class AdminFilter extends AuthorizationFilter {
    @Inject
    public AdminFilter(UserBean userBean) {
        super(userBean, List.of(Pattern.compile("admin")), Role.ADMIN);
    }
}
