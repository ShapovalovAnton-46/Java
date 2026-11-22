import controller.ShapeController;
import view.ShapeView;

public class Main {

    public static void main(String[] args) {

        ShapeView view = new ShapeView();
        ShapeController controller = new ShapeController(view);

        // Створення набору фігур
        controller.createShapes();

        // Виведення початкового масиву
        view.displayMessage("INITIAL ARRAY:");
        controller.displayShapes();

        // Обчислення загальної площі
        controller.calculateTotalArea();

        // Обчислення площі фігур випадкового типу
        controller.calculateAreaByType();

        // Сортування за площею
        controller.sortByArea();

        // Сортування за кольором
        controller.sortByColor();
    }
}