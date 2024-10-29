package com.example.datastructurevisualiser;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VisualiseArray {

    // ArrayList to hold values
    private ArrayList<String> arrayList = new ArrayList<>();
    private HBox visualBox = new HBox(10); // HBox to hold visual elements horizontally

    public Scene createScene(Stage primaryStage) {
        // Create the title text
        Text title = new Text("Visualise Arrays");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));  // Title font settings
        title.setFill(Color.web("#EEEEEE"));  // Title text color

        // Create a VBox for the main content
        VBox mainVBox = new VBox(20);  // Vertical spacing
        mainVBox.setStyle("-fx-background-color: #3B1E54; -fx-alignment: center;");  // Background and alignment

        // Add the title to the main VBox
        mainVBox.getChildren().add(title);

        // Create a TextField for user input
        TextField inputField = new TextField();
        inputField.setPromptText("Enter a value");  // Placeholder text
        inputField.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-padding: 10px; -fx-pref-width: 250px;");  // Styling for the TextField

        // Create buttons for adding, removing, and going back
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button backButton = new Button("Back");
        addButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        removeButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        backButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");

        // Create an HBox for the input field and buttons
        HBox inputBox = new HBox(10);  // Horizontal spacing between components
        inputBox.getChildren().addAll(inputField, addButton, removeButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");  // Center the HBox

        // Add functionality for "Back" button
        backButton.setOnAction(e -> {
            // Go back to the main screen
            primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage));
        });

        // Add functionality for "Add" button
        addButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            if (!inputValue.isEmpty()) {
                arrayList.add(inputValue); // Add to the ArrayList
                inputField.clear();  // Clear the input field after adding
                visualizeArray(); // Update visualization
            }
        });

        // Add functionality for "Remove" button
        removeButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            if (arrayList.remove(inputValue)) { // Remove from the ArrayList
                inputField.clear();  // Clear the input field after removing
                visualizeArray(); // Update visualization
            }
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

        // Add visualization box to the center of the AnchorPane
        AnchorPane.setTopAnchor(visualBox, 150.0); // Adjust for vertical spacing from the title
        AnchorPane.setLeftAnchor(visualBox, 0.0);
        AnchorPane.setRightAnchor(visualBox, 0.0);
        AnchorPane.setBottomAnchor(visualBox, 100.0); // Add bottom anchor for padding
        root.getChildren().add(visualBox); // Add visualization box to the root AnchorPane

        // Center the visualBox
        visualBox.setStyle("-fx-alignment: center; -fx-padding: 20px;"); // Add padding to the visualBox

        // Create the scene with the specified dimensions
        Scene scene = new Scene(root, 1270, 660); // Set window size to 1270x660

        return scene;
    }

    // Method to visualize the current state of the array
    private void visualizeArray() {
        visualBox.getChildren().clear(); // Clear existing visual elements
        for (int i = 0; i < arrayList.size(); i++) {
            String value = arrayList.get(i);

            // Create a rectangle for each value
            Rectangle rectangle = new Rectangle(100, 50); // Size of the rectangle
            rectangle.setFill(Color.web("#D4BEE4")); // Rectangle color
            rectangle.setStroke(Color.web("#3B1E54")); // Border color
            rectangle.setStrokeWidth(2);

            // Create a text element to display the value
            Text valueText = new Text(value);
            valueText.setFill(Color.web("#3B1E54")); // Text color
            valueText.setFont(Font.font("Verdana", FontWeight.BOLD, 20)); // Increased text font size
            valueText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER); // Center alignment

            // Use a StackPane to center the text over the rectangle
            StackPane rectangleStack = new StackPane();
            rectangleStack.getChildren().addAll(rectangle, valueText); // Add rectangle and text to StackPane

            // Create a text element for the index
            Text indexText = new Text(Integer.toString(i));
            indexText.setFill(Color.web("#EEEEEE")); // Index text color
            indexText.setFont(Font.font("Verdana", FontWeight.NORMAL, 14)); // Index font

            // Create a VBox for the rectangle and index
            VBox vBox = new VBox(5); // Spacing between elements
            vBox.setAlignment(Pos.CENTER); // Center alignment
            vBox.getChildren().addAll(rectangleStack, indexText); // Add StackPane and index text to VBox

            // Add the VBox to the visualBox
            visualBox.getChildren().add(vBox);

            // Create and center the arrow between the rectangle and the next rectangle if not the last element
            if (i < arrayList.size() - 1) { // Draw arrow only if there's a next element
                Line arrow = new Line(0, 0, 30, 0); // Adjusted line length
                arrow.setStroke(Color.web("#EEEEEE")); // Arrow color
                arrow.setStrokeWidth(2);

                // Position the arrow StackPane above the rectangle
                StackPane arrowStack = new StackPane();
                arrowStack.setAlignment(Pos.CENTER); // Center alignment for the arrow
                arrowStack.getChildren().add(arrow); // Add the arrow to the StackPane

                // Set the arrow's Y position to be right above the rectangle
                StackPane.setMargin(arrow, new javafx.geometry.Insets(-15, 0, 0, 0)); // Negative margin to position the arrow

                // Add the arrowStack to visualBox
                visualBox.getChildren().add(arrowStack);
            }
        }
    }
}
