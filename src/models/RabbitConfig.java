package models;

public class RabbitConfig {

    private String host;
    private String username;
    private String password;

    public RabbitConfig(String host, String username, String password){
        super();
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public String getHost(){
        return this.host;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

}
