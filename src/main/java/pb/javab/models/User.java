package pb.javab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.List;

@Entity
@Table(name = "my_user")
public class User extends BaseModel {
    @Email
    private String email;
    private String password;

    private Role role;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CarRental> carRentals;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<CarRental> getCarRentals() {
        return carRentals;
    }

    public void setCarRentals(List<CarRental> carRentals) {
        this.carRentals = carRentals;
    }
}
