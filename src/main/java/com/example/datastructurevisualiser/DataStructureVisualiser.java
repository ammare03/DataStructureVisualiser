package com.example.datastructurevisualiser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class DataStructureVisualiser extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set up the main scene
        primaryStage.setScene(createScene(primaryStage));
        primaryStage.setTitle("Data Structure Visualiser");
        primaryStage.show();
    }

    // Method to create the main scene
    public Scene createScene(Stage primaryStage) {
        // Create the title text with larger font and better font family
        Text title = new Text("Data Structure Visualiser");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));  // Increased font size to 40
        title.setFill(Color.web("#EEEEEE"));  // Set text color

        // Create a VBox to hold the title and buttons
        VBox vbox = new VBox(40);  // Increased spacing for title and button row
        vbox.setStyle("-fx-background-color: #3B1E54; -fx-alignment: center;"); // Background and centering
        vbox.getChildren().add(title);

        // Create buttons for each data structure
        Button arrayButton = createStyledButton("Array");
        Button stackButton = createStyledButton("Stack");
        Button queueButton = createStyledButton("Queue");
        Button circularQueueButton = createStyledButton("Circular Queue");
        Button linkedListButton = createStyledButton("Linked List");
        Button binarySearchTreeButton = createStyledButton("Binary Search Tree");
        Button binaryTreeButton = createStyledButton("Binary Tree");

        // Add action to the buttons to switch scenes
        arrayButton.setOnAction(_ -> primaryStage.setScene(new VisualiseArray().createScene(primaryStage)));
        stackButton.setOnAction(_ -> {
            try {
                getInputFromUser("Enter stack capacity").ifPresent(capacity -> primaryStage.setScene(new VisualiseStack(Integer.parseInt(capacity)).createScene(primaryStage)));
            } catch (IllegalArgumentException iae) {
                alertError(iae);
            }
        });
        queueButton.setOnAction(_ -> {
            try {
                getInputFromUser("Enter queue capacity").ifPresent(capacity -> primaryStage.setScene(new VisualiseQueue(Integer.parseInt(capacity)).createScene(primaryStage)));
            } catch (IllegalArgumentException iae) {
                alertError(iae);
            }
        });
        circularQueueButton.setOnAction(_ -> {
            try {
                getInputFromUser("Enter circular queue capacity").ifPresent(capacity -> primaryStage.setScene(new VisualiseCircularQueue(Integer.parseInt(capacity)).createScene(primaryStage)));
            } catch (IllegalArgumentException iae) {
                alertError(iae);
            }
        });
        linkedListButton.setOnAction(_ -> getInputFromUser("Enter head").ifPresent(head -> primaryStage.setScene(new VisualiseLinkedList(head).createScene(primaryStage))));
        binarySearchTreeButton.setOnAction(_ -> primaryStage.setScene(new VisualiseBinarySearchTree().createScene(primaryStage)));
        binaryTreeButton.setOnAction(_ -> getInputFromUser("Enter root").ifPresent(root -> primaryStage.setScene(new VisualiseBinaryTree(root).createScene(primaryStage))));

        // Create an HBox to hold the buttons horizontally with spacing
        HBox hbox = new HBox(20);  // Horizontal spacing between buttons
        hbox.setStyle("-fx-alignment: center;");  // Center the HBox within the VBox
        hbox.getChildren().addAll(arrayButton, stackButton, queueButton, circularQueueButton, linkedListButton, binarySearchTreeButton, binaryTreeButton);

        // Add HBox of buttons to the VBox
        vbox.getChildren().add(hbox);

        // Return the Scene object with the desired dimensions
        return new Scene(new StackPane(vbox), 1270, 660); // Set window size to 1270x660
    }

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

    // Helper method to create styled buttons
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
