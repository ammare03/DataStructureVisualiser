package com.example.datastructurevisualiser;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
        primaryStage.setScene(createScene(primaryStage));
        primaryStage.setTitle("Data Structure Visualiser");
        primaryStage.show();
    }

    public Scene createScene(Stage primaryStage) {
        Text title = new Text("Data Structure Visualiser");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        title.setFill(Color.web("#EEEEEE"));

        VBox vbox = new VBox(40);
        vbox.setStyle("-fx-background-color: #3B1E54; -fx-alignment: center;");
        vbox.getChildren().add(title);

        // Create descriptions for each data structure
        VBox arrayBox = createButtonWithDescription("Array", "A collection of elements identified by index.", primaryStage, "Array");
        VBox stackBox = createButtonWithDescription("Stack", "A Last In First Out (LIFO) structure.", primaryStage, "Stack");
        VBox queueBox = createButtonWithDescription("Queue", "A First In First Out (FIFO) structure.", primaryStage, "Queue");
        VBox linkedListBox = createButtonWithDescription("Linked List", "A sequence of nodes linked by pointers.", primaryStage, "Linked List");
        VBox bstBox = createButtonWithDescription("Binary Search Tree", "A tree structure with ordered nodes.", primaryStage, "Binary Search Tree");
        VBox btBox = createButtonWithDescription("Binary Tree", "A tree structure with up to two child nodes per parent.", primaryStage, "Binary Tree");

        // Set up a GridPane for arranging the buttons in two columns
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(40);
        gridPane.setVgap(40);

        // Add buttons in a two-column layout
        gridPane.add(arrayBox, 0, 0);
        gridPane.add(stackBox, 1, 0);
        gridPane.add(queueBox, 0, 1);
        gridPane.add(linkedListBox, 1, 1);
        gridPane.add(bstBox, 0, 2);
        gridPane.add(btBox, 1, 2);

        vbox.getChildren().add(gridPane);

        // Footer text
        Text footerText = new Text("Created By Ammar Engineer and Vidit Pawar");
        footerText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        footerText.setFill(Color.web("#CCCCCC"));

        vbox.getChildren().add(footerText); // Add footer to the VBox layout

        return new Scene(new StackPane(vbox), 1270, 660);
    }

    private VBox createButtonWithDescription(String text, String description, Stage primaryStage, String structureType) {
        Button button = createStyledButton(text);

        // Set action events for each button based on type
        button.setOnAction(_ -> {
            switch (structureType) {
                case "Array":
                    primaryStage.setScene(new VisualiseArray().createScene(primaryStage));
                    break;
                case "Stack":
                    Utilities.getInputFromUser("Enter stack capacity")
                            .ifPresent(capacity -> primaryStage.setScene(new VisualiseStack(Integer.parseInt(capacity)).createScene(primaryStage)));
                    break;
                case "Queue":
                    Utilities.getChoiceFromUser("Circular Queue Type", "Select the type of circular queue",
                                    VisualiseQueue.Choice.NORMAL_QUEUE, VisualiseQueue.Choice.CIRCULAR_QUEUE)
                            .ifPresent(choice -> Utilities.getInputFromUser("Enter queue capacity")
                                    .ifPresent(capacity -> primaryStage.setScene(new VisualiseQueue(Integer.parseInt(capacity), choice).createScene(primaryStage))));
                    break;
                case "Linked List":
                    Utilities.getChoiceFromUser("Linked List Type", "Select the type of linked list",
                                    VisualiseLinkedList.Choice.SINGLY_LINKED_LIST, VisualiseLinkedList.Choice.DOUBLY_LINKED_LIST)
                            .ifPresent(choice -> Utilities.getInputFromUser("Enter head")
                                    .ifPresent(head -> primaryStage.setScene(new VisualiseLinkedList(head, choice).createScene(primaryStage))));
                    break;
                case "Binary Search Tree":
                    Utilities.getInputFromUser("Enter root")
                            .ifPresent(root -> {
                                try {
                                    primaryStage.setScene(new VisualiseBinarySearchTree(Integer.parseInt(root)).createScene(primaryStage));
                                } catch (NumberFormatException e) {
                                    new Alert(Alert.AlertType.ERROR, "Please enter an integer").show();
                                }
                            });
                    break;
                case "Binary Tree":
                    Utilities.getInputFromUser("Enter root")
                            .ifPresent(root -> primaryStage.setScene(new VisualiseBinaryTree(root).createScene(primaryStage)));
                    break;
            }
        });

        // Description text below the button
        Text descriptionText = new Text(description);
        descriptionText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        descriptionText.setFill(Color.web("#EEEEEE"));

        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(button, descriptionText);

        return box;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);

        // Style the button
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.15), 4, 0.5, 0, 2);");
        button.setMinWidth(200); // Set a minimum width to align buttons

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
