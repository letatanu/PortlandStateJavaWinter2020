package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Flight extends AbstractFlight implements Comparable<Flight> {
  private final int flightNumber;
  private final String src;
  private final Date departDateTime;
  private final String dest;
  private final Date arriveDateTime;
  /**
   * Creates a new <code>Flight</code> with inputs
   *
   * @param flightNumber
   *        The number of flight
   * @param src
   *        Three-letter code of departure airport
   * @param dest
   *        Three-letter code of arrival airport
   * @param departDateTime
   *        Departure date time (am/pm)
   * @param arriveDateTime
   *        Arrival date time (am/pm)
   */
  Flight(String flightNumber, String src, String departDateTime, String dest, String arriveDateTime ) throws IllegalArgumentException {
    super();
    this.flightNumber = validateNumber(flightNumber);

    this.src = validateCode(src);

    this.departDateTime = validateDate(departDateTime);

    this.dest = validateCode(dest);
    this.arriveDateTime = validateDate(arriveDateTime);
    long diff = this.timesDiffInMinutes();
    if (diff < 0 ) {
      throw new IllegalArgumentException("The arrive date time is before the departure Date time");
    }
  }

  Flight() {
    super();
    this.flightNumber = 42;
    this.src = "";
    this.dest = "";
    this.departDateTime = new Date();
    this.arriveDateTime = new Date();
  }

  /**
   * This function will validate an airport code input.
   * The code must have a length of 3 and cannot contain numbers nor special characters.
   * The code must exist in the airport names
   * if it is valid, the function will return its valid code
   */
  private String validateCode(String code) throws IllegalArgumentException {
    if (code.length() != 3 ) {
      throw new IllegalArgumentException(code + " is not 3-letter code");
    }

    for (int i=0; i<code.length();i++) {
      char c = code.charAt(i);
      if (c >= '0' && c <= '9') {
        throw new IllegalArgumentException(code + " contains number");
      }
      if (!((c>= 'A' && c<='Z') || (c>= 'a' && c<='z'))) {
        throw new IllegalArgumentException(code + " contains special character");
      }
    }

    code = code.toUpperCase();
    if (AirportNames.getName(code) == null) {
      throw new IllegalArgumentException(code + " does not exist in airport names");
    }

    return code;
  }

  /**
   * This function will validate if a string can be parsed as int
   * It will return the int value if ok.
   */
  private int validateNumber(String value) throws IllegalArgumentException {
    int tmp = 0;
    try {
      tmp = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(value + " is not an integer");
    }
    return tmp;
  }

  /**
   *
   */
  private Date validateDate(String value) throws IllegalArgumentException {
    String checkDate = "";
    Date parsedDate = null;
    try {
      SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
      format1.setLenient(false);
      parsedDate = format1.parse(value);
      checkDate = format1.format(parsedDate);
    } catch (ParseException e1) {
      try {
        SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy h:mm aa");
        format2.setLenient(false);
        parsedDate = format2.parse(value);
        checkDate = format2.format(parsedDate);
      } catch (ParseException e2) {
        try {
          SimpleDateFormat format3 = new SimpleDateFormat("M/dd/yyyy hh:mm aa");
          format3.setLenient(false);
          parsedDate = format3.parse(value);
          checkDate = format3.format(parsedDate);
        } catch (ParseException e3) {
          try {
            SimpleDateFormat format4 = new SimpleDateFormat("M/dd/yyyy h:mm aa");
            format4.setLenient(false);
            parsedDate = format4.parse(value);
            checkDate = format4.format(parsedDate);
          } catch (ParseException e4) {
            throw new IllegalArgumentException(value + " is not in correct date format");
          }
        }
      }
    }
    if (parsedDate == null) {
      throw new IllegalArgumentException(value + " cannot be parsed to date format");
    }

    return parsedDate;
  }

  /**
   *
   */
  public long timesDiffInMinutes() {
    long diff = this.arriveDateTime.getTime() - this.departDateTime.getTime();
    return diff/(1000*60);
  }
  /** getting the flight number
   *
   * @return
   */
  @Override
  public int getNumber() {
    return this.flightNumber;
  }

  /** getting the departure airport code
   *
   * @return
   */

  @Override
  public String getSource() {
   return this.src;
  }

  /** getting the depart time
   *
   * @return
   */
  @Override
  public String getDepartureString() {
    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
    String depart = df.format(this.departDateTime).toString();
    return depart;
  }

  /** getting the arrival airport code
   *
   * @return
   */
  @Override
  public String getDestination() {
    return this.dest;
  }

  /** getting the arrival time
   *
   * @return
   */
  @Override
  public String getArrivalString() {
    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
    String arrival = df.format(this.arriveDateTime).toString();
    return arrival;
  }

  /**
   * Get departure time
   * @return
   */

  @Override
  public Date getDeparture() {
    return this.departDateTime;
  }

  /**
   * Get arrival time
   * @return
   */
  @Override
  public Date getArrival() {
    return this.arriveDateTime;
  }

  /**
   * Compare 2 flights. 
   * @param o
   * @return compare
   */

  @Override
  public int compareTo(Flight o) {
    Flight compares = (Flight) o;
    int compare = this.src.compareTo(compares.getSource());
    if (compare == 0) {
      compare = this.departDateTime.compareTo(compares.getDeparture());
    }
    return compare;
  }

  public Date getDepartDateTime() {
    return this.departDateTime;
  }

  public Date getArriveDateTime() {
    return this.arriveDateTime;
  }

}
