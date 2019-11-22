package controllers;

import entities.AirTrip;
import entities.Booking;
import entities.Flight;
import entities.Passenger;
import view.InvalidUserInput;
import view.PassengerInputs;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleController {
    private UserController userController = new UserController();
    private BookingController bookingController = new BookingController();
    private PassengerInputs passengerInputs = new PassengerInputs();
    private FlightController flightController = new FlightController();

    public Passenger startAuthorization() {
        String login = passengerInputs.getLogin();
        String password = passengerInputs.getPassword();
        return userController.getPassengerData(login, password);
    }

    public Passenger startRegistration() {
        String login = passengerInputs.getLogin();
        String password = passengerInputs.getPassword();
        String name = passengerInputs.getName();
        String surname = passengerInputs.getSurname();
        return userController.createUser(login, password, name, surname);
    }

    public void getNearestFlight() {
        List<Flight> nearestFlights = flightController.getNearestFlights();
        System.out.println("reached here");
        nearestFlights.forEach(System.out::println);
    }

    public void getFlightByIdAndShowIt() {
        try {
            Flight flightByNumber =
                    flightController.getFlightByNumber(passengerInputs.getFlightId());
            if (flightByNumber != null) {
                System.out.println(flightByNumber);
            }
        } catch (Exception e) {
            System.out.println("There is no suserControllerh flight");
        }
    }

    public void searchAndBooking() throws InvalidUserInput {
        AtomicInteger flightNumber = new AtomicInteger(1);
        String destination = passengerInputs.getDestination();
        String date = passengerInputs.getDate();
        int ticketsQuantity = passengerInputs.getTicketsQuantity();
        List<AirTrip> suitableFlights = flightController.getSuitableFlights(destination, date, ticketsQuantity);
        suitableFlights.forEach(f -> System.out.println(flightNumber.getAndIncrement() + " . " + f));
        if (suitableFlights.size() == 0) {
            System.out.println("There are no available options");
        } else {
            AirTrip flight = suitableFlights.get(passengerInputs.getMenuItem() - 1);
            for (int i = 0; i < ticketsQuantity; i++) {
                bookingController.makeBooking(flight.getFlightNumber(), new Passenger(passengerInputs.getName(), passengerInputs.getSurname()));
            }
            flightController.bookSeats(ticketsQuantity,
                    flight.getFlightNumber());
        }
    }

    public void getBookingsOfPassenger(Passenger passenger, boolean isAuthorized) {
        if (isAuthorized) {
            bookingController.getFlightsOfPassenger(passenger);
        } else {
            String name = passengerInputs.getName();
            String surname = passengerInputs.getSurname();
            List<Booking> flightsOfPassenger
                    = bookingController.getFlightsOfPassenger(new Passenger(name, surname));
            System.out.println(flightsOfPassenger);
        }
    }

    public void cancelBooking() {
        Booking booking = bookingController.cancelBooking(passengerInputs.getBookingId());
        flightController.returnSeats(booking.getPassengers().size(), booking.getFlight());
    }

    public void saveChanges(boolean isAuthorized) {
        if (isAuthorized) {
            userController.save();
        }
        flightController.save();
        bookingController.save();
    }

    public void getAllflights() {
        flightController.getAllFlights()
                .stream()
                .sorted(Comparator.comparing(Flight::getDepart))
                .forEach(System.out::println);
    }
}
