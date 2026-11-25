import java.util.Random;

public class ParallelMonteCarloPi {

    private static final long ITERATIONS = 1_000_000_000L;

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java ParallelMonteCarloPi <threads>");
            return;
        }

        int threads = Integer.parseInt(args[0]);

        if (threads <= 0) {
            System.out.println("Number of threads must be positive.");
            return;
        }

        Thread[] workers = new Thread[threads];
        long[] results = new long[threads];

        long startTime = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            final int index = i;

            workers[i] = new Thread(() -> {
                long localInside = 0;
                long pointsPerThread = ITERATIONS / threads;
                long remainder = ITERATIONS % threads;

                long count = pointsPerThread + (index < remainder ? 1 : 0);

                Random random = new Random(12345L + index);

                for (long j = 0; j < count; j++) {
                    double x = random.nextDouble();
                    double y = random.nextDouble();

                    if (x * x + y * y <= 1.0) {
                        localInside++;
                    }
                }

                results[index] = localInside;
            });

            workers[i].start();
        }

        try {
            for (Thread worker : workers) {
                worker.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Calculation interrupted.");
            return;
        }

        long totalInside = 0;

        for (long result : results) {
            totalInside += result;
        }

        double pi = 4.0 * totalInside / ITERATIONS;

        long endTime = System.nanoTime();
        double time = (endTime - startTime) / 1_000_000.0;

        System.out.printf("PI is %.5f%n", pi);
        System.out.println("THREADS " + threads);
        System.out.printf("ITERATIONS %,d%n", ITERATIONS);
        System.out.printf("TIME %.2fms%n", time);
    }
}