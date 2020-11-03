package no.ntnu.epsilon_app.api;

import java.util.List;

import no.ntnu.epsilon_app.ui.faq.Faq;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface EpsilonAPI {
    @GET("web/newsfeed")
    Call<ResponseBody> getNewsfeed();

    @PUT("web/postNews")
    @FormUrlEncoded
    Call<ResponseBody> postNews(@Field("title") String title,
                                @Field("content") String content);

    @GET("web/get_faqs")
    Call<List<Faq>> getFaqs();

    @POST("web/ask_question")
    @FormUrlEncoded
    Call<ResponseBody> askQuestion(@Field("question") String questionAsked);


}
