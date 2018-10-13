package travelAPI;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightDataDeserializer implements JsonDeserializer<FlightData> {

    @Override
    public FlightData deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = element.getAsJsonObject();
        String dest = "";
        List<FlightDetail> flightDetails = new ArrayList<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            dest = entry.getKey();
            flightDetails = deserializeFlightDetail(entry.getValue(), context);
        }

        return new FlightData(dest, flightDetails);
    }

    private List<FlightDetail> deserializeFlightDetail(JsonElement element, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = element.getAsJsonObject();

        List<FlightDetail> flightDetails = new ArrayList<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonElement flightDetailJson = entry.getValue();

            FlightDetail flightDetail = context.deserialize(flightDetailJson, FlightDetail.class);
            flightDetails.add(flightDetail);
        }
        return flightDetails;
    }

}