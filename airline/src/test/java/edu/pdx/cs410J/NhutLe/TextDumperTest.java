package edu.pdx.cs410J.NhutLe;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link TextDumper} class.
 */
public class TextDumperTest {
    @Test
    public void TextDumperWithoutAnyError() {
        String number = "123";
        String src = "PDX";
        String departDate = "12/12/2020 11:22 AM" ;
        String dest = "PDX";
        String arriveDate = "12/13/2020 12:31 PM";
        Flight flight = new Flight(number, src, departDate, dest, arriveDate);

        Airline airline = new Airline("Hello Kitty");
        airline.addFlight(flight);

        TextDumper td = new TextDumper("aaa");
        try {
            td.dump(airline);
        }
        catch (IOException e){
            System.out.println("IT SHOULD NOT BE HERE");
        }
    }

}
