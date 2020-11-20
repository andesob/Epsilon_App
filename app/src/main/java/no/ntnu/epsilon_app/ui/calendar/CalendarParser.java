package no.ntnu.epsilon_app.ui.calendar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CalendarParser {
    public static void parseCalendarItems(String jsonLine){
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for(JsonElement element:jsonArray){
            parseCalendarJsonObject(element.getAsJsonObject());
        }

    }
    private static void parseCalendarJsonObject(JsonObject object){

    }
}
