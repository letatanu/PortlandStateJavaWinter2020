package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.IOException;

public class Converter {
    public static void main(String[] args){

        if (args.length < 2) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }
        if (args.length > 2) {
            System.err.println("Unknown command line arguments");
            System.exit(1);
        }

        String textFile = args[0];
        String xmlFile = args[1];

        TextParser textParser = new TextParser(textFile);
        try {
            Airline airline = (Airline) textParser.parse();
            // Airline adds new flight
            XmlDumper xmlDumper = new XmlDumper(xmlFile);
            xmlDumper.dump(airline);
        } catch (ParserException | IOException | IllegalArgumentException e) {
            String err = e.getMessage();
            if (err.equals("File is created successfully")) {
                File file = new File(textFile);
                file.delete();
                err = "Text file does not exist";
            }
            System.err.println(err);
            System.exit(1);
        }

        System.exit(0);
    }
}
