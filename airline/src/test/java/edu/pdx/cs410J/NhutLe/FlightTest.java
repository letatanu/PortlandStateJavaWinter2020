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
    String departDate = "3/1/2020 12:4 pm";
    String dest = "PDX";
    String arriveDate = "12/13/2020 11:20 PM";
    Flight flight = new Flight(number,src,departDate,dest,arriveDate);
    assertThat(flight.toString(), equalTo("Flight 123 departs PDX at 3/1/20, 12:04 PM arrives PDX at 12/13/20, 11:20 PM"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void FlightWithWrongDate() {
    String number = "123";
    String src = "PDX";
    String departDate = "1/1/2020 1:2 PM";
    String dest = "PDX";
    String arriveDate = "12/10/200 11:20 PM";
    Flight flight = new Flight(number,src,departDate,dest,arriveDate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void FlightWithWrongSource() {
    String number = "123";
    String src = "AAA";
    String departDate = "1/1/2020 1:2 PM";
    String dest = "PDX";
    String arriveDate = "12/15/2020 11:20 PM";
    Flight flight = new Flight(number,src,departDate,dest,arriveDate);
  }


  @Test(expected = IllegalArgumentException.class)
  public void FlightWithWrongArrivalDateLessThanDepartDate() {
    String number = "123";
    String src = "PDX";
    String departDate = "12/12/2020 11:22 PM";
    String dest = "PDX";
    String arriveDate = "12/10/2020 11:20 PM";
    Flight flight = new Flight(number,src,departDate,dest,arriveDate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void FlightWithWrongFlightNumber() {
    String number = "A23";
    String src = "PDX";
    String departDate = "12/12/2020 11:22 PM";
    String dest = "PDX";
    String arriveDate = "12/20/2020 11:20 PM";
    Flight flight = new Flight(number,src,departDate,dest,arriveDate);
  }
}
