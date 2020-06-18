package com.example.profilte;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //<----------------------------------------------->//
    //해본거//수영
    //<----------------------------------------------->//


    public static final String API_URL = "https://28b8190a668c.jp.ngrok.io/";

    @GET("userinfos/")
    Call<List<UserInfo>> get_Userinfo(@Query("format") String json);

    @GET("userinfos/{pk}")
    Call<UserInfo> get_CertainUserinfo(@Path("pk") int pk, @Query("format") String json);

    @POST("userinfos/")
    Call<UserInfo> post_Userinfo(@Query("format") String json, @Body UserInfo userinfo);

    @GET("subboards/")
    Call<List<SubBoard>> get_SubBoard(@Query("format") String json);

    @GET("subboards/{pk}")
    Call<SubBoard> get_CertainSubBoard(@Path("pk") int pk, @Query("format") String json);

    @POST("subboards/")
    Call<SubBoard> post_SubBoard(@Query("format") String json, @Body SubBoard subboard);
/*
   @POST("userinfos/")
   Call<UserInfo> post_json_Userinfo_java(@Query("format") String json, @Body UserInfo json_test_java);

   @FormUrlEncoded
   @PATCH("userinfos/{pk}/")
   Call<ResponseBody> patch_Userinfo(@Path("pk") int pk, @Query("format") String json, @Field("Userinfo") String test);


   @DELETE("userinfos/{pk}/")
    Call<ResponseBody> delete_Usereinfo(@Path("pk") int pk, @Query("format") String json);
*/
}