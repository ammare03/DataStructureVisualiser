package com.example.datastructurevisualiser;

import datastructures.linnear.DoublyLinkedList;
import datastructures.linnear.LinkedList;
import datastructures.linnear.SinglyLinkedList;
import exceptions.UnderflowException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;

import static com.example.datastructurevisualiser.Utilities.*;

public class VisualiseLinkedList {

    private LinkedList<String> linkedList; // Initialize the LinkedList instance
    private VBox centerVBox = new VBox(); // Pane for centering the list box
    private TextArea stateTextArea = new TextArea(); // TextArea for displaying the linked list state

    public VisualiseLinkedList(String head, Choice variant) {
        linkedList = switch (variant) {
            case SINGLY_LINKED_LIST -> new SinglyLinkedList<>(head);
            case DOUBLY_LINKED_LIST -> new DoublyLinkedList<>(head);
        };
    }

    public Scene createScene(Stage primaryStage) {
        // Create the title text
        Text title = new Text("Linked List");
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
        Button removeHeadButton = new Button("Remove head");
        Button removeTailButton = new Button("Remove tail");
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button backButton = new Button("Back");

        // Button styles
        styleButton(appendButton);
        styleButton(prependButton);
        styleButton(removeHeadButton);
        styleButton(removeTailButton);
        styleButton(addButton);
        styleButton(removeButton);
        styleButton(backButton);

        // Create an HBox for the input field and buttons
        HBox inputBox = new HBox(10);  // Horizontal spacing between components
        inputBox.getChildren().addAll(appendButton, prependButton, removeHeadButton, removeTailButton, addButton, removeButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");  // Center the HBox

        // Add functionality for "Back" button
        backButton.setOnAction(e -> {
            // Go back to the main screen
            primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage));
        });

        // Add functionality for each linked list operation button
        appendButton.setOnAction(_ -> Utilities.getInputFromUser("Enter data").ifPresent(data -> {
            linkedList.append(data);
            visualizeList(); // Update visualization
        }));

        prependButton.setOnAction(_ -> Utilities.getInputFromUser("Enter data").ifPresent(data -> {
            linkedList.prepend(data);
            visualizeList(); // Update visualization
        }));

        removeHeadButton.setOnAction(_ -> {
            try {
                linkedList.removeHead();
                visualizeList();
            } catch (UnderflowException ue) {
                alertError(ue);
            }
        });

        removeTailButton.setOnAction(_ -> {
            try {
                linkedList.removeTail();
                visualizeList();
            } catch (UnderflowException ue) {
                alertError(ue);
            }
        });

        addButton.setOnAction(_ -> {
            // Create the dialog
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Input Index and Data");
            dialog.setHeaderText("Please enter the index and data.");

            // Create the grid pane for input fields
            GridPane grid = new GridPane();
            TextField indexField = new TextField();
            TextField dataField = new TextField();
            grid.add(new Label("Index:"), 0, 0);
            grid.add(indexField, 1, 0);
            grid.add(new Label("Data:"), 0, 1);
            grid.add(dataField, 1, 1);
            grid.setVgap(4);
            grid.setHgap(4);

            // Set the grid pane in the dialog
            dialog.getDialogPane().setContent(grid);

            // Add buttons to the dialog
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Handle the OK button action
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return new Pair<>(indexField.getText(), dataField.getText());
                }
                return null;
            });

            // Show the dialog and wait for the response
            Optional<Pair<String, String>> result = dialog.showAndWait();

            // Process the input if the user clicked OK
            result.ifPresent(pair -> {
                String index = pair.getKey();
                String data = pair.getValue();
                try {
                    linkedList.add(data, Integer.parseInt(index));
                    visualizeList();
                } catch (IllegalArgumentException e) {
                    alertError(e);
                }
            });
        });

        removeButton.setOnAction(_ -> getInputFromUser("Enter index").ifPresent(index -> {
            try {
                linkedList.remove(Integer.parseInt(index));
                visualizeList();
            } catch (UnderflowException | IllegalArgumentException e) {
                alertError(e);
            }
        }));

        // Initialize TextArea for linked list state display
        stateTextArea.setEditable(false);
        stateTextArea.setWrapText(true); // Wrap text for better readability
        stateTextArea.setStyle("-fx-font-size: 14px; -fx-text-fill: #EEEEEE; -fx-control-inner-background: #3B1E54;");
        stateTextArea.setPrefSize(300, 200); // Set fixed size for the TextArea

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

        // Position the stateTextArea at the bottom right of the AnchorPane
        AnchorPane.setTopAnchor(stateTextArea, 20.0);
        AnchorPane.setRightAnchor(stateTextArea, 20.0);
        root.getChildren().add(stateTextArea); // Add the TextArea to the root AnchorPane

        visualizeList();

        // Create the scene with the specified dimensions
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

        linkedList.forEach(node -> {
            // Create rectangle for node representation
            Rectangle rect = new Rectangle(100, 50); // Width and height of rectangle
            rect.setFill(Color.web("#D4BEE4")); // Node color

            // Text for node value
            Text nodeText = new Text(node.getData());
            nodeText.setFill(Color.web("#3B1E54"));
            nodeText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

            // Label for node information
            Label nodeLabel = new Label(String.valueOf(node.hashCode()));
            nodeLabel.setTextFill(Color.web("#EEEEEE"));
            nodeLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));

            // StackPane to combine rectangle and text
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rect, nodeText);

            Tooltip.install(stackPane, new Tooltip(getState(node)));

            // VBox to stack the node and its label
            VBox nodeBox = new VBox(5);
            nodeBox.setAlignment(Pos.CENTER); // Center the stackPane and label
            nodeBox.getChildren().addAll(stackPane, nodeLabel);

            tempListBox.getChildren().add(nodeBox); // Add nodeBox (containing node and label) to HBox

            // Draw line between nodes
            if (node.getNext() != null) {
                Line line = new Line(0, 25, 30, 25); // Line positioned vertically between nodes
                line.setStroke(Color.web("#EEEEEE"));
                line.setStrokeWidth(2);
                tempListBox.getChildren().add(line); // Add line after the current node
            }

            // Context menu for node interaction
            stackPane.setOnMouseClicked(e -> {
                if(e.getButton().toString().equals("SECONDARY")) {
                    MenuItem addBefore = new MenuItem("Add before");
                    MenuItem addAfter = new MenuItem("Add after");
                    MenuItem removeNode = new MenuItem("Remove node");
                    addBefore.setOnAction(_ -> {
                        Utilities.getInputFromUser("Enter data").ifPresent(data -> {
                            linkedList.addBefore(data.trim(), node.getId());
                            visualizeList();
                        });
                    });
                    addAfter.setOnAction(_ -> {
                        Utilities.getInputFromUser("Enter data").ifPresent(data -> {
                            linkedList.addAfter(data.trim(), node.getId());
                            visualizeList();
                        });
                    });
                    removeNode.setOnAction(_ -> {
                        try {
                            linkedList.remove(node.getId());
                            visualizeList();
                        } catch (UnderflowException ue) {
                            Utilities.alertError(ue);
                        }
                    });
                    new ContextMenu(addBefore, addAfter, removeNode).show(rect, e.getScreenX(), e.getSceneY());
                }
            });
        });

        // Update stateTextArea with the current state of the linked list using getState()
        stateTextArea.setText("State:-\n" + getState(linkedList));

        // Add the tempListBox to the centerVBox
        centerVBox.getChildren().add(tempListBox); // This automatically centers it vertically
    }

    public enum Choice {
        SINGLY_LINKED_LIST("Singly Linked List"),
        DOUBLY_LINKED_LIST("Doubly Linked List");

        private final String toString;

        Choice(String toString) {
            this.toString = toString;
        }

        public String toString() {
            return toString;
        }
    }
}
