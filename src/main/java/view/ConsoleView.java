package view;

import controllers.ConsoleController;
import entities.Passenger;
import logger.Logger;

public class ConsoleView {
    private PassengerInputs passengerInputs = new PassengerInputs();
    private Logger logger = new Logger();
    private ConsoleController concoleController = new ConsoleController();
    private Passenger passenger;
    private boolean isAuthorized = false;

    public void startApp(){
        System.out.println("Press 1 to Register new user");
        System.out.println("Press 2 to Authorize");
        System.out.println("Press 3 to continue without Authorization");
        System.out.println("Press 4 to Exit");
        try {
            switch (passengerInputs.getMenuItem()) {
                case 1:
                    System.out.println("Registration:\n");
                    passenger = concoleController.startRegistration();
                    System.out.println(passenger);
                    isAuthorized = true;
                    this.showMenu();
                    break;
                case 2:
                    System.out.println("Authorization:\n");
                    passenger = concoleController.startAuthorization();
                    while (passenger == null) {
                        System.out.println("Wrong data\n\n");
                        passenger = concoleController.startAuthorization();
                    }
                    isAuthorized = true;
                    System.out.println(passenger);
                    this.showMenu();
                    isAuthorized = true;
                case 3:
                    this.showMenu();
                    break;
                case 4:
                default:
                    System.out.println("Bye");
                    System.exit(0);
            }
        } catch (InvalidUserInput e) {
            System.out.println(e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Original exception " + e.getCause().getMessage());
            }
            System.out.print("\n\n");
            this.startApp();
        }

    }

    private void showMenu() throws InvalidUserInput {
        System.out.print("\n");
        System.out.print("\n");
        System.out.println("1.Online-table;");
        System.out.println("2.Check info about flight;");
        System.out.println("3.Searching and Booking;");
        System.out.println("4.Cancel Booking;");
        System.out.println("5.My Flights;");
        System.out.println("6.Exit;");
        if (isAuthorized) {
            System.out.println("7.Logout;");
        }
        System.out.println("100.Get whole flights table");
        this.menuItemReader();
    }


    private void menuItemReader() throws InvalidUserInput {
        try {
            switch (passengerInputs.getMenuItem()) {
                case 1:
                    concoleController.getNearestFlight();
                    if (isAuthorized) logger.add(passenger.getName(), "Searched for nearest flight");
                    break;
                case 2:
                    concoleController.getFlightByIdAndShowIt();
                    if (isAuthorized) logger.add(passenger.getName(), "Got flight by Id");
                    break;
                case 3:
                    concoleController.searchAndBooking();
                    if (isAuthorized) logger.add(passenger.getName(), "Searched and Booked");
                    break;
                case 4:
                    concoleController.cancelBooking();
                    if (isAuthorized) logger.add(passenger.getName(), "Cancelled booking");
                    break;
                case 5:
                    concoleController.getBookingsOfPassenger(passenger, isAuthorized);
                    if (isAuthorized) logger.add(passenger.getName(), "Searched for own bookings");
                    break;
                case 6:
                    concoleController.saveChanges(isAuthorized);
                    if (isAuthorized) logger.save();
                    System.exit(1);
                    break;
                case 7:
                    concoleController.saveChanges(isAuthorized);
                    if (isAuthorized) {
                        logger.save();
                        passenger = null;
                        isAuthorized = false;
                        this.startApp();
                    } else {
                        System.exit(1);
                    }
                case 0:
                case 100:
                    concoleController.getAllflights();
                default:
                    System.out.println("Try to enter menu item again ");
            }
        } catch (InvalidUserInput e) {
            System.out.println(e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Original exception " + e.getCause().getMessage());
            }
        } finally {
            this.showMenu();
        }
    }
}
