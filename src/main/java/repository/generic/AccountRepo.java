package repository.generic;

import domain.Account;
import repository.Repository;

public interface AccountRepo extends Repository<Long, Account> {

    Account FindByCredentials(String username, String password);
}
