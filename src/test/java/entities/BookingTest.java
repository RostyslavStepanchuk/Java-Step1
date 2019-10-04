package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    static private Booking booking;
    static private Passenger passenger;
    static private Passenger p2;


    @BeforeAll
    static void beforeAll(){
        passenger = new Passenger("Slava", "Stepanchuk");
        booking = new Booking("FlightId", passenger);
        p2 = new Passenger("Nastia", "Stepanchuk");
    }

    @Test
    void getBookedBy() {

        assertEquals(passenger, booking.getBookedBy());
    }

    @Test
    void getId() {

        assertNotEquals(null, booking.getId());
        Booking newBooking = new Booking("FlightId", passenger);
        assertNotEquals(newBooking.getId(), booking.getId());
    }

    @Test
    void addPassenger() {

        booking.addPassenger(passenger);
        booking.addPassenger(p2);
        assertArrayEquals(new Passenger[]{passenger, p2}, booking.getPassengers().toArray());
        booking.addPassenger(null);
        assertArrayEquals(new Passenger[]{passenger, p2}, booking.getPassengers().toArray());
        booking.addPassenger(passenger);
        assertArrayEquals(new Passenger[]{passenger, p2}, booking.getPassengers().toArray());
    }

    @Test
    void equals() {

        Booking copyBooking = booking;
        assertEquals(copyBooking,booking);
        Booking newBooking = new Booking("FlightId", passenger);
        assertNotEquals(newBooking, booking);
        assertNotEquals(null, booking);
        Booking fullCopy = new Booking("FlightId", passenger, booking.getId());
        assertEquals(fullCopy, booking);
        booking.addPassenger(p2);
        assertEquals(fullCopy, booking);
    }

    @Test
    void test_hashCode() {

        int code = booking.hashCode();
        assertNotEquals(0, code);
    }
}