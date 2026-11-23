import java.io.*;
import java.util.List;

public class FileManager {

    public static void saveItems(List<Item> items, String fileName)
            throws IOException {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            output.writeObject(items);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Item> loadItems(String fileName)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            List<Item> items = (List<Item>) input.readObject();

            for (Item item : items) {
                System.out.println(item);
            }

            return items;
        }
    }

    public static String findMaxWordsLine(String fileName)
            throws IOException {

        String maxLine = "";
        int maxWords = 0;

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();

                int words = trimmed.isEmpty()
                        ? 0
                        : trimmed.split("\\s+").length;

                if (words > maxWords) {
                    maxWords = words;
                    maxLine = line;
                }
            }
        }

        return maxLine;
    }
}