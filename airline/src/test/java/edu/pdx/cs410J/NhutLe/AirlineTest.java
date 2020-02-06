package edu.pdx.cs410J.NhutLe;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Airline} class.
 */
public class AirlineTest {
  /**
   * Tests if the class is initialized without any errors.
   */
  @Test
  public void createAirline() {
    Airline airline = new Airline("Hello Kitty");
  }

  /**
   * Tests if can getName invoked
   */

  @Test
  public void getNameShouldBeGood() {
    Airline airline = new Airline("Hello Kitty");
    assertThat(airline.getName(), equalTo("Hello Kitty"));
  }
  /**
   * Tests if can add a new valid flight
   */
  @Test
  public void addNewValidFlight() {
    String number = "123";
    String src = "PDX";
    String departDate = "12/12/2020 11:22 am" ;
    String dest = "PDX";
    String arriveDate = "12/13/2020 12:31 pm";
    Flight flight = new Flight(number, src, departDate, dest, arriveDate);
    Airline airline = new Airline("Hello Kitty");
    airline.addFlight(flight);
    Flight gotFlight = (Flight) airline.getFlights().get(0);
    assertThat(gotFlight, equalTo(flight));
  }
  /**
   * Tests for adding an invalid flight
   * It should return an error
   */
  @Test(expected = IllegalArgumentException.class)
  public void addNewInvalidFlight() {
    String number = "123df";
    String src = "AAA";
    String departDate = "12/12/2020 11:22 am" ;
    String dest = "AAB";
    String arriveDate = "12/13/2020 12:31 pm";
    Flight flight = new Flight(number, src, departDate, dest, arriveDate);
    Airline airline = new Airline("Hello Kitty");
    airline.addFlight(flight);
    Flight gotFlight = (Flight) airline.getFlights().get(0);
  }
  /**
   * Tests if we can get the number of flights from the airline
   */
  @Test
  public void printOutTheNumberOfFlightsInTheAirline() {
    String number = "123";
    String src = "PDX";
    String departDate = "12/12/2020 11:22 AM" ;
    String dest = "PDX";
    String arriveDate = "12/13/2020 12:31 PM";
    Flight flight = new Flight(number, src, departDate, dest, arriveDate);
    Airline airline = new Airline("Hello Kitty");
    airline.addFlight(flight);
    assertThat(airline.toString(),equalTo("Hello Kitty with 1 flights"));
  }

  /**
   * Tests if it can print out the information of a flight in the airline
   */
  @Test
  public void printOutTheInformationOfAFlightInTheAirline() {
    String number = "123";
    String src = "PDX";
    String departDate = "12/12/2020 11:22 AM" ;
    String dest = "PDX";
    String arriveDate = "12/13/2020 12:31 PM";
    Flight flight = new Flight(number, src, departDate, dest, arriveDate);

    Airline airline = new Airline("Hello Kitty");
    airline.addFlight(flight);

    Flight f = (Flight) airline.getFlights().get(0);
    assertThat(flight.toString(), equalTo("Flight 123 departs PDX at 12/12/20, 11:22 AM arrives PDX at 12/13/20, 12:31 PM"));
  }

}
