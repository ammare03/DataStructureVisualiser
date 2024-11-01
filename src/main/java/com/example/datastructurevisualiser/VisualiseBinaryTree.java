package com.example.datastructurevisualiser;

import datastructures.nonlinnear.BinaryTree;
import datastructures.nonlinnear.BaseTree.Node;
import datastructures.nonlinnear.Traversable;
import exceptions.UnderflowException;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import static com.example.datastructurevisualiser.DataStructureVisualiser.alertError;
import static com.example.datastructurevisualiser.DataStructureVisualiser.getInputFromUser;

public class VisualiseBinaryTree {
    Scene scene;

    private BinaryTree<String> binaryTree;
    private Text traversalResultText = new Text();
    private AnchorPane treePane = new AnchorPane(); // Pane to display the tree structure

    private Button traverseNextButton = new Button("Traverse Next");

    private final Map<UUID, Circle> nodes = new HashMap<>();

    public VisualiseBinaryTree(String root) {
        binaryTree = new BinaryTree<>(root);
    }

    public Scene createScene(Stage primaryStage) {
        // Title text
        Text title = new Text("Visualise Binary Trees");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        title.setFill(Color.web("#EEEEEE"));

        // Configure traversal result text display
        traversalResultText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        traversalResultText.setFill(Color.web("#EEEEEE"));

        // Create buttons for tree operations
        Button inorderButton = new Button("Inorder");
        Button preorderButton = new Button("Preorder");
        Button postorderButton = new Button("Postorder");
        Button backButton = new Button("Back");

        traverseNextButton.setDisable(true);

        // Style buttons
        styleButton(inorderButton);
        styleButton(preorderButton);
        styleButton(postorderButton);
        styleButton(backButton);

        styleButton(traverseNextButton);

        // Set up HBox for input field and buttons
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(inorderButton, preorderButton, postorderButton, traverseNextButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");

        // "Back" button functionality
        backButton.setOnAction(e -> primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage)));

        // Button actions
        inorderButton.setOnAction(e -> initializeTraversal(Traversable.Traversal.INORDER));
        preorderButton.setOnAction(e -> initializeTraversal(Traversable.Traversal.PREORDER));
        postorderButton.setOnAction(e -> initializeTraversal(Traversable.Traversal.POSTORDER));

        // VBox layout to center all components with consistent spacing
        VBox mainVBox = new VBox(10); // Set spacing between elements
        mainVBox.setStyle("-fx-background-color: #3B1E54; -fx-alignment: center; -fx-pref-width: 100%; -fx-pref-height: 600;");

        // Adding elements to main VBox
        mainVBox.getChildren().addAll(title, traversalResultText, treePane, inputBox);

        // AnchorPane for the tree visualization
        treePane.setPrefHeight(400); // Set preferred height for the tree pane

        // AnchorPane root to hold the VBox
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #3B1E54;"); // Set the background color of the root pane
        AnchorPane.setTopAnchor(mainVBox, 20.0); // Maintain 10 spacing at the top from the title
        AnchorPane.setLeftAnchor(mainVBox, 0.0);
        AnchorPane.setRightAnchor(mainVBox, 0.0);
        AnchorPane.setBottomAnchor(mainVBox, 20.0); // Maintain 10 spacing at the bottom from the inputBox
        root.getChildren().add(mainVBox);

        // Set the scene size to 1270x660
        scene = new Scene(root, 1270, 660);

        // Add a listener to adjust the position of the tree when the window is resized
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> visualizeTree(scene));
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> visualizeTree(scene));

        visualizeTree(scene);
        return scene; // Updated window size
    }

    private void initializeTraversal(Traversable.Traversal traversal) {
        nodes.values().forEach(circle -> circle.setStroke(Color.web("#3B1E54")));
        traverseNextButton.setDisable(false);
        traversalResultText.setText(traversal.name() + ':');
        Iterator<Node<String>> i = binaryTree.iterator(traversal);
        traverseNextButton.setOnAction(_ -> {
            if (i.hasNext()) {
                Node<String> node = i.next();
                traversalResultText.setText(traversalResultText.getText() + " " + node);
                nodes.forEach((id, circle) -> {
                    if (id.equals(node.getId())) {
                        circle.setStroke(Color.YELLOW);
                    } else {
                        circle.setStroke(Color.web("#3B1E54"));
                    }
                });
            } else {
                nodes.values().forEach(circle -> circle.setStroke(Color.web("#3B1E54")));
                traverseNextButton.setDisable(true);
            }
        });
    }

    // Method to style buttons
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    // Visualize the binary tree centered horizontally in the window
    private void visualizeTree(Scene scene) {
        treePane.getChildren().clear(); // Clear previous tree display

        // Only visualize if the tree has nodes
        if (binaryTree.getRoot() != null) {
            double treePaneWidth = treePane.getWidth();
            double centerX = (scene.getWidth() - treePaneWidth) / 2; // Use scene width for dynamic centering
            displayTree(binaryTree.getRoot(), centerX + treePaneWidth / 2, 20, 150, binaryTree.getRoot().getId()); // Start visualization at the calculated center position
        }

        // Center the treePane itself within the main VBox
        double sceneWidth = scene.getWidth(); // Get current scene width
        AnchorPane.setLeftAnchor(treePane, (sceneWidth - treePane.getWidth()) / 2);
        AnchorPane.setRightAnchor(treePane, (sceneWidth - treePane.getWidth()) / 2);
    }

    // Recursive method to display the binary tree with accurate positioning and lines
    private void displayTree(Node<String> node, double x, double y, double offset, UUID id) {
        if (node == null) return;

        // Create a Circle and Text for the current node
        Circle circle = new Circle(20, Color.web("#D4BEE4"));
        circle.setStroke(Color.web("#3B1E54"));
        Text text = new Text(String.valueOf(node.getData()));
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        text.setFill(Color.web("#3B1E54"));

        StackPane nodePane = new StackPane(circle, text);
        nodePane.setLayoutX(x - 20); // Position StackPane center at (x, y)
        nodePane.setLayoutY(y - 20);
        treePane.getChildren().add(nodePane); // Add node to the treePane

        // Calculate child node positions and adjust line length
        double childY = y + 80; // Increase vertical spacing between levels

        if (node.getLeft() != null) {
            double leftX = x - offset * 1.25; // Increase spacing by 25%
            Line leftLine = new Line(x, y + 20, leftX, childY - 15); // Line from parent to left child
            leftLine.setStroke(Color.web("#D4BEE4")); // Change line color
            leftLine.setStrokeWidth(2.5); // Set line thickness
            treePane.getChildren().add(leftLine); // Add line to treePane
            displayTree(node.getLeft(), leftX, childY, offset * 0.75, node.getLeft().getId()); // Recursive call for left child with reduced offset
        }

        if (node.getRight() != null) {
            double rightX = x + offset * 1.25; // Increase spacing by 25%
            Line rightLine = new Line(x, y + 20, rightX, childY - 15); // Line from parent to right child
            rightLine.setStroke(Color.web("#D4BEE4")); // Change line color
            rightLine.setStrokeWidth(2.5); // Set line thickness
            treePane.getChildren().add(rightLine); // Add line to treePane
            displayTree(node.getRight(), rightX, childY, offset * 0.75, node.getRight().getId()); // Recursive call for right child with reduced offset
        }

        nodePane.setOnMouseClicked(e -> {
            if(e.getButton().toString().equals("SECONDARY")) {
                MenuItem assignLeft = new MenuItem("Assign left");
                MenuItem assignRight = new MenuItem("Assign right");
                MenuItem removeNode = new MenuItem("Remove node");
                assignLeft.setOnAction(_ -> getInputFromUser("Enter data").ifPresent(data -> {
                    binaryTree.assignLeft(data.trim(), id);
                    visualizeTree(scene);
                }));
                assignRight.setOnAction(_ ->  getInputFromUser("Enter data").ifPresent(data -> {
                    binaryTree.assignRight(data.trim(), id);
                    visualizeTree(scene);
                }));
                removeNode.setOnAction(_ -> {
                    try {
                        binaryTree.remove(id);
                        visualizeTree(scene);
                    } catch (UnderflowException ex) {
                        alertError(ex);
                    }
                });
                new ContextMenu(
                        assignLeft,
                        assignRight,
                        removeNode
                ).show(circle,e.getScreenX(),e.getSceneY());
            }
        });

        nodes.put(id, circle);
    }
}
