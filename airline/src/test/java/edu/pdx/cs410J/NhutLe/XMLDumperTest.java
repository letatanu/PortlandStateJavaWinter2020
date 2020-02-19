package edu.pdx.cs410J.NhutLe;

import org.junit.Test;

import java.io.IOException;

/**
 * Unit tests for the {@link XmlDumper} class.
 */
public class XMLDumperTest {

    @Test
    public void itShouldDumpToXMLSuccessfully() {
        String number = "123";
        String src = "PDX";
        String departDate = "12/12/2020 11:22 am" ;
        String dest = "PDX";
        String arriveDate = "12/13/2020 12:31 pm";
        Flight flight = new Flight(number, src, departDate, dest, arriveDate);
        Airline airline = new Airline("Hello Kitty");
        airline.addFlight(flight);
        airline.addFlight(flight);
        XmlDumper xmlDumper = new XmlDumper("Te1.xml");
        try {
            xmlDumper.dump(airline);
        } catch (IOException e) {
            System.err.println("It shouldn't be here");
        }
    }
}
