package com.example.datastructurevisualiser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        Button arraysButton = createStyledButton("Visualise Arrays");
        Button stacksButton = createStyledButton("Visualise Stacks");
        Button queuesButton = createStyledButton("Visualise Queues");
        Button linkedListsButton = createStyledButton("Visualise Linked Lists");
        Button binarySearchTreesButton = createStyledButton("Visualise Binary Search Trees");
        Button binaryTreesButton = createStyledButton("Visualize Binary Trees");

        // Add action to the buttons to switch scenes
        arraysButton.setOnAction(e -> primaryStage.setScene(new VisualiseArray().createScene(primaryStage)));
        stacksButton.setOnAction(e -> primaryStage.setScene(new VisualiseStack().createScene(primaryStage)));
        queuesButton.setOnAction(e -> primaryStage.setScene(new VisualiseQueue().createScene(primaryStage)));
        linkedListsButton.setOnAction(e -> primaryStage.setScene(new VisualiseLinkedList().createScene(primaryStage)));
        binarySearchTreesButton.setOnAction(e -> primaryStage.setScene(new VisualiseBinarySearchTree().createScene(primaryStage)));
        binaryTreesButton.setOnAction(e -> primaryStage.setScene(new VisualiseBinaryTree().createScene(primaryStage)));

        // Create an HBox to hold the buttons horizontally with spacing
        HBox hbox = new HBox(20);  // Horizontal spacing between buttons
        hbox.setStyle("-fx-alignment: center;");  // Center the HBox within the VBox
        hbox.getChildren().addAll(arraysButton, stacksButton, queuesButton, linkedListsButton, binarySearchTreesButton, binaryTreesButton);

        // Add HBox of buttons to the VBox
        vbox.getChildren().add(hbox);

        // Return the Scene object with the desired dimensions
        return new Scene(new StackPane(vbox), 1270, 660); // Set window size to 1270x660
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
