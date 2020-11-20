package no.ntnu.epsilon_app.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.ui.about_us.AboutUsObject;
import no.ntnu.epsilon_app.ui.about_us.AboutUsViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageParser {

    public static void parseImageList(String jsonLine) {
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            parseImageObject(element.getAsJsonObject());
        }
    }

    public static void parseImage(String jsonLine) {
        JsonElement element = new JsonParser().parse(jsonLine);
        parseImageObject(element.getAsJsonObject());
    }

    private static void parseImageObject(JsonObject object) {
        String imageString = object.get("base64String").getAsString();
        long imageId = object.get("imageId").getAsLong();
        long userId = object.get("user").getAsJsonObject().get("userid").getAsLong();

        boolean exists = false;
        boolean userHasPicture = false;
        int position = 0;
        for (int i = 0; i < AboutUsViewModel.IMAGE_LIST.size(); i++) {
            Image image = AboutUsViewModel.IMAGE_LIST.get(i);
            if (image.getImageId() == imageId) {
                exists = true;
                break;
            }

            if (image.getUserId() == userId) {
                userHasPicture = true;
                position = i;
            }
        }

        byte[] decodedString = android.util.Base64.decode(imageString, android.util.Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Image image = new Image(imageId, userId, decodedByte);

        if (userHasPicture) {
            AboutUsViewModel.IMAGE_LIST.set(position, image);
        } else if (!exists) {
            AboutUsViewModel.IMAGE_LIST.add(image);
        }

    }
}
