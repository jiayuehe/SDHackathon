package travelAPI;

import java.util.List;

public class FlightData {
    String destination;
    List<FlightDetail> flightDetail;

    FlightData(String dest, List<FlightDetail> fD){
        destination = dest;
        flightDetail = fD;
    }
}
