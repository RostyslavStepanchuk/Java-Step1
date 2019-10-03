package view;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
//import sun.awt.windows.WPrinterJob;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PassengerInputs {
    // validators must be added;
    private Scanner scan = new Scanner(System.in);


    public int getMenuItem() throws InvalidUserInput {
        int input = 0;
        try {
            input = scan.nextInt();
        } catch (InputMismatchException e) {
            scan.nextLine();
            throw new InvalidUserInput("Wrong input", input, e);
        }
        return input;
    }


    public String getDestination() {
        System.out.println("Enter your place of destination");
        String place = scan.nextLine();
        return validateDestination(place);
    }

    public String getDate() {
        System.out.println("Enter date of your flight");
        String date = scan.nextLine();
        return validateDate(date);
    }


    public int getTicketsQuantity() {
        System.out.println("Enter how many people are going to take a flight? ");
        String tickets = scan.nextLine();
        return validateTicketsQuantity(tickets);
    }


    public String getLogin() {
        System.out.println("Enter the login");
        return scan.next();
    }


    public String getPassword() {
        System.out.println("Enter the password");
        return scan.next();
    }

    public String getName() {
        System.out.println("Enter the name of passenger");
        return scan.next();
    }

    public String getSurname() {
        System.out.println("Enter the surname of passenger");
        return scan.next();
    }

    public String getFlightId() {
        System.out.println("Enter your flight ID");
        return scan.next();
    }

    public String getBookingId() {
        System.out.println("Enter your booking ID");
        return scan.next();
    }

    // validators goes here
    private String validateDestination(String city) {
        if (city.length() < 2) {
            System.out.println("wrong input");
            return this.getDestination();
        }
        boolean check = city.matches("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
        if (!check) {
            System.out.println("You have to enter correct city name");
            return this.getDestination();
        }
        return city;

    }

    private String validateDate(String date) {
        boolean check = date.matches("([0-9]{2})\\.([0-9]{2})\\.([0-9]{4})");
        if (!check) {
            System.out.println("Not valid date. Enter the date at format dd.mm.yyyy");
            return this.getDate();
        } else {
            return date;
        }
    }

    private int validateTicketsQuantity(String tickets) {
        if (tickets.matches("([0-9]+)")) {
            return Integer.parseInt(tickets);
        } else {
            System.out.println("Not valid date. Enter the date at format dd.mm.yyyy");
            return this.getTicketsQuantity();
        }
    }

}
