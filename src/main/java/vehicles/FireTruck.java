package main.java.vehicles;

import main.java.passengers.Firefighter;

public class FireTruck extends Vehicle<Firefighter> {

    public FireTruck(int maxSeats) {
        super(maxSeats);
    }

    @Override
    public String getVehicleName() {
        return "Fire Truck";
    }
}