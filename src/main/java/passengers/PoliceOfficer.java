package main.java.passengers;

public class PoliceOfficer extends Passenger {

    public PoliceOfficer(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Police Officer: " + getName();
    }
}