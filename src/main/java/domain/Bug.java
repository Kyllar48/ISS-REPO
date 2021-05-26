package domain;

public class Bug extends Entity<Long>{
    private String name;
    private String description;
    private Boolean isResolved;
    private Integer resolvingPercent;

    public Bug(String name, String description, Boolean isResolved, Integer resolvingPercent) {
        this.name = name;
        this.description = description;
        this.isResolved = isResolved;
        this.resolvingPercent = resolvingPercent;
        this.setId(0L);
    }

    public Bug(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getResolvingPercent() {
        return resolvingPercent;
    }

    public void setResolvingPercent(Integer resolvingPercent) {
        this.resolvingPercent = resolvingPercent;
    }

    public Boolean getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(Boolean isResolved) {
        this.isResolved = isResolved;
    }
}
