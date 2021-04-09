package dao;

import java.util.Properties;

public class FtpDao {
    private String ipAddr;     //ip地址
    private Integer port;      //端口号
    private String userName;   //用户名
    private String pwd;        //密码
    private String path;       //路径：FTP服务器保存目录,如果是根目录则为“/”
    public FtpDao(){

    }
    public FtpDao(Properties properties,boolean b){//b=true,download
        ipAddr = properties.getProperty("ftp.ipAdress");
        port   = Integer.parseInt(properties.getProperty("ftp.port"));
        userName = properties.getProperty("ftp.usename");
        pwd  = properties.getProperty("ftp.password");
        if(b){
            path = properties.getProperty("ftp.ftpDownPath");
        }else{
            path = properties.getProperty("ftp.ftpUpPath");
        }
    }
    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

	@Override
	public String toString() {
		return "FtpDao{" +
				"ipAddr='" + ipAddr + '\'' +
				", port=" + port +
				", userName='" + userName + '\'' +
				", pwd='" + pwd + '\'' +
				", path='" + path + '\'' +
				'}';
	}

}

