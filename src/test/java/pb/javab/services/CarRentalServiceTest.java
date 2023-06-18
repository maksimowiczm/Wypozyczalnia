package pb.javab.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pb.javab.daos.ICarRentalDao;
import pb.javab.models.Car;
import pb.javab.models.CarRental;
import pb.javab.models.CarRentalStatus;
import pb.javab.models.User;
import pb.javab.utils.EmailSender;
import pb.javab.utils.IEmailSender;

import java.util.*;

import static org.mockito.Mockito.*;

class CarRentalServiceTest {
    private final CarRentalService carRentalService;
    private final ICarRentalDao carRentalDao;
    private final IEmailSender emailSender;

    public CarRentalServiceTest() {
        carRentalDao = Mockito.mock(ICarRentalDao.class);
        emailSender = Mockito.mock(IEmailSender.class);
        carRentalService = new CarRentalService();
    }

    @Test
    public void pay_validCarRentalId_shouldCallCarRentalDaoWithUpdatedCarRentalStatusAndReturnTrue() {
        // arrange
        var user = new User();
        var rental = new CarRental();
        var uuid = UUID.randomUUID();
        rental.setId(uuid);
        rental.setUser(user);
        rental.setStatus(CarRentalStatus.NOT_PAID);
        when(carRentalDao.getByStatus(CarRentalStatus.NOT_PAID)).thenReturn(List.of(rental));
        carRentalService.init(carRentalDao);
        carRentalService.setEmailSender(emailSender);

        // act
        var result = carRentalService.pay(uuid);

        // assert
        assert result;
        verify(carRentalDao).update(rental);
        verify(emailSender, atMostOnce()).SendEmail(user, EmailSender.reservationPayedMessage);
        assert rental.getStatus() == CarRentalStatus.PAID;
    }

    @Test
    public void pay_invalidCarRentalId_shouldReturnFalse() {
        carRentalService.init(carRentalDao);

        var result = carRentalService.pay(UUID.randomUUID());

        assert !result;
    }

    @Test
    public void cancel_validCarRentalId_shouldCallCarRentalDaoWithUpdatedCarRentalStatusAndReturnTrue() {
        // arrange
        var user = new User();
        var rental = new CarRental();
        var uuid = UUID.randomUUID();
        rental.setUser(user);
        rental.setId(uuid);
        rental.setStatus(CarRentalStatus.NOT_PAID);
        rental.setCar(new Car());
        when(carRentalDao.getByStatus(CarRentalStatus.NOT_PAID)).thenReturn(List.of(rental));
        carRentalService.init(carRentalDao);
        carRentalService.setEmailSender(emailSender);

        // act
        var result = carRentalService.cancel(uuid);

        // assert
        assert result;
        verify(carRentalDao).update(rental);
        verify(emailSender, atMostOnce()).SendEmail(user, EmailSender.reservationCancelledByUserMessage);
        assert rental.getStatus() == CarRentalStatus.CANCELED;
    }

    @Test
    public void cancel_invalidCarRentalId_shouldReturnFalse() {
        when(carRentalDao.getByStatus(CarRentalStatus.NOT_PAID)).thenReturn(new ArrayList<>());
        carRentalService.init(carRentalDao);

        var result = carRentalService.cancel(UUID.randomUUID());

        assert !result;
    }

    @Test
    public void cancelAllCarRentalsThatAreNotPaidAndOlderThan_daoWithCarRentalsThatShouldBeDeleted_deleteCarRentals() {
        // arrange
        var rental = new CarRental();
        var uuid = UUID.randomUUID();
        rental.setId(uuid);
        rental.setStatus(CarRentalStatus.NOT_PAID);
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);
        rental.setCreatedAt(calendar.getTime());

        when(carRentalDao.getByStatus(CarRentalStatus.NOT_PAID)).thenReturn(List.of(rental));
        when(carRentalDao.getByStatus(CarRentalStatus.PAID)).thenReturn(List.of(new CarRental()));
        carRentalService.init(carRentalDao);

        // act
        carRentalService.cancelAllCarRentalsThatAreNotPaidAndOlderThan(new Date());

        // assert
        verify(carRentalDao).delete(rental);
    }
}