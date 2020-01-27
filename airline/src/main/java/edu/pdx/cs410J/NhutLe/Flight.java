package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractFlight;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class Flight extends AbstractFlight {
  private final int flightNumber;
  private final String src;
  private final String departDate;
  private final String departTime;
  private final String dest;
  private final String arriveDate;
  private final String arriveTime;
  /**
   * Creates a new <code>Flight</code> with inputs
   *
   * @param flightNumber
   *        The number of flight
   * @param src
   *        Three-letter code of departure airport
   * @param dest
   *        Three-letter code of arrival airport
   * @param departDate
   *        Departure date
   * @param departTime
   *        Departure time (24-hour time)
   * @param arriveDate
   *        Arrival date
   * @param arriveTime
   *        Arrival time (24-hour time)
   */
  Flight(String flightNumber, String src, String departDate, String departTime, String dest, String arriveDate, String arriveTime ) throws IllegalArgumentException {
    super();
    this.flightNumber = validateNumber(flightNumber);

    this.src = validateCode(src);

    this.departDate = validateDate(departDate);
    this.departTime = validateTime(departTime);

    this.dest = validateCode(dest);
    this.arriveDate = validateDate(arriveDate);
    this.arriveTime = validateTime(arriveTime);
  }

  Flight() {
    super();
    this.flightNumber = 42;
    this.src = "";
    this.dest = "";
    this.departDate = "";
    this.departTime = "";
    this.arriveDate = "";
    this.arriveTime = "";
  }

  /**
   * This function will validate an airport code input.
   * The code must have a length of 3 and cannot contain numbers nor special characters.
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
  private String validateDate(String value) throws IllegalArgumentException {
    String checkDate = "";
    try {
      SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
      format1.setLenient(false);
      Date parsedDate = format1.parse(value);
      checkDate = format1.format(parsedDate);
    } catch (ParseException e) {
      try {
        SimpleDateFormat format2 = new SimpleDateFormat("M/dd/yyyy");
        format2.setLenient(false);
        Date parsedDate = format2.parse(value);
        checkDate = format2.format(parsedDate);
      } catch (ParseException e1) {
        throw new IllegalArgumentException(value + " is not in correct date format");
      }
    }
    if (!value.equals(checkDate)) {
      throw new IllegalArgumentException(value + " cannot be parsed to date format");
    }
    return value;
  }

  /**
   *
   */
  private String validateTime(String value) throws IllegalArgumentException {
    String checkDate = "";
    try {
      SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
      format1.setLenient(false);
      Date parsedDate = format1.parse(value);
      checkDate = format1.format(parsedDate);
    } catch (ParseException e) {
      try {
        SimpleDateFormat format2 = new SimpleDateFormat("H:mm");
        format2.setLenient(false);
        Date parsedDate = format2.parse(value);
        checkDate = format2.format(parsedDate);
      } catch (ParseException e1) {
        throw new IllegalArgumentException(value + " is not in correct time format");
      }
    }
    if (!value.equals(checkDate)) {
      throw new IllegalArgumentException(value + " cannot be parsed to time format");
    }
    return value;
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
    return this.departDate + " " + this.departTime;
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
    return this.arriveDate + " " + this.arriveTime;
  }
}
