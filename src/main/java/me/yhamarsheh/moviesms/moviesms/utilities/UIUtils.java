package me.yhamarsheh.moviesms.moviesms.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class UIUtils {

    public static Label label(String text, FontWeight weight, int size, Color color) {
        if (size == 0) size = 12;
        if (color == null) color = Color.BLACK;
        if (weight == null) weight = FontWeight.NORMAL;

        Label label = new Label(text);
        label.setFont(Font.font("Poppins", weight, FontPosture.REGULAR, size));
        return label;
    }

    public static Alert alert(String context, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(context);
        return alert;
    }
}
