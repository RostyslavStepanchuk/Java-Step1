package entities;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

public class FlightConnection implements Serializable, AirTrip {
    private final ArrayList<Flight> storage;
    private String flightNumber;

    public FlightConnection(AirTrip trip1, AirTrip trip2) {
        this.storage = new ArrayList<>(5);
        addDestination(trip1);
        addDestination(trip2);
    }

    public void addDestination (AirTrip trip) {
        trip.getDirectFlights().forEach(flight -> {
            if (storage.size() == 0) {
                storage.add(flight);
                flightNumber = flight.getFlightNumber();
            } else {
                if (validFlight(flight)) {
                    storage.add(flight);
                    flightNumber += "&" + flight.getFlightNumber();
                } else {
                    throw new IllegalArgumentException(String.format("Flight %s can't be joined to FlightConnection %s", trip.getFlightNumber(), getFlightNumber()));
                }
            }
        });
    }

    public boolean validFlight (Flight flight) {
        boolean noTimeToTransfer = flight.getDepart().getTime() - this.getArrival().getTime() < (60 * 60 * 1000);
        boolean tooLongWaiting = flight.getDepart().getTime() - this.getArrival().getTime() > (12 * 60 * 60 * 1000);
        boolean citiesMatch = this.getDestination().equals(flight.getFrom());

        return (citiesMatch & !noTimeToTransfer & !tooLongWaiting);
    }

    public ArrayList<Flight> getDirectFlights(){
        return new ArrayList<>(storage);
    }

    @Override
    public String getDestination() {
        return storage.get(storage.size()-1).getDestination();
    }

    @Override
    public String getFrom() {
        return storage.get(0).getFrom();
    }

    @Override
    public String getFlightNumber() {
        return flightNumber;
    }

    @Override
    public Date getDepart() {
        return storage.get(0).getDepart();
    }

    @Override
    public Date getArrival() {
        return storage.get(storage.size()-1).getArrival();
    }

    @Override
    public int getSeats() {
        return storage
                .stream()
                .min(Comparator.comparingInt(Flight::getSeats))
                .map(Flight::getSeats)
                .orElse(0);
    }

    @Override
    public String toString(){
        List<Flight> flights = getDirectFlights();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Flight connection %s of %d flights %s --> %s:\n", getFlightNumber(), flights.size(), getFrom(), getDestination()));
        flights.forEach(obj -> sb.append("--- ").append(obj).append("\n"));
        return sb.toString();
    }



    public static void main(String[] args) throws ParseException {
        Flight f1 = new Flight("KB755","kyiv", "berlin", "12.09.2019-12:00", "12.09.2019-16:00", 100);
        Flight f2 = new Flight("BP450","berlin", "paris", "12.09.2019-18:00", "12.09.2019-22:00", 300);
        Flight f3 = new Flight("BM200","paris", "madrid", "13.09.2019-04:00", "13.09.2019-06:00", 250);
        Flight f4 = new Flight("KT755","madrid", "los-angeles", "13.09.2019-08:00", "13.09.2019-16:00", 120);

        FlightConnection fc1 = new FlightConnection(f1, f2);
        FlightConnection fc2 = new FlightConnection(f3, f4);

        FlightConnection fc3 = new FlightConnection(fc1, fc2);
        System.out.println(fc3);
    }
}
