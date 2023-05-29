package pb.javab.beans;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pb.javab.daos.ICarDao;
import pb.javab.models.Car;
import pb.javab.models.CarStatus;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarBeanTest {
    private final CarBean carBean;
    @Mock
    private ICarDao carDao;

    CarBeanTest() {
        carDao = Mockito.mock(ICarDao.class);
        carBean = new CarBean(carDao);
    }

    @Test
    public void persist_whenCarIsNull_doesntCallDao() {
        // act
        carBean.persist();

        // assert
        Mockito.verify(carDao, never()).update(any());
        Mockito.verify(carDao, never()).save(any());
    }

    @Test
    public void persist_whenCarIsValidAndIdIsNull_callsDaoSave() {
        // arrange
        var car = new Car();
        carBean.setCar(car);

        // act
        carBean.persist();

        // assert
        Mockito.verify(carDao, times(1)).save(any(Car.class));
    }

    @Test
    public void persist_whenCarIsValidAndIdIsNotNull_callsDaoUpdate() {
        // arrange
        var car = new Car();
        car.setId(1L);
        carBean.setCar(car);

        // act
        carBean.persist();

        // assert
        Mockito.verify(carDao, times(1)).update(any(Car.class));
    }

    @Test
    public void persist_whenCarsRentalsIsNullAndStatusIsNull_setsDefaultValue() {
        // arrange
        var car = new Car();
        carBean.setCar(car);

        // act
        carBean.persist();

        // assert
        assert car.getCarRentals() != null;
        assert car.getStatus() != null;
    }

    @Test
    public void persist_whenCarsRentalsIsValid_doesntOverrideValue() {
        // arrange
        var car = Mockito.mock(Car.class);
        when(car.getCarRentals()).thenReturn(new ArrayList<>());
        carBean.setCar(car);

        // act
        carBean.persist();

        // assert
        verify(car, never()).setCarRentals(any());
    }

    @Test
    public void persist_whenCarStatusIsValid_doesntOverrideValue() {
        // arrange
        var car = Mockito.mock(Car.class);
        when(car.getStatus()).thenReturn(CarStatus.AVAILABLE);
        carBean.setCar(car);

        // act
        carBean.persist();

        // assert
        verify(car, never()).setStatus(any());
    }

    @Test
    public void delete_whenCarIsNull_doesntCallDaoDelete() {
        // arrange
        carBean.setCar(null);

        // act
        carBean.delete();

        // assert
        verify(carDao, never()).delete(any());
    }

    @Test
    public void delete_whenCarIsNotNull_CallsDaoDelete() {
        // arrange
        var car = Mockito.mock(Car.class);
        carBean.setCar(car);

        // act
        carBean.delete();

        // assert
        verify(carDao, times(1)).delete(car);
    }

    @Test
    public void delete_whenExistingCarIdGivenAsParameter_CallsDaoDelete() {
        // arrange
        var car = Mockito.mock(Car.class);
        car.setId(3L);
        when(carDao.get(3L)).thenReturn(Optional.of(car));

        // act
        carBean.delete(3);

        // assert
        verify(carDao, times(1)).delete(car);
    }

    @Test
    public void delete_whenNonExistingCarIdGivenAsParameter_doesntCallDaoDelete() {
        // arrange
        when(carDao.get(3L)).thenReturn(Optional.empty());

        // act
        carBean.delete(3);

        // assert
        verify(carDao, times(0)).delete(any());
    }
}