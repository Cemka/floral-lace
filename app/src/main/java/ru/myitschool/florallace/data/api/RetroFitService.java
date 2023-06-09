package ru.myitschool.florallace.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetroFitService {

//    private static final String BASE_URL = "http://192.168.1.4:8080";
//    private static final String BASE_URL = "http://192.168.1.11:8080";
//    private static final String BASE_URL = "http://192.168.60.177:8080";
//    private static final String BASE_URL = "http://192.168.91.177:8080";
    private static final String BASE_URL = "http://172.20.10.3:8080";
    private static Retrofit retrofit;

    private static Retrofit create(){
        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    //Create singleton
    public static Retrofit getInstance(){
        if(retrofit == null) retrofit = create();
        return retrofit;
    }
}
