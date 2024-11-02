package com.example.datastructurevisualiser;

import datastructures.StateFull;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public static <T> Optional<T> getChoiceFromUser(String title, String headerText, T... choices) {
        ChoiceDialog<T> dialog = new ChoiceDialog<>(choices[0], Arrays.asList(choices));
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText("Choose your option:");
        return dialog.showAndWait();
    }
}
