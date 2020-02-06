package edu.pdx.cs410J.NhutLe;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

  @Test
  public void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight();
    assertThat(flight.getNumber(), equalTo(42));
  }



  @Test(expected = IllegalArgumentException.class)
  public void DepartAirportCodeExpectToGetError() {
    Flight flight = new Flight("123", "PX", "03/03/2020 12:00 am", "OPD", "09/09/2020 16:00 pm");
  }

  @Test
  public void FlightDescriptionShouldBeMatched() {
    String number = "123";
    String src = "PDX";
    String departDate = "12/12/2020 11:22 PM";
    String dest = "PDX";
    String arriveDate = "12/13/2020 11:20 PM";
    Flight flight = new Flight(number,src,departDate,dest,arriveDate);
    assertThat(flight.toString(), equalTo("Flight 123 departs PDX at 12/12/20, 11:22 PM arrives PDX at 12/13/20, 11:20 PM"));
  }
}
