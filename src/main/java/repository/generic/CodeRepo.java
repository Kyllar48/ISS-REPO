package repository.generic;

import domain.Code;
import repository.Repository;

public interface CodeRepo extends Repository<Long, Code> {

    Iterable<Code> GetProgrammersCodes(Long programmerID);
}
