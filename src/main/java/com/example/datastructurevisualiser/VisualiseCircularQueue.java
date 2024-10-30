package com.example.datastructurevisualiser;

import datastructures.linnear.CircularQueue;
import datastructures.linnear.Queue;
import exceptions.OverflowException;
import exceptions.UnderflowException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.example.datastructurevisualiser.DataStructureVisualiser.alertError;

public class VisualiseCircularQueue {

    private CircularQueue<String> queue;
    private HBox queueBox = new HBox(10); // HBox for visualizing queue nodes
    private VBox centerQueueBox = new VBox(); // Container to center the queue visualization

    public VisualiseCircularQueue(int capacity) {
        queue = new CircularQueue<>(capacity);
    }

    public Scene createScene(Stage primaryStage) {
        // Create the title text
        Text title = new Text("Visualise Queue");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        title.setFill(Color.web("#EEEEEE"));

        // Create a VBox for the main layout
        VBox mainVBox = new VBox();
        mainVBox.setStyle("-fx-background-color: #3B1E54;"); // Set background color
        mainVBox.setAlignment(Pos.CENTER); // Center the main VBox
        mainVBox.setSpacing(20); // Space between components

        // Add title to the main VBox
        mainVBox.getChildren().add(title);

        // Create a VBox to center the queue visualization
        centerQueueBox.setAlignment(Pos.CENTER); // Center the queue visualization
        centerQueueBox.setStyle("-fx-pref-height: 400;"); // Set preferred height for vertical centering

        // Configure queueBox to center horizontally
        queueBox.setAlignment(Pos.CENTER); // Center the queue nodes
        centerQueueBox.getChildren().add(queueBox); // Add queueBox to the center box
        mainVBox.getChildren().add(centerQueueBox); // Add center box to main VBox

        // TextField for user input
        TextField inputField = new TextField();
        inputField.setPromptText("Enter value to enqueue");
        inputField.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-padding: 10px; -fx-pref-width: 200px;");

        // Buttons for queue operations
        Button enqueueButton = new Button("Enqueue");
        Button dequeueButton = new Button("Dequeue");
        Button backButton = new Button("Back");
        styleButton(enqueueButton);
        styleButton(dequeueButton);
        styleButton(backButton);

        // Event handler for "Back" button
        backButton.setOnAction(e -> primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage)));

        // Event handler for "Enqueue" button
        enqueueButton.setOnAction(e -> {
            String inputValue = inputField.getText().trim();
            if (!inputValue.isEmpty()) {
                try {
                    queue.enqueue(inputValue);
                    visualizeQueue(); // Update visualization
                    inputField.clear();
                } catch (OverflowException oe) {
                    alertError(oe);
                }
            }
        });

        // Event handler for "Dequeue" button
        dequeueButton.setOnAction(e -> {
            try {
                queue.dequeue();
                visualizeQueue();
            } catch (UnderflowException ue) {
                alertError(ue);
            }
        });

        // Input and button layout at the bottom
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(inputField, enqueueButton, dequeueButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");
        mainVBox.getChildren().add(inputBox); // Add the input box to main VBox

        visualizeQueue();

        // Create and return scene with specified dimensions
        return new Scene(mainVBox, 1270, 660); // Set window size to 1270x660
    }

    // Method to style buttons consistently
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    // Visualize the queue by adding rectangles representing queue nodes
    private void visualizeQueue() {
        queueBox.getChildren().clear(); // Clear current queue visualization

        // Iterate over queue values and add visual nodes
        // Get the first node of the queue
        queue.forEach(current -> {

            // Rectangle representing the queue node
            Rectangle rect = new Rectangle(100, 50);
            rect.setFill(Color.web("#D4BEE4"));
            rect.setStroke(Color.web("#3B1E54"));
            rect.setStrokeWidth(2);

            // Text displaying the value on the node
            Text valueText = new Text(current);
            valueText.setFill(Color.web("#3B1E54"));
            valueText.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

            // StackPane to combine rectangle and text
            StackPane stackPane = new StackPane(rect, valueText);
            queueBox.getChildren().add(stackPane); // Add the stack pane to the queue box
        });
    }
}
