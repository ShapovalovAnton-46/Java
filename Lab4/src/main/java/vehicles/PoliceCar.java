package main.java.vehicles;

import main.java.passengers.PoliceOfficer;

public class PoliceCar extends Vehicle<PoliceOfficer> {

    public PoliceCar(int maxSeats) {
        super(maxSeats);
    }

    @Override
    public String getVehicleName() {
        return "Police Car";
    }
}