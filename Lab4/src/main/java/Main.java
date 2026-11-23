package main.java;

import main.java.passengers.*;
import main.java.vehicles.*;

public class Main {

    public static void main(String[] args) {

        // Створення пасажирів
        Passenger passenger1 = new Passenger("Anton");
        Passenger passenger2 = new Passenger("Ivan");

        Firefighter firefighter1 = new Firefighter("Oleg");
        Firefighter firefighter2 = new Firefighter("Andriy");

        PoliceOfficer police1 = new PoliceOfficer("Petro");
        PoliceOfficer police2 = new PoliceOfficer("Dmytro");

        // Створення транспортних засобів
        Bus bus = new Bus(5);
        Taxi taxi = new Taxi(3);
        FireTruck fireTruck = new FireTruck(2);
        PoliceCar policeCar = new PoliceCar(2);

        // Посадка пасажирів
        bus.boardPassenger(passenger1);
        bus.boardPassenger(firefighter1);
        bus.boardPassenger(police1);

        taxi.boardPassenger(passenger2);
        taxi.boardPassenger(firefighter2);

        fireTruck.boardPassenger(new Firefighter("Serhii"));

        policeCar.boardPassenger(police2);

        // Створення дороги
        Road road = new Road();

        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);
        road.addCarToRoad(fireTruck);
        road.addCarToRoad(policeCar);

        // Виведення інформації
        System.out.println("\n===== VEHICLES =====");

        road.displayCars();

        System.out.println("\n===== ROAD INFORMATION =====");

        System.out.println("Total vehicles: " +
                road.getCountOfCars());

        System.out.println("Total passengers: " +
                road.getCountOfHumans());

        // Висадка пасажира
        System.out.println("\n===== PASSENGER EXIT =====");

        bus.removePassenger(passenger1);

        System.out.println("Occupied seats in bus: " +
                bus.getOccupiedSeats());

        System.out.println("Total passengers on road: " +
                road.getCountOfHumans());
    }
}