package classes;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Flight {
    protected int tripId;
    protected String fromLocation;
    protected String toLocation;
    protected String startTime;
    protected String arriveTime;

    public Flight(int tripId, String fromLocation, String toLocation, String startTime, String endTime){
        this.tripId = tripId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.startTime = startTime;
        this.arriveTime = endTime;
    }
    public int getTripId(){
        return this.tripId;
    }
    public String getFromLocation(){
        return this.fromLocation;
    }
    public String getToLocation(){
        return this.toLocation;
    }
    public String getStartTime(){
        return this.startTime;
    }
    public String getEndTime(){
        return this.arriveTime;
    }
}
