package com.example.captionforu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import kr.co.prnd.YouTubePlayerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class subBoardPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_board_popup);
        String cookie=getIntent().getStringExtra("Cookie");

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<SubBoard> call =  retrofitConnection.server.get_CertainSubBoard(Integer.parseInt(cookie),"json");
        call.enqueue(new Callback<SubBoard>() {
            @Override
            public void onResponse(Call<SubBoard> call, Response<SubBoard> response) {
                try
                {
                    Log.e("succ","게시판 cerdb 불러오기 성공");
                    SubBoard subboard = response.body();
                    YouTubePlayerView youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeView);
                    Log.e("ytest",subboard.getlink());
                    youTubePlayerView.play(subboard.getlink(),null);
                    TextView popupyoutubelink = findViewById(R.id.popupyoutubelink);
                    popupyoutubelink.setText("https://youtu.be/"+subboard.getlink());
                    TextView popuplanguage =findViewById(R.id.popuplanguage);
                    popuplanguage.setText(subboard.getlanguage());
                    TextView popupcontents=findViewById(R.id.popupcontents);
                    popupcontents.setText(subboard.getcontents());
                    TextView popuppay=findViewById(R.id.popuppay);
                    popuppay.setText(subboard.gettax().toString());
                    TextView popuprequestnickname=findViewById(R.id.popuprequestnickname);
                    popuprequestnickname.setText(subboard.getrequestNickname());
                    TextView popupregisternickname=findViewById(R.id.popupregistrnickname);
                    if(subboard.getstatus()!=1){
                        popupregisternickname.setText(subboard.getregisterNickname());
                        LinearLayout registerlayout=findViewById(R.id.registerlayout);
                        registerlayout.setVisibility(View.VISIBLE);
                    }
                    LinearLayout vislayout=findViewById(R.id.vislayout);
                    vislayout.setVisibility(View.VISIBLE);
                }
                catch (Exception e) {
                    Log.e("fail","게시판 ceredb 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<SubBoard> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }
}