package repository.generic;

import domain.Verifier;
import repository.Repository;

public interface VerifierRepo extends Repository<Long, Verifier> {

    Verifier FindByAccountID(Long accountID);
}
