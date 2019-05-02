package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ShellComponent
public class ReservationCommand {

    @Autowired
    FlightRepository flightRepository;

    private boolean dataLoaded = false;

    @PostConstruct
    public void init() {
        if (!this.dataLoaded) {
            flightRepository.save(new Flight("PKB", "CMH", 400.00));
            flightRepository.save(new Flight("PKB", "RNO", 800.00));
            this.dataLoaded = true;
        }
    }

    @ShellMethod(value = "list all flights")
    public String listFlights() throws ExecutionException, InterruptedException {
        System.out.println();
        System.out.println("Available Flights:");
        System.out.println();
        Iterable<Flight> flights = flightRepository.findAll();
        for (Flight flight : flights) {
            System.out.println(flight.getId() + ": " + flight.getFromAirport() + " > " + flight.getToAirport());
        }
        System.out.println();
        System.out.println("Total Flights: " + ((Collection<?>) flights).size());

        return "";
    }

    @ShellMethod(value="get flight details")
    public String getFlightDetails(long flightId) {
        System.out.println();
        System.out.println("Flight Details:");
        System.out.println();
        Flight flight = flightRepository.findById(flightId).get();

        System.out.println(flight.getFromAirport() + " > " + flight.getToAirport() + " - Price: " + flight.getPrice());

        return "";
    }
}
