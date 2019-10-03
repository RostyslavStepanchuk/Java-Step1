package dao;

import entities.Flight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightDAOTest {
    private static File file;
    private static List<Flight> referenceArray = new ArrayList<>();
    private static FlightDAO flDAO;
    private static Flight lh1;
    private static Flight lh2;
    private static Flight lh3;
    private static Flight lh4;
    private static Flight lh5;
    private static Flight lh6;
    private static Flight lh7;
    private static Flight lh8;
    private static Flight lh9;
    private static Flight lh10;

    @BeforeAll
    static void beforeAll() throws ParseException {
        file = new File("./src/test/data", "flightsTest.txt");

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        lh1 = new Flight("LH123", "kyiv", "san-francisko", "30.08.2019-12:00", "31.08.2019-02:00", 300);
        lh2 = new Flight("LH222", "kyiv", "barcelona", "01.08.2019-12:00", "01.08.2019-14:00", 250);
        lh3 = new Flight("LH333", "kyiv", "palma", "03.08.2019-12:00", "03.08.2019-15:00", 200);
        lh4 = new Flight("LH444", "kyiv", "madrid", "04.08.2019-22:00", "05.08.2019-00:30", 250);
        lh5 = new Flight("LH555", "kyiv", "tel-aviv", "04.08.2019-23:00", "05.08.2019-01:30", 200);
        lh6 = new Flight("LH666", "kyiv", "new-york", "05.08.2019-12:00","05.08.2019-23:45",  250);
        lh7 = new Flight("LH777", "kyiv", "wroclaw", "12.08.2019-14:15", "12.08.2019-15:10", 200);
        lh8 = new Flight("LH888", "kyiv", "prague", "12.08.2019-18:00", "12.08.2019-20:00", 250);
        lh9 = new Flight("LH999", "kyiv", "oslo", "14.08.2019-19:00", "14.08.2019-121:00", 200);
        lh10 = new Flight("LH000", "kyiv", "oslo", "15.08.2019-19:00", "15.08.2019-121:00", 200);
        flDAO = new FlightDAO(file);
        flDAO.retrieveInitialData();
        flDAO.insert(lh1);
        flDAO.insert(lh2);
        flDAO.insert(lh3);
        flDAO.insert(lh4);
        flDAO.insert(lh5);
        flDAO.insert(lh6);
        flDAO.insert(lh7);
        flDAO.insert(lh8);
        flDAO.insert(lh9);
        flDAO.saveData();

        referenceArray.add(lh1);
        referenceArray.add(lh2);
        referenceArray.add(lh3);
        referenceArray.add(lh4);
        referenceArray.add(lh5);
        referenceArray.add(lh6);
        referenceArray.add(lh7);
        referenceArray.add(lh8);
        referenceArray.add(lh9);
    }

    @Test
    void get() {

        assertEquals(lh1, flDAO.get("LH123"));
    }

    @Test
    void getAll() {

        assertEquals(referenceArray, flDAO.getAll());
        List<Flight> result = flDAO.getAll();
        assertEquals(9, result.size());
        assertTrue(result.contains(lh1));
        assertTrue(result.contains(lh2));
    }

    @Test
    void insert() {

        flDAO.insert(lh10);
        List<Flight> result = flDAO.getAll();
        assertEquals(10, result.size());
        assertTrue(result.contains(lh1));
        assertTrue(result.contains(lh2));
        assertTrue(result.contains(lh10));
        assertThrows(IllegalArgumentException.class, ()->flDAO.insert(null));

    }

    @Test
    void update() throws ParseException {

        Flight lh11 = new Flight("LH0001", "helsinki", "oslo", "15.08.2019-19:00", "15.08.2019-121:00", 100);
        flDAO.insert(lh11);
        assertEquals(lh11, flDAO.get(lh11.getFlightNumber()));
        Flight lh11Copy = new Flight("LH0001", "helsinki", "oslo", "15.08.2019-19:00", "15.08.2019-121:00", 100);
        flDAO.update(lh11Copy);
        assertEquals(lh11Copy, flDAO.get(lh11.getFlightNumber()));
        assertTrue(flDAO.getAll().contains(lh1));
        assertTrue(flDAO.getAll().contains(lh11));
        assertThrows(IllegalArgumentException.class, ()->flDAO.insert(null));
    }

    @Test
    void remove() {

        flDAO.remove(lh10.getFlightNumber());
        List<Flight> result = flDAO.getAll();
        assertEquals(9, result.size());
        assertTrue(result.contains(lh9));
        assertFalse(result.contains(lh10));
        assertThrows(IllegalArgumentException.class, ()->flDAO.insert(null));
    }

    @Test
    void save_and_restore_from_file() {

        File source = new File("./src/test/data", "flightsTest.txt");

        try {
            PrintWriter writer = new PrintWriter(source);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FlightDAO flights = new FlightDAO(source);
        flights.insert(lh1);
        flights.insert(lh2);
        flights.saveData();
        FlightDAO newFlights = new FlightDAO(source);
        newFlights.retrieveInitialData();
        List<Flight> result = newFlights.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(lh1));
        assertTrue(result.contains(lh2));

    }
}