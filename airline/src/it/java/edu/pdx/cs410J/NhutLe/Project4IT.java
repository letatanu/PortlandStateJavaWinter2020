package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project4} main class.
 */
public class Project4IT extends InvokeMainTestCase {

  private final Class<?> mainClass;

  protected Project4IT(Class<?> mainClass) {
    this.mainClass = mainClass;
  }

  public Project4IT() {
    this(Project4.class);
  }
    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */

  protected MainMethodResult invokeMain(String... args) {
      return invokeMain( mainClass, args );
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  /**
  * Tests that invoking the main method with appropriate arguments without errors
   */

  @Test
  public void testWithValidCommandLineArguments() {
    MainMethodResult result = invokeMain( "Hello", "123", "CAE", "02/02/2020", "12:11", "am", "BWI", "02/03/2020", "11:34", "pm");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardError(), containsString(""));


  }
  /**
   * Tests that invoking the main method with inappropriate arguments + option <code>-print</code>
   * It will print out the information input without errors
   */
  @Test
  public void testWithValidCommandLineArgumentsWithPrintOption() {
    MainMethodResult result = invokeMain("-print", "Hello", "123", "CAE", "02/02/2020", "12:11", "am", "BWI", "02/03/2020", "11:34", "pm");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardError(), containsString(""));
    assertThat(result.getTextWrittenToStandardOut(), equalTo("Airline: Hello\nThe last flight: Flight 123 departs CAE at 2/2/20, 12:11 AM arrives BWI at 2/3/20, 11:34 PM\n"));
  }
  /**
   * Tests that invoking the main method with appropriate arguments + option <code>-print</code>
   * It will print out the Readme without errors
   */
  @Test
  public void testWithReadmeOption () {
    MainMethodResult result = invokeMain( Project4.class, "-README");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), containsString("Project 3"));
  }

  /**
   * Test that <code>-pretty</code> is working
   */
  @Test
  public void prettyShouldWork() {
    MainMethodResult result = invokeMain("-pretty", "file", "Hello", "123", "PDX", "02/02/2020", "12:11", "am", "PDX", "02/03/2020", "11:34", "am");
    assertThat(result.getExitCode(), equalTo(0));
//    assertThat(result.getTextWrittenToStandardOut(), equalTo("Airline: Hello\n" +
//                                                                 "Flight 123 departs CAE at 02/02/2020 12:11 AM arrives CAE at 02/03/2020 11:34 AM\n"));
  }
  @Test
  public void prettyShouldNotWork() {
    MainMethodResult result = invokeMain("-pretty", "Hello", "123", "CAE", "02/02/2020", "12:11", "am", "CAE", "02/03/2020", "11:34", "am");
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void prettyShouldPrintOutSth() {
    MainMethodResult result = invokeMain("-pretty", "-" , "Hello", "123", "PDX", "02/02/2020", "12:11", "am", "PDX", "02/03/2020", "11:34", "am");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), equalTo("The airline is Hello with 1 flights following: \nThe flight 0\nFlight 123 departs PDX at 2/2/20, 12:11 AM arrives PDX at 2/3/20, 11:34 AM duration: 2123 minutes\n------------------------------------------\n"));
  }



}