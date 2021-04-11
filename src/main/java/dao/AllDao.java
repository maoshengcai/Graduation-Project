package dao;

public class AllDao {
    private String student_id;
    private String student_name;
    private String academy;
    private int project_id;
    private String project_name;
    private int grade;

    @Override
    public String toString() {
        return "AllDao{" +
                "student_id='" + student_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", academy='" + academy + '\'' +
                ", project_id=" + project_id +
                ", project_name='" + project_name + '\'' +
                ", grade=" + grade +
                '}';
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
