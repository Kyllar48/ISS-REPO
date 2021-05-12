package domain;

public class Programmer extends Entity<Long> implements User{
    private Long accountID;
    private String fullname;

    public Programmer(Long accountID, String fullname) {
        this.accountID = accountID;
        this.fullname = fullname;
    }
    public Programmer(){}

    @Override
    public Long getAccountID() {
        return accountID;
    }

    @Override
    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    @Override
    public String getFullname() {
        return fullname;
    }

    @Override
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
