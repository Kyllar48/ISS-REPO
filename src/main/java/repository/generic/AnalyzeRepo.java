package repository.generic;

import domain.Analyze;
import repository.Repository;

import java.util.Collection;
import java.util.List;
public interface AnalyzeRepo extends Repository<Long, Analyze> {

    Collection<Long> GetBugIdsByCodeID(Long codeID);

    List<Long> GetVerifierRegisteredBugIds(Long verifierID);
}
