package services;

import dao.UserDAO;
import entities.User;
import entities.Passenger;

import java.io.File;
import java.util.List;

public class UserService {
    private final UserDAO data;

    public UserService(File file) {
        this.data = new UserDAO(file);
        data.retrieveInitialData();
    }

    public List<User> getAll(){
        return data.getAll();
    }

    public Passenger createUser(String login, String password, String name, String surname) {
        if (login == null | password == null | name == null | surname == null) throw new IllegalArgumentException("Invalid input: null can't be accepted as an argument");
        User user = new User (login, password, name, surname);
        if (data.getKeys().contains(login)) { return null;
        } else {
            data.insert(user);
            return data.get(login).getPassengerData();
        }
    }

    public Passenger getPassengerData (String login, String password) {
        if (login == null | password == null)
            throw new IllegalArgumentException("Invalid input: null can't be accepted as an argument");
        User user = data.get(login);
        if (user == null) { return null;
    } else if (user.checkPassword(password)) {return user.getPassengerData();
        } else return null;
    }


    public boolean deleteUser(String login) {
        return data.remove(login);
    }

    public boolean deleteUser(User user) {
        return data.remove(user);
    }

    public void save() {
        data.saveData();
    }
}
