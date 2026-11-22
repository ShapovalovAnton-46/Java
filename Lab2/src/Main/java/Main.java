import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();

        // Create Person object
        Person person1 = new Person("Ivanenko", "Ivan", 20);

        // Convert object to JSON
        String json = gson.toJson(person1);
        System.out.println("JSON:");
        System.out.println(json);

        // Convert JSON back to object
        Person person2 = gson.fromJson(json, Person.class);

        System.out.println("\nDeserialized object:");
        System.out.println(person2);

        // Compare objects
        System.out.println("\nEquals result:");
        System.out.println(person1.equals(person2));
    }
}