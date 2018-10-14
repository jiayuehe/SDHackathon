package server;

import TravellingSalesman.Location;
import TravellingSalesman.TravelSalesMan;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import travelAPI.Flight;

import java.net.InetSocketAddress;
import java.util.*;

public class Server extends WebSocketServer {

    private static int TCP_PORT = 4444;

    private Set<WebSocket> conns;

    private Map<String,Set<String>> teamToLocationsName = new HashMap<>();

    private Map<String, Set<WebSocket>> teamMap = new HashMap<>();

    public Server() {
        super(new InetSocketAddress(TCP_PORT));
        conns = new HashSet<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    public void joinTeam(WebSocket conn, String name) {
        if (teamMap.containsKey(name)) {
            teamMap.get(name).add(conn);
        }
        else {
            Set<WebSocket> socketSet = new HashSet<>();
            teamMap.put(name, socketSet);
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);

        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from client: " + message);
        Gson gson = new Gson();

        Query query = gson.fromJson(message, Query.class);
        String username = query.userName;
        String teamName = query.tripName;
        Set<String> nameSet = teamToLocationsName.get(teamName);
        switch (query.type) {
            case "INITIALIZE":
                System.out.println("Initializing");
                if (!teamMap.containsKey(teamName)) {
                    teamMap.put(teamName, new HashSet<>());
                }
                teamMap.get(teamName).add(conn);
                System.out.println(teamMap);
                break;
            case "CHAT":

                teamBroadcast(teamName,"{'type': 'CHAT', 'username' :" + username +
                        ", 'content':" + query.content + "}");
                break;
            case "JOIN_TEAM":
                joinTeam(conn, teamName);
                teamBroadcast(teamName, message);
                break;
            case "ADD_LOCATION":
                String destination, origin;

                FlightQuery flightQuery = gson.fromJson(query.content, FlightQuery.class);
                destination = flightQuery.dest;
                origin = flightQuery.origin;
                nameSet.add(destination);
                nameSet.add(origin);
                teamBroadcast(teamName, "{'origin':" + origin + "," +
                                                    "destination:" + destination + "}");
                break;

            case "SAVE":
                String startMonth = query.content;
                Set<Location> locations = new HashSet<>();
                for (String name : nameSet) {
                    Location location = new Location(name);
                    locations.add(location);
                }

                for (Location location : locations) {
                    makeConnection(location, locations, startMonth);
                }

                List<Location> allLocationPerTeam = new ArrayList<>(locations);
                List<Location> finalSolution = TravelSalesMan.processCommand(allLocationPerTeam);
                for(Location solution:finalSolution){
                    System.out.print(solution.getName() + ",");
                }
                int sum = calculatePrize(finalSolution.get(0),finalSolution);

                List<String> solution = new ArrayList<>();
                for (Location loc : finalSolution) {
                    solution.add(loc.getName());
                }
                Result result = new Result("RESULT",solution,sum);
                String json = gson.toJson(result);
                teamBroadcast(teamName,json);

                // Save to database

        }

    }

    private int calculatePrize(Location startPosition, List<Location> finalSolution){
        int sum = 0;
        for(int i = 1; i < finalSolution.size(); i++){
            Location toLocation = finalSolution.get(i);
            sum += startPosition.getPriceMap().get(toLocation);
            startPosition = toLocation;
        }
        return sum;
    }

    private void makeConnection(Location origin, Set<Location> locationSet, String startMonth) {
        Map<String, Integer> priceMap = origin.getPriceMap();
        for (Location location : locationSet) {
            if (priceMap.containsKey(location.getName())) continue;
            Flight flight = new Flight(location.getName(), origin.getName(), startMonth);
            int price = flight.cheapestPrice();
            priceMap.put(location.getName(), price);
        }
    }

    public void teamBroadcast(String teamName, String message) {
        System.out.println(teamMap);
        Set<WebSocket> teamSockets = teamMap.get(teamName);
        for (WebSocket sock : teamSockets) {
            if (sock == null) {
                System.out.println("Socket dead");
            } else {
                sock.send(message);
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        /*f (conn != null) {
            conns.remove(conn);
            // do some thing if required
        } */
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    public static void main(String[] args) {
        new Server().start();
    }
}