package pb.javab.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import pb.javab.beans.UserBean;
import pb.javab.models.Role;
import pb.javab.models.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorizationFilterTest {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final FilterChain chain;
    private final UserBean userBean;

    public AuthorizationFilterTest() {
        this.request = Mockito.mock(HttpServletRequest.class);
        this.response = Mockito.mock(HttpServletResponse.class);
        this.chain = Mockito.mock(FilterChain.class);
        this.userBean = Mockito.mock(UserBean.class);
    }

    @Test
    void doFilter_adminUserRequestsAnySite_callsChainDoFilter() throws ServletException, IOException {
        var user = new User();
        user.setRole(Role.ADMIN);
        when(userBean.getUser()).thenReturn(user);

        when(request.getServletPath()).thenReturn("/admin");
        new AuthorizationFilter(userBean).doFilter(request, response, chain);
        verify(chain, times(1)).doFilter(request, response);

        when(request.getServletPath()).thenReturn("/user");
        new AuthorizationFilter(userBean).doFilter(request, response, chain);
        verify(chain, times(2)).doFilter(request, response);

        when(request.getServletPath()).thenReturn("/test");
        new AuthorizationFilter(userBean).doFilter(request, response, chain);
        verify(chain, times(3)).doFilter(request, response);
    }

    @Test
    public void doFilter_guestRequestsGuestSite_callsChainDoFilter() throws ServletException, IOException {
        when(userBean.getUser()).thenReturn(null);
        when(request.getServletPath()).thenReturn("/login");

        new AuthorizationFilter(userBean).doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void doFilter_guestRequestsUserSite_callsResponseRedirect() throws ServletException, IOException {
        when(userBean.getUser()).thenReturn(null);
        when(request.getServletPath()).thenReturn("/views/user");

        new AuthorizationFilter(userBean).doFilter(request, response, chain);

        verify(response, times(1)).sendRedirect("/login");
    }

    @Test
    public void doFilter_guestRequestsForbiddenSite_callsResponseSendError() throws ServletException, IOException {
        when(userBean.getUser()).thenReturn(null);
        when(request.getServletPath()).thenReturn("/views/admin");

        new AuthorizationFilter(userBean).doFilter(request, response, chain);

        verify(response, times(1)).sendError(404);
    }
}