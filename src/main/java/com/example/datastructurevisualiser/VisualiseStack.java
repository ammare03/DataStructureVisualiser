package com.example.datastructurevisualiser;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox; // Keep VBox for vertical alignment
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import datastructures.Stack;

public class VisualiseStack {

    private Stack stack = new Stack(); // Initialize the Stack instance
    private VBox stackBox = new VBox(10); // VBox for vertical alignment

    public Scene createScene(Stage primaryStage) {
        // Title text
        Text title = new Text("Visualise Stack");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        title.setFill(Color.web("#EEEEEE"));

        // Main layout VBox for title
        VBox mainVBox = new VBox(20);
        mainVBox.setStyle("-fx-background-color: #3B1E54; -fx-alignment: center;");
        mainVBox.getChildren().add(title);

        // TextField for user input
        TextField inputField = new TextField();
        inputField.setPromptText("Enter a value");
        inputField.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-padding: 10px; -fx-pref-width: 200px;");

        // Buttons for stack operations
        Button pushButton = new Button("Push");
        Button popButton = new Button("Pop");
        Button backButton = new Button("Back");
        styleButton(pushButton);
        styleButton(popButton);
        styleButton(backButton);

        // Event handler for "Back" button
        backButton.setOnAction(e -> primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage)));

        // Event handler for "Push" button
        pushButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            if (!inputValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(inputValue);
                    stack.push(value);
                    visualizeStack(); // Update visualization
                    inputField.clear();
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a valid integer.");
                }
            }
        });

        // Event handler for "Pop" button
        popButton.setOnAction(e -> {
            if (!stack.isEmpty()) { // Check if the stack is not empty before popping
                stack.pop();
                visualizeStack(); // Update visualization
            } else {
                System.out.println("Stack is empty. Cannot pop.");
            }
        });

        // HBox for input and buttons at the bottom
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(inputField, pushButton, popButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");

        // Main pane setup
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #3B1E54;");

        // Add mainVBox (title) to top of the AnchorPane
        AnchorPane.setTopAnchor(mainVBox, 20.0);
        AnchorPane.setLeftAnchor(mainVBox, 0.0);
        AnchorPane.setRightAnchor(mainVBox, 0.0);
        root.getChildren().add(mainVBox);

        // Center stackBox vertically and horizontally
        AnchorPane.setBottomAnchor(stackBox, 100.0); // Positioned slightly above the input buttons
        AnchorPane.setLeftAnchor(stackBox, 0.0); // Align stackBox to the left
        AnchorPane.setRightAnchor(stackBox, 0.0); // Align stackBox to the right
        stackBox.setAlignment(Pos.CENTER); // Align nodes to the center
        root.getChildren().add(stackBox);

        // Add inputBox to bottom of the AnchorPane
        AnchorPane.setBottomAnchor(inputBox, 20.0);
        AnchorPane.setLeftAnchor(inputBox, 0.0);
        AnchorPane.setRightAnchor(inputBox, 0.0);
        root.getChildren().add(inputBox);

        // Create and return scene with specified dimensions
        return new Scene(root, 1270, 660); // Set window size to 1270x660
    }

    // Method to style buttons consistently
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    // Visualize the stack by adding rectangles representing stack nodes
    private void visualizeStack() {
        stackBox.getChildren().clear(); // Clear current stack visualization

        // Iterate over stack values and add visual nodes
        Stack.Node current = stack.getTop(); // Get the top node of the stack
        while (current != null) {
            int value = current.value;

            // Rectangle representing the stack node
            Rectangle rect = new Rectangle(120, 60); // Increase size for better visibility
            rect.setFill(Color.web("#D4BEE4"));
            rect.setStroke(Color.web("#3B1E54"));
            rect.setStrokeWidth(2);

            // Text displaying the value on the node
            Text valueText = new Text(String.valueOf(value));
            valueText.setFill(Color.web("#3B1E54"));
            valueText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

            // StackPane to combine rectangle and text
            StackPane stackPane = new StackPane(rect, valueText);
            stackBox.getChildren().add(stackPane); // Add new nodes at the end of VBox

            // Move to the next node in the stack
            current = current.next;
        }
    }
}
