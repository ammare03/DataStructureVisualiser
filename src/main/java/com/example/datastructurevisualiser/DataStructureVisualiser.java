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

        Button arrayButton = createStyledButton("Array");
        Button stackButton = createStyledButton("Stack");
        Button queueButton = createStyledButton("Queue");
        Button linkedListButton = createStyledButton("Linked List");
        Button binarySearchTreeButton = createStyledButton("Binary Search Tree");
        Button binaryTreeButton = createStyledButton("Binary Tree");

        final String FONT_STYLE = "-fx-font-family: 'Verdana'; -fx-text-fill: #EEEEEE;";

        arrayButton.setOnAction(_ -> primaryStage.setScene(new VisualiseArray().createScene(primaryStage)));

        stackButton.setOnAction(_ -> {
            try {
                Utilities.getInputFromUser("Enter stack capacity", FONT_STYLE)
                        .ifPresent(capacity -> primaryStage.setScene(new VisualiseStack(Integer.parseInt(capacity)).createScene(primaryStage)));
            } catch (IllegalArgumentException iae) {
                Utilities.alertError(iae);
            }
        });

        queueButton.setOnAction(_ -> {
            try {
                Utilities.getChoiceFromUser("Circular Queue Type", "Select the type of circular queue", FONT_STYLE,
                                VisualiseQueue.Choice.NORMAL_QUEUE, VisualiseQueue.Choice.CIRCULAR_QUEUE)
                        .ifPresent(choice -> Utilities.getInputFromUser("Enter queue capacity", FONT_STYLE)
                                .ifPresent(capacity -> primaryStage.setScene(new VisualiseQueue(Integer.parseInt(capacity), choice).createScene(primaryStage))));
            } catch (IllegalArgumentException iae) {
                Utilities.alertError(iae);
            }
        });

        linkedListButton.setOnAction(_ -> {
            Utilities.getChoiceFromUser("Linked List Type", "Select the type of linked list", FONT_STYLE,
                            VisualiseLinkedList.Choice.SINGLY_LINKED_LIST, VisualiseLinkedList.Choice.DOUBLY_LINKED_LIST)
                    .ifPresent(choice -> Utilities.getInputFromUser("Enter head", FONT_STYLE)
                            .ifPresent(head -> primaryStage.setScene(new VisualiseLinkedList(head, choice).createScene(primaryStage))));
        });

        binarySearchTreeButton.setOnAction(_ -> Utilities.getInputFromUser("Enter root", FONT_STYLE)
                .ifPresent(root -> primaryStage.setScene(new VisualiseBinarySearchTree(root).createScene(primaryStage))));

        binaryTreeButton.setOnAction(_ -> Utilities.getInputFromUser("Enter root", FONT_STYLE)
                .ifPresent(root -> primaryStage.setScene(new VisualiseBinaryTree(root).createScene(primaryStage))));

        HBox hbox = new HBox(20);
        hbox.setStyle("-fx-alignment: center;");
        hbox.getChildren().addAll(arrayButton, stackButton, queueButton, linkedListButton, binarySearchTreeButton, binaryTreeButton);

        vbox.getChildren().add(hbox);

        return new Scene(new StackPane(vbox), 1270, 660);
    }

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
