package server;

import java.util.List;

public class Query {
    String type;
    String tripName;
    String userName;
    String content;
}

class FlightQuery {
    String origin;
    String dest;
    String startMonth;
    String endMonth;
    int price;
}

class Result {
    private String type;
    private List<String> list;
    private int price;

    Result(String type, List<String> list, int price) {
        this.type = type;
        this.list = list;
        this.price = price;
    }
}