package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractFlight;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Flight extends AbstractFlight {
  private final int flightNumber;
  private final String src;
  private final String depart;
  private final String dest;
  private final String arrive;
  /*
   * Creates a new <code>Flight</code>
   *
   * @param flightNumber
   *        The number of flight
   * @param src
   *        Three-letter code of departure airport
   * @param dest
   *        Three-letter code of arrival airport
   * @param depart
   *        Departure date and time (24-hour time)
   * @param arrive
   *        Arrival date and time (24-hour time)
   */
  public Flight() {
    super();
    this.flightNumber = 42;
    this.src = "";
    this.dest = "";
    this.depart = "";
    this.arrive = "";
  }
  /*
  * This function will validate an airport code input.
  * The code must have a length of 3 and cannot contain numbers nor special characters.
   */
  private void ValidateCode(String code) throws IllegalArgumentException {
    if (code.length() != 3 ) {
      throw new IllegalArgumentException(src + " is not 3-letter code");
    }
    Pattern codeFormat = Pattern.compile("[a-zA-Z]");
    Matcher isLetter = codeFormat.matcher(code);
    if (!isLetter.find()) {
      throw  new IllegalArgumentException(src + " contains numbers or special characters");
    }
  }

  /*
   * Creates a new <code>Flight</code> with inputs
   *
   * @param flightNumber
   *        The number of flight
   * @param src
   *        Three-letter code of departure airport
   * @param dest
   *        Three-letter code of arrival airport
   * @param depart
   *        Departure date and time (24-hour time)
   * @param arrive
   *        Arrival date and time (24-hour time)
   */
  public Flight(int flightNumber, String src, String depart, String dest, String arrive ) {
    super();
    this.flightNumber = flightNumber;

    ValidateCode(src);
    ValidateCode(dest);

    this.src = src;
    this.depart = depart;
    this.dest = dest;
    this.arrive = arrive;
  }

  // getting the flight number
  @Override
  public int getNumber() {
    return this.flightNumber;
  }
  // getting the departure airport code
  @Override
  public String getSource() {
   return this.src;
  }
  // getting the depart time
  @Override
  public String getDepartureString() {
    return this.depart;
  }
  // getting the arrival airport code
  @Override
  public String getDestination() {
    return this.dest;
  }
  // getting the arrival time
  @Override
  public String getArrivalString() {
    return this.arrive;
  }
}
