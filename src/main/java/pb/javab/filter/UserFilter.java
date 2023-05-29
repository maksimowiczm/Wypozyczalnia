package pb.javab.filter;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebFilter;
import pb.javab.beans.UserBean;
import pb.javab.models.Role;

import java.util.List;
import java.util.regex.Pattern;

// TODO filtr działa tylko jak się zalogujesz XD?
@WebFilter("/views/user/*")
public class UserFilter extends AuthorizationFilter {
    @Inject
    public UserFilter(UserBean userBean) {
        super(userBean, List.of(Pattern.compile("user")), Role.USER);
    }
}
