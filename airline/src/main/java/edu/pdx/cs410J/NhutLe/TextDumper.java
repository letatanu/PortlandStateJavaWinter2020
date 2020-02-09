package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.ArrayList;

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
      for (AbstractFlight flight : flights) {
        bufferedWriter.write(Integer.toString(flight.getNumber()));
        bufferedWriter.newLine();

        bufferedWriter.write(flight.getSource());
        bufferedWriter.newLine();

        bufferedWriter.write(flight.getDepartureString());
        bufferedWriter.newLine();

        bufferedWriter.write(flight.getDestination());
        bufferedWriter.newLine();

        bufferedWriter.write(flight.getArrivalString());
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
