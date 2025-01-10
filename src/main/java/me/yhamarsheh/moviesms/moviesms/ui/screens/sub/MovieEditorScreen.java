package me.yhamarsheh.moviesms.moviesms.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import me.yhamarsheh.moviesms.moviesms.MoviesMS;
import me.yhamarsheh.moviesms.moviesms.objects.Movie;
import me.yhamarsheh.moviesms.moviesms.ui.UIHandler;
import me.yhamarsheh.moviesms.moviesms.ui.screens.YazanScreen;
import me.yhamarsheh.moviesms.moviesms.utilities.GeneralUtils;
import me.yhamarsheh.moviesms.moviesms.utilities.UIUtils;

import java.time.LocalDate;
import java.util.Optional;

public class MovieEditorScreen extends YazanScreen {

    private Movie currentMovie;
    public MovieEditorScreen(Movie currentMovie) {
        super("Movie Catalog Management System", "Movie Editor", false);
        this.currentMovie = currentMovie;

        setCenter(setup());
    }

    @Override
    public Node setup() {
        VBox vBox = new VBox(20);
        GridPane gp = new GridPane();

        Label titleL = UIUtils.label("Title", FontWeight.BOLD, 12, null);
        TextField titleTF = new TextField(currentMovie == null ? "" : currentMovie.getTitle());
        titleTF.setPrefHeight(20);
        titleTF.setPrefWidth(120);

        Label description = UIUtils.label("Description", FontWeight.BOLD, 12, null);
        TextArea descriptionTF = new TextArea(currentMovie == null ? "" : currentMovie.getDescription());
        descriptionTF.setPrefHeight(20);
        descriptionTF.setPrefWidth(120);
        descriptionTF.setWrapText(true);

        Label year = UIUtils.label("Release Year", FontWeight.BOLD, 12, null);
        DatePicker releaseYear = new DatePicker();
        releaseYear.setValue(currentMovie != null ? LocalDate.of(currentMovie.getYear(), 1, 1) : LocalDate.now());

        Label rating = UIUtils.label("Rating", FontWeight.BOLD, 12, null);
        TextField ratingTF = new TextField(currentMovie == null ? "" : currentMovie.getRating() + "");
        ratingTF.setPrefHeight(20);
        ratingTF.setPrefWidth(120);

        gp.setHgap(20);
        gp.setVgap(20);

        gp.setAlignment(Pos.CENTER);

        gp.add(titleL, 0, 0);
        gp.add(titleTF, 0, 1);

        gp.add(description, 0, 2);
        gp.add(descriptionTF, 0, 3);

        gp.add(year, 1, 0);
        gp.add(releaseYear, 1, 1);

        gp.add(rating, 1, 2);
        gp.add(ratingTF, 1, 3);

        HBox actions = new HBox(10);
        Button insert = new Button("Insert");
        insert.setPrefHeight(20);
        insert.setPrefWidth(120);

        insert.setDisable(currentMovie != null);

        insert.setOnAction(e -> {
            if (!allFilledAndCorrect(titleTF, descriptionTF, ratingTF)) {
                UIUtils.alert("One or more of the fields contained invalid values", Alert.AlertType.ERROR).show();
                return;
            }

            Movie movie = new Movie(titleTF.getText(), descriptionTF.getText(), releaseYear.getValue().getYear(), Double.parseDouble(ratingTF.getText()));
            if (GeneralUtils.movieExists(movie)) {
                UIUtils.alert("The entered movie title already exists!", Alert.AlertType.ERROR).show();
                return;
            }

            MoviesMS.PRIMARY_MANAGER.getMovieCatalog().put(movie);

            UIUtils.alert("Success!", Alert.AlertType.INFORMATION).show();

            titleTF.clear();
            descriptionTF.clear();
            ratingTF.clear();
            releaseYear.setValue(LocalDate.now());
        });

        Button update = new Button("Update");
        update.setPrefHeight(20);
        update.setPrefWidth(120);

        update.setDisable(currentMovie == null);

        update.setOnAction(e -> {
            if (!allFilledAndCorrect(titleTF, descriptionTF, ratingTF)) {
                UIUtils.alert("One or more of the fields contained invalid values", Alert.AlertType.ERROR).show();
                return;
            }

            Optional<ButtonType> confirmation = UIUtils.alert("Are you sure you'd like to update these values?", Alert.AlertType.CONFIRMATION).showAndWait();

            if (confirmation.isEmpty()) return;
            if (confirmation.get() == ButtonType.CANCEL) return;

            if (currentMovie == null) return;
            String newTitle = titleTF.getText();
            String newDescription = descriptionTF.getText();
            int newYear = releaseYear.getValue().getYear();
            double newRating = Double.parseDouble(ratingTF.getText());

            if (!newTitle.equalsIgnoreCase(currentMovie.getTitle()) && GeneralUtils.getMovieByTitle(newTitle) != null) {
                UIUtils.alert("Unable to create a movie with this title because another movies has this title.", Alert.AlertType.ERROR).show();
                return;
            }

            currentMovie.setTitle(newTitle);
            currentMovie.setDescription(newDescription);
            currentMovie.setYear(newYear);
            currentMovie.setRating(newRating);

            UIUtils.alert("Success!", Alert.AlertType.INFORMATION).show();

            titleTF.clear();
            descriptionTF.clear();
            ratingTF.clear();
            releaseYear.setValue(LocalDate.now());
        });

        Button back = new Button("Back");
        back.setPrefHeight(20);
        back.setPrefWidth(120);

        back.setOnAction(e -> {
            UIHandler.getInstance().open(new MoviesScreen(), 800, 500);
        });

        actions.getChildren().addAll(insert, update, back);
        actions.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(gp, actions);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private boolean allFilledAndCorrect(TextField title, TextArea description, TextField rating) {
        if (title.getText().isEmpty() || description.getText().isEmpty() || rating.getText().isEmpty())
            return false;

        try {
            double ratingInt = Double.parseDouble(rating.getText());
            if (ratingInt < 0) return false;
        } catch (NumberFormatException ex) { return false; }

        return true;
    }
}