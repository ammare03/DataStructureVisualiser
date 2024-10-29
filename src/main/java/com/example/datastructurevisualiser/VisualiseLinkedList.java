package com.example.datastructurevisualiser;

import com.example.datastructurevisualiser.DataStructureVisualiser;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
import datastructures.LinkedList; // Ensure this is imported correctly

public class VisualiseLinkedList {

    private LinkedList linkedList = new LinkedList(); // Initialize the LinkedList instance
    private HBox listBox = new HBox(5); // HBox for visualizing linked list nodes with reduced spacing
    private VBox centerVBox = new VBox(); // Pane for centering the list box vertically

    public Scene createScene(Stage primaryStage) {
        // Create the title text
        Text title = new Text("Visualise Linked Lists");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));  // Title font settings
        title.setFill(Color.web("#EEEEEE"));  // Title text color

        // Create a VBox for the main content
        VBox mainVBox = new VBox(20);  // Vertical spacing
        mainVBox.setStyle("-fx-background-color: #3B1E54; -fx-alignment: center;");  // Background and alignment

        // Add the title to the main VBox
        mainVBox.getChildren().add(title);

        // Create a TextField for user input
        TextField inputField = new TextField();
        inputField.setPromptText("Enter value");  // Placeholder text
        inputField.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-padding: 10px; -fx-pref-width: 200px;");  // Styling for the TextField

        // Create buttons for linked list operations and going back
        Button appendButton = new Button("Append");
        Button prependButton = new Button("Prepend");
        Button removeByValueButton = new Button("Remove By Value");
        Button insertAfterButton = new Button("Insert After");
        Button setNodeValueButton = new Button("Set Node Value");
        Button backButton = new Button("Back");

        // Button styles
        styleButton(appendButton);
        styleButton(prependButton);
        styleButton(removeByValueButton);
        styleButton(insertAfterButton);
        styleButton(setNodeValueButton);
        styleButton(backButton);

        // Create an HBox for the input field and buttons
        HBox inputBox = new HBox(10);  // Horizontal spacing between components
        inputBox.getChildren().addAll(inputField, appendButton, prependButton, removeByValueButton, insertAfterButton, setNodeValueButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");  // Center the HBox

        // Add functionality for "Back" button
        backButton.setOnAction(e -> {
            // Go back to the main screen
            primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage));
        });

        // Add functionality for each linked list operation button
        appendButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            if (!inputValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(inputValue);
                    linkedList.append(value);
                    visualizeList(); // Update visualization
                    inputField.clear();  // Clear the input field after appending
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a valid integer.");
                }
            }
        });

        prependButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            if (!inputValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(inputValue);
                    linkedList.prepend(value);
                    visualizeList(); // Update visualization
                    inputField.clear();  // Clear the input field after prepending
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a valid integer.");
                }
            }
        });

        removeByValueButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            if (!inputValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(inputValue);
                    linkedList.removeByValue(value);
                    visualizeList(); // Update visualization
                    inputField.clear();  // Clear the input field after removing
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a valid integer.");
                }
            }
        });

        insertAfterButton.setOnAction(e -> {
            String[] values = inputField.getText().split(",");
            if (values.length == 2) {
                try {
                    int targetValue = Integer.parseInt(values[0].trim());
                    int newValue = Integer.parseInt(values[1].trim());
                    linkedList.insertAfter(targetValue, newValue);
                    visualizeList(); // Update visualization
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter valid integers.");
                }
                inputField.clear();  // Clear the input field after insertion
            }
        });

        // Corrected logic for setNodeValueButton
        setNodeValueButton.setOnAction(e -> {
            String[] values = inputField.getText().split(",");
            if (values.length == 2) {
                try {
                    int targetValue = Integer.parseInt(values[0].trim()); // The value to find
                    int newValue = Integer.parseInt(values[1].trim()); // The new value to set
                    boolean result = linkedList.setNodeValue(targetValue, newValue); // Correct usage
                    if (result) {
                        visualizeList(); // Update visualization only if the value is successfully set
                    } else {
                        System.out.println("Node with value " + targetValue + " not found.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter valid integers.");
                }
                inputField.clear();  // Clear the input field after setting value
            }
        });

        // Create a new AnchorPane for the layout
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #3B1E54;");  // Set background color for the AnchorPane

        // Add the main content VBox at the top of the AnchorPane
        AnchorPane.setTopAnchor(mainVBox, 20.0);
        AnchorPane.setLeftAnchor(mainVBox, 0.0);
        AnchorPane.setRightAnchor(mainVBox, 0.0);
        root.getChildren().add(mainVBox);

        // Configure the centerVBox to fill the space and center its content
        centerVBox.setAlignment(Pos.CENTER); // Center the VBox
        centerVBox.setStyle("-fx-pref-height: 400; -fx-pref-width: 800;"); // Set preferred size
        root.getChildren().add(centerVBox);
        AnchorPane.setTopAnchor(centerVBox, 100.0); // Offset for centering
        AnchorPane.setLeftAnchor(centerVBox, 0.0);
        AnchorPane.setRightAnchor(centerVBox, 0.0);
        AnchorPane.setBottomAnchor(centerVBox, 100.0); // Allow vertical centering

        // Add the input box at the bottom of the AnchorPane
        AnchorPane.setBottomAnchor(inputBox, 20.0);
        AnchorPane.setLeftAnchor(inputBox, 0.0);
        AnchorPane.setRightAnchor(inputBox, 0.0);
        root.getChildren().add(inputBox);  // Add the input box to the root AnchorPane

        // Create the scene with the specified dimensions
        Scene scene = new Scene(root, 1270, 660); // Set window size to 1270x660

        return scene;
    }

    // Method to style buttons consistently
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    // Visualize the linked list by adding rectangles representing linked list nodes
    private void visualizeList() {
        centerVBox.getChildren().clear(); // Clear current list visualization

        // Create a new HBox for the linked list nodes
        HBox tempListBox = new HBox(5); // Temporary HBox to hold nodes
        tempListBox.setAlignment(Pos.CENTER); // Center nodes within the HBox
        LinkedList.Node current = linkedList.head; // Access head node of linked list

        while (current != null) {
            int value = current.value;

            // Create rectangle for node representation
            Rectangle rect = new Rectangle(100, 50); // Width and height of rectangle
            rect.setFill(Color.web("#D4BEE4")); // Node color

            // Text for node value
            Text nodeText = new Text(String.valueOf(value));
            nodeText.setFill(Color.web("#3B1E54"));
            nodeText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

            // StackPane to combine rectangle and text
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rect, nodeText);
            tempListBox.getChildren().add(stackPane); // Add node to temporary HBox

            // Draw line between nodes
            if (current.next != null) {
                Line line = new Line(0, 25, 30, 25); // Line positioned vertically between nodes
                line.setStroke(Color.web("#EEEEEE"));
                line.setStrokeWidth(2);
                tempListBox.getChildren().add(line); // Add line after the current node
            }

            current = current.next; // Move to the next node
        }

        // Add the tempListBox to the centerVBox
        centerVBox.getChildren().add(tempListBox); // This automatically centers it vertically
    }
}
