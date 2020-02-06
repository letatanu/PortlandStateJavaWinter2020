package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
public class Project3IT extends InvokeMainTestCase {

  private final Class<?> mainClass;

  protected Project3IT(Class<?> mainClass) {
    this.mainClass = mainClass;
  }

  public Project3IT() {
    this(Project3.class);
  }
    /**
     * Invokes the main method of {@link Project1} with the given arguments.
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

    assertThat(result.getTextWrittenToStandardOut(), equalTo("Airline: Hello\n" +
                                                                        "Flight 123 departs CAE at 02/02/2020 12:11 AM arrives BWI at 02/03/2020 11:34 PM\n"));
  }
  /**
   * Tests that invoking the main method with appropriate arguments + option <code>-print</code>
   * It will print out the Readme without errors
   */
  @Test
  public void testWithReadmeOption () {
    MainMethodResult result = invokeMain( Project3.class, "-README");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), containsString("Project 3"));
  }

  /**
   * Test that <code>-pretty</code> is working
   */
  public void prettyShouldWork() {
    MainMethodResult result = invokeMain("-pretty", "file", "Hello", "123", "AAA", "02/02/2020", "12:11", "am", "AAB", "02/03/2020", "11:34");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), equalTo("Airline: Hello\n" +
                                                                 "Flight 123 departs AAA at 02/02/2020 12:11 arrives AAB at 02/03/2020 11:34\n"));
  }



}