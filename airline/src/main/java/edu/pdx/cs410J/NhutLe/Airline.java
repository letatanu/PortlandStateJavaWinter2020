package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;
/*
* The Airline class extends the <code>AbstractAirline</code>.
* It will contain the information of all flights of an <code>Airline</code>.
 */
public class Airline extends AbstractAirline {
    private final String name;
    private ArrayList<AbstractFlight> Flights;

    /*
     * Creates a new <code>Airline</code>
     *
     * @param name
     *        The airline's name
     * @param Flights
     *        The information of the flights in the Airline.
     */
    public Airline(String name) {
        super();
        this.Flights = new ArrayList<AbstractFlight>();
        this.name = name;
    }
    /*
    * Adding a new <code>Flight</code> to <code>Flights</code>
     */
    @Override
    public void addFlight(AbstractFlight abstractFlight) {
        this.Flights.add(abstractFlight);
    }

    // getting the name of the airline
    @Override
    public String getName() {
        return this.name;
    }

    // getting all flights in the airline
    @Override
    public ArrayList<AbstractFlight> getFlights() {
        return this.Flights;
    }
}
