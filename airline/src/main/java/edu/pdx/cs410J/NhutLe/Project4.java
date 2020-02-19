package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The main class for the CS410J airline Project
 */
public class Project4 {
  public static void main(String[] args) {
    /**
     * This flag will be turned on when option '-print' is input.
     */
    boolean printFlag = false;

    /**
     * This variable will make sure the option input consecutively
     */
    boolean stillOptionInput = true;

    /**
     * @param fileName Name of text file
     *
     */
    String fileName = null;

    /**
     * @param filePrint Name of text file
     *
     */
    String filePrint = null;
    /**
     * @param xmlFile Name of XML file
     */
    String xmlFile = null;

    /** Check if the option <code>-README</code> or <code>-print</code> or both is input.
     * If yes <code>-README</code>, print out <code> README </code> and exit.
     * If yes <code>-print</code>, set printFlag = true.
     */
    ArrayList<String> newArgs = new ArrayList<String>();
    for (int i = 0; i < args.length; i++) {
      String arg = args[i];

      if (arg.length() < 1) {
        System.err.println("Unknown command");
        System.exit(1);
      } else if (arg.charAt(0) == '-') {
        String option = arg.substring(1);
        if (option.equals("README") && stillOptionInput) {
          String Readme = "# Project 4 \n" +
                  "This project will implement a class Airline that extends AbstractAirline " +
                  "and a class Flight that extends AbstractFlight. \n" +
                  "An Airline has a name and consists of multiple Flights. A Flight departs from a source and leaves " +
                  "at a given departure time, and \narrives at a destination at a given arrival time";
          System.out.println(Readme);
          System.exit(0);
        } else if (option.equals("print") && stillOptionInput) {
          printFlag = true;
        } else if (option.equals("textFile")) {
          if (i + 1 >= args.length) {
            System.err.println("The saving file name is missing...");
            System.exit(1);
          }
          fileName = args[i + 1];
          i++;
        } else if (option.equals("pretty")) {
          if (i + 1 >= args.length) {
            System.err.println("The printing file name is missing...");
            System.exit(1);
          }
          filePrint = args[i + 1];
          i++;
        } else if (option.equals("xmlFile")) {
          if (i + 1 >= args.length) {
            System.err.println("The XML file name is missing...");
            System.exit(1);
          }
          xmlFile = args[i + 1];
          i++;
        } else {
          System.err.println("Unknown command line options");
          System.exit(1);
        }
      } else {
        stillOptionInput = false;
        newArgs.add(arg);
      }
    }

    /** If there's no option <code>README</code>. Check the number of arguments input.
     *
     */
    if (newArgs.size() < 10) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    } else if (newArgs.size() > 10) {
      System.err.println("Unknown command line arguments");
      System.exit(1);
    }

    /** Getting airline name from the argument 0
     *
     */
    String airlineName = newArgs.get(0);

    /** try to parse <code>flightNumber<code> from the argument 1 to integer.
     *
     */

    /**
     * Getting flight number from the argument 1
     */
    String flightNumber = newArgs.get(1);

    /**
     * Getting <code>source</code>
     */
    String src = newArgs.get(2);

    /**
     * Getting <code>departDate</code>
     */
    String departDate = newArgs.get(3);

    /**
     * Getting <code>departTime</code>
     */
    String departTime = newArgs.get(4);

    /**
     * Getting <code>departTimeAA</code>
     */
    String departTimeAA = newArgs.get(5);

    /**
     * Getting <code>destination</code>
     */
    String dest = newArgs.get(6);

    /**
     * Getting <code>arrival</code>
     */
    String arriveDate = newArgs.get(7);

    /**
     * Getting <code>arrivalTime</code>
     */
    String arriveTime = newArgs.get(8);
    /**
     * Getting <code>departTimeAA</code>
     */
    String arriveTimeAA = newArgs.get(9);
    /**
     * Create a new flight from the input
     */
    String departDateTime = departDate + ' ' + departTime + ' ' + departTimeAA;
    String arriveDateTime = arriveDate + ' ' + arriveTime + ' ' + arriveTimeAA;

    try {
      Flight flight = new Flight(flightNumber, src, departDateTime, dest, arriveDateTime);

      /**
       * create an airline and add the flight to the airline
       */
      Airline airline = null;

      /**
       * If fileName is given, it will handle file processing....
       */
      if (fileName != null) {
        TextParser textParser = new TextParser(fileName);
        try {
          airline = (Airline) textParser.parse();
          if (!airline.getName().equals(airlineName)) {
            System.err.println("The airline name is not as same as the airline name from the file");
            System.exit(1);
          }
          // Airline adds new flight
          airline.addFlight(flight);
          TextDumper textDumper = new TextDumper(fileName);
          try {
            textDumper.dump(airline);
          } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
          }
        } catch (ParserException e) {
          if (e.getMessage().equals("File is created successfully") || e.getMessage().equals("File is empty")) {
            airline = new Airline(airlineName);
            airline.addFlight(flight);
            TextDumper textDumper = new TextDumper(fileName);
            try {
              textDumper.dump(airline);
            } catch (IOException err) {
              System.err.println(err.getMessage());
              System.exit(1);
            }
          } else {
            System.err.println(e.getMessage());
            System.exit(1);
          }
        }
      } else {
        airline = new Airline(airlineName);
        airline.addFlight(flight);
      }
      /**
       * XML File
       */
      if (xmlFile != null) {
        try {
          XmlParser xmlParser = new XmlParser(xmlFile);
          airline = (Airline) xmlParser.parse();
          if (!airline.getName().equals(airlineName)) {
            System.err.println("The airline name is not as same as the airline name from the XML file");
            System.exit(1);
          }
          airline.addFlight(flight);
          XmlDumper xmlDumper = new XmlDumper(xmlFile);
          xmlDumper.dump(airline);
        } catch (ParserException | IOException e) {
          if (e.getMessage().equals("XML File is created successfully")) {
            airline = new Airline(airlineName);
            airline.addFlight(flight);
            XmlDumper xmlDumper = new XmlDumper(xmlFile);
            xmlDumper.dump(airline);
          } else {
            System.err.println(e.getMessage());
            System.exit(1);
          }
        }
      }
      /** Check if "print" option is input
       * This function will check if <code>print</code> option is input.
       * If yes, it will print all the information of flights in the airline.
       */
      if (printFlag || filePrint != null) {
        if (printFlag) {
          System.out.println("Airline: " + airline.getName());
          ArrayList<AbstractFlight> flightList = airline.getFlights();
          AbstractFlight lastFlight = flightList.get(flightList.size() - 1);
          System.out.println("The last flight: " + lastFlight.toString());
        }
        if (filePrint != null) {
          PrettyPrinter prt = new PrettyPrinter(filePrint);
          prt.dump(airline);
        }
        System.exit(0);
      }

    } catch (IllegalArgumentException | IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    System.exit(0);
  }
}