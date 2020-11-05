package no.ntnu.epsilon_app.api;

import android.media.Image;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface EpsilonAPI {
    @GET("web/newsfeed")
    Call<ResponseBody> getNewsfeed();

    @PUT("web/postNews")
    @FormUrlEncoded
    Call<ResponseBody> postNews(@Field("title") String title,
                                @Field("content") String content);
    @Multipart
    @POST("web/uploadPicture")
    Call<ResponseBody> uploadPicture(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("web/uploadPictureAsString")
    Call<ResponseBody> uploadPictureAsString(@Field("base64String") String base64String, @Field("userId") String userId, @Field("filename") String filename);

    @GET("web/getUserPictures")
    Call<ResponseBody> getUserPictures();

    @GET("web/getAboutUsObjects")
    Call<ResponseBody> getAboutUsObjects();
}
