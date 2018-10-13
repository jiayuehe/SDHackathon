package travelAPI;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.parser.JSONParser;
import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;



public class Flight{
    private String destination;
    private String origin;
    private static final String APIKEY = "<API-KEY>";

    public Flight(String des, String ori) {
        destination = des;
        origin = ori;
    }

    private static final long serialVersionUID = 1L;

    protected void service() throws IOException {

        String url = "http://api.travelpayouts.com/v1/prices/cheap?origin=MOW&destination=HKT&depart_date=2018-12&return_date=2018-12&token="+ APIKEY;

        URL obj = new URL(url);

        // get JSON object from request
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        InputStream response = con.getInputStream();

        // No response.
        if (response == null) {
            return;
        }

        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
            GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(FlightData.class, new FlightDataDeserializer());

            Gson gson = builder.create();
            APIResponse apiResponse = gson.fromJson(responseBody, APIResponse.class);


            System.out.println("Here test for api response...");
            System.out.println("Sucess :" + apiResponse.success);
            System.out.println("Currency: " + apiResponse.currency);
            FlightData data = apiResponse.data;
            System.out.println("Dest: " + data.destination);
            for (FlightDetail detail : data.flightDetail){
                System.out.println();
                System.out.println("Price:" + detail.price);
                System.out.println("AirLine: " + detail.airline);
                System.out.println("Flight Num: " + detail.flight_number);
                System.out.println("Departure at " + detail.departure_at);
                System.out.println("Return at " + detail.return_at);
                System.out.println("Expires at " + detail.expires_at);
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Flight flight = new Flight("mos","hi");
        try {
            flight.service();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
