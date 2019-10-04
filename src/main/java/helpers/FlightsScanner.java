package helpers;

import entities.AirTrip;
import entities.FlightConnection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class FlightsScanner {
    private final List<AirTrip> origin;
    private final String from;
    private final String destination;
    private List<AirTrip> relevantTrips = new ArrayList<>();
    private List<AirTrip> furtherOptions = new ArrayList<>();

    public FlightsScanner(String from, String destination, List<AirTrip> origin) {
        this.from = from;
        this.destination = destination;
        this.origin = origin;
    }

    private List<AirTrip> createTripExtensions(AirTrip trip){
        List<AirTrip> result = new ArrayList<>();
        origin.stream().filter(new FlightConnectionPossible(trip)).forEach(airTrip -> result.add(new FlightConnection(trip, airTrip)));
        return result;
    }

    private void sortTrips(List<AirTrip> options) {
        List<AirTrip> newFurOptions = new ArrayList<>();
        options.forEach(trip -> {
            if (trip.getFrom().equals(from) & trip.getDestination().equals(destination)) {
                relevantTrips.add(trip);
            } else {
                newFurOptions.add(trip);
            }
        });
        furtherOptions = newFurOptions;
    }

    private List<AirTrip> investigateOptions(){
        List<AirTrip> result = new ArrayList<>();
        furtherOptions.forEach(trip -> result.addAll(createTripExtensions(trip)));
        return result;
    }

    public List<AirTrip> findAllConnections(Date depart, int seats){
        sortTrips(origin);
        while (!furtherOptions.isEmpty()){
           sortTrips(investigateOptions());
        }
        return relevantTrips
                .stream()
                .filter(airTrip ->
                        airTrip.getDepart().before(new Date(depart.getTime()+(1000*60*60*24))) &
                                airTrip.getSeats() >= seats)
                .collect(Collectors.toList());
    }

}
