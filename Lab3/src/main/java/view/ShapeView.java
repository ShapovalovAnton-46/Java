package view;

import model.Shape;

public class ShapeView {

    public void displayShapes(Shape[] shapes) {
        System.out.println("\n===== SHAPES =====");

        for (int i = 0; i < shapes.length; i++) {
            System.out.println((i + 1) + ". " + shapes[i]);
        }
    }

    public void displayTotalArea(double area) {
        System.out.printf("\nTotal area of all shapes: %.2f%n", area);
    }

    public void displayTypeArea(String type, double area) {
        System.out.printf("\nTotal area of %s: %.2f%n", type, area);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}