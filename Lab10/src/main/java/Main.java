import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Logger logger = LogManager.getLogger("app");
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Item> items = new ArrayList<>();
    private static Locale currentLocale = new Locale("uk");
    private static ResourceBundle messages = ResourceBundle.getBundle(
            "location.messages", currentLocale
    );

    public static void main(String[] args) {
        logger.info("Application started");

        while (true) {
            printMenu();

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> findMaxWords();
                    case 2 -> addItem();
                    case 3 -> showItems();
                    case 4 -> searchItem();
                    case 5 -> saveItems();
                    case 6 -> loadItems();
                    case 7 -> encryptFile();
                    case 8 -> decryptFile();
                    case 9 -> countHtmlTags();
                    case 10 -> chooseLanguage();
                    case 11 -> reflectionTask();
                    case 0 -> {
                        System.out.println(t("exit"));
                        logger.info("Application finished");
                        return;
                    }
                    default -> {
                        System.out.println(t("invalid_choice"));
                        logger.warn("Invalid menu choice: {}", choice);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(t("integer_error"));
                logger.warn("Non-integer menu input");
            } catch (Exception e) {
                System.out.println(t("error") + e.getMessage());
                logger.error("Unexpected application error", e);
            }
        }
    }

    private static String t(String key) {
        return messages.getString(key);
    }

    private static void printMenu() {
        System.out.println("\n========== I/O STREAMS ==========");
        System.out.println("1. " + t("menu_1"));
        System.out.println("2. " + t("menu_2"));
        System.out.println("3. " + t("menu_3"));
        System.out.println("4. " + t("menu_4"));
        System.out.println("5. " + t("menu_5"));
        System.out.println("6. " + t("menu_6"));
        System.out.println("7. " + t("menu_7"));
        System.out.println("8. " + t("menu_8"));
        System.out.println("9. " + t("menu_9"));
        System.out.println("10. " + t("menu_10"));
        System.out.println("11. " + t("menu_11"));
        System.out.println("0. " + t("menu_0"));
        System.out.print(t("choice"));
    }

    // Task 1: reflection
    private static void reflectionTask() {
        logger.info("Reflection task started");

        try {
            // String created as a literal
            String literal = "Hello";
            System.out.println(t("before_literal") + literal);
            System.out.print(t("replacement_literal"));
            String literalReplacement = scanner.nextLine();
            String changedLiteral = changeStringValue(literal, literalReplacement);
            System.out.println(t("after") + changedLiteral);

            // String entered from keyboard
            System.out.print(t("enter_string"));
            String entered = scanner.nextLine();
            System.out.println(t("before_input") + entered);

            System.out.print(t("replacement_input"));
            String inputReplacement = scanner.nextLine();
            String changedInput = changeStringValue(entered, inputReplacement);
            System.out.println(t("after") + changedInput);

            logger.info("Reflection task completed");
        } catch (Exception e) {
            System.out.println(t("reflection_error") + e.getMessage());
            logger.error("Reflection task failed", e);
        }
    }

    /*
     * Changes the internal String array.
     * Java 8: String.value is char[].
     * Java 9+: String.value is byte[] and uses the coder field.
     * Replacement must have exactly the same String length.
     */
    private static String changeStringValue(String original, String replacement)
            throws ReflectiveOperationException {

        if (original.length() != replacement.length()) {
            throw new IllegalArgumentException(t("same_length"));
        }

        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);

        Object value = valueField.get(original);

        if (value instanceof char[]) {
            char[] chars = (char[]) value;
            char[] newChars = replacement.toCharArray();
            System.arraycopy(newChars, 0, chars, 0, chars.length);
        } else if (value instanceof byte[]) {
            Field coderField = String.class.getDeclaredField("coder");
            coderField.setAccessible(true);
            byte coder = coderField.getByte(original);

            byte[] bytes = (byte[]) value;

            if (coder == 0) {
                // Compact String: one byte per character (Latin-1)
                for (int i = 0; i < replacement.length(); i++) {
                    char c = replacement.charAt(i);
                    if (c > 255) {
                        throw new IllegalArgumentException(t("latin1_error"));
                    }
                    bytes[i] = (byte) c;
                }
            } else {
                // UTF-16 representation used by modern OpenJDK
                for (int i = 0; i < replacement.length(); i++) {
                    char c = replacement.charAt(i);
                    bytes[i * 2] = (byte) (c & 0xFF);
                    bytes[i * 2 + 1] = (byte) ((c >>> 8) & 0xFF);
                }
            }
        } else {
            throw new IllegalStateException(t("unknown_string"));
        }

        return original;
    }

    private static void chooseLanguage() {
        System.out.println("1. Українська");
        System.out.println("2. English");
        System.out.print(t("language_choice"));

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            currentLocale = new Locale("uk");
        } else if (choice.equals("2")) {
            currentLocale = new Locale("en");
        } else {
            System.out.println(t("invalid_choice"));
            logger.warn("Invalid language selection: {}", choice);
            return;
        }

        messages = ResourceBundle.getBundle("location.messages", currentLocale);
        System.out.println(t("language_changed"));
        logger.info("Language changed to {}", currentLocale);
    }

    private static void findMaxWords() {
        System.out.print(t("file_path"));
        String path = scanner.nextLine();

        try {
            String result = FileManager.findMaxWordsLine(path);
            System.out.println(t("max_words_line"));
            System.out.println(result);
            logger.info("Maximum-words line found in file {}", path);
        } catch (IOException e) {
            System.out.println(t("read_error") + e.getMessage());
            logger.error("Could not read file: {}", path, e);
        }
    }

    private static void addItem() {
        try {
            System.out.print(t("item_name"));
            String name = scanner.nextLine();

            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException(t("empty_name"));
            }

            System.out.print(t("item_description"));
            String description = scanner.nextLine();

            System.out.print(t("item_quantity"));
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity < 0) {
                throw new IllegalArgumentException(t("negative_quantity"));
            }

            items.add(new Item(name, description, quantity));
            System.out.println(t("item_added"));
            logger.info("Item added: {}", name);
        } catch (NumberFormatException e) {
            System.out.println(t("quantity_integer"));
            logger.warn("Invalid item quantity");
        } catch (IllegalArgumentException e) {
            System.out.println(t("error") + e.getMessage());
            logger.warn("Item was not added: {}", e.getMessage());
        }
    }

    private static void showItems() {
        if (items.isEmpty()) {
            System.out.println(t("empty_collection"));
            logger.info("Attempted to display an empty collection");
            return;
        }

        System.out.println(t("items_header"));
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        logger.info("Displayed {} items", items.size());
    }

    private static void searchItem() {
        System.out.print(t("search_name"));
        String search = scanner.nextLine().trim();

        if (search.isEmpty()) {
            System.out.println(t("empty_search"));
            logger.warn("Empty item search request");
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
            System.out.println(t("not_found"));
            logger.info("No item found for query: {}", search);
        } else {
            logger.info("Item search completed for query: {}", search);
        }
    }

    private static void saveItems() {
        System.out.print(t("save_path"));
        String path = scanner.nextLine();

        try {
            FileManager.saveItems(items, path);
            System.out.println(t("saved"));
            logger.info("Saved {} items to {}", items.size(), path);
        } catch (IOException e) {
            System.out.println(t("save_error") + e.getMessage());
            logger.error("Could not save items to {}", path, e);
        }
    }

    private static void loadItems() {
        System.out.print(t("file_path"));
        String path = scanner.nextLine();

        try {
            items = FileManager.loadItems(path);
            System.out.println(t("loaded"));
            System.out.println(t("item_count") + items.size());
            logger.info("Loaded {} items from {}", items.size(), path);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(t("load_error") + e.getMessage());
            logger.error("Could not load items from {}", path, e);
        }
    }

    private static void encryptFile() {
        System.out.print(t("input_file"));
        String input = scanner.nextLine();
        System.out.print(t("output_file"));
        String output = scanner.nextLine();

        try {
            System.out.print(t("key_char"));
            String keyInput = scanner.nextLine();

            if (keyInput.length() != 1) {
                throw new IllegalArgumentException(t("one_char_key"));
            }

            CipherStreams.encrypt(input, output, keyInput.charAt(0));
            System.out.println(t("encrypted"));
            logger.info("File encrypted: {} -> {}", input, output);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(t("error") + e.getMessage());
            logger.error("File encryption failed", e);
        }
    }

    private static void decryptFile() {
        System.out.print(t("encrypted_file"));
        String input = scanner.nextLine();
        System.out.print(t("output_file"));
        String output = scanner.nextLine();

        try {
            System.out.print(t("key_char"));
            String keyInput = scanner.nextLine();

            if (keyInput.length() != 1) {
                throw new IllegalArgumentException(t("one_char_key"));
            }

            CipherStreams.decrypt(input, output, keyInput.charAt(0));
            System.out.println(t("decrypted"));
            logger.info("File decrypted: {} -> {}", input, output);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(t("error") + e.getMessage());
            logger.error("File decryption failed", e);
        }
    }

    private static void countHtmlTags() {
        System.out.print(t("url"));
        String address = scanner.nextLine();
        Map<String, Integer> tags = new TreeMap<>();
        Pattern pattern = Pattern.compile("<\\s*([a-zA-Z][a-zA-Z0-9:-]*)\\b");

        try {
            URL url = new URL(address);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            url.openConnection().getInputStream(),
                            StandardCharsets.UTF_8))) {

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
                System.out.println(t("no_tags"));
                logger.info("No HTML tags found at {}", address);
                return;
            }

            System.out.println(t("tags_alphabetical"));
            for (Map.Entry<String, Integer> entry : tags.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            List<Map.Entry<String, Integer>> sortedTags =
                    new ArrayList<>(tags.entrySet());

            sortedTags.sort(
                    Comparator.comparingInt(
                            (Map.Entry<String, Integer> entry) -> entry.getValue()
                    ).thenComparing(Map.Entry::getKey)
            );

            System.out.println(t("tags_frequency"));
            for (Map.Entry<String, Integer> entry : sortedTags) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            logger.info("Counted {} different HTML tags at {}", tags.size(), address);
        } catch (IOException e) {
            System.out.println(t("page_error") + e.getMessage());
            logger.error("Could not process URL: {}", address, e);
        }
    }
}
