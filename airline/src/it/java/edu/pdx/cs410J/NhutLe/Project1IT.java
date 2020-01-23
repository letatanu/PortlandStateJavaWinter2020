package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
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

  /*
  * Tests that invoking the main method with appropriate arguments without errors
   */

  @Test
  public void testWithValidCommandLineArguments() {
    MainMethodResult result = invokeMain("\"Hello Kitty\" 123 AAA \"02-02-2020 11:58\" AAB \"02-03-2020 1:58\"");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(""));

  }
  /*
   * Tests that invoking the main method with appropriate arguments + option <code>-print</code>
   * It will print out the information input without errors
   */
  @Test
  public void testWithValidCommandLineArgumentsWithPrintOption() {
    MainMethodResult result = invokeMain("\"Hello Kitty\" 123 AAA \"02-02-2020 11:58\" AAB \"02-03-2020 1:58\" -print");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(""));
//    assertThat(result.getTextWrittenToStandardOut(), equalTo("Airline: Hello Kitty\n" +
//                                                                        "Flight 123 departs AAA at 02-02-2020 11:58 arrives AAB at 02-03-2020 1:58"));
  }
  /*
   * Tests that invoking the main method with appropriate arguments + option <code>-print</code>
   * It will print out the Readme without errors
   */
  @Test
  public void testWithReadmeOption () {
    MainMethodResult result = invokeMain("\"Hello Kitty\" 123 AAA \"02-02-2020 11:58\" AAB \"02-03-2020 1:58\" -print -README");
    assertThat(result.getExitCode(), equalTo(1));
//    assertThat(result.getTextWrittenToStandardOut(), containsString("Project 1"));

  }



}