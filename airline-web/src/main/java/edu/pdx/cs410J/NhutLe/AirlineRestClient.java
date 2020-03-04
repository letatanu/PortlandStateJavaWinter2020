package edu.pdx.cs410J.NhutLe;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient extends HttpRequestHelper
{
  private static final String WEB_APP = "airline";
  private static final String SERVLET = "flights";

  private final String url;


  /**
   * Creates a client to the airline REST service running on the given host and port
   * @param hostName The name of the host
   * @param port The port
   */
  public AirlineRestClient( String hostName, int port )
  {
    this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
  }

  /**
   * Returns all dictionary entries from the server
   */
  public Map<String, String> getAllDictionaryEntries() throws IOException {
    Response response = get(this.url, Map.of());
    return Messages.parseDictionary(response.getContent());
  }

  /**
   * Returns the definition for the given word
   */
  public String getAirlineAsXml(String word) throws IOException {
    Response response = get(this.url, Map.of("airline", word));
    throwExceptionIfNotOkayHttpStatus(response);
    return response.getContent();
  }

  /**
   * Returns the definition for the given word
   */
  public String getFlightsInTheAirlineBySrcAndDestAsXml(String word, String src, String dest) throws IOException {
    Response response = get(this.url, Map.of("airline", word, "src", src, "dest", dest));
    throwExceptionIfNotOkayHttpStatus(response);
    return response.getContent();
  }

  public void addFlight(String airlineName, String flightNumber, String src, String departDateTime, String dest, String arriveDateTime) throws IOException {
    Response response = postToMyURL(Map.of("airline", airlineName, "flightNumber", flightNumber, "src", src, "departDateTime", departDateTime, "dest", dest, "arriveDateTime", arriveDateTime));
    throwExceptionIfNotOkayHttpStatus(response);
  }

  @VisibleForTesting
  Response postToMyURL(Map<String, String> dictionaryEntries) throws IOException {
    return post(this.url, dictionaryEntries);
  }

  public void removeAllDictionaryEntries() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      throw new AirlineRestException(code);
    }
    return response;
  }

  @VisibleForTesting
  class AirlineRestException extends RuntimeException {
    AirlineRestException(int httpStatusCode) {
      super("Got an HTTP Status Code of " + httpStatusCode);
    }
  }
}