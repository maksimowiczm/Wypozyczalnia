package pb.javab.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pb.javab.beans.UserBean;
import pb.javab.services.UserService;
import pb.javab.utils.AuthorizationResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginServletTest {
    @Mock
    private final UserService userService;
    @Mock
    private final UserBean userBean;
    @Mock
    private final HttpServletRequest request;
    @Mock
    private final HttpServletResponse response;

    LoginServletTest() {
        userService = Mockito.mock(UserService.class);
        userBean = Mockito.mock(UserBean.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void handleLogin_validData_callsUserBeanAndReturnsSuccess() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("valid@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(userService.authenticateAndAuthorizeUser(any())).thenReturn(true);

        // act
        var result = new LoginServlet(userService, userBean).handleLogin(request, response);

        // assert
        assert result == AuthorizationResult.SUCCESS;
        verify(userBean, times(1)).setUser(any());
    }

    @Test
    public void handleLogin_invalidData_returnsError() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("valid@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(userService.authenticateAndAuthorizeUser(any())).thenReturn(false);

        // act
        var result = new LoginServlet(userService, userBean).handleLogin(request, response);

        // assert
        assert result == AuthorizationResult.ERROR;
    }

    @Test
    public void handleLogin_invalidEmail_returnsEmailError() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(userService.authenticateAndAuthorizeUser(any())).thenReturn(true);

        // act
        var result = new LoginServlet(userService, userBean).handleLogin(request, response);

        // assert
        assert result == AuthorizationResult.BAD_EMAIL;
    }

    @Test
    public void handleLogin_invalidPassword_returnsBadPassword() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("valid@email.com");
        when(request.getParameter("password")).thenReturn("");
        when(userService.authenticateAndAuthorizeUser(any())).thenReturn(true);

        // act
        var result = new RegisterServlet(userService, userBean).handleRegister(request, response);

        // assert
        assert result == AuthorizationResult.BAD_PASSWORD;
    }
}