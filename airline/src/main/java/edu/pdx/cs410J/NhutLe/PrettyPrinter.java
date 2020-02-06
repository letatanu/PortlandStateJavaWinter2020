package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PrettyPrinter implements AirlineDumper {
  private final String fileName;


  /**
   * @param fileName the name of writing file
   * @param airline  the airline information needed to write
   */
  PrettyPrinter(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void dump(AbstractAirline abstractAirline) throws IOException {
    ArrayList<AbstractFlight> Flights = (ArrayList<AbstractFlight>) abstractAirline.getFlights();

    //Convert Flights in airline
    ArrayList<Flight> flights = new ArrayList<>();
    for (AbstractFlight f: Flights){
      flights.add((Flight)f);
    }
    Collections.sort(flights);

    int l = flights.size();

    if (fileName.equals("-")) {
      System.out.println("The airline is " + abstractAirline.getName() + " with " + Integer.toString(l) + " flights following: ");
      for (int i = 0; i < l; i++) {
        System.out.println("The flight " + i);
        Flight flight = (Flight) flights.get(i);
        System.out.println(flight.toString() + " duration: " + Long.toString(flight.timesDiffInMinutes()) + " minutes");
        System.out.println("------------------------------------------");
      }
      return;
    }

      BufferedWriter bufferedWriter = null;
      try {
        FileWriter fileWriter = new FileWriter(fileName);
        bufferedWriter = new BufferedWriter(fileWriter);
        /**
         * writing airline name
         */
        bufferedWriter.write("The airline is " + abstractAirline.getName() + " with flights following: ");
        bufferedWriter.newLine();


        /**
         * It write line by line every flight information
         */
        l = flights.size();
        for (int i = 0; i < l; i++) {
          bufferedWriter.write("The flight " + i);
          bufferedWriter.newLine();

          Flight flight = (Flight) flights.get(i);
          bufferedWriter.write(flight.toString());
          bufferedWriter.write(" duration: " + Long.toString(flight.timesDiffInMinutes()) + " minutes");
          bufferedWriter.newLine();

          bufferedWriter.write("------------------------------------------");
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
