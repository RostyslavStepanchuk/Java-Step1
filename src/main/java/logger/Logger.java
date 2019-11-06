package logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Logger {
    private final File file;
    private final HashMap<String, ArrayList<String>> passengersLogHistory;

    public Logger() {
        this(new File("./data", "logs.txt"));
    }

    private Logger(File file) {
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

        ArrayList<String> historyArray;
        if (passengersLogHistory.containsKey(login)) {
            historyArray = passengersLogHistory.get(login);
            historyArray.add(action);
            passengersLogHistory.put(login, historyArray);
        } else {
            historyArray = new ArrayList<>();
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
