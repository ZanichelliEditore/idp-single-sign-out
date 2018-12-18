package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Envirorment {

    public enum Env {
        LOCAL, PRODUCTION
    }

    private static Envirorment envirorment;

    private Env currentEnv;

    private Map<String,String> map;

    /**
     *
     * @param env
     */
    private Envirorment(Env env){
        super();
        this.map = new HashMap<>();
        this.currentEnv = env;
        this.loadData(env);
    }

    /**
     *
     * @param key
     * @return
     */
    public String get(String key){
        return this.map.get(key);
    }

    /**
     *
     * @return
     */
    public static Envirorment getEnvirorment(){
        return envirorment;
    }

    /**
     *
     * @param env
     */
    public static void init(Env env){

        if(envirorment == null){
            envirorment = new Envirorment(env);
        }
    }

    private void loadData(Env env){
        File file = new File("src/.env" + this.getEnvFileExtension(env));

        try {
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] tokens = line.split("=");

                if(tokens.length != 2){
                    continue;
                }

                this.map.put(tokens[0], tokens[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    private String getEnvFileExtension(Env env){

        switch (env){
            case LOCAL:
                return ".local";
            case PRODUCTION:
                return "";
            default:
                return "";
        }

    }

}
