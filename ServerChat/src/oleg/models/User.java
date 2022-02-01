package oleg.models;

public class User {
    public static User lineToNewObjectUser(String line) {
        String[] arrayUserData = line.split(";");
        User user = new User(arrayUserData[1], arrayUserData[2], arrayUserData[3], Integer.parseInt(arrayUserData[4]));
        return user;
    }

    public User(String login, String password, String ip, int port) {
        this.login = login;
        this.password = password;
        this.ip = ip;
        this.port = port;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private String login;
    private String password;
    private String ip;
    private int port;
}
