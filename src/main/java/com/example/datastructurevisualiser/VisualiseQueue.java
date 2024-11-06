package com.example.datastructurevisualiser;

import datastructures.linnear.CircularQueue;
import datastructures.linnear.Queue;
import exceptions.OverflowException;
import exceptions.UnderflowException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.datastructurevisualiser.Utilities.*;

public class VisualiseQueue {

    private Queue<String> queue;
    private HBox queueBox = new HBox(10); // HBox for visualizing queue nodes
    private VBox centerQueueBox = new VBox(); // Container to center the queue visualization
    private TextArea stateTextArea = new TextArea(); // TextArea to display the queue state

    // Buttons for queue operations
    Button enqueueButton = new Button("Enqueue");
    Button dequeueButton = new Button("Dequeue");
    Button backButton = new Button("Back");

    public VisualiseQueue(int capacity, Choice variant) {
        queue = switch (variant) {
            case NORMAL_QUEUE -> new Queue<>(capacity);
            case CIRCULAR_QUEUE -> new CircularQueue<>(capacity);
        };
    }

    public Scene createScene(Stage primaryStage) {
        // Create the title text
        Text title = new Text("Visualise Queue");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        title.setFill(Color.web("#EEEEEE"));

        // Create a VBox for the main layout
        VBox mainVBox = new VBox();
        mainVBox.setStyle("-fx-background-color: #3B1E54;");
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(20);

        // Add title to the main VBox
        mainVBox.getChildren().add(title);

        // Center the queue visualization
        centerQueueBox.setAlignment(Pos.CENTER);
        centerQueueBox.setStyle("-fx-pref-height: 400;");
        queueBox.setAlignment(Pos.CENTER);
        centerQueueBox.getChildren().add(queueBox);
        mainVBox.getChildren().add(centerQueueBox);

        styleButton(enqueueButton);
        styleButton(dequeueButton);
        styleButton(backButton);

        // Event handlers for buttons
        backButton.setOnAction(e -> primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage)));
        enqueueButton.setOnAction(e -> {
            getInputFromUser("Enter data").ifPresent(data -> {
                try {
                    queue.enqueue(data.trim());
                    visualizeQueue();
                } catch (OverflowException oe) {
                    alertError(oe);
                }
            });
        });
        dequeueButton.setOnAction(e -> {
            try {
                queue.dequeue();
                visualizeQueue();
            } catch (UnderflowException ue) {
                alertError(ue);
            }
        });

        // HBox for buttons positioned at the bottom of the screen
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(enqueueButton, dequeueButton, backButton);
        inputBox.setAlignment(Pos.CENTER);

        // TextArea to display the queue state
        stateTextArea.setEditable(false);
        stateTextArea.setWrapText(true);
        stateTextArea.setStyle("-fx-font-family: 'Verdana'; -fx-text-fill: #EEEEEE; -fx-font-weight: bold; " +
                "-fx-control-inner-background: #3B1E54; -fx-font-size: 14px;");
        stateTextArea.setPrefSize(300, 200);

        // Main pane setup with AnchorPane for positioning
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #3B1E54;");

        // Add main VBox at the top center
        AnchorPane.setTopAnchor(mainVBox, 20.0);
        AnchorPane.setLeftAnchor(mainVBox, 0.0);
        AnchorPane.setRightAnchor(mainVBox, 0.0);
        root.getChildren().add(mainVBox);

        // Center the queueBox just above the buttons
        AnchorPane.setBottomAnchor(centerQueueBox, 100.0);
        AnchorPane.setLeftAnchor(centerQueueBox, 0.0);
        AnchorPane.setRightAnchor(centerQueueBox, 0.0);
        root.getChildren().add(centerQueueBox);

        // Position inputBox with buttons at the bottom of the screen
        AnchorPane.setBottomAnchor(inputBox, 20.0);
        AnchorPane.setLeftAnchor(inputBox, 0.0);
        AnchorPane.setRightAnchor(inputBox, 0.0);
        root.getChildren().add(inputBox);

        // Position stateTextArea at the top right of the screen
        AnchorPane.setTopAnchor(stateTextArea, 20.0);
        AnchorPane.setRightAnchor(stateTextArea, 20.0);
        root.getChildren().add(stateTextArea);

        visualizeQueue();

        // Create and return scene with specified dimensions
        return new Scene(root, 1270, 660);
    }

    // Method to style buttons consistently
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    // Visualize the queue by adding rectangles representing queue nodes
    private void visualizeQueue() {
        queueBox.getChildren().clear();

        AtomicInteger index = new AtomicInteger(0);

        queue.forEach(current -> {
            Label indexLabel = new Label(String.valueOf(index.getAndIncrement()));
            indexLabel.setFont(Font.font("Verdana", 14));
            indexLabel.setTextFill(Color.WHITE);
            indexLabel.setAlignment(Pos.CENTER);
            indexLabel.setPadding(new Insets(5, 0, 0, 0));

            Rectangle rect = new Rectangle(100, 50);
            rect.setFill(Color.web("#D4BEE4"));
            rect.setStroke(Color.web("#3B1E54"));
            rect.setStrokeWidth(2);

            Text valueText = new Text(current);
            valueText.setFill(Color.web("#3B1E54"));
            valueText.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

            StackPane stackPane = new StackPane(rect, valueText);

            VBox nodeBox = new VBox(stackPane, indexLabel);
            nodeBox.setAlignment(Pos.CENTER);

            queueBox.getChildren().add(nodeBox);
        });

        stateTextArea.setText("State:-\n" + getState(queue));

        enqueueButton.setTooltip(new Tooltip(getState(() -> {
            try {
                return queue.getStateAfterEnqueue("<new>");
            } catch (OverflowException oe) {
                return Map.of(oe.getMessage(), "");
            }
        })));

        dequeueButton.setTooltip(new Tooltip(getState(() -> {
            try {
                return queue.getStateAfterDequeue();
            } catch (UnderflowException ue) {
                return Map.of(ue.getMessage(), "");
            }
        })));
    }

    public enum Choice {
        NORMAL_QUEUE("Normal Queue"),
        CIRCULAR_QUEUE("Circular Queue");

        private final String toString;

        Choice(String toString) {
            this.toString = toString;
        }

        @Override
        public String toString() {
            return toString;
        }
    }
}
