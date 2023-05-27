package pb.javab.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pb.javab.beans.UserBean;
import pb.javab.beans.UserService;
import pb.javab.utils.AuthorizationResult;

import static org.mockito.Mockito.*;

class RegisterServletTest {
    @Mock
    private final UserService userService;
    @Mock
    private final UserBean userBean;
    @Mock
    private final HttpServletRequest request;
    @Mock
    private final HttpServletResponse response;

    RegisterServletTest() {
        userService = Mockito.mock(UserService.class);
        userBean = Mockito.mock(UserBean.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void handleRegister_validData_callsUserBeanAndReturnsSuccess() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("valid@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("password1")).thenReturn("password");
        when(userService.registerUser(any())).thenReturn(true);

        // act
        var result = new RegisterServlet(userService, userBean).handleRegister(request, response);

        // assert
        assert result == AuthorizationResult.SUCCESS;
        verify(userBean, times(1)).setUser(any());
    }

    @Test
    public void handleRegister_invalidEmail_returnsEmailError() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("password1")).thenReturn("password");
        when(userService.registerUser(any())).thenReturn(true);

        // act
        var result = new RegisterServlet(userService, userBean).handleRegister(request, response);

        // assert
        assert result == AuthorizationResult.BAD_EMAIL;
    }

    @Test
    public void handleRegister_notMatchingPasswords_returnsEmailError() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("valid@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("password1")).thenReturn("password1");
        when(userService.registerUser(any())).thenReturn(true);

        // act
        var result = new RegisterServlet(userService, userBean).handleRegister(request, response);

        // assert
        assert result == AuthorizationResult.PASSWORD_DOESNT_MATCH;
    }

    @Test
    public void handleRegister_invalidPassword_returnsBadPassword() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("valid@email.com");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("password1")).thenReturn("");
        when(userService.registerUser(any())).thenReturn(true);

        // act
        var result = new RegisterServlet(userService, userBean).handleRegister(request, response);

        // assert
        assert result == AuthorizationResult.BAD_PASSWORD;
    }

    @Test
    public void handleRegister_UserServiceReturnFalse_returnsError() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("valid@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("password1")).thenReturn("password");
        when(userService.registerUser(any())).thenReturn(false);

        // act
        var result = new RegisterServlet(userService, userBean).handleRegister(request, response);

        // assert
        assert result == AuthorizationResult.ERROR;
    }
}