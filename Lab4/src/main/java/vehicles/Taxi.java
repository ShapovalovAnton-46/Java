package main.java.vehicles;

import main.java.passengers.Passenger;

public class Taxi extends Vehicle<Passenger> {

    public Taxi(int maxSeats) {
        super(maxSeats);
    }

    @Override
    public String getVehicleName() {
        return "Taxi";
    }
}