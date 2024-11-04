package com.example.datastructurevisualiser;

import exceptions.OverflowException;
import exceptions.UnderflowException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import datastructures.linnear.Stack;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.datastructurevisualiser.Utilities.*;

public class VisualiseStack {

    private Stack<String> stack; // Initialize the Stack instance
    private VBox stackBox = new VBox(10); // Change back to VBox for vertical alignment
    private TextArea stateTextArea = new TextArea(); // TextArea to display the stack state

    // Buttons for stack operations
    Button pushButton = new Button("Push");
    Button popButton = new Button("Pop");
    Button backButton = new Button("Back");

    public VisualiseStack(int capacity) {
        stack = new Stack<>(capacity);
    }

    public Scene createScene(Stage primaryStage) {
        // Title text
        Text title = new Text("Stack");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        title.setFill(Color.web("#EEEEEE"));

        // Main layout VBox for title
        VBox mainVBox = new VBox(20);
        mainVBox.setStyle("-fx-background-color: #3B1E54; -fx-alignment: center;");
        mainVBox.getChildren().add(title);

        styleButton(pushButton);
        styleButton(popButton);
        styleButton(backButton);

        // Event handler for "Back" button
        backButton.setOnAction(e -> primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage)));

        // Event handler for "Push" button
        pushButton.setOnAction(e -> {
            Utilities.getInputFromUser("Enter data", "-fx-font-family: 'Verdana'; -fx-text-fill: #EEEEEE;")
                    .ifPresent(data -> {
                        try {
                            stack.push(data);
                            visualizeStack(); // Update visualization
                        } catch (OverflowException oe) {
                            alertError(oe);
                        }
                    });
        });

        // Event handler for "Pop" button
        popButton.setOnAction(e -> {
            try {
                stack.pop();
                visualizeStack(); // Update visualization
            } catch (UnderflowException ue) {
                alertError(ue);
            }
        });

        // HBox for input and buttons at the bottom
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(pushButton, popButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");

        // Initialize TextArea for stack state display
        stateTextArea.setEditable(false);
        stateTextArea.setWrapText(true);
        stateTextArea.setStyle("-fx-font-size: 14px; -fx-text-fill: #EEEEEE; -fx-control-inner-background: #3B1E54;");
        stateTextArea.setPrefSize(300, 200); // Set width to half

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

        // Add inputBox to the bottom of the AnchorPane
        AnchorPane.setBottomAnchor(inputBox, 20.0);
        AnchorPane.setLeftAnchor(inputBox, 0.0);
        AnchorPane.setRightAnchor(inputBox, 0.0);
        root.getChildren().add(inputBox);

        // Position the stateTextArea at the bottom right of the AnchorPane
        AnchorPane.setTopAnchor(stateTextArea, 20.0);
        AnchorPane.setRightAnchor(stateTextArea, 20.0);
        root.getChildren().add(stateTextArea); // Add the TextArea to the root AnchorPane

        visualizeStack();

        // Create and return the scene
        return new Scene(root, 1040, 600);
    }

    // Method to style buttons consistently
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    // Visualize the stack by adding rectangles representing stack nodes
    private void visualizeStack() {
        stackBox.getChildren().clear(); // Clear current stack visualization

        AtomicInteger index = new AtomicInteger(0); // Initialize index tracker

        // Iterate over stack values and add visual nodes
        stack.forEach(value -> {
            // Label displaying the index
            Label indexLabel = new Label(String.valueOf(index.getAndIncrement()));
            indexLabel.setFont(Font.font("Verdana", 14));
            indexLabel.setTextFill(Color.web("#EEEEEE"));
            indexLabel.setAlignment(Pos.CENTER_RIGHT);
            indexLabel.setPadding(new Insets(0, 10, 0, 0));

            // Rectangle representing the stack node
            Rectangle rect = new Rectangle(120, 60); // Increase size for better visibility
            rect.setFill(Color.web("#D4BEE4"));
            rect.setStroke(Color.web("#3B1E54"));
            rect.setStrokeWidth(2);

            // Text displaying the value on the node
            Text valueText = new Text(value);
            valueText.setFill(Color.web("#3B1E54"));
            valueText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

            // StackPane to combine rectangle and text
            StackPane stackPane = new StackPane(rect, valueText);

            // HBox to combine index label and stack node
            HBox nodeBox = new HBox(indexLabel, stackPane);
            nodeBox.setAlignment(Pos.CENTER);

            stackBox.getChildren().addFirst(nodeBox); // Add new nodes at the end of VBox
        });

        // Update tooltips for push and pop buttons with stack state
        pushButton.setTooltip(new Tooltip(getState(() -> {
            try {
                return stack.getStateAfterPush("<new>");
            } catch (OverflowException oe) {
                return Map.of(oe.getMessage(), "");
            }
        })));

        popButton.setTooltip(new Tooltip(getState(() -> {
            try {
                return stack.getStateAfterPop();
            } catch (UnderflowException ue) {
                return Map.of(ue.getMessage(), "");
            }
        })));

        // Update stateTextArea with the current state of the stack using getState()
        stateTextArea.setText("State:-\n" + getState(stack));
    }
}
