package com.example.datastructurevisualiser;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
import datastructures.linnear.LinkedList;

public class VisualiseLinkedList {

    private LinkedList<Integer> linkedList = new LinkedList<>(); // Initialize with a dummy head node
    private VBox centerVBox = new VBox(20); // Use VBox for vertical alignment with spacing
    private Text errorMessage = new Text(); // Text object for error messages

    public Scene createScene(Stage primaryStage) {
        // Title text configuration
        Text title = new Text("Visualise Linked Lists");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        title.setFill(Color.web("#EEEEEE"));

        // Main content layout
        VBox mainVBox = new VBox(20);
        mainVBox.setStyle("-fx-background-color: #3B1E54;");
        mainVBox.setAlignment(Pos.TOP_CENTER); // Align title at top center
        mainVBox.getChildren().add(title);
        mainVBox.getChildren().add(errorMessage); // Add the error message to the main VBox

        // Center VBox for visualization
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setStyle("-fx-pref-height: 400; -fx-pref-width: 800;"); // This might not be needed anymore

        // Input field configuration
        TextField inputField = new TextField();
        inputField.setPromptText("Enter value");
        inputField.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-padding: 10px; -fx-pref-width: 200px;");

        // Button configurations
        Button appendButton = new Button("Append");
        Button prependButton = new Button("Prepend");
        Button removeByValueButton = new Button("Remove By Value");
        Button insertAfterButton = new Button("Insert After");
        Button setNodeValueButton = new Button("Set Node Value");
        Button backButton = new Button("Back");

        styleButton(appendButton);
        styleButton(prependButton);
        styleButton(removeByValueButton);
        styleButton(insertAfterButton);
        styleButton(setNodeValueButton);
        styleButton(backButton);

        // Input field and buttons
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(inputField, appendButton, prependButton, removeByValueButton, insertAfterButton, setNodeValueButton, backButton);
        inputBox.setAlignment(Pos.CENTER); // Centering button group

        // Event handler for "Back" button
        backButton.setOnAction(e -> primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage)));

        // Linked list operation buttons
        appendButton.setOnAction(e -> handleAppend(inputField));
        prependButton.setOnAction(e -> handlePrepend(inputField));
        removeByValueButton.setOnAction(e -> handleRemoveByValue(inputField));
        insertAfterButton.setOnAction(e -> handleInsertAfter(inputField));
        setNodeValueButton.setOnAction(e -> handleSetNodeValue(inputField));

        // Layout setup for StackPane
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #3B1E54;");

        // Create a VBox to hold the main components and ensure proper centering
        VBox contentVBox = new VBox(20);
        contentVBox.setAlignment(Pos.CENTER); // Center the VBox
        contentVBox.getChildren().addAll(mainVBox, centerVBox, inputBox); // Add all components

        root.getChildren().add(contentVBox); // Add contentVBox to the root

        // Make sure the centerVBox is centered
        centerVBox.setAlignment(Pos.CENTER); // Center visualization box

        return new Scene(root, 1270, 660);
    }

    // Event handlers for buttons
    private void handleAppend(TextField inputField) {
        String inputValue = inputField.getText();
        if (!inputValue.isEmpty()) {
            try {
                int value = Integer.parseInt(inputValue);
                linkedList.addLast(value);
                visualizeList();
                inputField.clear();
                clearErrorMessage(); // Clear error message on successful operation
            } catch (NumberFormatException ex) {
                setErrorMessage("Please enter a valid integer.");
            } catch (IllegalArgumentException ex) {
                setErrorMessage("Index Out of Range!"); // Set error message for IllegalArgumentException
            }
        }
    }

    private void handlePrepend(TextField inputField) {
        String inputValue = inputField.getText();
        if (!inputValue.isEmpty()) {
            try {
                int value = Integer.parseInt(inputValue);
                linkedList.addFirst(value);
                visualizeList();
                inputField.clear();
                clearErrorMessage(); // Clear error message on successful operation
            } catch (NumberFormatException ex) {
                setErrorMessage("Please enter a valid integer.");
            } catch (IllegalArgumentException ex) {
                setErrorMessage("Index Out of Range!"); // Set error message for IllegalArgumentException
            }
        }
    }

    private void handleRemoveByValue(TextField inputField) {
        String inputValue = inputField.getText();
        if (!inputValue.isEmpty()) {
            try {
                int value = Integer.parseInt(inputValue);
                linkedList.removeByValue(value);
                visualizeList();
                inputField.clear();
                clearErrorMessage(); // Clear error message on successful operation
            } catch (NumberFormatException ex) {
                setErrorMessage("Please enter a valid integer.");
            } catch (IllegalArgumentException ex) {
                setErrorMessage("Index Out of Range!"); // Set error message for IllegalArgumentException
            }
        }
    }

    private void handleInsertAfter(TextField inputField) {
        String[] values = inputField.getText().split(",");
        if (values.length == 2) {
            try {
                int targetValue = Integer.parseInt(values[0].trim());
                int newValue = Integer.parseInt(values[1].trim());
                LinkedList.Node<Integer> targetNode = linkedList.findNodeByValue(targetValue);
                if (targetNode != null) {
                    linkedList.addNext(newValue, targetNode.getId());
                    visualizeList();
                } else {
                    setErrorMessage("Target value not found.");
                }
            } catch (NumberFormatException ex) {
                setErrorMessage("Please enter valid integers.");
            } catch (IllegalArgumentException ex) {
                setErrorMessage("Index Out of Range!"); // Set error message for IllegalArgumentException
            }
            inputField.clear();
        }
    }

    private void handleSetNodeValue(TextField inputField) {
        String[] values = inputField.getText().split(",");
        if (values.length == 2) {
            try {
                int targetValue = Integer.parseInt(values[0].trim());
                int newValue = Integer.parseInt(values[1].trim());
                LinkedList.Node<Integer> targetNode = linkedList.findNodeByValue(targetValue);
                if (targetNode != null) {
                    targetNode.setData(newValue); // Assuming `setData` exists in Node
                    visualizeList();
                } else {
                    setErrorMessage("Node with value " + targetValue + " not found.");
                }
            } catch (NumberFormatException ex) {
                setErrorMessage("Please enter valid integers.");
            } catch (IllegalArgumentException ex) {
                setErrorMessage("Index Out of Range!"); // Set error message for IllegalArgumentException
            }
            inputField.clear();
        }
    }

    // Button styling
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    // Visualize the linked list
    private void visualizeList() {
        centerVBox.getChildren().clear();
        HBox tempListBox = new HBox(5);
        tempListBox.setAlignment(Pos.CENTER); // Centering the linked list visualization
        for (LinkedList.Node<Integer> node : linkedList) {
            int value = node.getData();
            Rectangle rect = new Rectangle(100, 50);
            rect.setFill(Color.web("#D4BEE4"));
            Text nodeText = new Text(String.valueOf(value));
            nodeText.setFill(Color.web("#3B1E54"));
            nodeText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rect, nodeText);
            tempListBox.getChildren().add(stackPane);

            if (node.getNext() != null) {
                Line line = new Line(0, 25, 30, 25);
                line.setStroke(Color.web("#EEEEEE"));
                line.setStrokeWidth(2);
                tempListBox.getChildren().add(line);
            }
        }
        centerVBox.getChildren().add(tempListBox);
    }

    // Set error message
    private void setErrorMessage(String message) {
        errorMessage.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        errorMessage.setFill(Color.RED);
    }

    // Clear error message
    private void clearErrorMessage() {
        errorMessage.setText(""); // Clear the text for no error
    }
}
