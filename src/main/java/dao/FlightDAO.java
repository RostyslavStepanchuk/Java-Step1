package dao;

import entities.Booking;
import entities.Flight;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlightDAO {
//    private final File file;
    private final HashMap<String, Flight> map;

//    public FlightDAO(){
//        this(new File("./data", "flights.txt"));
//    }

//    public FlightDAO (File file){
////        this.file = file;
////        this.map = new HashMap<>();
////    }

//FOR DEV PURPOSE ONLY///

    public FlightDAO (HashMap<String, Flight> map){
        this.map = map;
    }


    public Flight get(String flightNumber) {
        return map.get(flightNumber);
    }

    public List<Flight> getAll() {
        return new ArrayList<>(map.values());
    }

    public boolean insert(Flight flight) {
        // WE NO NEED TO IMPLEMENT THIS METHOD
        if (flight == null) throw new IllegalArgumentException("Invalid insert arguments: null is not accepted");
        String flightNumber = flight.getFlightNumber();
        if (map.containsKey(flightNumber)) {
            return false;
        } else {
            map.put(flightNumber, flight);
            return true;
        }
//        return false;
    }

    public boolean update(Flight flight) {
        if (flight == null) throw new IllegalArgumentException("Invalid insert arguments: null is not accepted");
        String flightNumber = flight.getFlightNumber();
        if (map.containsKey(flightNumber)) {
            map.put(flightNumber, flight);
            return true;
        } else {
            return false;
        }
//        return false;
    }

    public boolean remove(String flightNumber) {
        // WE NO NEED TO IMPLEMENT THIS METHOD
        if (flightNumber == null) throw new IllegalArgumentException("Invalid insert arguments: null is not accepted");
        if (map.containsKey(flightNumber)) {
            map.remove(flightNumber);
            return true;
        } else {
            return false;
        }
//        return false;
    }

//    public void retrieveInitialData() {
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            if (fis.available() > 0) {
//                ObjectInputStream ois = new ObjectInputStream(fis);
//
//                List data = (ArrayList) ois.readObject();
//                data.forEach(o -> {
//                    Flight flight = (Flight) o;
//                    map.put(flight.getFlightNumber(), flight);
//                });
//                ois.close();
//                fis.close();
//            }
//        } catch (FileNotFoundException e) {
//            throw new IllegalArgumentException("Flights.txt file not found");
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException("Error while initializing stream");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void saveData() {
//        try {
//            FileOutputStream f = new FileOutputStream(file);
//            ObjectOutputStream o = new ObjectOutputStream(f);
//            o.writeObject(new ArrayList<>(map.values()));
//            o.close();
//            f.close();
//        } catch (FileNotFoundException e) {
//            throw new IllegalArgumentException("Flights.txt file not found");
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException("Error while initializing stream");
//
//        }
//    }

}
