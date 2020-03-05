package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class XmlDumper implements AirlineDumper {
    private final String fileName;
    /**
     * @param fileName the name of writing file
     */
    XmlDumper(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Convert Date to String as formatted
     * @param value
     * @return
     */
    private ArrayList<String> dateToString(Date value) throws IOException{
        String a =  new SimpleDateFormat("MM/dd/YYYY HH:mm").format(value);
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
    public void dump(AbstractAirline abstractAirline) throws IOException {
        BufferedWriter bufferedWriter = null;

        FileWriter fileWriter = new FileWriter(fileName);
        bufferedWriter = new BufferedWriter(fileWriter);
        /**
         * Writing header XML
         */
        bufferedWriter.write("<?xml version='1.0' encoding='us-ascii'?>\n" +
                "<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">\n");
        /**
         * writing airline name
         */
        bufferedWriter.write("<airline>");
        bufferedWriter.newLine();
        bufferedWriter.write("<name>" + abstractAirline.getName() + "</name>");
        bufferedWriter.newLine();

        ArrayList<AbstractFlight> flights = (ArrayList<AbstractFlight>) abstractAirline.getFlights();

        /**
         * It write line by line every flight information
         */
        for (AbstractFlight f : flights) {
            Flight flight = (Flight) f;
            bufferedWriter.write("<flight>");
            bufferedWriter.newLine();

            bufferedWriter.write("<number>" + Integer.toString(flight.getNumber()) + "</number>");
            bufferedWriter.newLine();

            bufferedWriter.write("<src>" + flight.getSource() + "</src>");
            bufferedWriter.newLine();

            bufferedWriter.write("<depart>");
            bufferedWriter.newLine();

            // need to fix depart string
            ArrayList<String> depart = dateToString(flight.getDepartDateTime());
            String departDate = String.format("<date day=\"%s\" month=\"%s\" year=\"%s\"/>\n" + "<time hour=\"%s\" minute=\"%s\"/>", depart.get(1), depart.get(0), depart.get(2), depart.get(3), depart.get(4));
            bufferedWriter.write(departDate);
            bufferedWriter.newLine();

            bufferedWriter.write("</depart>");
            bufferedWriter.newLine();

            bufferedWriter.write("<dest>" + flight.getDestination() + "</dest>");
            bufferedWriter.newLine();

            bufferedWriter.write("<arrive>");
            bufferedWriter.newLine();
            ArrayList<String> arrive = dateToString(flight.getArriveDateTime());
            String arriveDate = String.format("<date day=\"%s\" month=\"%s\" year=\"%s\"/>\n" + "<time hour=\"%s\" minute=\"%s\"/>", arrive.get(1), arrive.get(0), arrive.get(2), arrive.get(3), arrive.get(4));
            bufferedWriter.write(arriveDate);
            bufferedWriter.newLine();
            bufferedWriter.write("</arrive>");
            bufferedWriter.newLine();

            bufferedWriter.write("</flight>");
            bufferedWriter.newLine();
        }

        bufferedWriter.write("</airline>");
        bufferedWriter.close();

    }
}
