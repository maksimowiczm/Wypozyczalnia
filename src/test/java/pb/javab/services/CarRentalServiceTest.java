package pb.javab.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pb.javab.daos.CarRentalDao;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.CarRental;
import pb.javab.models.CarRentalStatus;

import javax.swing.plaf.SpinnerUI;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarRentalServiceTest {
    private final CarRentalService carRentalService;
    private final ICarRentalDao carRentalDao;

    public CarRentalServiceTest() {
        carRentalDao = Mockito.mock(ICarRentalDao.class);
        carRentalService = new CarRentalService();
    }

    @Test
    public void pay_validCarRentalId_shouldCallCarRentalDaoWithUpdatedCarRentalStatusAndReturnTrue() {
        // arrange
        var rental = new CarRental();
        rental.setId(1L);
        rental.setStatus(CarRentalStatus.NOT_PAID);
        when(carRentalDao.getByStatus(CarRentalStatus.NOT_PAID)).thenReturn(List.of(rental));
        carRentalService.init(carRentalDao);

        // act
        var result = carRentalService.pay(1L);

        // assert
        assert result;
        verify(carRentalDao).update(rental);
        assert rental.getStatus() == CarRentalStatus.PAID;
    }

    @Test
    public void pay_invalidCarRentalId_shouldReturnFalse() {
        carRentalService.init(carRentalDao);

        var result = carRentalService.pay(1L);

        assert !result;
    }

    @Test
    public void cancel_validCarRentalId_shouldCallCarRentalDaoWithUpdatedCarRentalStatusAndReturnTrue() {
        // arrange
        var rental = new CarRental();
        rental.setId(1L);
        rental.setStatus(CarRentalStatus.PAID);
        when(carRentalDao.getByStatus(CarRentalStatus.PAID)).thenReturn(List.of(rental));
        carRentalService.init(carRentalDao);

        // act
        var result = carRentalService.cancel(1L);

        // assert
        assert result;
        verify(carRentalDao).update(rental);
        assert rental.getStatus() == CarRentalStatus.CANCELED;
    }

    @Test
    public void cancel_invalidCarRentalId_shouldReturnFalse() {
        carRentalService.init(carRentalDao);

        var result = carRentalService.cancel(1L);

        assert !result;
    }

    @Test
    public void cancelAllCarRentalsThatAreNotPaidAndOlderThan_daoWithCarRentalsThatShouldBeDeleted_deleteCarRentals() {
        // arrange
        var rental = new CarRental();
        rental.setId(1L);
        rental.setStatus(CarRentalStatus.PAID);
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);
        rental.setCreatedAt(calendar.getTime());

        when(carRentalDao.getByStatus(CarRentalStatus.PAID)).thenReturn(List.of(rental));
        when(carRentalDao.getByStatus(CarRentalStatus.NOT_PAID)).thenReturn(List.of(new CarRental()));
        carRentalService.init(carRentalDao);

        // act
        carRentalService.cancelAllCarRentalsThatAreNotPaidAndOlderThan(new Date());

        // assert
        verify(carRentalDao).delete(rental);
    }
}