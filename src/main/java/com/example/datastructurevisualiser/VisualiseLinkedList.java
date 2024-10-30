package com.example.datastructurevisualiser;

import datastructures.linnear.LinkedList;
import exceptions.UnderflowException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import static com.example.datastructurevisualiser.DataStructureVisualiser.alertError;
import static com.example.datastructurevisualiser.DataStructureVisualiser.getInputFromUser;

public class VisualiseLinkedList {

    private LinkedList<String> linkedList; // Initialize the LinkedList instance
    private HBox listBox = new HBox(5); // HBox for visualizing linked list nodes with reduced spacing
    private VBox centerVBox = new VBox(); // Pane for centering the list box

    public VisualiseLinkedList(String head) {
        linkedList = new LinkedList<>(head);
    }

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

        // Create buttons for linked list operations and going back
        Button appendButton = new Button("Append");
        Button prependButton = new Button("Prepend");
        Button backButton = new Button("Back");

        // Button styles
        styleButton(appendButton);
        styleButton(prependButton);
        styleButton(backButton);

        // Create an HBox for the input field and buttons
        HBox inputBox = new HBox(10);  // Horizontal spacing between components
        inputBox.getChildren().addAll(appendButton, prependButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");  // Center the HBox

        // Add functionality for "Back" button
        backButton.setOnAction(e -> {
            // Go back to the main screen
            primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage));
        });

        // Add functionality for each linked list operation button
        appendButton.setOnAction(_ -> getInputFromUser("Enter data").ifPresent(data -> {
            linkedList.append(data);
            visualizeList(); // Update visualization
        }));

        prependButton.setOnAction(_ -> getInputFromUser("Enter data").ifPresent(data -> {
            linkedList.prepend(data);
            visualizeList(); // Update visualization
        }));

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

        visualizeList();

        // Create the scene with the specified dimensions
        // Set window size to 1270x660

        return new Scene(root, 1270, 660);
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

        linkedList.forEach(current -> {

            // Create rectangle for node representation
            Rectangle rect = new Rectangle(100, 50); // Width and height of rectangle
            rect.setFill(Color.web("#D4BEE4")); // Node color

            // Text for node value
            Text nodeText = new Text(String.valueOf(current));
            nodeText.setFill(Color.web("#3B1E54"));
            nodeText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

            // StackPane to combine rectangle and text
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rect, nodeText);
            tempListBox.getChildren().add(stackPane); // Add node to temporary HBox

            // Draw line between nodes
            if (current.getNext() != null) {
                Line line = new Line(0, 25, 30, 25); // Line positioned vertically between nodes
                line.setStroke(Color.web("#EEEEEE"));
                line.setStrokeWidth(2);
                tempListBox.getChildren().add(line); // Add line after the current node
            }

            stackPane.setOnMouseClicked(e -> {
                if(e.getButton().toString().equals("SECONDARY")) {
                    MenuItem addBefore = new MenuItem("Add before");
                    MenuItem addAfter = new MenuItem("Add after");
                    MenuItem removeNode = new MenuItem("Remove node");
                    addBefore.setOnAction(_ -> {
                        getInputFromUser("Enter data").ifPresent(data -> {
                            linkedList.addBefore(data.trim(), current.getId());
                            visualizeList();
                        });
                    });
                    addAfter.setOnAction(_ -> {
                        getInputFromUser("Enter data").ifPresent(data -> {
                            linkedList.addAfter(data.trim(), current.getId());
                            visualizeList();
                        });
                    });
                    removeNode.setOnAction(_ -> {
                        try {
                            linkedList.remove(current.getId());
                            visualizeList();
                        } catch (UnderflowException ue) {
                            alertError(ue);
                        }
                    });
                    new ContextMenu(
                            addBefore,
                            addAfter,
                            removeNode
                    ).show(rect,e.getScreenX(),e.getSceneY());
                }
            });
        });

        // Add the tempListBox to the centerVBox
        centerVBox.getChildren().add(tempListBox); // This automatically centers it vertically
    }
}
