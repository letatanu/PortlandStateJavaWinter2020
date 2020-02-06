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
  public void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight();
    assertThat(flight.getArrivalString(), equalTo(" "));
  }

  @Test
  public void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight();
    assertThat(flight.getNumber(), equalTo(42));
  }
  @Test
  public void getSrcStringNeedsToBeImplemented() {
    Flight flight = new Flight();
    assertThat(flight.getSource(), equalTo(""));
  }

  @Test
  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void DepartAirportCodeExpectToGetError() {
    Flight flight = new Flight("123", "PX", "03/03/2020 12:00 am", "OPD", "09/09/2020 16:00 pm");
  }

  @Test
  public void FlightDescriptionShouldBeMatched() {
    String number = "123";
    String src = "AAA";
    String departDate = "12/12/2020 11:22 pm";
    String dest = "AAB";
    String arriveDate = "12/13/2020 11:20 pm";
    Flight flight = new Flight(number,src,departDate,dest,arriveDate);
    assertThat(flight.toString(), equalTo("Flight " + number + " departs " + src + " at " + departDate  + " arrives " + dest + " at " + arriveDate));

  }
}
