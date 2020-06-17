package com.example.captionforu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newreqActivity extends AppCompatActivity {


    String selectedlanguage;
    String selectedcontents;
    EditText youtubelink;
    EditText newpay;
    Spinner langspinner;
    Spinner contentspinner;
    Button newregButton;
    String ID;
    String NN;
    Integer cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newreq);


        youtubelink=(EditText)findViewById(R.id.newyoutubelink);
        newpay=(EditText)findViewById(R.id.newpay);
        langspinner=(Spinner)findViewById(R.id.langspinner);
        contentspinner=(Spinner)findViewById(R.id.contentspinner);
        newregButton =(Button)findViewById(R.id.newregbutton);

        ID = getIntent().getStringExtra("ID");
        NN = getIntent().getStringExtra("NN");


        TextView reqnickname=(TextView)findViewById(R.id.reqnickname);
        reqnickname.setText(NN);

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<List<SubBoard>> call =  retrofitConnection.server.get_SubBoard("json");
        call.enqueue(new Callback<List<SubBoard>>() {
            @Override
            public void onResponse(Call<List<SubBoard>> call, Response<List<SubBoard>> response) {
                try
                {
                    Log.e("succ","게시판 db 불러오기 성공");
                    boardList=response.body();
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


        langspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedlanguage=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedlanguage=parent.getItemAtPosition(0).toString();
            }
        });

        contentspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedcontents=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedcontents=parent.getItemAtPosition(0).toString();
            }
        });

        newregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertNewSubBoard(NN);
            }
        });

    }
    public List<SubBoard> boardList ;

    private void insertNewSubBoard(String nickname) {

        cancel=0;
        for (int i = 0; i < boardList.size(); i++) {
            if(boardList.get(i).link.equals(youtubelink.getText().toString()) && boardList.get(i).language.equals(selectedlanguage))
                cancel=1;
        }

        //Glide.with(this).load("https://img.youtube.com/vi/"+youtubelink.getText().toString()+"/hqdefault.jpg");

        if(newpay.getText().toString().equals("") || youtubelink.getText().toString().equals(""))
        {
            Toast.makeText(this, "빈칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(cancel==2)
        {
            Toast.makeText(this, "잘못된 주소입니다.", Toast.LENGTH_SHORT).show();
        }
        else if(cancel==1)
        {
            Toast.makeText(this, "이미 존재하는 요청입니다.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SubBoard subboard = new SubBoard();
            subboard.setlink(youtubelink.getText().toString());
            subboard.setstatus(1);
            subboard.setrequestNickname(NN);
            Log.e("anjwl",subboard.getrequestNickname());
            subboard.setcontents(selectedcontents);
            subboard.setlanguage(selectedlanguage);
            subboard.settax(Integer.parseInt(newpay.getText().toString()));
            subboard.settime((int) (System.currentTimeMillis()));
            RetrofitConnection retrofitConnection = new RetrofitConnection();
            Call<SubBoard> postcall =  retrofitConnection.server.post_SubBoard("json",subboard);
            postcall.enqueue(new Callback<SubBoard>() {
                @Override
                public void onResponse(Call<SubBoard> call, Response<SubBoard> response) {
                    try {
                        Log.e("succ","ID 등록 성공");
                    }
                    catch (Exception e) {
                        int StatusCode = response.code();
                        Log.e("fail","ID 등록 실패");
                    }

                }

                @Override
                public void onFailure(Call<SubBoard> call, Throwable t) {
                    Log.e("fail",t.toString());
                }
            });
            setResult(RESULT_OK);
            finish();
        }
    }
}
