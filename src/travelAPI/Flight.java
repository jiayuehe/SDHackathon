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
    private static final String APIKEY = "<API-KEY>";
    private APIResponse response;

    public Flight(String des, String ori, String startMonth) {
        this.destination = des;
        this.origin = ori;
        this.startMonth = startMonth;
        try {
            response = this.getResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public APIResponse getResponse() throws IOException {

        String url = "http://api.travelpayouts.com/v1/prices/cheap?origin="+ origin + "&destination=" + destination +
                "&depart_date="+ startMonth +  "&token="+ APIKEY;

        URL obj = new URL(url);
        APIResponse apiResponse = null;

        // get JSON object from request
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        InputStream response = con.getInputStream();

        // No response.
        if (response == null) {
            return null;
        }

        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
            GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(FlightData.class, new FlightDataDeserializer());

            Gson gson = builder.create();
            apiResponse = gson.fromJson(responseBody, APIResponse.class);

        }

        return apiResponse;
    }

    public int cheapestPrice() {
        int lowestPrice = Integer.MAX_VALUE;
        FlightData data = response.data;
        if (data == null) return Integer.MAX_VALUE;
        for (FlightDetail detail : data.flightDetail) {
            if (detail.price < lowestPrice) {
                lowestPrice = detail.price;
            }
        }
        return lowestPrice;
    }




}
