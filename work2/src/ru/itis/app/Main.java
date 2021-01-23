package ru.itis.app;

import com.beust.jcommander.JCommander;
import ru.itis.utills.App;


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
