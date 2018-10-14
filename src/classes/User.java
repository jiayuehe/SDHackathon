package classes;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class User {
    protected int userid;
    protected String username;
    protected List<Integer> trips = new ArrayList<Integer>();
    public User(int userid, String username, List<Integer> trips){
        this.userid = userid;
        this.username = username;
        this.trips = trips;
    }
    public int getUserid(){
        return this.userid;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public List<Integer> getTrips(){
        return this.trips;
    }
    public void setTrips(List<Integer> trips){
        this.trips = trips;
    }

}
