package TravellingSalesman;

import java.util.HashMap;
import java.util.Map;

public class Location {
    String currentLoc;
    double lon;
    double lat;
    Map<String, Integer> mapOfLocation;

    public Location(double longitude, double latitude, String name){
        lon = longitude;
        lat = latitude;
        currentLoc = name;
    }

    public String getCurrentLoc() {
        return currentLoc;
    }

    public void addLocation(Location k, int price){
        if(mapOfLocation == null){
            mapOfLocation = new HashMap<>();
        }

        mapOfLocation.put(k.currentLoc,price);
    }


}
