import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.java.Road;
import main.java.passengers.*;
import main.java.vehicles.*;

import main.java.passengers.*;
import main.java.vehicles.*;

public class VehicleTest {

    // Перевірка посадки різних пасажирів в автобус
    @Test
    public void testBusPassengers() {
        Bus bus = new Bus(5);

        bus.boardPassenger(new Passenger("Anton"));
        bus.boardPassenger(new Firefighter("Oleg"));
        bus.boardPassenger(new PoliceOfficer("Ivan"));

        assertEquals(5, bus.getMaxSeats());
        assertEquals(3, bus.getOccupiedSeats());
    }

    // Перевірка посадки пасажирів у таксі
    @Test
    public void testTaxiPassengers() {
        Taxi taxi = new Taxi(3);

        taxi.boardPassenger(new Passenger("Anton"));
        taxi.boardPassenger(new Firefighter("Oleg"));
        taxi.boardPassenger(new PoliceOfficer("Ivan"));

        assertEquals(3, taxi.getOccupiedSeats());
    }

    // Перевірка пожежної машини
    @Test
    public void testFireTruck() {
        FireTruck truck = new FireTruck(3);

        truck.boardPassenger(new Firefighter("Oleg"));
        truck.boardPassenger(new Firefighter("Andriy"));

        assertEquals(2, truck.getOccupiedSeats());
    }

    // Перевірка поліцейської машини
    @Test
    public void testPoliceCar() {
        PoliceCar car = new PoliceCar(2);

        car.boardPassenger(new PoliceOfficer("Ivan"));

        assertEquals(1, car.getOccupiedSeats());
    }

    // Перевірка переповнення транспортного засобу
    @Test
    public void testFullVehicle() {
        Taxi taxi = new Taxi(1);

        taxi.boardPassenger(new Passenger("Anton"));

        assertThrows(IllegalStateException.class, () ->
                taxi.boardPassenger(new Passenger("Ivan"))
        );
    }

    // Перевірка висадки пасажира
    @Test
    public void testRemovePassenger() {
        Passenger passenger = new Passenger("Anton");
        Bus bus = new Bus(5);

        bus.boardPassenger(passenger);
        bus.removePassenger(passenger);

        assertEquals(0, bus.getOccupiedSeats());
    }

    // Перевірка висадки відсутнього пасажира
    @Test
    public void testRemoveMissingPassenger() {
        Bus bus = new Bus(5);

        assertThrows(IllegalArgumentException.class, () ->
                bus.removePassenger(new Passenger("Anton"))
        );
    }

    // Перевірка підрахунку пасажирів на дорозі
    @Test
    public void testRoadPassengersCount() {
        Bus bus = new Bus(5);
        Taxi taxi = new Taxi(3);
        FireTruck truck = new FireTruck(2);
        PoliceCar policeCar = new PoliceCar(2);

        bus.boardPassenger(new Passenger("Anton"));
        bus.boardPassenger(new Passenger("Ivan"));

        taxi.boardPassenger(new Passenger("Oleg"));

        truck.boardPassenger(new Firefighter("Petro"));

        policeCar.boardPassenger(new PoliceOfficer("Dmytro"));

        Road road = new Road();

        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);
        road.addCarToRoad(truck);
        road.addCarToRoad(policeCar);

        assertEquals(5, road.getCountOfHumans());
        assertEquals(4, road.getCountOfCars());
    }
}