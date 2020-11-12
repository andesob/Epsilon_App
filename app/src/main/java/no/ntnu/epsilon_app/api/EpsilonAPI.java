package no.ntnu.epsilon_app.api;

import android.media.Image;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import java.util.List;

import no.ntnu.epsilon_app.ui.faq.Faq;
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

    @GET("web/get_faqs")
    Call<List<Faq>> getFaqs();

    @POST("web/ask_question")
    @FormUrlEncoded
    Call<ResponseBody> askQuestion(@Field("question") String questionAsked);

    @POST("web/edit_faq")
    @FormUrlEncoded
    Call<ResponseBody> edit_faq(@Field("question") String question,
                                @Field("answer")String answer,
                                @Field("questionId") long questionID);

    @PUT("web/add_faqs")
    @FormUrlEncoded
    Call<ResponseBody> add_faqs(@Field("question") String question,
                                @Field("answer") String answer);

    @PUT("web/delete_faq")
    @FormUrlEncoded
    Call<ResponseBody> delete_faq(@Field("id") long faqId);


    @POST("auth/create_user")
    @FormUrlEncoded
    Call<ResponseBody> createUser(@Field("firstName") String firstName,
                                  @Field("lastName") String lastName,
                                  @Field("pwd") String pwd,
                                  @Field("email") String email);

}
