package edu.pdx.cs410J.NhutLe;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
public class AirlineTest {
  @Test
  public void createAirline() {
    Airline airline = new Airline("Hello Kitty");
    assertThat(airline.getName(), equalTo("Hello Kitty"));
  }

  @Test
  public void addNewFlight() {
    int number = 123;
    String src = "AAA";
    String depart = "12-12-2020 11:22";
    String dest = "AAB";
    String arrive = "12-13-2020 11:20";
    Flight flight = new Flight(number,src,depart,dest,arrive);
    Airline airline = new Airline("Hello Kitty");
    airline.addFlight(flight);
    Flight gotFlight = (Flight) airline.getFlights().get(0);
    assertThat(gotFlight, equalTo(flight));
  }

}
