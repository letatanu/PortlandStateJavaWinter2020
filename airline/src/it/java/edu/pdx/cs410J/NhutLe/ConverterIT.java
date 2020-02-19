package edu.pdx.cs410J.NhutLe;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import edu.pdx.cs410J.InvokeMainTestCase;

import java.io.IOException;

/**
 * An integration test for the {@link Converter} main class.
 */
public class ConverterIT extends InvokeMainTestCase {
    private final Class<?> mainClass;

    protected ConverterIT(Class<?> mainClass) {
        this.mainClass = mainClass;
    }

    public ConverterIT() {
        this(Converter.class);
    }

    /**
     * Invokes the main method of {@link Converter} with the given arguments.
     */

    protected MainMethodResult invokeMain(String... args) {
        return invokeMain(this.mainClass, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */

    @Test
    public void ItCanConvertFromTxtToXML() {
        String number = "123";
        String src = "PDX";
        String departDate = "12/12/2020 11:22 am";
        String dest = "PDX";
        String arriveDate = "12/13/2020 12:31 pm";
        Flight flight = new Flight(number, src, departDate, dest, arriveDate);
        Airline airline = new Airline("Hello Kitty");
        airline.addFlight(flight);
        TextDumper textDumper = new TextDumper("test.txt");
        try {
            textDumper.dump(airline);
            MainMethodResult result = invokeMain("test.txt", "test.xml");
            assertThat(result.getExitCode(), equalTo(0));
        } catch (IOException ignored) {
            System.err.println("It should not be here");
        }
    }

    @Test
    public void MissingCommandLineArguments() {
        MainMethodResult result = invokeMain("test.txt");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), equalTo("Missing command line arguments\n"));
    }

    @Test
    public void UnknownCommandLineArguments() {

        MainMethodResult result = invokeMain("test.txt");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), equalTo("Unknown command line arguments\n"));

    }
}
