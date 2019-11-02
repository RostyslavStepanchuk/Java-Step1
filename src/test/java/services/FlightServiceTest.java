package services;

import dao.FlightDAO;
import entities.AirTrip;
import entities.Flight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceTest {
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
    private static File file;
    private static List<Flight> referenceArray = new ArrayList<>();
    private static FlightDAO flDAO;
    private static FlightService flService;
    private static Flight lh1;
    private static Flight lh2;
    private static Flight lh3;
    private static Flight lh4;
    private static Flight lh5;
    private static Flight lh6;
    private static Flight lh7;
    private static Flight lh8;
    private static Flight lh9;

    @BeforeAll
    static void beforeAll() throws ParseException {
        file = new File("./src/test/data", "flightsTest.txt");
        lh1 = new Flight("LH123", "kyiv", "san-francisko", "30.08.2019-12:00", "31.08.2019-02:00", 300);
        lh2 = new Flight("LH222", "kyiv", "barcelona", "01.08.2019-12:00", "01.08.2019-14:00", 250);
        lh3 = new Flight("LH333", "kyiv", "palma", "03.08.2019-12:00", "03.08.2019-15:00", 200);
        lh4 = new Flight("LH444", "kyiv", "madrid", "04.08.2019-22:00", "05.08.2019-00:30", 250);
        lh5 = new Flight("LH555", "kyiv", "tel-aviv", "04.08.2019-23:00", "05.08.2019-01:30", 200);
        lh6 = new Flight("LH666", "kyiv", "new-york", "05.08.2019-12:00", "05.08.2019-23:45", 250);
        lh7 = new Flight("LH777", "kyiv", "wroclaw", "12.08.2019-14:15", "12.08.2019-15:10", 200);
        lh8 = new Flight("LH888", "kyiv", "prague", "13.08.2019-18:00", "13.08.2019-20:00", 250);
        lh9 = new Flight("LH999", "kyiv", "oslo", "14.08.2019-19:00", "14.08.2019-121:00", 200);

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        flService = new FlightService(file);
    }

    @Test
    void getAllFlights() {

        assertEquals(flService.getAllFlights(), referenceArray);
    }

    @Test
    void getSuitableFlights() {

        List<AirTrip> referenceArray = new ArrayList<>();
        referenceArray.add(lh2);
        assertEquals(referenceArray, flService.getSuitableFlights("barcelona", "01.08.2019", 3));
        assertNotEquals(referenceArray, flService.getSuitableFlights("barcelona", "01.08.2019", 300));
        assertNotEquals(referenceArray, flService.getSuitableFlights("madrid", "01.08.2019", 3));
        assertNotEquals(referenceArray, flService.getSuitableFlights("barcelona", "02.08.2019", 300));

    }

    @Test
    void getFlightByNumber() {

        assertEquals(lh1, flService.getFlightByNumber("LH123"));
    }

    @Test
    void bookSeats() {

        int initialSeats = flService.getFlightByNumber("LH123").getSeats();
        flService.bookSeats(10, "LH123");
        assertEquals(initialSeats - 10, flService.getFlightByNumber("LH123").getSeats());
    }

    @Test
    void returnSeats() {

        int initialSeats = flService.getFlightByNumber("LH123").getSeats();
        flService.returnSeats(10, "LH123");
        assertEquals(initialSeats + 10, flService.getFlightByNumber("LH123").getSeats());
    }


    @Test
    void getAllTripsWithinTimeRange() throws ParseException {
        List<AirTrip> referenceArray = new ArrayList<>();
        referenceArray.add(lh3);
        referenceArray.add(lh4);
        referenceArray.add(lh5);
        referenceArray.add(lh6);
        assertEquals(referenceArray, flService.getAllFlightsWithinTimeRange(format.parse("03.08.2019-00:00"), format.parse("05.08.2019-23:59")));
    }
}