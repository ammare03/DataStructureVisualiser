package com.example.datastructurevisualiser;

import datastructures.StateFull;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class Utilities {
    public static Optional<String> getInputFromUser(String contentText) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setContentText(contentText);
        if(inputDialog.showAndWait().isPresent()) {
            return Optional.of(inputDialog.getEditor().getText());
        } else {
            return Optional.empty();
        }
    }

    public static void alertError(Exception exception) {
        new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();

    }

    public static String getState(StateFull stateFull) {
        StringBuilder sate = new StringBuilder();
        stateFull.getState().forEach((key, value) -> sate.append(key).append(": ").append(value).append('\n'));
        return sate.toString();
    }
}
