package com.example.captionforu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        final String ID=getIntent().getStringExtra("ID");
        final String NN=getIntent().getStringExtra("NN");
        final String NO=getIntent().getStringExtra("NO");
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
                    Button statusButton = (Button)findViewById(R.id.statusbutton);
                    final Integer stn = (Integer)subboard.getstatus();
                    if(stn==1)
                    {
                        statusButton.setText("접수 필요 상태");
                    }
                    if(stn==2)
                    {
                        statusButton.setText("접수 완료 상태");
                        statusButton.setEnabled(false);
                        if(subboard.getregisterNickname().equals(NN))
                        {
                            statusButton.setText("등록 완료하기");
                            statusButton.setEnabled(true);
                        }
                    }
                    if(stn==3) {
                        statusButton.setText("등록 완료 상태");
                    }
                    statusButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(stn==1)
                            {
                                
                            }
                        }
                    });
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