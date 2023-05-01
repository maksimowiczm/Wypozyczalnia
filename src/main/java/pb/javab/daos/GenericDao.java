package pb.javab.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public abstract class GenericDao<T> implements IGenericDao<T> {

    protected final Class<T> type;
    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    protected GenericDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public void save(T t) {
        em.persist(t);
    }

    @Override
    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public void update(T t) {
        em.merge(t);
    }

    @Override
    public Optional<T> get(Long id) {
        T obj = em.find(type, id);
        return Optional.ofNullable(obj);
    }

    @Override
    public List<T> getAll() {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(type);
        var rootEntry = cq.from(type);
        var all = cq.select(rootEntry);
        var allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
}
