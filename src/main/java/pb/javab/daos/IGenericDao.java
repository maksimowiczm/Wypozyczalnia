package pb.javab.daos;

import pb.javab.models.BaseModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IGenericDao<T extends BaseModel> {
    void save(T t);

    void delete(T t);

    void update(T t);

    Optional<T> get(UUID id);

    List<T> getAll();
}