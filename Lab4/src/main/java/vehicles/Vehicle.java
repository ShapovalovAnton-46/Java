package main.java.vehicles;

import main.java.passengers.Passenger;
import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle<T extends Passenger> {

    private int maxSeats;
    private List<T> passengers;

    public Vehicle(int maxSeats) {
        if (maxSeats <= 0) {
            throw new IllegalArgumentException("Invalid seat capacity");
        }

        this.maxSeats = maxSeats;
        this.passengers = new ArrayList<>();
    }

    // Максимальна кількість місць
    public int getMaxSeats() {
        return maxSeats;
    }

    // Кількість зайнятих місць
    public int getOccupiedSeats() {
        return passengers.size();
    }

    // Посадка пасажира
    public void boardPassenger(T passenger) {
        if (passengers.size() >= maxSeats) {
            throw new IllegalStateException("No free seats!");
        }

        passengers.add(passenger);
        System.out.println(passenger + " boarded the vehicle.");
    }

    // Висадка пасажира
    public void removePassenger(T passenger) {
        if (!passengers.remove(passenger)) {
            throw new IllegalArgumentException(
                    "Passenger is not in the vehicle!"
            );
        }

        System.out.println(passenger + " left the vehicle.");
    }

    // Отримання списку пасажирів
    public List<T> getPassengers() {
        return new ArrayList<>(passengers);
    }

    public abstract String getVehicleName();

    @Override
    public String toString() {
        return getVehicleName() +
                " | Seats: " + getOccupiedSeats() +
                "/" + getMaxSeats();
    }
}