package TravellingSalesman;

import java.util.HashMap;
import java.util.Map;

public class Location {
    String name;
    double lon;
    double lat;

    public Map<String, Integer> getPriceMap() {
        return priceMap;
    }

    private Map<String, Integer> priceMap;

    public Location(String name) {
        this.name = name;
    }

    public Location(double longitude, double latitude, String name){
        lon = longitude;
        lat = latitude;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addLocation(Location k, int price){
        if(priceMap == null){
            priceMap = new HashMap<>();
        }

        priceMap.put(k.name,price);
    }


}
