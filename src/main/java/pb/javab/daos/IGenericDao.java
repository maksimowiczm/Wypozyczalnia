package pb.javab.daos;

import java.util.List;
import java.util.Optional;

public interface IGenericDao<T> {
    void save(T t);

    void delete(T t);

    void update(T t);

    Optional<T> get(Long id);

    List<T> getAll();
}