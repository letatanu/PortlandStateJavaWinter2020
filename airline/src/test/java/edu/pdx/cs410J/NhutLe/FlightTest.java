package edu.pdx.cs410J.NhutLe;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {
//  @Ignore
  @Test
  public void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight();
    assertThat(flight.getArrivalString(), equalTo(""));
  }
//  @Ignore
  @Test
  public void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight();
    assertThat(flight.getNumber(), equalTo(42));
  }
//  @Ignore
  @Test
  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }


  @Test(expected = IllegalArgumentException.class)
  public void DepartAirportCodeExpectToGetError() {
    Flight flight = new Flight(122,"11@A","","AAA","123213");
  }

  @Test
  public void FlightDescriptionShouldBeMatched() {
    int number = 123;
    String src = "AAA";
    String depart = "12-12-2020 11:22";
    String dest = "AAB";
    String arrive = "12-13-2020 11:20";
    Flight flight = new Flight(number,src,depart,dest,arrive);
    assertThat(flight.toString(), equalTo("Flight " + number + " departs " + src + " at " + depart + " arrives " + dest + " at " + arrive));

  }
}
