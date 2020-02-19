package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.SimpleFormatter;

public class TextDumper implements AirlineDumper {
  private final String fileName;


  /**
   * @param fileName the name of writing file
   * @param Airline  the airline information needed to write
   */
  TextDumper(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void dump(AbstractAirline abstractAirline) throws IOException {
    BufferedWriter bufferedWriter = null;

    try {
      FileWriter fileWriter = new FileWriter(fileName);
      bufferedWriter = new BufferedWriter(fileWriter);
      /**
       * writing airline name
       */
      bufferedWriter.write(abstractAirline.getName());
      bufferedWriter.newLine();
      ArrayList<AbstractFlight> flights = (ArrayList<AbstractFlight>) abstractAirline.getFlights();

      /**
       * It write line by line every flight information
       */
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
      for (AbstractFlight f : flights) {
        Flight flight = (Flight) f;
        bufferedWriter.write(Integer.toString(flight.getNumber()));
        bufferedWriter.newLine();

        bufferedWriter.write(flight.getSource());
        bufferedWriter.newLine();

        bufferedWriter.write(sdf.format(flight.getDepartDateTime()));
        bufferedWriter.newLine();

        bufferedWriter.write(flight.getDestination());
        bufferedWriter.newLine();

        bufferedWriter.write(sdf.format(flight.getArriveDateTime()));
        bufferedWriter.newLine();

      }
      bufferedWriter.close();

    } catch (FileNotFoundException e) {
      /**
       * If reading file does not exist, try to create a new one.
       */
      try {
        File file = new File(this.fileName);
        file.createNewFile();
      } catch (IOException e1) {
        throw new IOException("File does not exist and cannot create a new file;");
      }
    }
  }
}
