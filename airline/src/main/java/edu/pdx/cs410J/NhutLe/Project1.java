package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractFlight;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
  public static void main(String[] args) {

    /** Check if the option <code>-README</code> is input.
    * If yes, print out <code> README </code> and exit.
    */
    for (String arg : args) {
      if (arg.charAt(0) == '-') {
        String option = arg.substring(1);
        if (option.equals("README")) {
          String Readme = "# Project 1 \n" +
                              "This project will implement a class Airline that extends AbstractAirline " +
                              "and a class Flight that extends AbstractFlight. \n" +
                              "An Airline has a name and consists of multiple Flights. A Flight departs from a source and leaves " +
                              "at a given departure time, and \narrives at a destination at a given arrival time";
          System.out.println(Readme);
          System.exit(0);
        }
      }
    }

    /** If there's no option <code>README</code>. Check the number of arguments input.
     *
     */
    if (args.length < 6) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    /** Getting airline name from the argument 0
     *
     */
    String airlineName = args[0];

    /** try to parse <code>flightNumber<code> from the argument 1 to integer.
     *
     */
    try {
      /**
       * Getting flight number from the argument 1
       */
      int flightNumber = Integer.parseInt(args[1]);

      /**
       * Getting <code>source</code>
       */
      String src = args[2];

      /**
       * Getting <code>depart</code>
       */
      String depart = args[3];

      /**
       * Getting <code>destination</code>
       */
      String dest = args[4];

      /**
       * Getting <code>arrival</code>
       */
      String arrive = args[5];

      /**
       * Create a new flight from the input
       */
      Flight flight = new Flight(flightNumber, src, depart, dest, arrive);

      /**
       * create an airline and add the flight to the airline
       */
      Airline airline = new Airline(airlineName);
      airline.addFlight(flight);

      /** Check if "print" option is input
       * This function will check if <code>print</code> option is input.
       * If yes, it will print all the information of flights in the airline.
       */
      for (String arg : args) {
        if (arg.charAt(0) == '-') {
          String option = arg.substring(1);
          if (option.equals("print")) {
            System.out.println("Airline: " + airline.getName());
            for (AbstractFlight f : airline.getFlights()) {
              System.out.println(f.toString());
            }
          }
        }
      }

    } catch (NumberFormatException e) {
      System.err.println("The flight number must be an integer");
      System.exit(1);
    }

    System.exit(0);
  }
}