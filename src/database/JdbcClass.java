package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import classes.*;
import passwordhasher.*;

public class JdbcClass {
    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String password = "1304kenya";
    public static void connect(){
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection("jdbc:mysql://localhost/TripProject?user=root&password=" + password +"&useSSL=false");
            System.out.println("connect!");
        } catch (SQLException sqle) {
            System.out.println("sqle: " + sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {//if we cannot find from the driver
            System.out.println("cnfe: " + cnfe.getMessage());
        }
    }
    public static void close(){
        try{
            if (rs!=null){
                rs.close();
                rs = null;
            }
            if(conn != null){
                conn.close();
                conn = null;
            }
            if(ps != null ){
                ps = null;
            }
        }catch(SQLException sqle){
            System.out.println("connection close error");
            sqle.printStackTrace();
        }
    }
    public static Boolean isUser(String username){//check whether the username is valid
        connect();
        try{
            String queryCheck = "SELECT u.username " +
                    "FROM User u " +
                    "WHERE u.username=?";
            ps = conn.prepareStatement(queryCheck);
            ps.setString(1,username);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException sqle){
            System.out.println("SQLException in function \" getData\": ");
            sqle.printStackTrace();
        }finally{
            close();
        }
        return false;
    }
    public static int LoginSuccess(String username, String password){//check whether user can login successfully
                                                            //return userId when login success
                                                            //return 0 when login failure
        connect();
        try{
            String queryCheck = "SELECT u._password, u.user_id " +
                    "FROM User u " +
                    "WHERE u.username=?";
            ps = conn.prepareStatement(queryCheck);
            ps.setString(1,username);
            rs = ps.executeQuery();
            if(rs.next()){
                PasswordHasher ph = new PasswordHasher();
                char[] passwordChar = password.toCharArray();
                if(ph.authenticate(passwordChar, rs.getString("_password"))){
                    System.out.println(rs.getInt("user_id"));
                    return rs.getInt("user_id");
                }
            /*
            if(password.equalsIgnoreCase(rs.getString("password"))){
                return rs.getInt("user_id");
            }*/
                return 0;
            }
        }catch(SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        } finally {
            close();
        }
        return 0;
    }

    public static void registerUser(String username, String password){
        connect();
        String queryCheck = "INSERT INTO User (username, _password)" +
                " VALUES (?, ?)";
        try{
            ps = conn.prepareStatement(queryCheck);
            PasswordHasher ph = new PasswordHasher();
            char[] passwordChar = password.toCharArray();
            String hashedPassword = ph.hash(passwordChar);
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        } finally {
            close();
        }
    }
    public static int getUserId(String username){
        connect();
        try{
            String queryCheck = "SELECT u.user_id" +
                    " FROM User u " +
                    " WHERE u.username=?";
            ps = conn.prepareStatement(queryCheck);
            ps.setString(1,username);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("user_id");
            }
        }catch (SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        } finally {
            close();
        }
        return 0;
    }
    /*
    public static String getUserName(int userid){//use it when you get a valid userid
        connect();
        try{
            String queryCheck = "SELECT u.username" +
                    " FROM User u " +
                    " WHERE u.user_id=?";
            ps = conn.prepareStatement(queryCheck);
            ps.setInt(1,userid);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("username");
            }
        }catch (SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        } finally {
            close();
        }
        return "non-valid user";
    }*/
    public static User getUser(String username){
        connect();
        int userid = getUserId(username);
        List<Integer> trips = new ArrayList<>();
        try{
            String queryCheck = "SELECT ut.trip_id" +
                    " FROM User_to_Trip ut" +
                    " Where ut.user_id = " + userid + ";";
            if(conn == null){
                connect();
            }
            ps = conn.prepareStatement(queryCheck);
//            if(ps == null){
//                System.out.println("NULL PTR?");
//            }
            System.out.println("get user 2");
//            ps.setInt(1,userid);
            System.out.println("3");
            rs = ps.executeQuery();
            while(rs.next()) {
                System.out.println("4");
                int tripid = rs.getInt("trip_id");
                trips.add(tripid);
            }
        }catch (SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        } finally {
            close();
        }
        User user = new User(userid,username,trips);
        return user;
    }
    public static Trip getTrip(int tripid){
        connect();
        String title = "";
        String mainplace = "";
        try{
            String queryCheck = "SELECT t.title, t.mainplace" +
                    " FROM Trip t" +
                    " Where t.trip_id=?";

            ps = conn.prepareStatement(queryCheck);
            ps.setInt(1, tripid);
            //ps.setInt(1,tripid);
            rs = ps.executeQuery();
            title = rs.getString("title");
            mainplace = rs.getString("mainplace");
        }catch (SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        } finally {
            close();
        }
        Trip trip = new Trip(title,mainplace);
        return trip;
    }
    public static List<Integer> getLocationList(int tripid){
        List<Integer> locations = new ArrayList<>();
        try{
            String queryCheck = "SELECT l.location_id" +
                    " FROM Location l" +
                    " Where l.trip_id=?";
            ps = conn.prepareStatement(queryCheck);
            ps.setInt(1,tripid);
            rs = ps.executeQuery();
            while(rs.next()){
                int locationid = rs.getInt("location_id");
                locations.add(locationid);
            }
        }catch (SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        }finally {
            close();
        }
        return locations;
    }
    public static Location getLocation(int locationid){
        int tripid = 0;
        String title = "";
        String description = "";
        String longitutde = "";
        String latitude = "";
        String itemtype = "";
        String fromdate = "";
        String todate = "";
        try{
            String queryCheck = "SELECT *" +
                    " FROM Location l" +
                    " Where l.location_id=?";
            ps = conn.prepareStatement(queryCheck);
            ps.setInt(1,locationid);
            rs = ps.executeQuery();
            tripid = rs.getInt("trip_id");
            title = rs.getString("title");
            description = rs.getString("description");
            longitutde = rs.getString("longitutde");
            latitude = rs.getString("latitude");
            itemtype = rs.getString("item_type");
            fromdate = rs.getString("from_date");
            todate = rs.getString("to_date");
        }catch (SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        }finally {
            close();
        }
        Location l = new Location(tripid, title, description, longitutde, latitude, itemtype, fromdate, todate);
        return l;
    }
    public static List<Integer> getFlightList(int tripid){
        List<Integer> flights = new ArrayList<>();
        try{
            String queryCheck = "SELECT f.flight_id" +
                    " FROM Flight f" +
                    " Where f.trip_id=?";
            ps = conn.prepareStatement(queryCheck);
            ps.setInt(1,tripid);
            rs = ps.executeQuery();
            while(rs.next()){
                int flightid = rs.getInt("flight_id");
                flights.add(flightid);
            }
        }catch (SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        }finally {
            close();
        }
        return flights;
    }
    public static Flight getFlight(int flightid){
        int tripId = 0;
        String fromLocation = "";
        String toLocation = "";
        String startTime = "";
        String arriveTime = "";
        try{
            String queryCheck = "SELECT *" +
                    " FROM Flight f" +
                    " Where f.flight_id=?";
            ps = conn.prepareStatement(queryCheck);
            ps.setInt(1,flightid);
            rs = ps.executeQuery();
            tripId = rs.getInt("trip_id");
            fromLocation = rs.getString("from_location");
            toLocation = rs.getString("to_location");
            startTime = rs.getString("start_time");
            arriveTime = rs.getString("arrive_time");
        }catch (SQLException e) {
            System.out.println("SQLException in function \"validate\"");
            e.printStackTrace();
        }finally {
            close();
        }
        Flight f = new Flight(tripId, fromLocation, toLocation, startTime, arriveTime);
        return f;
    }
}
