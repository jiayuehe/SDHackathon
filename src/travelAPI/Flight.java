package travelAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;



public class Flight{
    private String destination;
    private String origin;
    private String startMonth;
    private String endMonth;
    private static final String APIKEY = "<API-KEY>";

    public Flight(String des, String ori, String startMonth, String endMonth) {
        this.destination = des;
        this.origin = ori;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        try {
            query();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected void query() throws IOException {

        String url = "http://api.travelpayouts.com/v1/prices/cheap?origin="+ origin + "&destination=" + destination +
                "&depart_date="+ startMonth + "&return_date=" + endMonth + "&token="+ APIKEY;

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


}
