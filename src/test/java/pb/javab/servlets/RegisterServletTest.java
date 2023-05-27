package pb.javab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pb.javab.beans.UserBean;
import pb.javab.beans.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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
    public void doPost_validData_callsUserBeanAndRedirectsToIndex() throws Exception {
        // arrange
        when(request.getParameter("email")).thenReturn("valid@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("password1")).thenReturn("password");
        when(userService.registerUser(any())).thenReturn(true);

        // act
        new RegisterServlet(userService, userBean).doPost(request, response);

        // assert
        verify(response, times(1)).sendRedirect("");
        verify(userBean, times(1)).setUser(any());
    }
}