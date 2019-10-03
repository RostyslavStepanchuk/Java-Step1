package logger;

//import sun.rmi.runtime.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Logger {
    private final File file;
    private final HashMap<String, ArrayList<String>> passengersLogHistory;
    private ArrayList<String> historyArray;

    public Logger() {
        this(new File("./data", "logs.txt"));
    }

    public Logger(File file) {
        this.file = file;
        this.passengersLogHistory = new HashMap<>();

    }

    public ArrayList<String> LogHistory(String login) {
        return passengersLogHistory.get(login);

    }

    public void add(String login, String action) {
        if (login == null) {
            throw new IllegalArgumentException("User is not authorized");
        }


        if (passengersLogHistory.containsKey(login)) {
            historyArray = passengersLogHistory.get(login);
            historyArray.add(action);
            passengersLogHistory.put(login, historyArray);
        } else {
            historyArray = new ArrayList<String>();
            historyArray.add(action);
            passengersLogHistory.put(login, historyArray);
        }
    }


    public void save() {
        try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(passengersLogHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
