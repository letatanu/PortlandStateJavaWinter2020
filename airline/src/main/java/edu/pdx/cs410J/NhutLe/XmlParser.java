package edu.pdx.cs410J.NhutLe;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.ProjectXmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XmlParser extends ProjectXmlHelper implements AirlineParser {
    private final String fileName;

    /** The System ID for the Family Tree DTD */
    protected static final String SYSTEM_ID =
            "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd";

    /** The Public ID for the Family Tree DTD */
    protected static final String PUBLIC_ID =
            "-//Portland State University//DTD CS410J Airline//EN";

    /**
     * Constructor
     * @param fileName
     */
    XmlParser(String fileName) {
        super(PUBLIC_ID, SYSTEM_ID, "airline.dtd");
        this.fileName = fileName;
    }

    private String getDateTime(Element node) throws ParserException {
        try {
            Element departDateEle = (Element) node.getElementsByTagName("date").item(0);
            String departDate = String.format("%s/%s/%s", departDateEle.getAttribute("month"), departDateEle.getAttribute("day"), departDateEle.getAttribute("year"));
            Element departTimeEle = (Element) node.getElementsByTagName("time").item(0);
            String time = String.format("%s:%s", departTimeEle.getAttribute("hour"), departTimeEle.getAttribute("minute"));
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date time_ = sdf.parse(time);
            String time_12 = new SimpleDateFormat("K:mm aa").format(time_);
            return departDate + " " + time_12.toLowerCase();
        } catch (Exception e) {
            throw new ParserException("Cannot parse Date");
        }

    }

    @Override
    public AbstractAirline parse() throws ParserException {
        /**
         * If reading file does not exist, try to create a new one.
         */

        File file = new File(this.fileName);
        if (!file.exists()) {
            try {
                if(file.createNewFile()) {
                    throw new ParserException("XML File is created successfully");
                }
            }
            catch (IOException e) {
                throw new ParserException("XML File cannot be created.");
            }
        }


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(this);
            builder.setEntityResolver(this);
            Document doc = builder.parse(this.getClass().getResourceAsStream(this.fileName));
            doc.getDocumentElement().normalize();
            String airlineName = doc.getElementsByTagName("name").item(0).getTextContent();
            Airline airline = new Airline(airlineName);

            NodeList flightNodes = doc.getElementsByTagName("flight");
            for(int i=0; i< flightNodes.getLength(); i++) {
                Node flightNode = flightNodes.item(i);

                if (flightNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element flightElement = (Element) flightNode;
                    String number = flightElement.getElementsByTagName("number").item(0).getTextContent();
                    String src = flightElement.getElementsByTagName("src").item(0).getTextContent();
                    Element depart = (Element) flightElement.getElementsByTagName("depart").item(0);
                    String departDateTime = getDateTime(depart);
                    String dest = flightElement.getElementsByTagName("dest").item(0).getTextContent();
                    Element arrive = (Element) flightElement.getElementsByTagName("arrive").item(0);
                    String arriveDateTime = getDateTime(arrive);

                    Flight flight = new Flight(number,src,departDateTime,dest,arriveDateTime);
                    airline.addFlight(flight);
                }
            }
            return airline;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ParserException("The XML is malformed");
        }
    }
}
