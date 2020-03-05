package edu.pdx.cs410J.NhutLe;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
  static final String AIRLINE_NAME_PARAMETER = "airline";
  static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";
  static final String SRC_PARAMETER = "src";
  static final String DEPART_DATETIME_PARAMETER = "departDateTime";
  static final String DEST_PARAMETER = "dest";
  static final String ARRIVE_DATETIME_PARAMETER = "arriveDateTime";


  private final Map<String, Airline> airlines = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing the definition of the
   * word specified in the "word" HTTP parameter to the HTTP response.  If the
   * "word" parameter is not specified, all of the entries in the dictionary
   * are written to the HTTP response.
   */
  private ArrayList<String> dateToString(Date value) throws IOException {
    String a = new SimpleDateFormat("MM/dd/YYYY HH:mm").format(value);
    ArrayList<String> result = new ArrayList<String>();
    try {
      String[] a_ = a.split(" ");
      String aDate = a_[0];
      String aTime = a_[1];
      result.add(aDate.split("/")[0]);
      result.add(aDate.split("/")[1]);
      result.add(aDate.split("/")[2]);
      result.add(aTime.split(":")[0]);
      result.add(aTime.split(":")[1]);
    } catch (IndexOutOfBoundsException ignored) {
      throw new IOException("Date is malformed");
    }

    return result;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");

    String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request);
    if (airlineName == null) {
      missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
      return;
    }
    String src = getParameter(SRC_PARAMETER, request);
    String dest = getParameter(DEST_PARAMETER, request);
    if (src != null && dest == null) {
      missingRequiredParameter(response, DEST_PARAMETER);
      return;
    }
    if (src == null && dest != null) {
      missingRequiredParameter(response, SRC_PARAMETER);
      return;
    }


    try {
      XmlParser xmlParser = new XmlParser(airlineName);
      Airline airline = (Airline) xmlParser.parse();

      if (src != null && dest != null) {
        Airline newAirline = new Airline(airlineName);
        for (AbstractFlight f : airline.getFlights()) {
          Flight flight = (Flight) f;
          if (flight.getSource().equals(src) && flight.getDestination().equals(dest)) {
            newAirline.addFlight(flight);
          }
        }
        airline = newAirline;
      }
      /**
       * return airline in xml format
       */
      response.setContentType("text/plain");
      PrintWriter out = response.getWriter();
      StringBuilder writer = new StringBuilder();
      writer.append("<?xml version='1.0' encoding='us-ascii'?>\n" +
                        "<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">\n");
      writer.append("<airline>");
      writer.append("\n");

      writer.append("<name>" + airlineName + "</name>");
      writer.append("\n");

      for (AbstractFlight f : airline.getFlights()) {
        Flight flight = (Flight) f;
        writer.append("<flight>");
        writer.append("\n");

        writer.append("<number>" + Integer.toString(flight.getNumber()) + "</number>");
        writer.append("\n");

        writer.append("<src>" + flight.getSource() + "</src>");
        writer.append("\n");

        writer.append("<depart>");
        writer.append("\n");

        // need to fix depart string
        ArrayList<String> depart = dateToString(flight.getDepartDateTime());
        String departDate = String.format("<date day=\"%s\" month=\"%s\" year=\"%s\"/>\n" + "<time hour=\"%s\" minute=\"%s\"/>", depart.get(1), depart.get(0), depart.get(2), depart.get(3), depart.get(4));
        writer.append(departDate);
        writer.append("\n");

        writer.append("</depart>");
        writer.append("\n");

        writer.append("<dest>" + flight.getDestination() + "</dest>");
        writer.append("\n");

        writer.append("<arrive>");
        writer.append("\n");
        ArrayList<String> arrive = dateToString(flight.getArriveDateTime());
        String arriveDate = String.format("<date day=\"%s\" month=\"%s\" year=\"%s\"/>\n" + "<time hour=\"%s\" minute=\"%s\"/>", arrive.get(1), arrive.get(0), arrive.get(2), arrive.get(3), arrive.get(4));
        writer.append(arriveDate);
        writer.append("\n");
        writer.append("</arrive>");
        writer.append("\n");

        writer.append("</flight>");
        writer.append("\n");
      }

      writer.append("</airline>");
      out.println(writer);
      out.flush();
      response.setStatus(HttpServletResponse.SC_OK);

    } catch (ParserException e) {
      if (e.getMessage().equals("XML File is created successfully")) {
        File f = new File(airlineName);
        f.delete();
      }
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "The airline name does not exist");
    }

//        Airline airline = getAirline(airlineName);
//        XmlDumper dumper = new XmlDumper(airlineName);
//        dumper.dump(airline);
  }

  /**
   * Handles an HTTP POST request by storing the dictionary entry for the
   * "word" and "definition" request parameters.  It writes the dictionary
   * entry to the HTTP response.
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request);
    if (airlineName == null) {
      missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
      return;
    }

    Airline airline = getOrCreateAirline(airlineName);

    String flightNumber = getParameter(FLIGHT_NUMBER_PARAMETER, request);
    if (flightNumber == null) {
      missingRequiredParameter(response, FLIGHT_NUMBER_PARAMETER);
      return;
    }

    String src = getParameter(SRC_PARAMETER, request);
    if (src == null) {
      missingRequiredParameter(response, SRC_PARAMETER);
      return;
    }

    String departDateTime = getParameter(DEPART_DATETIME_PARAMETER, request);
    if (departDateTime == null) {
      missingRequiredParameter(response, DEPART_DATETIME_PARAMETER);
      return;
    }

    String dest = getParameter(DEST_PARAMETER, request);
    if (dest == null) {
      missingRequiredParameter(response, DEST_PARAMETER);
      return;
    }

    String arriveDateTime = getParameter(ARRIVE_DATETIME_PARAMETER, request);
    if (arriveDateTime == null) {
      missingRequiredParameter(response, ARRIVE_DATETIME_PARAMETER);
      return;
    }

    Flight flight = new Flight(flightNumber, src, departDateTime, dest, arriveDateTime);
    try {
      XmlParser xmlParser = new XmlParser(airlineName);
      airline = (Airline) xmlParser.parse();
      if (!airline.getName().equals(airlineName)) {
        System.err.println("The airline name is not as the same in XML file");
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "The airline name is not as the same in XML file");
        return;
      }

      airline.addFlight(flight);
      XmlDumper xmlDumper = new XmlDumper(airlineName);
      xmlDumper.dump(airline);

    } catch (ParserException | IOException e) {
      if (e.getMessage().equals("XML File is created successfully")) {
        airline = new Airline(airlineName);
        airline.addFlight(flight);
        XmlDumper xmlDumper = new XmlDumper(airlineName);
        xmlDumper.dump(airline);
      } else {
        System.err.println(e.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        return;
      }
    }


    response.setStatus(HttpServletResponse.SC_OK);
  }

  private Airline getOrCreateAirline(String airlineName) {
    Airline airline = getAirline(airlineName);
    if (airline == null) {
      airline = new Airline(airlineName);
      this.airlines.put(airlineName, airline);
    }

    return airline;
  }

  /**
   * Handles an HTTP DELETE request by removing all dictionary entries.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");

    this.airlines.clear();

    PrintWriter pw = response.getWriter();
    pw.println(Messages.allDictionaryEntriesDeleted());
    pw.flush();

    response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   * <p>
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter(HttpServletResponse response, String parameterName)
      throws IOException {
    String message = Messages.missingRequiredParameter(parameterName);
    response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }


  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   * <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;
    } else {
      return value;
    }
  }

  @VisibleForTesting
  Airline getAirline(String airlineName) {
    return this.airlines.get(airlineName);
  }
}