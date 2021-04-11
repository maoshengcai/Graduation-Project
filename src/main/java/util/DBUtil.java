package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    static String ip = "127.0.0.1";
    static int port = 3307;
    static String database = "gradeManagement";
    static String encoding = "UTF-8";
    static String loginName = "root";
    static String password = "123456";

    public DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=%s&serverTimezone=UTC", ip, port, database, encoding);
        return DriverManager.getConnection(url, loginName, password);
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException var1) {
            var1.printStackTrace();
        }

    }
    public static void main(String[] args){
        try {
            getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
