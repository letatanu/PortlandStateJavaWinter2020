package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.IllegalFormatException;

public class TextParser implements AirlineParser {
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

          String src = line;
          line = bufferedReader.readLine();
          if (line == null) {
            throw new ParserException("Wrong Format");
          }

          String[] departDateTime = line.split(" ");
          if (departDateTime.length != 2) {
            throw new ParserException("Wrong format");
          }
          String departDate = departDateTime[0];
          String departTime = departDateTime[1];

          line = bufferedReader.readLine();
          if (line == null) {
            throw new ParserException("Wrong Format");
          }

          String dest = line;
          line = bufferedReader.readLine();
          if (line == null) {
            throw new ParserException("Wrong Format");
          }

          String[] arriveDateTime = line.split(" ");
          if (arriveDateTime.length != 2) {
            throw new ParserException("Wrong format");
          }
          String arriveDate = arriveDateTime[0];
          String arriveTime = arriveDateTime[1];


          Flight flight = new Flight(flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
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
