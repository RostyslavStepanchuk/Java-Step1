package dao;

import entities.Booking;

import java.io.*;
import java.util.*;

public class BookingDAO implements DAO<Booking> {
    private final File file;
    private final HashMap<String, Booking> map;

    public BookingDAO(){
        this(new File("./data", "bookings.txt"));
    }

    public BookingDAO (File file){
        this.file = file;
        this.map = new HashMap<>();
    }

    public Booking get(String id) {
        return map.get(id);
    }

    public List<Booking> getAll() {
        return new ArrayList<>(map.values());
    }

    public boolean insert(Booking booking) {
        String id = booking.getId();
        if (map.containsKey(id)) {
            return false;
        } else {
            map.put(id, booking);
            return true;
        }
    }

    public boolean update(Booking booking) {
        String id = booking.getId();
        if (map.containsKey(id)) {
            map.put(id, booking);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(String id) {
        if (map.containsKey(id)) {
            map.remove(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(Booking booking) {
        String id = booking.getId();
        if (map.containsKey(id)) {
            map.remove(id);
            return true;
        } else {
            return false;
        }
    }

    public void retrieveInitialData() {
        try {
            FileInputStream fis = new FileInputStream(file);
            if (fis.available() > 0) {
                ObjectInputStream ois = new ObjectInputStream(fis);

                List data = (ArrayList) ois.readObject();
                data.forEach(o -> {
                    Booking bkg = (Booking) o;
                    map.put(bkg.getId(), bkg);
                });
                ois.close();
                fis.close();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Bookings.txt file not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error while initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(new ArrayList<>(map.values()));
            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Bookings.txt file not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error while initializing stream");

        }
    }
}

