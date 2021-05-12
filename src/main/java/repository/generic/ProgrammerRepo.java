package repository.generic;

import domain.Programmer;
import repository.Repository;

public interface ProgrammerRepo extends Repository<Long, Programmer> {

    Programmer FindByAccountID(Long accountID);
}
