package domain;

public class Analyze extends Entity<Long>{
    private Long verifierID;
    private Long bugID;
    private Long codeID;

    public Analyze(Long verifierID, Long bugID, Long codeID) {
        this.verifierID = verifierID;
        this.bugID = bugID;
        this.codeID = codeID;
        this.setId(0L);
    }

    public Analyze(){}

    public Long getVerifierID() {
        return verifierID;
    }

    public void setVerifierID(Long verifierID) {
        this.verifierID = verifierID;
    }

    public Long getBugID() {
        return bugID;
    }

    public void setBugID(Long bugID) {
        this.bugID = bugID;
    }

    public Long getCodeID() {
        return codeID;
    }

    public void setCodeID(Long codeID) {
        this.codeID = codeID;
    }
}
