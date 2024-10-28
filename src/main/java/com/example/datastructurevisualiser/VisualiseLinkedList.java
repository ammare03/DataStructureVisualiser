package com.example.datastructurevisualiser;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VisualiseLinkedList {

    public Scene createScene(Stage primaryStage) {
        // Create the title text
        Text title = new Text("Visualise Linked Lists");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));  // Title font settings
        title.setFill(Color.web("#EEEEEE"));  // Title text color

        // Create a VBox for the main content
        VBox mainVBox = new VBox(20);  // Vertical spacing
        mainVBox.setStyle("-fx-background-color: #3B1E54; -fx-alignment: center;");  // Background and alignment

        // Add the title to the main VBox
        mainVBox.getChildren().add(title);

        // Create a TextField for user input
        TextField inputField = new TextField();
        inputField.setPromptText("Enter value");  // Placeholder text
        inputField.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-padding: 10px; -fx-pref-width: 200px;");  // Styling for the TextField

        // Create buttons for linked list operations and going back
        Button appendButton = new Button("Append");
        Button prependButton = new Button("Prepend");
        Button removeByValueButton = new Button("Remove By Value");
        Button insertAfterButton = new Button("Insert After");
        Button setNodeValueButton = new Button("Set Node Value");
        Button backButton = new Button("Back");

        // Button styles
        appendButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        prependButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        removeByValueButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        insertAfterButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        setNodeValueButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        backButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");

        // Create an HBox for the input field and buttons
        HBox inputBox = new HBox(10);  // Horizontal spacing between components
        inputBox.getChildren().addAll(inputField, appendButton, prependButton, removeByValueButton, insertAfterButton, setNodeValueButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");  // Center the HBox

        // Add functionality for "Back" button
        backButton.setOnAction(e -> {
            // Go back to the main screen
            primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage));
        });

        // Add functionality for each linked list operation button
        appendButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            System.out.println("Appended: " + inputValue);
            inputField.clear();  // Clear the input field after appending
        });

        prependButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            System.out.println("Prepended: " + inputValue);
            inputField.clear();  // Clear the input field after prepending
        });

        removeByValueButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            System.out.println("Removed by value: " + inputValue);
            inputField.clear();  // Clear the input field after removing
        });

        insertAfterButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            System.out.println("Inserted after: " + inputValue);
            inputField.clear();  // Clear the input field after insertion
        });

        setNodeValueButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            System.out.println("Set node value: " + inputValue);
            inputField.clear();  // Clear the input field after setting value
        });

        // Create a new AnchorPane for the layout
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #3B1E54;");  // Set background color for the AnchorPane

        // Add the main content VBox at the top of the AnchorPane
        AnchorPane.setTopAnchor(mainVBox, 20.0);
        AnchorPane.setLeftAnchor(mainVBox, 0.0);
        AnchorPane.setRightAnchor(mainVBox, 0.0);
        root.getChildren().add(mainVBox);

        // Add the input box at the bottom of the AnchorPane
        AnchorPane.setBottomAnchor(inputBox, 20.0);
        AnchorPane.setLeftAnchor(inputBox, 0.0);
        AnchorPane.setRightAnchor(inputBox, 0.0);
        root.getChildren().add(inputBox);  // Add the input box to the root AnchorPane

        // Create the scene with the specified dimensions
        Scene scene = new Scene(root, 1040, 600); // Set window size to 1040x600

        return scene;
    }
}
