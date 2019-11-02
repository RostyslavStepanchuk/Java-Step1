package entities;


import java.io.Serializable;

public class Passenger implements Serializable {
    private final String name;
    private final String surname;

    public Passenger(String name, String surname) {
        if (name != null && surname != null) {
            this.name = name;
            this.surname = surname;
        } else {
            throw new IllegalArgumentException("Invalid arguments: null is not accepted");
        }
    }


    public String getFullName() {
        return String.format("%s %s", name, surname);
    }

    public String getName() {
        return String.format("%s", name);
    }

    public String getSurname() {
        return String.format("%s", surname);
    }

    @Override
    public int hashCode() {
        return this.getFullName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Passenger passenger = (Passenger) obj;
        return this.getFullName().equals(passenger.getFullName());
    }

    @Override
    public String toString() {
        return String.format("{%s}", this.getFullName());
    }

}
