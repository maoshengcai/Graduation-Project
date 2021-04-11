package dao;

public class StuInfoDao {
    private String number;
    private String name;
    private String academy;

    public StuInfoDao(String number,String name,String academy){
        this.number = number;
        this.name = name;
        this.academy = academy;
    }
    public StuInfoDao(){

    }

    @Override
    public String toString() {
        return "StuInfoDao{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", academy='" + academy + '\'' +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }
}
