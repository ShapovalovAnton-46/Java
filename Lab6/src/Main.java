
import java.util.HashMap;
import java.util.Scanner;

class Translator {
    private HashMap<String, String> dictionary = new HashMap<>();

    public void addWord(String english, String ukrainian) {
        dictionary.put(english.toLowerCase(), ukrainian);
    }

    public String translate(String phrase) {
        StringBuilder result = new StringBuilder();
        String[] words = phrase.toLowerCase().split("\\s+");

        for (String word : words) {
            String translation = dictionary.get(word);

            if (translation != null) {
                result.append(translation);
            } else {
                result.append(word);
            }

            result.append(" ");
        }

        return result.toString().trim();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Translator translator = new Translator();

        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("peace", "мир");
        translator.addWord("friend", "друг");
        translator.addWord("computer", "комп'ютер");
        translator.addWord("book", "книга");
        translator.addWord("apple", "яблуко");
        translator.addWord("i", "я");
        translator.addWord("programming", "програмування");

        while (true) {
            System.out.println("\nАнгло-український перекладач");
            System.out.println("1. Додати слово до словника");
            System.out.println("2. Перекласти фразу");
            System.out.println("0. Вийти з програми");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Введіть англійське слово: ");
                String english = scanner.nextLine();

                System.out.print("Введіть український переклад: ");
                String ukrainian = scanner.nextLine();

                translator.addWord(english, ukrainian);
                System.out.println("Слово додано до словника!");

            } else if (choice.equals("2")) {
                System.out.print("Введіть фразу англійською: ");
                String phrase = scanner.nextLine();

                System.out.println("Переклад: " + translator.translate(phrase));

            } else if (choice.equals("0")) {
                System.out.println("Програму завершено.");
                break;

            } else {
                System.out.println("Помилка! Введіть 1, 2 або 0.");
            }
        }

        scanner.close();
    }
}