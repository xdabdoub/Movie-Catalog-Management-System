package me.yhamarsheh.moviesms.moviesms.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.yhamarsheh.moviesms.moviesms.MoviesMS;
import me.yhamarsheh.moviesms.moviesms.ui.UIHandler;
import me.yhamarsheh.moviesms.moviesms.ui.screens.YazanScreen;
import me.yhamarsheh.moviesms.moviesms.utilities.UIUtils;

import java.io.File;

public class FileSelectionScreen extends YazanScreen {

    private Stage stage;

    public FileSelectionScreen(Stage stage) {
        super("Wait!", "Prior to starting the application, please make sure to select the appropriate data files", true);

        this.stage = stage;
    }

    @Override
    public Node setup() {
        VBox vBox = new VBox(20);

        VBox moviesBox = new VBox(5);
        Label moviesLabel = UIUtils.label("Select Movies Data File", FontWeight.BOLD, 14, null);

        Button selectMButton = new Button("Click here to select a file");
        selectMButton.setPrefHeight(30);
        selectMButton.setPrefWidth(220);

        selectMButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) return;

            MoviesMS.PRIMARY_MANAGER.getMovieCatalog().loadMoviesFromFile(file);

            selectMButton.setDisable(true);
            selectMButton.setText("Selected: " + file.getName());

            UIHandler.getInstance().open(new HomeScreen(), 500, 500);
        });

        moviesBox.getChildren().addAll(moviesLabel, selectMButton);
        moviesBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(moviesBox);

        return vBox;
    }
}

