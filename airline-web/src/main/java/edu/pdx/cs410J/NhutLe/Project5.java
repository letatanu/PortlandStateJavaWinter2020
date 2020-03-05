package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

  public static final String MISSING_ARGS = "Missing command line arguments";

  public static void main(String... args) {
    /**
     * This flag will be turned on when option '-host' is input.
     */
    boolean hostFlag = false;


    /**
     * @param hostName Name of host
     *
     */
    String hostName = null;


    /**
     * This flag will be turned on when option '-port' is input.
     */
    boolean portFlag = false;


    /**
     * @param portName Name of port
     *
     */
    String portName = null;


    /**
     * This variable will make sure the option input consecutively
     */
    boolean stillOptionInput = true;

    /**
     * This flag will be turned on when option '-print' is input.
     */
    boolean printFlag = false;

    /**
     * This flag will be turned on when option '-search' is input.
     */
    boolean searchFlag = false;


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
          String Readme = "# Project 5 \n" +
                              " In this project you will extend your airline application to support an airline server that provides REST-ful"
                              + "web services to an airline client.";
          System.out.println(Readme);
          System.exit(0);
        } else if (option.equals("print") && stillOptionInput) {
          printFlag = true;
        } else if (option.equals("host")) {
          if (i + 1 >= args.length) {
            System.err.println("The host name is missing...");
            System.exit(1);
          }
          hostFlag = true;
          hostName = args[i + 1];
          i++;
        } else if (option.equals("port")) {
          if (i + 1 >= args.length) {
            System.err.println("The port name is missing...");
            System.exit(1);
          }
          portFlag = true;
          portName = args[i + 1];
          i++;
        } else if (option.equals("search")) {
          searchFlag = true;
        } else {
          System.err.println("Unknown command line options");
          System.exit(1);
        }
      } else {
        stillOptionInput = false;
        newArgs.add(arg);
      }
    }
    int port = 0;
    if (hostFlag) {
      if (!portFlag) {
        System.err.println("A host without a port");
        System.exit(1);
      } else {
        try {
          port = Integer.parseInt(portName);
        } catch (NumberFormatException ex) {
          System.err.println("Port \"" + portName + "\" must be an integer");
          System.exit(1);
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);
        /**
         * Adding a new flight
         */
        if (!searchFlag) {
          if (newArgs.size() == 1) {

            /** Getting airline name from the argument 0
             *
             */
            String airlineName = newArgs.get(0);

            try {
              String response = client.getAirlineAsXml(airlineName);
              Airline airline = parseAirlineFromXML(response);
              PrettyPrinter prt = new PrettyPrinter("-");
              prt.dump(airline);
              System.exit(0);
            } catch (IOException | ParserException ex) {
              System.err.println("Error while contacting server: " + ex);
              System.exit(1);
            }
          } else if (newArgs.size() < 10) {
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
            /**
             * Creating a new airLine if it doesnt exist or adding a flight to the existing airLine
             * post it to the server
             */
            client.addFlight(airlineName, flightNumber, src, departDateTime, dest, arriveDateTime);
            System.out.println("The airline is added successfully");
            System.exit(0);
          } catch (IOException ex) {
            System.err.println("Error while contacting server: " + ex);
            System.exit(1);
          }

        } else {
          if (newArgs.size() < 3) {
            System.err.println("Missing command line arguments");
            System.exit(1);
          } else if (newArgs.size() > 3) {
            System.err.println("Unknown command line arguments");
            System.exit(1);
          } else if (newArgs.size() == 3) {

            /** Getting airline name from the argument 0
             *
             */
            String airlineName = newArgs.get(0);
            /**
             * Getting <code>source</code>
             */
            String src = newArgs.get(1);
            /**
             * Getting <code>destination</code>
             */
            String dest = newArgs.get(2);

            try {
              String response = client.getFlightsInTheAirlineBySrcAndDestAsXml(airlineName, src, dest);
              Airline airline = parseAirlineFromXML(response);
              PrettyPrinter prt = new PrettyPrinter("-");
              prt.dump(airline);
            } catch (IOException | ParserException ex) {
              System.err.println("Error while contacting server: " + ex);
              System.exit(1);
            }
          }
        }

      }
    }

    System.exit(0);
  }

  private static String getDateTime(Element node) throws ParserException {
    try {
      Element departDateEle = (Element) node.getElementsByTagName("date").item(0);
      String departDate = String.format("%s/%s/%s", departDateEle.getAttribute("month"), departDateEle.getAttribute("day"), departDateEle.getAttribute("year"));
      Element departTimeEle = (Element) node.getElementsByTagName("time").item(0);
      String time = String.format("%s:%s", departTimeEle.getAttribute("hour"), departTimeEle.getAttribute("minute"));
      final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
      final Date time_ = sdf.parse(time);
      String time_12 = new SimpleDateFormat("hh:mm aa").format(time_);
      return departDate + " " + time_12.toLowerCase();
    } catch (Exception e) {
      throw new ParserException("Cannot parse Date");
    }

  }

  private static Airline parseAirlineFromXML(String content) throws ParserException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setValidating(true);
    DocumentBuilder builder = null;
    if (content != null) {
      try {
        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(content)));
        doc.getDocumentElement().normalize();
        String airlineName = doc.getElementsByTagName("name").item(0).getTextContent();
        Airline airline = new Airline(airlineName);

        NodeList flightNodes = doc.getElementsByTagName("flight");
        for (int i = 0; i < flightNodes.getLength(); i++) {
          Node flightNode = flightNodes.item(i);

          if (flightNode.getNodeType() == Node.ELEMENT_NODE) {
            Element flightElement = (Element) flightNode;
            String number = flightElement.getElementsByTagName("number").item(0).getTextContent();
            String src = flightElement.getElementsByTagName("src").item(0).getTextContent();
            Element depart = (Element) flightElement.getElementsByTagName("depart").item(0);
            String departDateTime = getDateTime(depart);
            String dest = flightElement.getElementsByTagName("dest").item(0).getTextContent();
            Element arrive = (Element) flightElement.getElementsByTagName("arrive").item(0);
            String arriveDateTime = getDateTime(arrive);

            Flight flight = new Flight(number, src, departDateTime, dest, arriveDateTime);
            airline.addFlight(flight);
          }
        }
        return airline;

      } catch (SAXException | IOException | ParserConfigurationException e) {
        throw new ParserException("The XML is malformed");
      }
    }
    return null;
  }

}