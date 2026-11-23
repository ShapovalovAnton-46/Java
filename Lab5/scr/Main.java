import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Item> items = new ArrayList<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n========== I/O STREAMS ==========");
            System.out.println("1. Знайти рядок з максимальною кількістю слів");
            System.out.println("2. Додати об'єкт");
            System.out.println("3. Вивести всі об'єкти");
            System.out.println("4. Пошук об'єкта");
            System.out.println("5. Зберегти об'єкти у файл");
            System.out.println("6. Завантажити об'єкти з файлу");
            System.out.println("7. Зашифрувати файл");
            System.out.println("8. Розшифрувати файл");
            System.out.println("9. Підрахувати HTML-теги на сторінці");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        findMaxWords();
                        break;
                    case 2:
                        addItem();
                        break;
                    case 3:
                        showItems();
                        break;
                    case 4:
                        searchItem();
                        break;
                    case 5:
                        saveItems();
                        break;
                    case 6:
                        loadItems();
                        break;
                    case 7:
                        encryptFile();
                        break;
                    case 8:
                        decryptFile();
                        break;
                    case 9:
                        countHtmlTags();
                        break;
                    case 0:
                        System.out.println("Завершення програми.");
                        return;
                    default:
                        System.out.println("Невірний пункт меню!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Помилка! Введіть ціле число.");
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    // Task 1: пошук рядка з найбільшою кількістю слів
    private static void findMaxWords() {

        System.out.print("Введіть шлях до файлу: ");
        String path = scanner.nextLine();

        try {
            String result = FileManager.findMaxWordsLine(path);

            System.out.println("Рядок з максимальною кількістю слів:");
            System.out.println(result);

        } catch (IOException e) {
            System.out.println("Не вдалося прочитати файл: " + e.getMessage());
        }
    }

    // Додавання об'єкта
    private static void addItem() {

        try {
            System.out.print("Введіть назву: ");
            String name = scanner.nextLine();

            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("Назва не може бути порожньою.");
            }

            System.out.print("Введіть опис: ");
            String description = scanner.nextLine();

            System.out.print("Введіть кількість: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity < 0) {
                throw new IllegalArgumentException("Кількість не може бути від'ємною.");
            }

            items.add(new Item(name, description, quantity));

            System.out.println("Об'єкт успішно додано!");

        } catch (NumberFormatException e) {
            System.out.println("Помилка! Кількість повинна бути цілим числом.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    // Виведення об'єктів
    private static void showItems() {

        if (items.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }

        System.out.println("\nСписок об'єктів:");

        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }

    // Пошук об'єкта
    private static void searchItem() {

        System.out.print("Введіть назву для пошуку: ");
        String search = scanner.nextLine().trim();

        if (search.isEmpty()) {
            System.out.println("Пошуковий запит не може бути порожнім.");
            return;
        }

        boolean found = false;

        for (Item item : items) {
            if (item.getName().toLowerCase().contains(search.toLowerCase())) {
                System.out.println(item);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Об'єкт не знайдено.");
        }
    }

    // Збереження об'єктів
    private static void saveItems() {

        System.out.print("Введіть шлях та ім'я файлу: ");
        String path = scanner.nextLine();

        try {
            FileManager.saveItems(items, path);
            System.out.println("Дані успішно збережено!");

        } catch (IOException e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        }
    }

    // Завантаження об'єктів
    private static void loadItems() {

        System.out.print("Введіть шлях до файлу: ");
        String path = scanner.nextLine();

        try {
            items = FileManager.loadItems(path);
            System.out.println("Дані успішно завантажено!");
            System.out.println("Кількість об'єктів: " + items.size());

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Помилка завантаження: " + e.getMessage());
        }
    }

    // Шифрування файлу
    private static void encryptFile() {

        System.out.print("Введіть шлях до вхідного файлу: ");
        String input = scanner.nextLine();

        System.out.print("Введіть шлях до вихідного файлу: ");
        String output = scanner.nextLine();

        try {
            System.out.print("Введіть ключовий символ: ");
            String keyInput = scanner.nextLine();

            if (keyInput.length() != 1) {
                throw new IllegalArgumentException("Ключ повинен містити один символ.");
            }

            CipherStreams.encrypt(input, output, keyInput.charAt(0));

            System.out.println("Файл успішно зашифровано!");

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    // Дешифрування файлу
    private static void decryptFile() {

        System.out.print("Введіть шлях до зашифрованого файлу: ");
        String input = scanner.nextLine();

        System.out.print("Введіть шлях до вихідного файлу: ");
        String output = scanner.nextLine();

        try {
            System.out.print("Введіть ключовий символ: ");
            String keyInput = scanner.nextLine();

            if (keyInput.length() != 1) {
                throw new IllegalArgumentException("Ключ повинен містити один символ.");
            }

            CipherStreams.decrypt(input, output, keyInput.charAt(0));

            System.out.println("Файл успішно розшифровано!");

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    // Підрахунок HTML-тегів
    private static void countHtmlTags() {

        System.out.print("Введіть URL сторінки: ");
        String address = scanner.nextLine();

        Map<String, Integer> tags = new TreeMap<>();

        Pattern pattern = Pattern.compile(
                "<\\s*([a-zA-Z][a-zA-Z0-9:-]*)\\b"
        );

        try {
            URL url = new URL(address);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            url.openConnection().getInputStream(),
                            "UTF-8"))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);

                    while (matcher.find()) {
                        String tag = matcher.group(1).toLowerCase();

                        tags.put(tag, tags.getOrDefault(tag, 0) + 1);
                    }
                }
            }

            if (tags.isEmpty()) {
                System.out.println("HTML-теги не знайдено.");
                return;
            }

            // Сортування за назвою тегу
            System.out.println("\nТеги в лексикографічному порядку:");

            for (Map.Entry<String, Integer> entry : tags.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            // Сортування за частотою появи
            System.out.println("\nТеги за зростанням частоти:");

            List<Map.Entry<String, Integer>> sortedTags =
                    new ArrayList<>(tags.entrySet());

            sortedTags.sort(
                    Comparator.comparingInt(
                            (Map.Entry<String, Integer> entry) -> entry.getValue()
                    ).thenComparing(Map.Entry::getKey)
            );

            for (Map.Entry<String, Integer> entry : sortedTags) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

        } catch (IOException e) {
            System.out.println("Помилка отримання сторінки: " + e.getMessage());
        }
    }
}