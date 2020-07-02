package com.example.captionforu;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnection {
    String URL = "https://5a880ac1c4f4.jp.ngrok.io/"; // 서버 API
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService server = retrofit.create(ApiService.class);

}
