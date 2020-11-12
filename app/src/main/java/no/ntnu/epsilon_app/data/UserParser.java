package no.ntnu.epsilon_app.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.epsilon_app.ui.about_us.AboutUsViewModel;
import no.ntnu.epsilon_app.ui.news.News;
import no.ntnu.epsilon_app.ui.news.NewsFeedViewModel;

public class UserParser {
    public static List<User> parseUserList(String jsonLine){
        List<User> users = new ArrayList<>();
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            //parseUserJsonObject(element.getAsJsonObject());
            users.add(parseUserJsonObject(element.getAsJsonObject()));
        }
        return  users;
    }

    public static User parseUser(String jsonLine){
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        return parseUserJsonObject(jsonElement.getAsJsonObject());
    }

    private static User parseUserJsonObject(JsonObject object){
        String email = object.get("email").getAsString();
        String firstName = object.get("firstName").getAsString();
        String lastName = object.get("lastName").getAsString();
        long userId = object.get("userid").getAsLong();

        ArrayList<String> groups = new ArrayList<>();
        JsonArray jsonGroups = object.get("groups").getAsJsonArray();
        for (JsonElement group : jsonGroups){
            String name = group.getAsJsonObject().get("name").getAsString();
            groups.add(name);
        }

        boolean exists = false;
        for (User user : UserViewModel.USER_LIST) {
            if (user.getUserid() == userId) {
                exists = true;
                return user;
            }
        }

        if (!exists) {
            User user = new User(userId, firstName, lastName, email, groups);
            UserViewModel.USER_LIST.add(user);
            return user;
        }

        return null;
    }
}
