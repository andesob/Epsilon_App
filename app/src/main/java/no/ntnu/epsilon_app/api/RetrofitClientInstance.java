package no.ntnu.epsilon_app.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    //IP for server
    //private static final String BASE_URL = "http://158.38.101.247:8080/Epsilon_Backend/api/";
    private static final String BASE_URL = "https://epsilonbackend.uials.no/Epsilon_Backend/api/";

    //IP at school wifi
    //private static final String BASE_URL = "http://10.22.179.231:8080/Epsilon_Backend/api/";

    //local IP for emulator
    //private static final String BASE_URL = "http://10.0.2.2:8080/Epsilon_Backend/api/";

    //IP at home
    //private static final String BASE_URL = "http://192.168.1.185:8080/Epsilon_Backend/api/";

    private static Retrofit retrofit;
    private static RetrofitClientInstance SINGLETON;

    private RetrofitClientInstance(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetrofitClientInstance getSINGLETON(){
        if (SINGLETON == null){
            SINGLETON = new RetrofitClientInstance();
        }
        return SINGLETON;
    }

    public EpsilonAPI getAPI(){
        return retrofit.create(EpsilonAPI.class);
    }

    public static void addInterceptor(OkHttpClient.Builder httpClient){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
    }
}

