package controllers;

import controllers.BookingController;
import controllers.FlightController;
import controllers.UserController;
import entities.AirTrip;
import entities.Booking;
import entities.Flight;
import entities.Passenger;
import logger.Logger;
import view.InvalidUserInput;
import view.PassengerInputs;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleController {
    private UserController uc = new UserController();
    private BookingController bc = new BookingController();
    private PassengerInputs pi = new PassengerInputs();
    private FlightController fc = new FlightController();
    //    Logger logger = new Logger();
//    private Passenger passenger;
    private String currentUser;


    public Passenger startAuthorization() {
        String login = pi.getLogin();
        String password = pi.getPassword();
        return uc.getPassengerData(login, password);
    }

    public Passenger startRegistration() {
        String login = pi.getLogin();
        String password = pi.getPassword();
        String name = pi.getName();
        String surname = pi.getSurname();
        return uc.createUser(login, password, name, surname);
    }

    public void getNearestFlight() {
        List<Flight> nearestFlights = fc.getNearestFlights();
        nearestFlights.forEach(System.out::println);
    }

    public void getFlightByIdAndShowIt() {
        try {
            Flight flightByNumber =
                    fc.getFlightByNumber(pi.getFlightId());
            if (flightByNumber != null) {
                System.out.println(flightByNumber);
            }
        } catch (Exception e) {
            System.out.println("There is no such flight");
        }
    }

    public void searchAndBooking() throws InvalidUserInput {
        AtomicInteger flightNumber = new AtomicInteger(1);
        String destination = pi.getDestination();
        String date = pi.getDate();
        int ticketsQuantity = pi.getTicketsQuantity();
        List<AirTrip> suitableFlights = fc.getSuitableFlights(destination, date, ticketsQuantity);
        suitableFlights.forEach(f -> System.out.println(flightNumber.getAndIncrement() + " . " + f));
        if (suitableFlights.size() == 0) {
            System.out.println("There are no available options");
        } else {
            AirTrip flight = suitableFlights.get(pi.getMenuItem() - 1);
            for (int i = 0; i < ticketsQuantity; i++) {
                bc.makeBooking(flight.getFlightNumber(), new Passenger(pi.getName(), pi.getSurname()));
            }
            fc.bookSeats(ticketsQuantity,
                    flight.getFlightNumber());
        }
    }

    public void getBookingsOfPassenger(Passenger passenger, boolean isAuthorized) {
        if (isAuthorized) {
            bc.getFlightsOfPassenger(passenger);
        } else {
            String name = pi.getName();
            String surname = pi.getSurname();
            List<Booking> flightsOfPassenger
                    = bc.getFlightsOfPassenger(new Passenger(name, surname));
            System.out.println(flightsOfPassenger);
        }
    }

    public void cancelBooking() {
        Booking booking = bc.cancelBooking(pi.getBookingId());
        fc.returnSeats(booking.getPassengers().size(), booking.getFlight());
    }

    public void saveChanges(boolean isAuthorized) {
        if (isAuthorized) {
            uc.save();
        }
        fc.save();
        bc.save();
    }
}
