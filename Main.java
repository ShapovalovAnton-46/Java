import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a positive number: ");
        int n = scanner.nextInt();

        if (n < 1) {
            System.out.println("Number must be positive!");
            return;
        }

        System.out.println("Perfect numbers from 1 to " + n + ":");

        for (int i = 1; i <= n; i++) {
            int sum = 0;

            for (int j = 1; j <= i / 2; j++) {
                if (i % j == 0) {
                    sum += j;
                }
            }

            if (sum == i) {
                System.out.println(i);
            }
        }

        scanner.close();
    }
}