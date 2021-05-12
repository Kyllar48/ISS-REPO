package repository;

import domain.Entity;

public interface Repository<ID,E extends Entity<ID>> {

    E Store(E e);
    E Remove(ID id);
    E Update(ID id,E newer);
    E Find(ID id);
    Iterable<E> FindAll();
    Long Count();
}
