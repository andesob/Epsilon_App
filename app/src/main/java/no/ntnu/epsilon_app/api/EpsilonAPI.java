package no.ntnu.epsilon_app.api;


import no.ntnu.epsilon_app.ui.calendar.Calendar;
import okhttp3.MultipartBody;

import java.util.List;

import no.ntnu.epsilon_app.ui.faq.Faq;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EpsilonAPI {
    @GET("web/newsfeed")
    Call<ResponseBody> getNewsfeed();

    @GET("web/users")
    Call<ResponseBody> getUsers();

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

    @GET("web/getcalendar")
    Call<List<Calendar>> getCalendarItems();

    @PUT("web/add_faqs")
    @FormUrlEncoded
    Call<ResponseBody> add_faqs(@Field("question") String question,
                                @Field("answer") String answer);
    @PUT("web/add_calendar_item")
    @FormUrlEncoded
    Call<ResponseBody> addCalendarItem( @Field("title") String title,
                                        @Field("description") String description,
                                        @Field("latlng") String  latLng,
                                        @Field("starttime") String  startTime,
                                        @Field("endtime") String  endTime,
                                        @Field("address") String  address);

    @GET("auth/login")
    Call<ResponseBody> loginUser(
            @Query("email") String email,
            @Query("pwd") String password
    );

    @POST("auth/twofactor")
    @FormUrlEncoded
    Call<ResponseBody> checkTwoFactor(@Field("email") String email,
                                      @Field("pwd") String pwd,
                                      @Field("2factorkey") String key);

    @PUT("web/delete_faq")
    @FormUrlEncoded
    Call<ResponseBody> delete_faq(@Field("id") long faqId);


    @POST("auth/create_user")
    @FormUrlEncoded
    Call<ResponseBody> createUser(@Field("firstName") String firstName,
                                  @Field("lastName") String lastName,
                                  @Field("pwd") String pwd,
                                  @Field("email") String email);

    @GET("auth/verify")
    Call<ResponseBody> verifyJwt(@Header("Authorization") String token);

    @PUT("auth/changepassword")
    @FormUrlEncoded
    Call<ResponseBody> changePassword(@Field("oldPwd") String oldPassword,
                                      @Field("newPwd") String newPassword);

    @POST("auth/forgotpassword")
    @FormUrlEncoded
    Call<ResponseBody> forgotPassword(@Field("email") String email);

    @GET("auth/isTokenExpired")
    Call<ResponseBody> useRefreshToken(@Header("refreshToken") String refreshToken);

    @PUT("web/delete_calendar_item")
    @FormUrlEncoded
    Call<ResponseBody> deleteCalendarItem(@Field("id") long id);
}


