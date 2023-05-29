package pb.javab.filter;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebFilter;
import pb.javab.beans.UserBean;
import pb.javab.models.Role;

import java.util.List;
import java.util.regex.Pattern;

@WebFilter("*")
public class GuestFilter extends AuthorizationFilter {
    private static final List<Pattern> patterns = List.of(
            Pattern.compile("^/$"),
            Pattern.compile("^/index"),
            Pattern.compile("^/login"),
            Pattern.compile("^/register"),
            Pattern.compile("^/views/guest"));

    @Inject
    public GuestFilter(UserBean userBean) {
        super(userBean, patterns, Role.GUEST);
    }
}
