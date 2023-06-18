package pb.javab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pb.javab.beans.UserBean;
import pb.javab.daos.IUserDao;
import pb.javab.daos.UserDao;
import pb.javab.models.User;
import pb.javab.services.UserService;
import pb.javab.utils.AuthorizationResult;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class RegisterLoginTest {
    @Test
    public void registeredUserCanLogin() throws ServletException, IOException {
        // arrange

        // userDao mock
        IUserDao userDao = new UserDao() {
            private User user;

            @Override
            public User getByEmail(String email) {
                return user;
            }

            @Override
            public void save(User user) {
                this.user = user;
            }
        };

        var userService = new UserService();
        userService.init(userDao);

        var request = Mockito.mock(HttpServletRequest.class);
        var response = Mockito.mock(HttpServletResponse.class);
        when(request.getParameter("email")).thenReturn("valid@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("password1")).thenReturn("password");

        // act
        var registerResult = new RegisterServlet(userService, new UserBean()).handleRegister(request, response);
        var loginResult = new LoginServlet(userService, new UserBean()).handleLogin(request, response);

        // assert
        assert registerResult == AuthorizationResult.SUCCESS;
        assert loginResult == AuthorizationResult.SUCCESS;
    }
}
