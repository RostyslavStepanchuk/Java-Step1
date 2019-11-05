package controllers;

import entities.AirTrip;
import entities.Flight;
import helpers.FlightsScanner;
import services.FlightService;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightController {
    private final FlightService service;

    public FlightController() {
        this(new File("./data", "flights.txt"));
    }

    public FlightController(File file) {
        service = new FlightService(file);
    }

    public List<Flight> getAllFlights() {
        return service.getAllFlights();
    }

    public List<AirTrip> getSuitableFlights(String destination, String date, int requiredSeatsQuantity) {
        Date formattedDate;
        try {
            formattedDate = new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format("Invalid date format parameter %s", date));
        }

        List<Flight> options = service.getAllFlightsWithinTimeRange(formattedDate, new Date(formattedDate.getTime() + (1000 * 60 * 60 * 72)));
        return new FlightsScanner("kyiv", destination, new ArrayList<>(options)).findAllConnections(formattedDate, requiredSeatsQuantity);
    }

    public Flight getFlightByNumber(String flightNumber) {
        return service.getFlightByNumber(flightNumber);
    }

    public List<Flight> getNearestFlights() {
        return service.getNearestFlights();
    }

    public boolean bookSeats(int requiredSeatsQuantity, String flightNumber) {
        List<String> backup = new ArrayList<>();
        for (String flight : flightNumber.split("&")) {
            if (!service.bookSeats(requiredSeatsQuantity, flight)) {
                backup.forEach(s -> service.returnSeats(requiredSeatsQuantity, flight));
                return false;
            }
            backup.add(flight);
        }
        return true;
    }

    public boolean returnSeats(int returningSeatsQuantity, String flightNumber) {
        for (String flight : flightNumber.split("&")) {
            if (!service.returnSeats(returningSeatsQuantity, flight)) {
                return false;
            }
        }
        return true;
    }

    public List<Flight> getAllFlightsWithinTimeRange(Date dateFrom, Date dateTo) {
        return service.getAllFlightsWithinTimeRange(dateFrom, dateTo);
    }

    public void save() {
        service.save();
    }

}
