package com.example.datastructurevisualiser;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import datastructures.BinaryTree;

public class VisualiseBinaryTree {

    private BinaryTree binaryTree = new BinaryTree();
    private Text traversalResultText = new Text();
    private AnchorPane treePane = new AnchorPane(); // Pane to display the tree structure

    public Scene createScene(Stage primaryStage) {
        // Title text
        Text title = new Text("Visualise Binary Trees");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        title.setFill(Color.web("#EEEEEE"));

        // Configure traversal result text display
        traversalResultText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        traversalResultText.setFill(Color.web("#EEEEEE"));

        // Set up TextField for user input
        TextField inputField = new TextField();
        inputField.setPromptText("Enter value");
        inputField.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");

        // Create buttons for tree operations
        Button insertButton = new Button("Insert");
        Button inorderButton = new Button("Inorder");
        Button preorderButton = new Button("Preorder");
        Button postorderButton = new Button("Postorder");
        Button backButton = new Button("Back");

        // Style buttons
        styleButton(insertButton);
        styleButton(inorderButton);
        styleButton(preorderButton);
        styleButton(postorderButton);
        styleButton(backButton);

        // Set up HBox for input field and buttons
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(inputField, insertButton, inorderButton, preorderButton, postorderButton, backButton);
        inputBox.setStyle("-fx-alignment: center;");

        // "Back" button functionality
        backButton.setOnAction(e -> primaryStage.setScene(new DataStructureVisualiser().createScene(primaryStage)));

        // Button actions
        insertButton.setOnAction(e -> {
            String inputValue = inputField.getText();
            if (!inputValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(inputValue);
                    binaryTree.insert(value);
                    inputField.clear();
                    visualizeTree(); // Update tree visualization after insertion
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a valid integer.");
                }
            }
        });

        inorderButton.setOnAction(e -> traversalResultText.setText("Inorder: " + binaryTree.inorder()));
        preorderButton.setOnAction(e -> traversalResultText.setText("Preorder: " + binaryTree.preorder()));
        postorderButton.setOnAction(e -> traversalResultText.setText("Postorder: " + binaryTree.postorder()));

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

        // Add a listener to adjust the position of the tree when the window is resized
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> visualizeTree());
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> visualizeTree());

        // Set the scene size to 1270x660
        return new Scene(root, 1270, 660); // Updated window size
    }

    // Method to style buttons
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #D4BEE4; -fx-text-fill: #3B1E54; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    // Visualize the binary tree centered horizontally in the window
    private void visualizeTree() {
        treePane.getChildren().clear(); // Clear previous tree display

        // Only visualize if the tree has nodes
        if (binaryTree.root != null) {
            double treePaneWidth = treePane.getWidth();
            double centerX = (treePane.getScene().getWidth() - treePaneWidth) / 2; // Use scene width for dynamic centering
            displayTree(binaryTree.root, centerX + treePaneWidth / 2, 20, 150); // Start visualization at the calculated center position
        }

        // Center the treePane itself within the main VBox
        double sceneWidth = treePane.getScene().getWidth(); // Get current scene width
        AnchorPane.setLeftAnchor(treePane, (sceneWidth - treePane.getWidth()) / 2);
        AnchorPane.setRightAnchor(treePane, (sceneWidth - treePane.getWidth()) / 2);
    }

    // Recursive method to display the binary tree with accurate positioning and lines
    private void displayTree(BinaryTree.Node node, double x, double y, double offset) {
        if (node == null) return;

        // Create a Circle and Text for the current node
        Circle circle = new Circle(20, Color.web("#D4BEE4"));
        circle.setStroke(Color.web("#3B1E54"));
        Text text = new Text(String.valueOf(node.value));
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        text.setFill(Color.web("#3B1E54"));

        StackPane nodePane = new StackPane(circle, text);
        nodePane.setLayoutX(x - 20); // Position StackPane center at (x, y)
        nodePane.setLayoutY(y - 20);
        treePane.getChildren().add(nodePane); // Add node to the treePane

        // Calculate child node positions and adjust line length
        double childY = y + 80; // Increase vertical spacing between levels

        if (node.left != null) {
            double leftX = x - offset * 1.25; // Increase spacing by 25%
            Line leftLine = new Line(x, y + 20, leftX, childY - 15); // Line from parent to left child
            leftLine.setStroke(Color.web("#D4BEE4")); // Change line color
            leftLine.setStrokeWidth(2.5); // Set line thickness
            treePane.getChildren().add(leftLine); // Add line to treePane
            displayTree(node.left, leftX, childY, offset * 0.75); // Recursive call for left child with reduced offset
        }

        if (node.right != null) {
            double rightX = x + offset * 1.25; // Increase spacing by 25%
            Line rightLine = new Line(x, y + 20, rightX, childY - 15); // Line from parent to right child
            rightLine.setStroke(Color.web("#D4BEE4")); // Change line color
            rightLine.setStrokeWidth(2.5); // Set line thickness
            treePane.getChildren().add(rightLine); // Add line to treePane
            displayTree(node.right, rightX, childY, offset * 0.75); // Recursive call for right child with reduced offset
        }
    }
}
