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

    @GET("userinfos/")
    Call<List<UserInfo>> get_Userinfo(@Query("format") String json);

    @GET("userinfos/{pk}")
    Call<UserInfo> get_CertainUserinfo(@Path("pk") int pk, @Query("format") String json);

    @GET("userinfos/")
    Call<UserInfo> get_CertainnicknameUserinfo(@Query("format") String json,@Field("registernickname") String registernickname);

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
    Call<UserInfo> patchrequestnum_CertainUserinfo(@Path("pk") int pk, @Query("format") String json, @Field("requestnum") Integer requestnum);

    @FormUrlEncoded
    @PATCH("userinfos/{pk}/")
    Call<UserInfo> patchregisternum_CertainUserinfo(@Path("pk") int pk, @Query("format") String json, @Field("registernum") Integer registernum);

    @FormUrlEncoded
    @PATCH("userinfos/{pk}/")
    Call<UserInfo> patchratingCnt_CertainUserinfo(@Path("pk") int pk, @Query("format") String json, @Field("ratingcnt") Integer ratingcnt);

    @FormUrlEncoded
    @PATCH("userinfos/{pk}/")
    Call<UserInfo> patchratingCompleteness_CertainUserinfo(@Path("pk") int pk, @Query("format") String json, @Field("ratingcompleteness") double ratingcnt);

    @FormUrlEncoded
    @PATCH("userinfos/{pk}/")
    Call<UserInfo> patchratingClarity_CertainUserinfo(@Path("pk") int pk, @Query("format") String json, @Field("ratingclarity") double ratingcnt);

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

    @FormUrlEncoded
    @PATCH("subboards/{pk}/")
    Call<SubBoard> patchregisterno_SubBoard(@Path("pk") int pk, @Query("format") String json, @Field("registerno") Integer registerno);

    @FormUrlEncoded
    @PATCH("subboards/{pk}/")
    Call<SubBoard> patchisrated_SubBoard(@Path("pk") int pk, @Query("format") String json, @Field("israted") Integer israted);

}