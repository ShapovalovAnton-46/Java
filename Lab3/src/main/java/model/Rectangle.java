package model;

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String shapeColor, double width, double height) {
        super(shapeColor);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Rectangle");
    }

    @Override
    public String toString() {
        return "Rectangle | Color: " + shapeColor +
                " | Width: " + width +
                " | Height: " + height +
                " | Area: " + String.format("%.2f", calcArea());
    }
}