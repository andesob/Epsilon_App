package no.ntnu.epsilon_app.data;

import android.graphics.Bitmap;

public class Image {
    long imageId;

    String imageBase64String;

    Bitmap bitmap;

    int userId;

    public Image(long imageId, int userId, Bitmap bitmap){
        this.imageId = imageId;
        this.userId = userId;
        //this.imageBase64String = imageBase64String;
        this.bitmap = bitmap;
    }

    public String getImageBase64String(){
        return imageBase64String;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public long getImageId(){
        return imageId;
    }
}
