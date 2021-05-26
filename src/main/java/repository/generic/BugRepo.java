package repository.generic;

import domain.Bug;
import repository.Repository;

public interface BugRepo extends Repository<Long, Bug> {

    Long GetMaxID();
}
