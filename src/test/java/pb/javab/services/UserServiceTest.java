package pb.javab.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pb.javab.daos.IUserDao;
import org.mockito.MockitoAnnotations;


class UserServiceTest {

    @Mock
    private IUserDao userDao;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthenticateAndAuthorizeUser_WithValidCredentials_ShouldReturnTrue() {

    }
}