package com.example.captionforu;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnection {
    String URL = "https://d1b68028ecb1.jp.ngrok.io/"; // 서버 API
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService server = retrofit.create(ApiService.class);

}
