package controller;

import model.*;
import view.ShapeView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ShapeController {

    private Shape[] shapes;
    private ShapeView view;
    private Random random;

    public ShapeController(ShapeView view) {
        this.view = view;
        this.random = new Random();
    }

    // Створення набору фігур
    public void createShapes() {
        String[] colors = {
                "Red", "Blue", "Green", "Yellow",
                "Black", "White", "Orange", "Purple"
        };

        shapes = new Shape[12];

        for (int i = 0; i < shapes.length; i++) {
            String color = colors[random.nextInt(colors.length)];

            switch (random.nextInt(3)) {
                case 0:
                    shapes[i] = new Rectangle(
                            color,
                            random.nextInt(10) + 1,
                            random.nextInt(10) + 1
                    );
                    break;

                case 1:
                    shapes[i] = new Triangle(
                            color,
                            random.nextInt(10) + 1,
                            random.nextInt(10) + 1
                    );
                    break;

                case 2:
                    shapes[i] = new Circle(
                            color,
                            random.nextInt(10) + 1
                    );
                    break;
            }
        }
    }

    // Відображення початкового масиву
    public void displayShapes() {
        view.displayShapes(shapes);
    }

    // Обчислення сумарної площі всіх фігур
    public void calculateTotalArea() {
        double totalArea = 0;

        for (Shape shape : shapes) {
            totalArea += shape.calcArea();
        }

        view.displayTotalArea(totalArea);
    }

    // Обчислення площі фігур заданого типу
    public void calculateAreaByType() {
        String[] types = {"Rectangle", "Triangle", "Circle"};

        // Випадковий вибір типу фігури
        String selectedType = types[random.nextInt(types.length)];

        double totalArea = 0;
        boolean found = false;

        for (Shape shape : shapes) {
            if (shape.getClass().getSimpleName().equals(selectedType)) {
                totalArea += shape.calcArea();
                found = true;
            }
        }

        if (found) {
            view.displayTypeArea(selectedType, totalArea);
        } else {
            view.displayMessage(
                    "No shapes of type " + selectedType + " found."
            );
        }
    }

    // Сортування за збільшенням площі
    public void sortByArea() {
        Shape[] sortedShapes = shapes.clone();

        Arrays.sort(sortedShapes, Comparator.comparingDouble(Shape::calcArea));

        view.displayMessage("\n===== SORTED BY AREA =====");
        view.displayShapes(sortedShapes);
    }

    // Сортування за кольором
    public void sortByColor() {
        Shape[] sortedShapes = shapes.clone();

        Arrays.sort(
                sortedShapes,
                Comparator.comparing(Shape::getShapeColor)
        );

        view.displayMessage("\n===== SORTED BY COLOR =====");
        view.displayShapes(sortedShapes);
    }
}