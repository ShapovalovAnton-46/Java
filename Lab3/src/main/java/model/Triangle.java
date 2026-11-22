package model;

public class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return (base * height) / 2;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Triangle");
    }

    @Override
    public String toString() {
        return "Triangle | Color: " + shapeColor +
                " | Base: " + base +
                " | Height: " + height +
                " | Area: " + String.format("%.2f", calcArea());
    }
}