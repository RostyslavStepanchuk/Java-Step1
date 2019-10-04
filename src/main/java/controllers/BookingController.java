package controllers;

import entities.Booking;
import entities.Passenger;
import services.BookingService;

import java.io.File;
import java.util.List;


public class BookingController {
    private final BookingService service;

    public BookingController() {
        this(new File("./data", "bookings.txt"));
    }

    public BookingController(File file) {
        service = new BookingService(file);
    }

    public List<Booking> getAllBookings() {
        return service.getAllBookings();
    }

    public boolean makeBooking(String flight, Passenger bookedBy, Passenger... passengers) {
        return service.makeBooking(flight, bookedBy, passengers);
    }

    public List<Booking> getFlightsOfPassenger(Passenger passenger) {
        return service.getFlightsOfPassenger(passenger);
    }

    public Booking cancelBooking(String bookingId) {
        return service.cancelBooking(bookingId);
    }

    public boolean cancelBooking(Booking booking) {
        return service.cancelBooking(booking);
    }

    public void save() {
        service.save();
    }
}
