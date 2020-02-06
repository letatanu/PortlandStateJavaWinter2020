package edu.pdx.cs410J.NhutLe;


import edu.pdx.cs410J.ParserException;
import org.junit.Test;

/**
 * Unit tests for the {@link TextParser} class.
 */
public class TextParserTest {
    @Test
    public void ParsingWithoutAnyError() {
        TextParser tp = new TextParser("aaa");
        try {
            tp.parse();
        } catch (ParserException e) {
            System.out.println("IT SHOULD NOT BE HERE.");
        }
    }
}
