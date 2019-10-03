package services;

import entities.User;
import entities.Passenger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private static File file;
    private static String login;
    private static String password;
    private static Passenger p1;
    private static User u1;
    private static User u2;
    private static User u3;
    private UserService service;

    @BeforeAll
    static void beforeAll(){
        file = new File("./src/test/data", "usersTest.bin");
        login = "slv01";
        password = "passw123$";
        p1 = new Passenger("Slava", "Stepanchuk");
        u1 = new User("slv01", "passw123$", "Slava", "Stepanchuk");
        u2 = new User("nst01","Passsss","Nastia", "Stepanchuk");
        u3 = new User("olex", "psv","Oleksandr", "Ivanov");
    }

    @BeforeEach
    void beforeEach(){
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        service = new UserService(file);
        service.createUser(login, password, "Slava", "Stepanchuk");
        service.createUser("nst01","Passsss","Nastia", "Stepanchuk");
        service.createUser("olex", "psv","Oleksandr", "Ivanov");
    }

    @Test
    void getAll() {


        List<User> result = service.getAll();
        assertEquals(3, result.size());
        assertTrue(result.contains(u1));
        assertTrue(result.contains(u2));
        assertTrue(result.contains(u3));
    }

    @Test
    void getPassengerData() {

        assertEquals(p1, service.getPassengerData(login,password));
        assertThrows(IllegalArgumentException.class, ()->service.getPassengerData(null, password));
        assertThrows(IllegalArgumentException.class, ()->service.getPassengerData(login, null));
        assertNull(service.getPassengerData(login,"wrongPass"));
        assertNull(service.getPassengerData("notExisting",password));
    }

    @Test
    void deleteUser_by_object() {

        service.deleteUser(u1);
        List<User> result = service.getAll();
        assertEquals(2, result.size());
        assertFalse(result.contains(u1));
        assertTrue(result.contains(u2));
        assertTrue(result.contains(u3));
    }

    @Test
    void deleteUser_by_login() {

        service.deleteUser(u1.getLogin());
        List<User> result = service.getAll();
        assertEquals(2, result.size());
        assertFalse(result.contains(u1));
        assertTrue(result.contains(u2));
        assertTrue(result.contains(u3));
    }

    @Test
    void save_restore_from_file() {

        service.save();

        UserService service = new UserService(file);
        List<User> result = service.getAll();

        assertEquals(3, result.size());
        assertTrue(result.contains(u1));
        assertTrue(result.contains(u2));
        assertTrue(result.contains(u3));
    }
}