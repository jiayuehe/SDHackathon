package TravellingSalesman;

import java.util.HashMap;
import java.util.Map;

public class Location {
    double lon;
    double lat;
    Map<Location, Integer> mapOfLocation;

    public Location(double longitude, double latitude){
        lon = longitude;
        lat = latitude;
    }

    public void addLocation(Location k, int price){
        if(mapOfLocation == null){
            mapOfLocation = new HashMap<>();
        }

        mapOfLocation.put(k,price);
    }


}
