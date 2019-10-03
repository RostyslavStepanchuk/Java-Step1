package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {
    private Passenger passenger;

    @BeforeEach
    void beforeEach(){
    passenger = new Passenger("Slava", "Stepanchuk");
    }

    @Test
    void constructor(){

        assertThrows(IllegalArgumentException.class, ()->new Passenger(null, "Stepanchuk"));
        assertThrows(IllegalArgumentException.class, ()->new Passenger("Slava", null));
    }

    @Test
    void getFullName() {

        assertEquals("Slava Stepanchuk", passenger.getFullName());
    }

    @Test
    void hashCodeTest() {

        assertEquals(1180009953 ,passenger.hashCode());
    }

    @Test
    void equals() {

        Passenger fullCopy = passenger;
        assertEquals(fullCopy, passenger);
        assertNotEquals(null, passenger);
        Passenger copy = new Passenger("Slava", "Stepanchuk");
        assertEquals(passenger, copy);
    }
}