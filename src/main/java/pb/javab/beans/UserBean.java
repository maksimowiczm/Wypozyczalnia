package pb.javab.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import pb.javab.models.User;

import java.io.Serializable;


@Named
@SessionScoped
public class UserBean implements Serializable {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
