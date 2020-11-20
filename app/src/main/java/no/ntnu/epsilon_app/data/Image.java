package no.ntnu.epsilon_app.data;

import android.graphics.Bitmap;

public class Image {
    long imageId;

    Bitmap bitmap;

    long userId;

    public Image(long imageId, long userId, Bitmap bitmap){
        this.imageId = imageId;
        this.userId = userId;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public long getImageId(){
        return imageId;
    }

    public long getUserId(){
        return userId;
    }
}
