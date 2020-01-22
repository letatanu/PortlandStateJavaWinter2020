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

  public Flight() {
    super();
    this.flightNumber = 42;
    this.src = "";
    this.dest = "";
    this.depart = "";
    this.arrive = "";
  }
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

  @Override
  public int getNumber() {
    return this.flightNumber;
  }

  @Override
  public String getSource() {
   return this.src;
  }

  @Override
  public String getDepartureString() {
    return this.depart;
  }

  @Override
  public String getDestination() {
    return this.dest;
  }

  @Override
  public String getArrivalString() {
    return this.arrive;
  }
}