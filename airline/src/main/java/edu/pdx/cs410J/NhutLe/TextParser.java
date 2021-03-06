package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TextParser implements AirlineParser  {
  private final String fileName;

  TextParser(String fileName) {
    this.fileName = fileName;
  }
  @Override
  public AbstractAirline parse() throws ParserException  {
    BufferedReader bufferedReader = null;
    Airline parsedAirline = null;
    String airlineName = null;
    try {
      FileReader fileReader = new FileReader(this.fileName);
      bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();
      if (line == null) {
        throw new ParserException("File is empty");
      }
      while (line != null) {
        if (airlineName == null) {
          airlineName = line;
          parsedAirline = new Airline(airlineName);
          line = bufferedReader.readLine();
        }
        else {
          /**
           * Parse Flight information
           */
          String flightNumber = line;
          line = bufferedReader.readLine();
          if (line == null) {
            throw new ParserException("Wrong Format");
          }
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm aa");
          String src = line;
          line = bufferedReader.readLine();
          if (line == null) {
            throw new ParserException("Wrong Format");
          }

          String[] departDateTimeTest = line.split(" ");
          if (departDateTimeTest.length != 3) {
            throw new ParserException("Wrong format");
          }

          String departDateTime = line;
          line = bufferedReader.readLine();

          if (line == null) {
            throw new ParserException("Wrong Format");
          }

          String dest = line;
          line = bufferedReader.readLine();
          if (line == null) {
            throw new ParserException("Wrong Format");
          }

          String[] arriveDateTimeTest = line.split(" ");
          if (arriveDateTimeTest.length != 3) {
            throw new ParserException("Wrong format");
          }

          String arriveDateTime = line;

          Flight flight = new Flight(flightNumber, src, departDateTime, dest, arriveDateTime);
          parsedAirline.addFlight(flight);
          line = bufferedReader.readLine();
        }
      }
    }
    catch (FileNotFoundException e) {
      /**
       * If reading file does not exist, try to create a new one.
       */
      try {
        File file = new File(this.fileName);
        if (file.createNewFile()) {
          throw new ParserException("File is created successfully");
        }
      }
      catch (IOException e1) {
        throw new ParserException("File does not exist and cannot create a new file;");
      }
    }
    catch (IOException e2) {
      throw new ParserException("File is empty");
    }

    if (parsedAirline == null){
      throw new ParserException("Cannot parse file");
    }


    return parsedAirline;
  }

}
