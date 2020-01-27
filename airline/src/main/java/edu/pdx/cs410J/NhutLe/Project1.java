package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractFlight;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
    public static void main(String[] args) {
        /**
         * This flag will be turned on when option '-print' is input.
         */
        boolean printFlag = false;
        /** Check if the option <code>-README</code> or <code>-print</code> or both is input.
         * If yes <code>-README</code>, print out <code> README </code> and exit.
         * If yes <code>-print</code>, set printFlag = true.
         */
        ArrayList<String> newArgs = new ArrayList<String>();
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
                } else if (option.equals("print")) {
                    printFlag = true;
                } else {
                    System.err.println("Unknown command line options");
                    System.exit(1);
                }
            } else {
                newArgs.add(arg);
            }
        }

        /** If there's no option <code>README</code>. Check the number of arguments input.
         *
         */
        if (newArgs.size() < 8) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        } else if (newArgs.size() > 8){
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
         * Getting <code>destination</code>
         */
        String dest = newArgs.get(5);

        /**
         * Getting <code>arrival</code>
         */
        String arriveDate = newArgs.get(6);

        /**
         * Getting <code>arrivalTime</code>
         */
        String arriveTime = newArgs.get(7);

        /**
         * Create a new flight from the input
         */
        try {
            Flight flight = new Flight(flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);

            /**
             * create an airline and add the flight to the airline
             */
            Airline airline = new Airline(airlineName);
            airline.addFlight(flight);

            /** Check if "print" option is input
             * This function will check if <code>print</code> option is input.
             * If yes, it will print all the information of flights in the airline.
             */
            if (printFlag) {
                System.out.println("Airline: " + airline.getName());
                for (AbstractFlight f : airline.getFlights()) {
                    System.out.println(f.toString());
                }
                System.exit(0);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }
}