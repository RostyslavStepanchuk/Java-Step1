package services;

import dao.BookingDAO;
import entities.Booking;
import entities.Passenger;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private final BookingDAO data;

    public BookingService(File file) {
        data = new BookingDAO(file);
        data.retrieveInitialData();
    }

    public List<Booking> getAllBookings() {
        return data.getAll();
    }

    public boolean makeBooking(String flight, Passenger bookedBy, Passenger... passengers) {
        Booking booking = new Booking(flight, bookedBy);
        if (passengers == null) throw new IllegalArgumentException("Invalid arguments: passengers can't be null");
        if (passengers.length > 0) {
            for (Passenger person : passengers) {
                if (person == null) {
                    throw new IllegalArgumentException("Invalid arguments: passengers can't be null");
                } else {
                    booking.addPassenger(person);
                }
            }
        } else {
            booking.addPassenger(bookedBy);
        }
        return data.insert(booking);
    }

    public List<Booking> getFlightsOfPassenger(Passenger passenger) {
        return data
                .getAll()
                .stream()
                .filter(booking -> (
                        booking.getBookedBy().equals(passenger) ||
                                booking.getPassengers().contains(passenger)
                ))
                .collect(Collectors.toList());
    }

    public Booking cancelBooking(String bookingId) {
        return data.remove(bookingId);
    }

    public boolean cancelBooking(Booking booking) {
        return data.remove(booking);
    }

    public void save() {
        data.saveData();

    }

}
