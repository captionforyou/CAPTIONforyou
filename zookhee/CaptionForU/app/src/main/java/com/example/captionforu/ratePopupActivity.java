package com.example.captionforu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ratePopupActivity extends AppCompatActivity {

    float completenessRate;
    float clarityRate;
    String ID;
    String NO;
    String NN;
    Integer registerno;
    Integer subboardno;
    Integer subboardpay;
    UserInfo myuserinfo;
    UserInfo registeruserinfo;
    Integer rateCnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_popup);

        ID = getIntent().getStringExtra("ID");
        NO = getIntent().getStringExtra("NO");
        NN = getIntent().getStringExtra("NN");
        registerno = getIntent().getIntExtra("registerno", 0);
        subboardno = getIntent().getIntExtra("subboardno", 0);
        subboardpay = getIntent().getIntExtra("subboardpay", 0);
        Log.e("registerno",""+registerno);
        loadmyUser();
        loadregisterUser();

        RatingBar rbForCompleteness = (RatingBar) findViewById(R.id.ratingBarForCompleteness);

        rbForCompleteness.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                completenessRate = rating;
            }
        });

        RatingBar rbForClarity = (RatingBar) findViewById(R.id.ratingBarForClarity);

        rbForClarity.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                clarityRate = rating;
            }
        });

        Button rateButton = (Button)findViewById(R.id.rateButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patchmypoints();
                patchhispoints();
                patchisrated(subboardno,1);
                finish();
            }
        });
    }
    public void loadmyUser() {
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<UserInfo> usercall =  retrofitConnection.server.get_CertainUserinfo(Integer.parseInt(NO),"json");
        usercall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","게시판 ceruserinfo 불러오기 성공");
                    myuserinfo = response.body();
                }
                catch (Exception e) {
                    Log.e("fail","게시판 ceruserinfo 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }

    public void loadregisterUser() {
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<UserInfo> usercall =  retrofitConnection.server.get_CertainUserinfo(registerno,"json");
        usercall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","게시판 cerregisteruserinfo 불러오기 성공");
                    registeruserinfo = response.body();
                }
                catch (Exception e) {
                    Log.e("fail","게시판 cerregisteruserinfo 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }

    public void patchmypoints() {
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<UserInfo> call =  retrofitConnection.server.patchpoints_CertainUserinfo(Integer.parseInt(NO),"json",(myuserinfo.getpoints()+10));
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","patch point성공");
                    patchhismoney();
                }
                catch (Exception e) {
                    Log.e("fail","patch point실패");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }

    public void patchhispoints() {
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Integer pluspoints = (int)(completenessRate*20)+(int)(clarityRate*20);
        Call<UserInfo> call =  retrofitConnection.server.patchpoints_CertainUserinfo(registerno,"json",(registeruserinfo.getpoints()+pluspoints));
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","patch point성공");
                    patchhismoney();
                }
                catch (Exception e) {
                    Log.e("fail","patch point실패");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }

    public void patchhismoney() {
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<UserInfo> call =  retrofitConnection.server.patchmoney_CertainUserinfo(registerno,"json",(registeruserinfo.getmoney()+subboardpay));
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","patch money성공");
                    patchhisrate();
                }
                catch (Exception e) {
                    Log.e("fail","patch money실패");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }

    public void patchhisrate() {

        rateCnt = registeruserinfo.getratingCnt();
        Log.e("test",rateCnt+"");
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<UserInfo> cntcall =  retrofitConnection.server.patchratingCnt_CertainUserinfo(registerno,"json",rateCnt+1);
        cntcall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","patch ratecnt성공");
                    patchhisratecom();
                }
                catch (Exception e) {
                    Log.e("fail","patch ratecnt실패");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });

    }
    public void patchhisratecom(){
        double rateCom = registeruserinfo.getratingCompletenss()*rateCnt;
        rateCom+=completenessRate;
        rateCom/=rateCnt+1;
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<UserInfo> comcall =  retrofitConnection.server.patchratingCompleteness_CertainUserinfo(registerno,"json",rateCom);
        comcall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","patch ratecom성공");
                    patchhisratecla();
                }
                catch (Exception e) {
                    Log.e("fail","patch ratecom실패");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }
    public void patchhisratecla(){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        double rateCla = registeruserinfo.getratingClarity()*rateCnt;
        rateCla+=clarityRate;
        rateCla/=rateCnt+1;
        Call<UserInfo> clacall =  retrofitConnection.server.patchratingClarity_CertainUserinfo(registerno,"json",rateCla);
        clacall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","patch rateclarity성공");
                }
                catch (Exception e) {
                    Log.e("fail","patch rateclarity실패");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }
    public void patchisrated(Integer patchno,Integer status){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<SubBoard> call =  retrofitConnection.server.patchisrated_SubBoard(patchno,"json",status);
        call.enqueue(new Callback<SubBoard>() {
            @Override
            public void onResponse(Call<SubBoard> call, Response<SubBoard> response) {
                try
                {
                    Log.e("succ","patch israted성공");
                }
                catch (Exception e) {
                    Log.e("fail","patch israted실패");
                }
            }
            @Override
            public void onFailure(Call<SubBoard> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }
}