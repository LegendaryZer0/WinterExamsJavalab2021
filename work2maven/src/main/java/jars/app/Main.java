package jars.app;


import com.beust.jcommander.JCommander;
import jars.utills.App;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        JCommander.newBuilder()
                .addObject(app)
                .build()
                .parse(args);
        app.start();

    }
}
