package controllers;

import dao.FlightDAO;
import entities.AirTrip;
import entities.Flight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.FlightService;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FlightControllerTest {
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
    private static File file;
    private static List<Flight> referenceArray = new ArrayList<>();
    private static FlightController controller;
    private static FlightDAO flDAO;
    private static Flight lh1;
    private static Flight lh2;

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

        lh1 = new Flight("LH123", "kyiv", "san-francisko", "14.08.2019-12:00", "15.08.2019-02:00", 300);
        lh2 = new Flight("LH222", "kyiv", "barcelona", "01.08.2019-12:00", "01.08.2019-14:00", 250);
        flDAO = new FlightDAO(file);
        flDAO.retrieveInitialData();
        flDAO.insert(lh1);
        flDAO.insert(lh2);
        flDAO.saveData();
        controller = new FlightController(file);

        referenceArray.add(lh1);
        referenceArray.add(lh2);
    }

    @Test
    void getAllFlights() {

        List<Flight> result = controller.getAllFlights();
        assertEquals(2, result.size());
        Flight testFlight1 = result.get(0);
        assertEquals(Flight.class, testFlight1.getClass());

        assertTrue(result.contains(lh1));
        assertTrue(result.contains(lh2));
    }

    @Test
    void getSuitableFlights() {

        List<AirTrip> referenceArray = new ArrayList<>();
        referenceArray.add(lh2);
        assertEquals(referenceArray, controller.getSuitableFlights("barcelona", "01.08.2019", 3));
        assertNotEquals(referenceArray, controller.getSuitableFlights("barcelona", "01.08.2019", 300));
        assertNotEquals(referenceArray, controller.getSuitableFlights("madrid", "01.08.2019", 3));
        assertNotEquals(referenceArray, controller.getSuitableFlights("barcelona", "02.08.2019", 300));
    }

    @Test
    void getFlightByNumber() {

        assertEquals(lh1, controller.getFlightByNumber("LH123"));
    }


    //before test change flights date manually
    @Test
    void getNearestFlights() {

        List<AirTrip> referenceArray = new ArrayList<>();
        referenceArray.add(lh1);
        assertEquals(referenceArray, controller.getNearestFlights());
    }

    @Test
    void bookSeats() {

        int initialSeats = controller.getFlightByNumber("LH123").getSeats();
        controller.bookSeats(10, "LH123");
        assertEquals( initialSeats - 10, controller.getFlightByNumber("LH123").getSeats());
    }

    @Test
    void returnSeats() {

        int initialSeats = controller.getFlightByNumber("LH123").getSeats();
        controller.returnSeats(10, "LH123");
        assertEquals( initialSeats + 10, controller.getFlightByNumber("LH123").getSeats());
    }

}
