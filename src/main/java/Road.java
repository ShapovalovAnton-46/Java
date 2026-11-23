package main.java;



import main.java.passengers.Passenger;
import main.java.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Road {

    private List<Vehicle<? extends Passenger>> carsInRoad;

    public Road() {
        carsInRoad = new ArrayList<>();
    }

    // Додавання транспортного засобу на дорогу
    public void addCarToRoad(Vehicle<? extends Passenger> car) {
        carsInRoad.add(car);
    }

    // Підрахунок кількості пасажирів на дорозі
    public int getCountOfHumans() {
        int count = 0;

        for (Vehicle<? extends Passenger> car : carsInRoad) {
            count += car.getOccupiedSeats();
        }

        return count;
    }

    // Кількість транспортних засобів
    public int getCountOfCars() {
        return carsInRoad.size();
    }

    // Виведення транспортних засобів
    public void displayCars() {
        for (Vehicle<? extends Passenger> car : carsInRoad) {
            System.out.println(car);
        }
    }
}