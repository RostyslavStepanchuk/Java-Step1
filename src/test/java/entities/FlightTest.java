package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {
    private Flight flight;
    private Flight flight2;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm");

    @BeforeEach
    void beforeEach() throws ParseException {
         flight = new Flight("XX222", "kyiv", "barcelona", "05.08.2019-12:00", "05.08.2019-14:00", 100);
         flight2 = new Flight("YY111", "kyiv", "madrid", "07.08.2019-13:00", "07.08.2019-15:00", 200);
    }

    @Test
    void constructor(){

        assertThrows(IllegalArgumentException.class, ()->new Flight(null, "barcelona", "kyiv", "05.08.2019-12:00", "05.08.2019-13:00", 20));
        assertThrows(IllegalArgumentException.class, ()->new Flight("XX00", null, "kyiv", "05.08.2019-12:00", "05.08.2019-13:00", 20));
        assertThrows(IllegalArgumentException.class, ()->new Flight("XX00", "barcelona", null, "05.08.2019-12:00","05.08.2019-13:00",  20));
        assertThrows(IllegalArgumentException.class, ()->new Flight("XX00", "barcelona", "kyiv", null, "05.08.2019-13:00", 20));
        assertThrows(ParseException.class, ()->new Flight("XX00", "barcelona", "kyiv", "05.08.2019", "05.08.2019-13:00", 20));
        assertThrows(IllegalArgumentException.class, ()->new Flight("XX00", "barcelona", "kyiv", "05.08.2019-12:00", "05.08.2019-13:00", -1));
        assertThrows(IllegalArgumentException.class, ()->new Flight("XX00", "barcelona", "kyiv", "05.08.2019-12:00", null, -1));
    }

    @Test
    void getFlightNumber() {

        assertEquals("XX222", flight.getFlightNumber());
    }

    @Test
    void getDestination() {

        assertEquals("barcelona", flight.getDestination());
    }

    @Test
    void getFrom() {

        assertEquals("kyiv", flight.getFrom());
    }


    @Test
    void getDepartString() {

        assertEquals("05.08.2019", flight.getDepartString());
    }

    @Test
    void getDepart() throws ParseException {
        assertEquals(format.parse("05.08.2019-12:00"), flight.getDepart());
    }

    @Test
    void getArrival() throws ParseException {
        assertEquals(format.parse("05.08.2019-14:00"), flight.getArrival());
    }

    @Test
    void getDepartTime() {

        assertEquals("12:00", flight.getDepartTime());
    }

    @Test
    void getArrivalTime() {

        assertEquals("14:00", flight.getArrivalTime());
    }

    @Test
    void getSeats() {

        assertEquals(100, flight.getSeats());
    }

    @Test
    void bookSeats() {

        int initialSeats = flight.getSeats();
        flight.bookSeats(10);
        assertEquals( initialSeats - 10, flight.getSeats());

    }

    @Test
    void returnSeats() {

        int initialSeats = flight.getSeats();
        flight.returnSeats(10);
        assertEquals( initialSeats + 10, flight.getSeats());
    }


    @Test
    void getDirectFlights() {

        List<Flight> referenceArray = new ArrayList<>();
        referenceArray.add(flight);
        assertEquals( referenceArray, flight.getDirectFlights());
    }

    @Test
    void equals() throws ParseException {
        Flight copyFlight = flight;
        assertEquals(copyFlight,flight);
        assertNotEquals(flight, flight2);
        assertNotEquals(null, flight);
        Flight fullCopy = new Flight("XX222", "kyiv", "barcelona", "05.08.2019-12:00", "05.08.2019-14:00", 100);
        assertEquals(fullCopy, flight);

    }

    @Test
    void hashCodeTest() {

        assertEquals(83941106 ,flight.hashCode());
    }
}