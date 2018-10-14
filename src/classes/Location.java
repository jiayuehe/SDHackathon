package classes;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Location {
    protected int tripId;
    protected String title;
    protected String description;
    protected String longitude;
    protected String latitude;
    protected String itemType;
    protected String fromdate;
    protected String todate;


    public Location(int tridId, String title, String description, String longitude, String latitude, String itemType,
                    String fromdate, String todate){
        this.tripId = tripId;
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.itemType = itemType;
        this.fromdate = fromdate;
        this.todate = todate;
    }
    public int getTripId(){
        return this.tripId;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public String getLongitude(){
        return this.longitude;
    }
    public String getLatitude(){
        return this.latitude;
    }
    public String getItemType(){
        return this.itemType;
    }
}

