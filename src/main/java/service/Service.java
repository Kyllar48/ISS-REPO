package service;

import domain.*;
import repository.generic.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private AccountRepo accountRepo;
    private ProgrammerRepo programmerRepo;
    private VerifierRepo verifierRepo;
    private CodeRepo codeRepo;
    private BugRepo bugRepo;
    private AnalyzeRepo analyzeRepo;

    public Service(AccountRepo accountRepo, ProgrammerRepo programmerRepo, VerifierRepo verifierRepo, CodeRepo codeRepo, BugRepo bugRepo, AnalyzeRepo analyzeRepo) {
        this.accountRepo = accountRepo;
        this.programmerRepo = programmerRepo;
        this.verifierRepo = verifierRepo;
        this.codeRepo = codeRepo;
        this.bugRepo = bugRepo;
        this.analyzeRepo = analyzeRepo;
    }

    public User GetLogger(String username, String password) throws Exception {
        User user = null;
        Account account = this.accountRepo.FindByCredentials(username,password);
        if(account==null)
            throw new Exception("No user matching this credentials!");
        else{
            Long accountID = account.getId();
            user = this.verifierRepo.FindByAccountID(accountID);
            if(user == null)
                user = this.programmerRepo.FindByAccountID(accountID);
        }
        return user;
    }

    public Iterable<Code> ViewAllCodes(){
        return this.codeRepo.FindAll();
    }

    public List<Bug> GetProgrammerBugs(Long programmerID){
        List<Long> codeIDs = new ArrayList<>();
        this.codeRepo.GetProgrammersCodes(programmerID).forEach(c -> codeIDs.add(c.getId()));
        List<Long> bugIDs = new ArrayList<>();
        codeIDs.forEach(cID -> bugIDs.addAll(this.analyzeRepo.GetBugIdsByCodeID(cID)));
        return bugIDs.stream().map(this.bugRepo::Find).collect(Collectors.toList());
    }

    public List<Bug> GetVerifierRegisteredBugs(Long verifierID){
        return this.analyzeRepo.GetVerifierRegisteredBugIds(verifierID).stream().map(this.bugRepo::Find).collect(Collectors.toList());
    }

}
