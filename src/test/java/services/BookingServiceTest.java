package services;

import entities.Booking;
import entities.Passenger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {
    private BookingService service;
    private static Passenger p1;
    private static Passenger p2;
    private static Passenger p3;
    private static File file;


    @BeforeAll
    static void beforeAll(){
        p1 = new Passenger("Slava", "Stepanchuk");
        p2 = new Passenger("Nastia", "Stepanchuk");
        p3 = new Passenger("Oleksandr", "Ivanov");
        file = new File("./src/test/data", "bookingsTest.txt");
    }

    @BeforeEach
    void beforeEach(){
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        service = new BookingService(file);
        service.makeBooking("FamilyTrip", p1, p1, p2);
        service.makeBooking("TripToAmsterdam", p3);
        service.makeBooking("TripToIstanbul", p2, p1);
    }

    @Test
    void makeBooking(){
        assertTrue(service.makeBooking("TripToAmsterdam", p3));
        assertThrows(IllegalArgumentException.class, ()->service.makeBooking(null,p1));
        assertThrows(IllegalArgumentException.class, ()->service.makeBooking("NewFlight",null));
        assertThrows(IllegalArgumentException.class, ()->service.makeBooking("NewFlight",p1,null));
        assertThrows(IllegalArgumentException.class, ()->service.makeBooking("NewFlight",p1,p2,null));
    }
    @Test
    void getAllBookings() {

        List<Booking> result = service.getAllBookings();
        assertEquals(3, result.size());
        Booking testBooking1 = result.get(0);
        assertEquals(Booking.class, testBooking1.getClass());

        List<String> flights = result
                .stream()
                .map(Booking::getFlight)
                .collect(Collectors.toList());
        assertTrue(flights.contains("FamilyTrip"));
        assertTrue(flights.contains("TripToAmsterdam"));
        assertTrue(flights.contains("TripToIstanbul"));
    }

    @Test
    void getFlightsOfPassenger() {

        List<Booking> result = service.getFlightsOfPassenger(p2);
        assertEquals(2, result.size());
        Booking testBooking1 = result.get(0);
        assertEquals(Booking.class, testBooking1.getClass());

        List<String> flights = result
                .stream()
                .map(Booking::getFlight)
                .collect(Collectors.toList());
        assertTrue(flights.contains("FamilyTrip"));
        assertTrue(flights.contains("TripToIstanbul"));
        Passenger p4 = new Passenger("Serhiy","Sydorenko");
        assertEquals(0, service.getFlightsOfPassenger(p4).size());
        assertEquals(0, service.getFlightsOfPassenger(null).size());
    }

    @Test
    void cancelBooking_by_object() {

        Booking bTest = service.getAllBookings().get(0);
        String flight = bTest.getFlight();
        assertTrue(service.cancelBooking(bTest));
        List<Booking> result = service.getAllBookings();
        assertEquals(2, result.size());
        List<String> flights = result
                .stream()
                .map(Booking::getFlight)
                .collect(Collectors.toList());
        assertFalse(flights.contains(flight));
    }

    @Test
    void save_restore_from_file() {

        service.save();
        BookingService service = new BookingService(file);
        List<Booking> result = service.getAllBookings();
        assertEquals(3, result.size());
        Booking testBooking1 = result.get(0);
        assertEquals(Booking.class, testBooking1.getClass());

        List<String> flights = result
                .stream()
                .map(Booking::getFlight)
                .collect(Collectors.toList());
        assertTrue(flights.contains("FamilyTrip"));
        assertTrue(flights.contains("TripToAmsterdam"));
        assertTrue(flights.contains("TripToIstanbul"));
    }
}