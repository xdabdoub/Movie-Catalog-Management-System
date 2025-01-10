package me.yhamarsheh.moviesms.moviesms.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import me.yhamarsheh.moviesms.moviesms.utilities.UIUtils;

public abstract class YazanScreen extends BorderPane {

    protected String title;
    protected String description;

    public YazanScreen(String title, String description, boolean instantSetup) {
        this.title = title;
        this.description = description;

        init(instantSetup);
    }

    private void init(boolean instantSetup) {
        setPadding(new Insets(20, 20, 20, 20));

        VBox header = new VBox(10);
        Label titleLabel = UIUtils.label(title, FontWeight.BOLD, 24, null);
        Label descriptionLabel = UIUtils.label(description, null, 16, null);

        header.getChildren().addAll(titleLabel, descriptionLabel);
        header.setAlignment(Pos.CENTER);


        setTop(header);
        try {
            if (instantSetup) setCenter(setup());
        } catch (NullPointerException ignored) { }
    }

    public abstract Node setup();

}
