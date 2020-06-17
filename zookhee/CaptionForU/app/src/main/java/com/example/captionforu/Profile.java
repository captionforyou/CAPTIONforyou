package com.example.captionforu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.prnd.YouTubePlayerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {
    View rootview;
    String ID;
    String NN;
    String NO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview= inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            ID = bundle.getString("ID");
            NN = bundle.getString("NN");
            NO = bundle.getString("NO");
        }
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<UserInfo> call =  retrofitConnection.server.get_CertainUserinfo(Integer.parseInt(NO),"json");
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                try
                {
                    Log.e("succ","게시판 ceruserinfo 불러오기 성공");
                    UserInfo userinfo = response.body();
                    TextView editID = (TextView)rootview.findViewById(R.id.id) ;
                    editID.setText(userinfo.getid());
                    TextView editNickname = (TextView)rootview.findViewById(R.id.nickname) ;
                    editNickname.setText(userinfo.getnn());
                    TextView editPoints = (TextView)rootview.findViewById(R.id.points) ;
                    editPoints.setText(""+userinfo.getpoints());
                    TextView editMoney = (TextView)rootview.findViewById(R.id.money) ;
                    editMoney.setText(""+userinfo.getmoney());
                    TextView editRequestC = (TextView)rootview.findViewById(R.id.requestCount) ;
                    editRequestC.setText("요청수"+userinfo.getrequestNum()+"건");
                    TextView editRegisterC = (TextView)rootview.findViewById(R.id.registerCount) ;
                    editRegisterC.setText("등록수"+userinfo.getregiseterNum()+"건");
                    TextView editComplete = (TextView)rootview.findViewById(R.id.complete) ;
                    editComplete.setText(""+userinfo.getratingCompletenss()+" 점");
                    TextView editClarity = (TextView)rootview.findViewById(R.id.clarity) ;
                    editClarity.setText(""+userinfo.getratingClarity()+" 점");
                    TextView editTime = (TextView)rootview.findViewById(R.id.time) ;
                    editTime.setText(""+userinfo.getratingTime()+" 점");
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

        Button iwriteButton = (Button)rootview.findViewById(R.id.button_i_write);
        iwriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),showCaptionBoard.class);
                intent.putExtra("cookie","i_write");
                intent.putExtra("ID",ID);
                intent.putExtra("NN",NN);
                intent.putExtra("NO",NO);
                startActivityForResult(intent,0);
            }
        });

        Button workingonButton = (Button)rootview.findViewById(R.id.button_working_on);
        workingonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),showCaptionBoard.class);
                intent.putExtra("cookie","working_on");
                intent.putExtra("ID",ID);
                intent.putExtra("NN",NN);
                intent.putExtra("NO",NO);
                startActivityForResult(intent,0);
            }
        });

        Button doneButton = (Button)rootview.findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),showCaptionBoard.class);
                intent.putExtra("cookie","done");
                intent.putExtra("ID",ID);
                intent.putExtra("NN",NN);
                intent.putExtra("NO",NO);
                startActivityForResult(intent,0);
            }
        });
        return rootview;
    }
}