package com.example.captionforu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import kr.co.prnd.YouTubePlayerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class subBoardPopupActivity extends AppCompatActivity {
    SubBoard subboard;
    TextView popuprequestnickname;
    TextView popupregisternickname;
    String ID;
    String NN;
    String NO;
    String cookie;
    UserInfo userinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_board_popup);
        cookie=getIntent().getStringExtra("Cookie");
        ID=getIntent().getStringExtra("ID");
        NN=getIntent().getStringExtra("NN");
        NO=getIntent().getStringExtra("NO");
        loadUser();
        makeactivity();
    }
    public void makeactivity(){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<SubBoard> call =  retrofitConnection.server.get_CertainSubBoard(Integer.parseInt(cookie),"json");
        call.enqueue(new Callback<SubBoard>() {
            @Override
            public void onResponse(Call<SubBoard> call, Response<SubBoard> response) {
                try
                {
                    Log.e("succ","게시판 cerdb 불러오기 성공");
                    subboard = response.body();
                    YouTubePlayerView youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeView);
                    youTubePlayerView.play(subboard.getlink(),null);
                    TextView popupyoutubelink = findViewById(R.id.popupyoutubelink);
                    popupyoutubelink.setText("https://youtu.be/"+subboard.getlink());
                    TextView popuplanguage =findViewById(R.id.popuplanguage);
                    popuplanguage.setText(subboard.getlanguage());
                    TextView popupcontents=findViewById(R.id.popupcontents);
                    popupcontents.setText(subboard.getcontents());
                    TextView popuppay=findViewById(R.id.popuppay);
                    popuppay.setText(subboard.gettax().toString());
                    popuprequestnickname=findViewById(R.id.popuprequestnickname);
                    popuprequestnickname.setText(subboard.getrequestNickname());
                    popupregisternickname=findViewById(R.id.popupregistrnickname);
                    visregisternickname();
                    final Button statusButton = (Button)findViewById(R.id.statusbutton);
                    final Integer stn = (Integer)subboard.getstatus();
                    if(stn==1)
                    {
                        statusButton.setText("접수 필요 상태");
                        if(subboard.getrequestNickname().equals(NN))
                        {
                            statusButton.setEnabled(false);
                        }
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
                        statusButton.setEnabled(false);
                    }
                    statusButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(stn==1)
                            {
                                new AlertDialog.Builder(subBoardPopupActivity.this)
                                        .setMessage("정말 접수하시겠습니까?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                Toast.makeText(subBoardPopupActivity.this, "접수를 완료했습니다.", Toast.LENGTH_SHORT).show();
                                                subboard.setstatus(2);
                                                subboard.setregisterNickname(NN);
                                                patchstatus(subboard.getno(),2);
                                                patchregisternickname(subboard.getno(),NN);
                                                patchpoints();
                                                statusButton.setText("등록 완료하기");
                                                statusButton.setEnabled(true);
                                            }})
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }})
                                        .show();

                            }
                            else if(stn==2)
                            {
                                new AlertDialog.Builder(subBoardPopupActivity.this)
                                        .setMessage("정말 등록을 완료하시겠습니까?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                Toast.makeText(subBoardPopupActivity.this, "등록를 완료했습니다.", Toast.LENGTH_SHORT).show();
                                                subboard.setstatus(3);
                                                subboard.setregisterNickname(NN);
                                                patchstatus(subboard.getno(),3);
                                                patchregisternickname(subboard.getno(),NN);
                                                statusButton.setText("등록 완료 상태");
                                                statusButton.setEnabled(true);
                                            }})
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }})
                                        .show();
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
    public void visregisternickname(){
        if(subboard.getstatus()==2){
            popupregisternickname.setText("접수한 사람 : "+subboard.getregisterNickname());
            LinearLayout registerlayout=findViewById(R.id.registerlayout);
            registerlayout.setVisibility(View.VISIBLE);
        }
        if(subboard.getstatus()==3){
            popupregisternickname.setText("등록한 사람 : "+subboard.getregisterNickname());
            LinearLayout registerlayout=findViewById(R.id.registerlayout);
            registerlayout.setVisibility(View.VISIBLE);
        }
    }
    public void patchstatus(Integer patchno,Integer status){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<SubBoard> call =  retrofitConnection.server.patchstatus_SubBoard(patchno,"json",status);
        call.enqueue(new Callback<SubBoard>() {
            @Override
            public void onResponse(Call<SubBoard> call, Response<SubBoard> response) {
                try
                {
                    Log.e("succ","patch status성공");
                    visregisternickname();
                }
                catch (Exception e) {
                    Log.e("fail","patch status실패");
                }
            }
            @Override
            public void onFailure(Call<SubBoard> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });

    }
    public void patchregisternickname(Integer patchno, String nickname){
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<SubBoard> call =  retrofitConnection.server.patchregisternickname_SubBoard(patchno,"json",nickname);
        call.enqueue(new Callback<SubBoard>() {
            @Override
            public void onResponse(Call<SubBoard> call, Response<SubBoard> response) {
                try
                {
                    Log.e("succ","patch registernickanme성공");
                }
                catch (Exception e) {
                    Log.e("fail","patch registernickanme실패");
                }
            }
            @Override
            public void onFailure(Call<SubBoard> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }
    public void loadUser() {
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<UserInfo> usercall =  retrofitConnection.server.get_CertainUserinfo(Integer.parseInt(NO),"json");
        usercall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","게시판 ceruserinfo 불러오기 성공");
                    userinfo = response.body();
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
    public void patchpoints() {
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<UserInfo> call =  retrofitConnection.server.patchpoints_CertainUserinfo(Integer.parseInt(NO),"json",(userinfo.getpoints()+10));
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","patch point성공");
                }
                catch (Exception e) {
                    Log.e("fail","patch point성공");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }
}