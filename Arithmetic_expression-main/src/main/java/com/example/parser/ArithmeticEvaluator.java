package com.example.parser;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;

public class ArithmeticEvaluator {
    // This map stores variable names (as strings) and their corresponding values (as doubles).
    private Map<String, Double> variables = new HashMap<>();

    // Constructor initializes some predefined variables with values
    public ArithmeticEvaluator() {
        variables.put("a", 5.0);  
        variables.put("b", 3.0);  
        variables.put("c", 2.0);  
        variables.put("d", 4.0);  
        variables.put("A", 1.0);  
        variables.put("B", 2.0);  
        variables.put("C", 3.0);  
        variables.put("D", 4.0); 
    }

    // This method allows setting a variable's value.
    public void setVariable(String name, double value) {
        variables.put(name, value);
    }

    // This method evaluates the arithmetic expression represented by the ParseTree.
    public double evaluate(ParseTree tree) {
        int children = tree.getChildCount(); // Get the number of children of the current node in the tree.

        // Case 1: If the node has no children (a terminal node, like a variable or number)
        if (children == 0) {
            String text = tree.getText();  // Get the text at this node.
            
            // If it's a variable (a single letter), return its value from the variables map.
            if (text.matches("[a-zA-Z]")) {
                return variables.getOrDefault(text, 0.0);  // Return value or 0.0 if the variable is undefined.
            } 
            
            // If it's a number, parse it and return the value.
            else if (text.matches("[0-9]+")) {
                return Double.parseDouble(text);  // Parse the text as a number and return it.
            }

            // If it's neither a valid variable nor number, throw an error.
            throw new IllegalArgumentException("Invalid token: " + text);
        }

        // Case 2: If the node has one child, recursively evaluate it.
        if (children == 1) {
            return evaluate(tree.getChild(0));  // Recursively evaluate the child node.
        }

        // Case 3: If the node has three children, it's an expression with two operands and an operator.
        if (children == 3) {
            String op = tree.getChild(1).getText();  // The operator is the middle child.

            // If the expression is in parentheses, evaluate the expression inside.
            if ("(".equals(tree.getChild(0).getText()) && ")".equals(tree.getChild(2).getText())) {
                return evaluate(tree.getChild(1));  // Evaluate the expression inside the parentheses.
            }

            // Evaluate the left and right operands recursively.
            double left = evaluate(tree.getChild(0));
            double right = evaluate(tree.getChild(2));

            // Perform the operation based on the operator.
            switch (op) {
                case "+":
                    return left + right;  // Addition
                case "-":
                    return left - right;  // Subtraction
                case "*":
                    return left * right;  // Multiplication
                case "/":
                    // If dividing by zero, throw an error.
                    if (right == 0) throw new ArithmeticException("Division by zero");
                    return left / right;  // Division
                default:
                    // If the operator is unknown, throw an error.
                    throw new IllegalArgumentException("Unknown operator: " + op);
            }
        }

        // If the expression doesn't match any of the above cases, throw an error.
        throw new IllegalArgumentException("Unsupported expression: " + tree.getText());
    }
}
