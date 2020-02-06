package edu.pdx.cs410J.NhutLe;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.IOException;

/**
 * Unit tests for the {@link PrettyPrinter} class.
 */
public class PrettyPrinterTest {
    @Test
    public void PrettyPrintingWithoutAnyError () {
        String number = "123";
        String src = "PDX";
        String departDate = "12/12/2020 11:22 AM" ;
        String dest = "PDX";
        String arriveDate = "12/13/2020 12:31 PM";
        Flight flight = new Flight(number, src, departDate, dest, arriveDate);

        Airline airline = new Airline("Hello Kitty");
        airline.addFlight(flight);

        PrettyPrinter td = new PrettyPrinter("-");
        try {
            td.dump(airline);
        }
        catch (IOException e){
            System.out.println("IT SHOULD NOT BE HERE");
        }
    }
}
