package database;

public class MySqlConfig {

    private String host;
    private String database;
    private String username;
    private String password;

    public MySqlConfig(String host, String database, String username, String password) {
        super();
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
