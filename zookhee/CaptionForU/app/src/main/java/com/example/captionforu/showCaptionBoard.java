package com.example.captionforu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class showCaptionBoard extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<SubBoard> boardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_caption_board);

        Intent intent = getIntent(); /*데이터 수신*/
        final String command = intent.getExtras().getString("cookie");
        final String ID=intent.getExtras().getString("ID");
        final String NN=intent.getExtras().getString("NN");
        final String NO=intent.getExtras().getString("NO");
        final LinearLayout list = (LinearLayout)findViewById(R.id.showcblist);
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<List<SubBoard>> call =  retrofitConnection.server.get_SubBoard("json");
        call.enqueue(new Callback<List<SubBoard>>() {
            @Override
            public void onResponse(Call<List<SubBoard>> call, Response<List<SubBoard>> response) {
                try
                {
                    Log.e("succ","게시판 db 불러오기 성공");
                    boardList=response.body();
                    for (int i = 0; i < boardList.size(); i++) {
                        if(command.equals("i_write"))
                        {
                            if(!boardList.get(i).getrequestNickname().equals(NN))
                                continue;
                        }
                        else if(command.equals("working_on"))
                        {
                            if(!boardList.get(i).getregisterNickname().equals(NN) || boardList.get(i).getstatus()!=2)
                                continue;
                        }
                        else if(command.equals("done"))
                        {
                            if(!boardList.get(i).getregisterNickname().equals(NN) || boardList.get(i).getstatus()!=3)
                                continue;
                        }
                        LinearLayout newlist = (LinearLayout) View.inflate(showCaptionBoard.this , R.layout.captionboardlayout, null);
                        final String link = boardList.get(i).link.toString();
                        ImageView img=(ImageView)newlist.findViewById(R.id.youtubeThumbnail);
                        Glide.with(showCaptionBoard.this).load("https://img.youtube.com/vi/"+link+"/hqdefault.jpg").into(img);
                        TextView lang = (TextView) newlist.findViewById(R.id.lang);
                        lang.setText("언어 : " + boardList.get(i).language);
                        TextView pay = (TextView) newlist.findViewById(R.id.pay);
                        pay.setText("페이 : " + boardList.get(i).tax+"원");
                        TextView status = (TextView) newlist.findViewById(R.id.status);
                        Log.d("youtubetest", "" + boardList.get(i).status);
                        Integer stn = (Integer)boardList.get(i).status;
                        if(stn==1)
                            status.setText("접수 필요");
                        if(stn==2)
                            status.setText("접수 완료");
                        if(stn==3)
                            status.setText("등록 완료");
                        //YouTubePlayerView youTubePlayerView = (YouTubePlayerView) newlist.findViewById(R.id.youtubeView);
                        //youTubePlayerView.play(link, null);
                        final Integer temp = i;
                        newlist.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                Intent intent = new Intent(showCaptionBoard.this, subBoardPopupActivity.class);
                                intent.putExtra("Cookie",boardList.get(temp).no.toString());
                                startActivity(intent);
                            }
                        });;
                        list.addView(newlist);
                    }
                }
                catch (Exception e) {
                    Log.e("fail","게시판 db 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<SubBoard>> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });

    }


}
