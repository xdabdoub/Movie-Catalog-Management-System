package me.yhamarsheh.moviesms.moviesms.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.yhamarsheh.moviesms.moviesms.ui.screens.YazanScreen;
import me.yhamarsheh.moviesms.moviesms.ui.screens.sub.FileSelectionScreen;

public class UIHandler extends Application {

    private Stage stage;
    private static UIHandler instance;

    public static void launchApp(String[] args) {
        launch(args);
    }


    public static UIHandler getInstance() {
        return instance;
    }

    @Override
    public void init() {
        instance = this;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        Scene scene = new Scene(new FileSelectionScreen(stage), 800, 500);
        stage.setScene(scene);

        stage.show();
    }

    public void open(YazanScreen yazanScreen, int x, int y) {
        stage.setScene(new Scene(yazanScreen, x, y));
    }
    public Stage getStage() {
        return stage;
    }
}