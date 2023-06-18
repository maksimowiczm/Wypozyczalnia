package pb.javab.utils;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import pb.javab.models.User;

@Singleton
public class EmailSender implements IEmailSender {
    public static final String reservationCancelledTimeoutMessage = "Rezerwacja anulowana";
    public static final String reservationCancelledByUserMessage = "Rezerwacja anulowana";
    public static final String reservationPayedMessage = "Rezerwacja anulowana";
    public static final String reservationCreatedMessage = "Rezerwacja anulowana";

    @PostConstruct
    public void init() {
    }

    @Override
    public void SendEmail(User user, String message) {
    }
}
