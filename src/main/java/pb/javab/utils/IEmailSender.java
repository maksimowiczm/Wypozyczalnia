package pb.javab.utils;

import pb.javab.models.User;

public interface IEmailSender {
    void SendEmail(User user, String message);
}
