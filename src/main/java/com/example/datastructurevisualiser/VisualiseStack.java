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

public class VisualiseStack {

    public Scene createScene(Stage primaryStage) {
        // Create the title text
        Text title = new Text("Visualise Stacks");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));  // Title font settings
        title.setFill(Color.web("#EEEEEE"));  // Title text color

        // Create a VBox for the main content
        VBox mainVBox = new VBox(20);  // Vertical spacing
        mainVBox.setStyle("-fx-background-color: #3B1E54; -fx-alignment: center;");  // Background and alignment

        // Add the title to the main VBox
        mainVBox.getChildren().add(title);

        // Create a TextField for user input
        TextField inputField = new TextField();
        inputField.setPromptText("Enter value to push/pop");  // Placeholder text
        inputField.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-padding: 10px; -fx-pref-width: 250px;");  // Styling for the TextField

        // Create buttons for pushing, popping, and going back
        Button pushButton = new Button("Push");
        Button popButton = new Button("Pop");
        Button backButton = new Button("Back");

        // Button styles
        pushButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        popButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        backButton.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");

        // Create an HBox for the input field and buttons
        HBox inputBox = new HBox(10);  // Horizontal spacing between components
        inputBox.getChildren().addAll(inputField, pushButton, popButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");  // Center the HBox

        // Add functionality for "Back" button
        backButton.setOnAction(e -> {
            // Go back to the main screen
            primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage));
        });

        // Add functionality for "Push" button
        pushButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            // Logic to handle the input value, e.g., add it to a stack
            System.out.println("Pushed: " + inputValue);
            inputField.clear();  // Clear the input field after pushing
        });

        // Add functionality for "Pop" button
        popButton.setOnAction(e -> {
            // Logic to handle the pop operation
            System.out.println("Popped an item from the stack.");
            // Implement your stack pop logic here
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
