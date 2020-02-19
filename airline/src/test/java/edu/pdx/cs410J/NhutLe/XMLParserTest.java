package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;
import org.junit.Test;

/**
 * Unit tests for the {@link XmlDumper} class.
 */
public class XMLParserTest {
    @Test
    public void ItShouldParseSuccessfully() throws ParserException {
        try {
            XmlParser xmlParser = new XmlParser("valid-airline.xml");
            Airline airline = (Airline) xmlParser.parse();
            System.out.println(airline.toString());
            for (AbstractFlight flight: airline.getFlights()) {
                System.out.println(flight.toString());
            }
        } catch (Exception ignored) {
            System.err.println("It shouldn't be here");
        }
    }
}
