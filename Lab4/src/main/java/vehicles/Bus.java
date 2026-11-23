package main.java.vehicles;

import main.java.passengers.Passenger;

public class Bus extends Vehicle<Passenger> {

    public Bus(int maxSeats) {
        super(maxSeats);
    }

    @Override
    public String getVehicleName() {
        return "Bus";
    }
}