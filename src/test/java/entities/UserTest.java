package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    static private Passenger passenger;
    static private User user;
    static private String name;
    static private String surname;
    static private String password;
    static private String login;

    @BeforeAll
    static void beforeAll(){
        name = "Slava";
        surname = "Stepanchuk";
        password = "passw123&";
        login = "slv01";
        passenger = new Passenger(name, surname);
        user = new User (login, password, name, surname); 
    }

    @Test
    void getPassengerData() {

        assertEquals(passenger, user.getPassengerData());
    }

    @Test
    void checkPassword() {

        assertTrue(user.checkPassword(password));
        assertFalse(user.checkPassword("passw123"));
        assertFalse(user.checkPassword(null));
    }

    @Test
    void getLogin() {

        assertEquals(login, user.getLogin());
    }

    @Test
    void hashCodeTest() {

        assertEquals(109537278, user.hashCode());
    }

    @Test
    void equals() {

        User copyUser = user;
        assertEquals(copyUser,user);
        assertNotEquals(null, user);
        User newUser = new User("sl", password, name, surname);
        assertNotEquals(newUser, user);
        newUser = new User(login, "p123", name, surname);
        assertEquals(newUser, user);
        newUser = new User(login, password, "Serhiy", surname);
        assertNotEquals(newUser, user);
        newUser = new User(login, password, name, "Ivanov");
        assertNotEquals(newUser, user);
        User fullCopy = new User(login, password, name, surname);
        assertEquals(fullCopy, user);
    }


}