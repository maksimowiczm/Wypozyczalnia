package pb.javab.models;


import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public class BaseModel {
    @Id
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
