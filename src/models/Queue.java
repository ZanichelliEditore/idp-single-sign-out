package models;

public class Queue {

    private String applicationName;
    private String name;

    public Queue(String applicationName, String name){
        super();
        this.applicationName = applicationName;
        this.name = name;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getName() {
        return name;
    }

}
