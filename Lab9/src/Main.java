
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    // ==================== TASK 1 ====================

    static class Account {
        private final int id;
        private int balance;

        public Account(int id, int balance) {
            this.id = id;
            this.balance = balance;
        }

        public int getBalance() {
            return balance;
        }
    }

    static class Bank {

        public void transfer(Account from, Account to, int amount) {
            if (from == to || amount <= 0) {
                return;
            }

            // Lock accounts in a fixed order to prevent deadlock
            Account first = from.id < to.id ? from : to;
            Account second = from.id < to.id ? to : from;

            synchronized (first) {
                synchronized (second) {
                    if (from.balance >= amount) {
                        from.balance -= amount;
                        to.balance += amount;
                    }
                }
            }
        }
    }

    static void task1() throws InterruptedException {
        System.out.println("========== TASK 1 ==========");

        int accountCount = 100;
        int threadCount = 3000;

        Account[] accounts = new Account[accountCount];
        Random random = new Random();

        // Create accounts with random balances
        for (int i = 0; i < accountCount; i++) {
            accounts[i] = new Account(i, random.nextInt(10000));
        }

        Bank bank = new Bank();

        // Calculate total money before transfers
        long initialTotal = Arrays.stream(accounts)
                .mapToLong(Account::getBalance)
                .sum();

        System.out.println("Money before transfers: " + initialTotal);

        Thread[] threads = new Thread[threadCount];

        // Start multiple threads performing transfers
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                ThreadLocalRandom rnd = ThreadLocalRandom.current();

                Account from = accounts[rnd.nextInt(accountCount)];
                Account to = accounts[rnd.nextInt(accountCount)];

                int amount = rnd.nextInt(1, 1000);

                bank.transfer(from, to, amount);
            });

            threads[i].start();
        }

        // Wait for all transfers to finish
        for (Thread thread : threads) {
            thread.join();
        }

        // Calculate total money after transfers
        long finalTotal = Arrays.stream(accounts)
                .mapToLong(Account::getBalance)
                .sum();

        System.out.println("Money after transfers: " + finalTotal);
        System.out.println("Money is preserved: " +
                (initialTotal == finalTotal));
        System.out.println("All accounts have non-negative balances: " +
                Arrays.stream(accounts)
                        .allMatch(a -> a.getBalance() >= 0));
    }


    // ==================== TASK 2 ====================

    static class Node {
        String value;
        Node next;
    }

    static class RingBuffer {
        private Node head;
        private Node tail;

        public RingBuffer(int capacity) {
            if (capacity < 1) {
                throw new IllegalArgumentException("Capacity must be positive");
            }

            // One additional node distinguishes full from empty
            Node first = new Node();
            Node current = first;

            for (int i = 0; i < capacity; i++) {
                current.next = new Node();
                current = current.next;
            }

            current.next = first;

            head = first;
            tail = first;
        }

        // Add an element to the buffer
        public synchronized void put(String value)
                throws InterruptedException {

            Node next = tail.next;

            // Wait while the buffer is full
            while (next == head) {
                wait();
                next = tail.next;
            }

            tail = next;
            tail.value = value;

            notifyAll();
        }

        // Remove an element from the buffer
        public synchronized String take()
                throws InterruptedException {

            // Wait while the buffer is empty
            while (head == tail) {
                wait();
            }

            head = head.next;
            String value = head.value;
            head.value = null;

            notifyAll();

            return value;
        }
    }

    static void task2() throws InterruptedException {
        System.out.println("\n========== TASK 2 ==========");

        RingBuffer firstBuffer = new RingBuffer(20);
        RingBuffer secondBuffer = new RingBuffer(20);

        AtomicInteger messageCounter = new AtomicInteger(1);

        // Five producer threads
        for (int i = 1; i <= 5; i++) {
            final int producerId = i;

            Thread producer = new Thread(() -> {
                try {
                    while (true) {
                        int messageId = messageCounter.getAndIncrement();

                        String message = "Потік No " + producerId +
                                " згенерував повідомлення " + messageId;

                        firstBuffer.put(message);

                        Thread.sleep(ThreadLocalRandom.current()
                                .nextInt(10, 50));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            producer.setDaemon(true);
            producer.start();
        }

        // Two consumer threads
        for (int i = 1; i <= 2; i++) {
            final int consumerId = i;

            Thread consumer = new Thread(() -> {
                try {
                    while (true) {
                        String message = firstBuffer.take();

                        String translated = "Потік No " + consumerId +
                                " переклав повідомлення: " + message;

                        secondBuffer.put(translated);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            consumer.setDaemon(true);
            consumer.start();
        }

        // Main thread reads and prints 100 messages
        for (int i = 0; i < 100; i++) {
            String message = secondBuffer.take();
            System.out.println(message);
        }

        System.out.println("\n100 повідомлень успішно оброблено.");
    }


    // ==================== MAIN ====================

    public static void main(String[] args) throws InterruptedException {
        // task1();
        task2();
    }
}
