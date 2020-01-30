package edu.pdx.cs410J.NhutLe;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Project2IT extends Project1IT {

  public Project2IT() {
    super(Project2.class);

  }

  /**
   * Invokes the main method of {@link Project2} with the given arguments.
   */

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = this.invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  /**
   * Tests that invoking the main method with appropriate arguments without errors
   */

  @Test
  public void testWithValidCommandLineArguments() {
    MainMethodResult result = invokeMain("Hello", "123", "AAA", "02/02/2020", "12:11", "AAB", "02/03/2020", "11:34");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardError(), containsString(""));


  }
  /**
   * Tests that invoking the main method with inappropriate arguments + option <code>-print</code>
   * It will print out the information input without errors
   */
  @Test
  public void testWithValidCommandLineArgumentsWithPrintOption() {
    MainMethodResult result = invokeMain("-print", "Hello", "123", "AAA", "02/02/2020", "12:11", "AAB", "02/03/2020", "11:34");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardError(), containsString(""));

    assertThat(result.getTextWrittenToStandardOut(), equalTo("Airline: Hello\n" +
                                                                 "Flight 123 departs AAA at 02/02/2020 12:11 arrives AAB at 02/03/2020 11:34\n"));
  }
  /**
   * Tests that invoking the main method with appropriate arguments + option <code>-print</code>
   * It will print out the Readme without errors
   */
  @Test
  public void testWithReadmeOption () {
    MainMethodResult result = invokeMain( Project2.class, "-README");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), containsString("Project 2"));

  }


  /**
   * Tests that invoking the main method with appropriate arguments + option <code>-print</code>
   * It will print out the Readme without errors
   */
  @Test
  public void fileEmptyWithCorrectInput () {
    MainMethodResult result = invokeMain( Project2.class, "-textFile", "abc1", "Test4", "123", "PDX", "03/03/2020", "12:00", "ORD", "01/04/2010", "16:00");
    assertThat(result.getExitCode(), equalTo(0));
  }

  /**
   * It will test airline name in input and from file are different
   */
  @Test
  public void fileNameWithWrongAirlineNameInput () {
    MainMethodResult result1 = invokeMain( Project2.class, "-textFile", "abc2", "Test5", "123", "PDX", "03/03/2020", "12:00", "ORD", "01/04/2010", "16:00");
    MainMethodResult result2 = invokeMain( Project2.class, "-textFile", "abc2", "Test6", "123", "PDX", "03/03/2020", "12:00", "ORD", "01/04/2010", "16:00");
    assertThat(result2.getExitCode(), equalTo(1));
    assertThat(result2.getTextWrittenToStandardError(), equalTo("The airline name is not as same as the airline name from the file\n"));
  }
}