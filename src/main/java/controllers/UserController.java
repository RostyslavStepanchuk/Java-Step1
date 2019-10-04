package controllers;

import entities.Passenger;
import entities.User;
import services.UserService;

import java.io.File;
import java.util.List;

public class UserController {
    private final UserService service;

    public UserController(){
        this(new File("./data", "users.bin"));
    }

    public UserController(File file){
        service = new UserService(file);
    }

    public List<User> getAll(){
        return service.getAll();
    }

    public Passenger createUser(String login, String password, String name, String surname) {
        return service.createUser(login,password,name,surname);
    }

    public Passenger getPassengerData (String login, String password){
        return service.getPassengerData(login,password);
    }


    public boolean deleteUser(String login) {
        return service.deleteUser(login);
    }

    public boolean deleteUser(User user) {
        return service.deleteUser(user);
    }

    public void save() {
        service.save();
    }
}

