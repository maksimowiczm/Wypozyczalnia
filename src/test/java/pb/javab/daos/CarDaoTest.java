package pb.javab.daos;
import jakarta.persistence.EntityManager;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import pb.javab.daos.CarDao;
import pb.javab.daos.UserDao;
import pb.javab.models.Car;
import pb.javab.models.User;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CarDaoTest {
    @Mock
    private CarDao carDao;

    @Mock
    private UserDao userDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveCarTest() {
        // Arrange
        Car car = new Car();
        car.setId(UUID.randomUUID());

        // Act
        carDao.save(car);

        // Assert
        verify(carDao, times(1)).save(car);
    }

    @Test
    public void deleteCarTest() {
        // Arrange
        Car car = new Car();
        car.setId(UUID.randomUUID());

        // Act
        carDao.delete(car);

        // Assert
        verify(carDao, times(1)).delete(car);
    }

    @Test
    public void updateCarTest() {
        // Arrange
        Car car = new Car();
        car.setId(UUID.randomUUID());

        // Act
        carDao.update(car);

        // Assert
        verify(carDao, times(1)).update(car);
    }

    @Test
    public void getCarTest() {
        // Arrange
        UUID carId = UUID.randomUUID();
        Car car = new Car();
        car.setId(carId);

        when(carDao.get(carId)).thenReturn(Optional.of(car));

        // Act
        Optional<Car> retrievedCar = carDao.get(carId);

        // Assert
        assertTrue(retrievedCar.isPresent());
        assertEquals(carId, retrievedCar.get().getId());
    }

    @Test
    public void getUserByEmailTest() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userDao.getByEmail(email)).thenReturn(user);

        // Act
        User retrievedUser = userDao.getByEmail(email);

        // Assert
        assertEquals(email, retrievedUser.getEmail());
    }

}