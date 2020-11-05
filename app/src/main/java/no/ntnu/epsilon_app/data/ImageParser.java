package no.ntnu.epsilon_app.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import no.ntnu.epsilon_app.ui.about_us.AboutUsViewModel;

public class ImageParser {

    public static void parseImageList(String jsonLine){
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement element : jsonArray){
            parseImageObject(element.getAsJsonObject());
        }
    }

    private static void parseImageObject(JsonObject object){
        String imageString = object.get("base64String").getAsString();
        long imageId = object.get("imageId").getAsLong();
        long userId = object.get("user").getAsJsonObject().get("userid").getAsLong();

        byte[] decodedString = android.util.Base64.decode(imageString, android.util.Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Image image = new Image(imageId, userId, decodedByte);
        AboutUsViewModel.IMAGE_LIST.add(image);
    }
}
