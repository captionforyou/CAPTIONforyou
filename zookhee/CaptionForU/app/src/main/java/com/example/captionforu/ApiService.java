package com.example.captionforu;


import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("get_video_info")
    Call<JSONObject> get_youtubeinfo(@Query("video_id") String id);

    @GET("userinfos/")
    Call<List<UserInfo>> get_Userinfo(@Query("format") String json);

    @GET("userinfos/{pk}")
    Call<UserInfo> get_CertainUserinfo(@Path("pk") int pk, @Query("format") String json);

    @POST("userinfos/")
    Call<UserInfo> post_Userinfo(@Query("format") String json, @Body UserInfo userinfo);

    @FormUrlEncoded
    @PATCH("userinfos/{pk}/")
    Call<UserInfo> patchpoints_CertainUserinfo(@Path("pk") int pk, @Query("format") String json, @Field("points") Integer points);

    @FormUrlEncoded
    @PATCH("userinfos/{pk}/")
    Call<UserInfo> patchmoney_CertainUserinfo(@Path("pk") int pk, @Query("format") String json, @Field("money") Integer money);

    @FormUrlEncoded
    @PATCH("userinfos/{pk}/")
    Call<UserInfo> patchnoticecnt_CertainUserinfo(@Path("pk") int pk, @Query("format") String json, @Field("noticecnt") String noticecnt);

    @GET("subboards/")
    Call<List<SubBoard>> get_SubBoard(@Query("format") String json);

    @GET("subboards/{pk}")
    Call<SubBoard> get_CertainSubBoard(@Path("pk") int pk, @Query("format") String json);

    @POST("subboards/")
    Call<SubBoard> post_SubBoard(@Query("format") String json, @Body SubBoard subboard);

    @FormUrlEncoded
    @PATCH("subboards/{pk}/")
    Call<SubBoard> patchstatus_SubBoard(@Path("pk") int pk, @Query("format") String json, @Field("status") Integer status);

    @FormUrlEncoded
    @PATCH("subboards/{pk}/")
    Call<SubBoard> patchregisternickname_SubBoard(@Path("pk") int pk, @Query("format") String json, @Field("registernickname") String registernickname);

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