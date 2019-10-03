package dao;

import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    static private User u1;
    static private User u2;
    static private User u3;
    static private String login;
    private UserDAO dao;

    @BeforeAll
    static void beforeAll(){
        login = "slv01";
        u1 = new User (login, "passw123$", "Slava", "Stepanchuk");
        u2 = new User ("nst01","Passsss","Nastia", "Stepanchuk");
        u3 = new User ("olex", "psv","Oleksandr", "Ivanov");
    }

    @BeforeEach
    void beforeEach(){
        dao = new UserDAO(){{
            insert(u1);
            insert(u2);
            insert(u3);
        }};
    }

    @Test
    void getAll() {

        List<User> result = dao.getAll();
        assertEquals(3, result.size());
        assertTrue(result.contains(u1));
        assertTrue(result.contains(u2));
        assertTrue(result.contains(u3));
    }

    @Test
    void getKeys() {

        List<String> result = dao.getKeys();
        assertEquals(3, result.size());
        assertTrue(result.contains("slv01"));
        assertTrue(result.contains("nst01"));
        assertTrue(result.contains("olex"));
    }

    @Test
    void update() {

        User updU1 = new User (login, "newPass", "newName", "newSurname");
        dao.update(updU1);
        assertEquals(3, dao.getAll().size());
        assertNotEquals(u1, dao.get(login));
        assertEquals(updU1, dao.get(login));
        assertEquals(u2, dao.get(u2.getLogin()));
        assertEquals(u3, dao.get(u3.getLogin()));
        assertThrows(IllegalArgumentException.class, ()->dao.insert(null));
    }

    @Test
    void remove_by_obj() {

        dao.remove(u2);
        assertEquals(2, dao.getAll().size());
        assertNull(dao.get(u2.getLogin()));
        assertEquals(u1, dao.get(u1.getLogin()));
        assertEquals(u3, dao.get(u3.getLogin()));
        assertThrows(IllegalArgumentException.class, ()->dao.insert(null));
    }

    @Test
    void remove_by_login() {

        dao.remove(u2.getLogin());
        assertEquals(2, dao.getAll().size());
        assertNull(dao.get(u2.getLogin()));
        assertEquals(u1, dao.get(u1.getLogin()));
        assertEquals(u3, dao.get(u3.getLogin()));
        assertThrows(IllegalArgumentException.class, ()->dao.insert(null));
    }

    @Test
    void save_and_restore_from_file() {

        File source = new File("./src/test/data", "usersTest.bin");

        try {
            PrintWriter writer = new PrintWriter(source);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dao.saveData();

        UserDAO newUsers = new UserDAO(source);
        newUsers.retrieveInitialData();

        assertEquals(3, dao.getAll().size());
        assertEquals(u1, dao.get(u1.getLogin()));
        assertEquals(u2, dao.get(u2.getLogin()));
        assertEquals(u3, dao.get(u3.getLogin()));
    }

}