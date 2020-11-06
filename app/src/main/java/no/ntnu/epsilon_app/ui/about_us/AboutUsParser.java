package no.ntnu.epsilon_app.ui.about_us;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AboutUsParser {
    public static void parseAboutUsList(String jsonLine) {
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            parseAboutUsObject(element.getAsJsonObject());
        }
    }

    private static void parseAboutUsObject(JsonObject object) {
        long objectId = object.get("aboutUsObjectId").getAsLong();
        String firstName = object.get("user").getAsJsonObject().get("firstName").getAsString();
        String lastName = object.get("user").getAsJsonObject().get("lastName").getAsString();
        String email = object.get("user").getAsJsonObject().get("email").getAsString();
        long userid = object.get("user").getAsJsonObject().get("userid").getAsLong();
        String position = object.get("position").getAsString();
        String fullName = firstName + " " + lastName;


        boolean exists = false;
        for (int i = 0; i < AboutUsViewModel.OBJECT_LIST.size(); i++){
            AboutUsObject aboutUsObject = AboutUsViewModel.OBJECT_LIST.get(i);
            if (aboutUsObject.getId() == objectId){
                exists = true;
                break;
            }
        }

        /*for (AboutUsObject aboutUsObject : AboutUsViewModel.OBJECT_LIST) {
            if (aboutUsObject.getId() == objectId) {
                exists = true;
                break;
            }

            if (aboutUsObject.getUserid() == userid){

            }
        }*/

        if (!exists) {
            AboutUsObject aboutUsObject = new AboutUsObject(objectId, fullName, position, email, userid);
            AboutUsViewModel.OBJECT_LIST.add(aboutUsObject);
        }
    }
}
