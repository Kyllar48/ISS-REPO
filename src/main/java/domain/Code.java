package domain;

public class Code extends Entity<Long>{
    private String title;
    private String description;
    private Integer noLines;
    private Integer hoursWorked;
    private Long programmerID;

    public Code(String title, String description, Integer noLines, Integer hoursWorked, Long programmerID) {
        this.title = title;
        this.description = description;
        this.noLines = noLines;
        this.hoursWorked = hoursWorked;
        this.programmerID = programmerID;
        this.setId(0L);
    }

    public Code(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoLines() {
        return noLines;
    }

    public void setNoLines(Integer noLines) {
        this.noLines = noLines;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Long getProgrammerID() {
        return programmerID;
    }

    public void setProgrammerID(Long programmerID) {
        this.programmerID = programmerID;
    }
}
