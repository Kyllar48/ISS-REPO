package service;

import domain.*;
import org.jetbrains.annotations.NotNull;
import repository.generic.*;

import java.util.*;
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
        Account account = this.accountRepo.FindByCredentials(username, password);
        if (account == null)
            throw new Exception("No user matching this credentials!");
        else {
            Long accountID = account.getId();
            user = this.verifierRepo.FindByAccountID(accountID);
            if (user == null)
                user = this.programmerRepo.FindByAccountID(accountID);
        }
        return user;
    }

    public Iterable<Code> ViewAllCodes() {
        return this.codeRepo.FindAll();
    }

    public List<Bug> GetProgrammerBugs(Long programmerID) {
        List<Long> codeIDs = new ArrayList<>();
        this.codeRepo.GetProgrammersCodes(programmerID).forEach(c -> codeIDs.add(c.getId()));
        List<Long> bugIDs = new ArrayList<>();
        codeIDs.forEach(cID -> bugIDs.addAll(this.analyzeRepo.GetBugIdsByCodeID(cID)));
        return bugIDs.stream().map(this.bugRepo::Find).collect(Collectors.toList());
    }

    public List<Bug> GetVerifierRegisteredBugs(Long verifierID) {
        return this.analyzeRepo.GetVerifierRegisteredBugIds(verifierID).stream().map(this.bugRepo::Find).collect(Collectors.toList());
    }

    public void RegisterCode(@NotNull String title, @NotNull String desc, Integer noLines, Integer noHours, @NotNull Programmer programmer) throws Exception {
        Code code = this.codeRepo.Store(new Code(title.strip(), desc.strip(), noLines, noHours, programmer.getId()));
        if (code != null)
            throw new Exception("Already registered!");
    }

    public void RegisterBug(@NotNull String name, @NotNull String desc, @NotNull Code code, @NotNull Verifier verifier) throws Exception {
        Bug bug = this.bugRepo.Store(new Bug(name.strip(), desc.strip(), false, new Random().nextInt(100)));
        if (bug != null)
            throw new Exception("Already registered!");
        else {
            Analyze analyze = this.analyzeRepo.Store(new Analyze(verifier.getId(), this.bugRepo.GetMaxID(), code.getId()));
            if (analyze != null)
                throw new Exception("You can't register bugs to this code anymore!");
        }
    }

    public void UpdateBugStatus(@NotNull Bug bug) throws Exception {
        boolean isSolved = new Random().nextInt(100) < bug.getResolvingPercent();
        bug.setIsResolved(isSolved);
        if (!isSolved)
            bug.setResolvingPercent(new Random().nextInt(100 - bug.getResolvingPercent()) + bug.getResolvingPercent());
        Bug returned = this.bugRepo.Update(bug.getId(), bug);
        if (returned != null)
            throw new Exception("No bug matching the given ID!");
    }

    public void RemoveBug(@NotNull Bug bug) throws Exception {
        if (bug.getIsResolved()) {
            Long bugID = bug.getId();
            Bug deleted = this.bugRepo.Remove(bugID);
            if (deleted != null)
                this.analyzeRepo.RemoveByBugID(bugID);
            else throw new Exception("No bug matching the selected bug!");
        }
        else throw new Exception("Bug cannot be removed, not solved yet!");

    }
}
