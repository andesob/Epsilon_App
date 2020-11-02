package no.ntnu.epsilon_app.ui.news;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDateTime;

public class NewsParser {
    public static void parseNewsFeed(String jsonLine){
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            parseNewsJsonObject(element.getAsJsonObject());
        }
    }

    public static void parseNews(String jsonLine){
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        parseNewsJsonObject(jsonElement.getAsJsonObject());
    }

    private static void parseNewsJsonObject(JsonObject object){
        String timeWrittenString = object.get("timeWritten").getAsString();
        String lastUpdatedString = object.get("lastUpdated").getAsString();
        String title = object.get("title").getAsString();
        String content = object.get("newsContent").getAsString();
        long newsId = object.get("newsfeedObjectId").getAsLong();

        LocalDateTime timeWritten = LocalDateTime.parse(timeWrittenString);
        LocalDateTime lastUpdated = LocalDateTime.parse(lastUpdatedString);

        boolean exists = false;
        for (News news : NewsFeedViewModel.NEWS_LIST) {
            if (news.getId() == newsId && news.getLastUpdated().toString().equals(lastUpdated.toString())) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            NewsFeedViewModel.NEWS_LIST.add(new News(newsId, title, content, timeWritten, lastUpdated));
        }
    }
}
