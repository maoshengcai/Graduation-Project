package dao;

public class GradeDao {
    private String student_id;
    private int project_id;
    private int grade;
    private String project_name;

    public GradeDao(){
    }
    public GradeDao(String project_name,String student_id,int grade){
        this.project_name = project_name;
        this.student_id = student_id;
        this.grade = grade;
        this.project_id = -1;
    }
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    @Override
    public String toString() {
        return "GradeDao{" +
                "student_id='" + student_id + '\'' +
                ", project_id=" + project_id +
                ", grade=" + grade +
                ", project_name='" + project_name + '\'' +
                '}';
    }
}
