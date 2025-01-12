package me.yhamarsheh.moviesms.moviesms.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.yhamarsheh.moviesms.moviesms.MoviesMS;
import me.yhamarsheh.moviesms.moviesms.ui.UIHandler;
import me.yhamarsheh.moviesms.moviesms.ui.screens.YazanScreen;
import me.yhamarsheh.moviesms.moviesms.utilities.UIUtils;

import java.io.FileNotFoundException;

public class HomeScreen extends YazanScreen {

    public HomeScreen() {
        super("Movie Catalog Management System",
              "Home Screen",
                true);
    }

    @Override
    public Node setup() {

        VBox vBox = new VBox(20);

        Button viewMovies = new Button("Movies Viewer");
        viewMovies.setPrefHeight(30);
        viewMovies.setPrefWidth(220);

        Button saveToFile = new Button("Save Recent Changes");
        saveToFile.setPrefHeight(30);
        saveToFile.setPrefWidth(220);

        Button exit = new Button("Exit");
        exit.setPrefHeight(30);
        exit.setPrefWidth(220);

        viewMovies.setOnAction(e -> {
            UIHandler.getInstance().open(new MoviesScreen(), 800, 500);
        });

        exit.setOnAction(e -> {
            System.exit(0);
        });

        saveToFile.setOnAction(e -> {
            try {
                MoviesMS.PRIMARY_MANAGER.getMovieCatalog().saveMoviesToFile(MoviesMS.PRIMARY_MANAGER.getFileSystem().getMoviesFile());
            } catch (FileNotFoundException ex) {
                UIUtils.alert("Original files couldn't be found!", Alert.AlertType.ERROR).show();
                return;
            }

            UIUtils.alert("Successfully saved recent changes to the original files selected!", Alert.AlertType.INFORMATION).show();
        });

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(viewMovies, saveToFile, exit);
        return vBox;
    }
}
