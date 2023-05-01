package pb.javab.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "my_user")
public class User extends BaseModel {
    private String email;
    private String password;
    private Role role;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CarRental> carRentals;
}
