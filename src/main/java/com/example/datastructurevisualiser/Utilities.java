package com.example.datastructurevisualiser;

import datastructures.StateFull;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.Optional;

public class Utilities {

    private static final String BACKGROUND_STYLE = "-fx-background-color: #3B1E54;";
    private static final String DEFAULT_FONT_STYLE = "-fx-font-family: 'Verdana'; -fx-text-fill: #EEEEEE;";

    public static void styleButton(Button button) {
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    public static Optional<String> getInputFromUser(String contentText) {
        TextInputDialog inputDialog = new TextInputDialog();

        // Create a new Label with the desired text color
        Label contentLabel = new Label(contentText);
        contentLabel.setStyle("-fx-text-fill: #EEEEEE;"); // Set the desired text color

        // Get the editor's TextField
        TextField editor = inputDialog.getEditor();
        editor.setStyle("-fx-text-fill: #3B1E54; -fx-background-color: #EEEEEE;"); // Style the TextField

        // Create an HBox to hold both the label and the input field
        HBox hbox = new HBox(contentLabel, editor);
        hbox.setSpacing(10); // Set spacing between label and input field

        // Get the dialog pane and set the custom content
        DialogPane dialogPane = inputDialog.getDialogPane();
        dialogPane.setContent(hbox); // Set the HBox as the content

        // Style the dialog pane
        styleDialog(dialogPane, "-fx-font-family: 'Verdana'; -fx-text-fill: #EEEEEE;");

        // Style the buttons after the dialog is shown
        Platform.runLater(() -> {
            Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            if (okButton != null) {
                okButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54;");
            }

            Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
            if (cancelButton != null) {
                cancelButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54;");
            }
        });

        // Wait for user input and return the result
        return inputDialog.showAndWait().isPresent() ? Optional.of(editor.getText()) : Optional.empty();
    }

    public static void alertError(Exception exception) {
        // Create a new Label for the error message
        Label errorLabel = new Label(exception.getMessage());
        errorLabel.setStyle("-fx-text-fill: #EEEEEE;"); // Set the desired text color

        // Create a new Alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred:");

        // Set the content of the alert to the error label
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(errorLabel);

        // Style the dialog pane
        styleDialog(dialogPane, DEFAULT_FONT_STYLE);

        // Show the alert
        alert.show();

        // Now that the alert is shown, style the buttons
        Platform.runLater(() -> {
            Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            if (okButton != null) {
                okButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54;");
            }

            Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
            if (cancelButton != null) {
                cancelButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54;");
            }
        });
    }


    public static String getState(StateFull stateFull) {
        StringBuilder state = new StringBuilder();
        stateFull.getState().forEach((key, value) -> state.append(key).append(": ").append(value).append('\n'));
        return state.toString();
    }

    public static <T> Optional<T> getChoiceFromUser(String title, String headerText, T... choices) {
        // Create the ChoiceDialog with the default choice and available choices
        ChoiceDialog<T> dialog = new ChoiceDialog<>(choices[0], Arrays.asList(choices));
        dialog.setTitle(title);

        // Create a new Label for the header text
        Label headerLabel = new Label(headerText);
        headerLabel.setStyle("-fx-text-fill: #EEEEEE;"); // Set the desired text color

        // Create an HBox to hold the label and the dropdown
        HBox hbox = new HBox(10); // 10 is the spacing between the label and dropdown
        hbox.getChildren().add(headerLabel); // Add the header label first

        // Create the ComboBox manually and add the choices
        ComboBox<T> comboBox = new ComboBox<>(FXCollections.observableArrayList(choices));
        comboBox.setValue(choices[0]); // Set the default choice
        hbox.getChildren().add(comboBox); // Add the ComboBox to the HBox

        // Set the custom content for the dialog
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(hbox); // Set the HBox as the content

        // Style the dialog pane
        styleDialog(dialogPane, "-fx-font-family: 'Verdana'; -fx-text-fill: #EEEEEE;");

        // Style the buttons after the dialog is shown
        Platform.runLater(() -> {
            Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            if (okButton != null) {
                okButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54;");
            }

            Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
            if (cancelButton != null) {
                cancelButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54;");
            }
        });

        // Wait for user choice and return the result
        return dialog.showAndWait().map(choice -> comboBox.getValue());
    }



    private static void styleDialog(DialogPane dialogPane, String fontStyle) {
        dialogPane.setStyle(BACKGROUND_STYLE + fontStyle);
    }
}
