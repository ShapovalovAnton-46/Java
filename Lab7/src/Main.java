import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the upper limit: ");
        int n = scanner.nextInt();

        Predicate<Integer> isPerfect = number -> {
            int sum = IntStream.range(1, number)
                    .filter(i -> number % i == 0)
                    .sum();

            return sum == number;
        };

        System.out.println("Perfect numbers from 1 to " + n + ":");

        IntStream.rangeClosed(1, n)
                .filter(isPerfect::test)
                .forEach(System.out::println);

        scanner.close();
    }
}