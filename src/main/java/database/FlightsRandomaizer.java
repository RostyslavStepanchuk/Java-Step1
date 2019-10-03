package database;


import dao.FlightDAO;
import entities.Flight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FlightsRandomaizer {

    private final String[] destinations = {
            "barcelona",
            "amsterdam",
            "ankara",
            "astana",
            "athens",
            "baku",
            "berlin",
            "brussels",
            "bucharest",
            "budapest",
            "dublin",
            "helsinki",
            "lisbon",
            "london",
            "luxembourg",
//            "madrid",
//            "minsk",
//            "monaco",
//            "oslo",
//            "paris",
//            "prague",
//            "riga",
//            "rome",
//            "sofia",
//            "stockholm",
//            "tallinn",
//            "tbilisi",
//            "vienna",
//            "vilnius",
//            "warsaw"
    };
    private final int arraySize = destinations.length;
    private final int quantity;
    private final int lastMonthInSchedule;
    private final int firstMonthInSchedule;

    public FlightsRandomaizer(int quantity, int firstMonthInSchedule, int lastMonthInSchedule) {
        this.quantity = quantity;
        this.firstMonthInSchedule = firstMonthInSchedule;
        this.lastMonthInSchedule = lastMonthInSchedule;
    }

    int generateValueFromRange(int min, int max) {
        Random r = new Random();
        int val = r.nextInt(max - min);
        return min + val;
    }

    public List<Flight> get() throws ParseException {
        ArrayList<Flight> flights = new ArrayList<>();
        int homeQuantity = (int)(quantity * 0.3);
        for (int i = 0; i < homeQuantity; i++) {
            flights.add(generateNewFlight("kyiv"));
        }
        for (int i = homeQuantity; i < quantity; i++) {
            flights.add(generateNewFlight(null));
        }
        return flights;
    }

    private String generateFlightNumber(String from, String destination, int id) {
        StringBuilder sb = new StringBuilder();
        sb.append(from.toUpperCase().charAt(0));
        sb.append(destination.toUpperCase().charAt(0));
        sb.append(id);
        return sb.toString();
    }

    private String generateDate() {
        int day = generateValueFromRange(1, 30);
        int month = generateValueFromRange(firstMonthInSchedule, lastMonthInSchedule);
        int hour = generateValueFromRange(0, 24);

        return createStringDate(day,month,2019,hour);
    }

    private String generateArrivalTime(String date) {
        int tripTime = generateValueFromRange(1,6) * (60 * 60 * 1000);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy-HH:mm");

        try {
            Date depart = dateFormatter.parse(date);
            Date arrival = new Date(depart.getTime() + tripTime);
            return dateFormatter.format(arrival);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error while parsing date from string");
        }
    }

    private String createStringDate(int day, int month, int year, int hour) {
        String dayString = Integer.toString(day);
        if (dayString.length() == 1) dayString = "0"+dayString;
        String monthString = Integer.toString(month);
        if (monthString.length() == 1) monthString = "0"+monthString;
        String yearString = Integer.toString(year);
        String hourString = Integer.toString(hour);
        if (hourString.length() == 1) hourString = "0"+hourString;

        return dayString + "." + monthString + "." + yearString + "-" + hourString + ":" + "00";
    }

    private Flight generateNewFlight(String homeCity) throws ParseException {
        int id = generateValueFromRange(100, 1000);
        String destination = destinations[generateValueFromRange(0, arraySize)];
        int seatsMin = 100;
        int seatsMax = 350;
        int seats = generateValueFromRange(seatsMin, seatsMax);
        String from;
        if (homeCity == null) {
            from = destinations[generateValueFromRange(0, arraySize)];
            while (from.equals(destination)) {
                from = destinations[generateValueFromRange(0, arraySize)];
            }
        } else {
            from = homeCity;
        }
        String flightNumber = generateFlightNumber(from, destination, id);
        String depart = generateDate();
        String arrival = generateArrivalTime(depart);
        return new Flight(flightNumber, from, destination, depart, arrival, seats);
    }


    public static void main(String[] args) throws ParseException {
        FlightsRandomaizer newFlights = new FlightsRandomaizer(1500, 10,11);
        List<Flight> flights = newFlights.get();
        FlightDAO flDAO = new FlightDAO();
        for (int i = 0; i < flights.size(); i++) {
            flDAO.insert(flights.get(i));
        }
        flDAO.saveData();
    }
}
