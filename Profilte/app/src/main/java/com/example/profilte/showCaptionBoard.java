package com.example.profilte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class showCaptionBoard extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private static String DB_PATH = "/data/data/com.example.profilte/databases/";
    private static String DB_NAME = "CaptionForYou";
    private SQLiteDatabase profiledb;
    private static final String TAG = "showCaptionBoard";
    int cursorCount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_caption_board);

        // 리사이클러뷰의 notify()처럼 데이터가 변했을 때 성능을 높일 때 사용한다.

        Intent intent = getIntent(); /*데이터 수신*/
        String command = intent.getExtras().getString("command"); /*String형*/
        //만약에 command가 request 이면..working이면..

        profiledb=SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String SQL = null;

        if(command.equals("requestBoard")){
            SQL = "select * from subBoard where requestNickname = 'moon'";
        }
        else if(command.equals("workingOnBoard")){
            SQL = "select * from subBoard where registerNickname = 'moon' and status = 2";
        }
        else if(command.equals("workedBoard")){
            SQL = "select * from subBoard where registerNickname = 'moon' and status = 3";
        }

        Cursor profile = profiledb.rawQuery(SQL, null);
        cursorCount = profile.getCount();
        ArrayList<menu> m_orders = new ArrayList<menu>();

        int i =0;

        if(profile != null)
        {
            while (profile.moveToNext())
            {
                menu m = new menu(profile.getString(1), profile.getInt(2),profile.getString(4),
                        profile.getString(3), profile.getInt(7));
                m_orders.add(m);
                i++;
            }
        }

        MenuAdapting m_adapter = new MenuAdapting(this, R.layout.onedata_inrow, m_orders) ;
        ListView listview = (ListView) findViewById(R.id.listview) ;
        listview.setAdapter(m_adapter) ;

    }
    private class MenuAdapting extends ArrayAdapter<menu> {
        private ArrayList<menu> items;

        public MenuAdapting(Context context, int textViewResourceId, ArrayList<menu> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.onedata_inrow, null);
            }

            menu p = items.get(position);

            if (p != null) {

                YouTubeThumbnailView thumbnailView = (YouTubeThumbnailView) v.findViewById(R.id.video_thumbnail_image_view);
                Button button = (Button)v.findViewById(R.id.checkButton);
                EditText wholeUrl = (EditText)v.findViewById(R.id.link);
                TextView requesterNickname = (TextView)v.findViewById(R.id.requestNickName);
                TextView registerNickname = (TextView)v.findViewById(R.id.registerNickName);
                TextView resultPay = (TextView)v.findViewById(R.id.pay);

                final String urlYouTube = p.getUrl().substring(p.getUrl().indexOf("=")+1);

                thumbnailView.initialize(YouTubeConfig.getApiKey(), new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {

                        youTubeThumbnailLoader.setVideo(urlYouTube);

                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                Log.d(TAG, "onThumbnailLoaded");
                                youTubeThumbnailLoader.release();
                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                                Log.d(TAG, "onThumbnailfail");
                            }
                        });
                        //     readyForLoadingYoutubeThumbnail = true;

                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                        if (youTubeInitializationResult.isUserRecoverableError()) {
                            Log.d(TAG, "error Dialog");
                        } else {
                            String errorMessage = String.format("에러", youTubeInitializationResult.toString());
                            Log.d(TAG, errorMessage);
                        }

                        //    readyForLoadingYoutubeThumbnail = true;
                    }
                });
                if (p.getStatus() == 1) button.setText("접수");
                if (p.getStatus() == 2) button.setText("접수 완료");
                else if (p.getStatus() == 3) button.setText("자막 등록 완료");

                wholeUrl.setText(p.getUrl());
                requesterNickname.setText(p.getRequester());
                registerNickname.setText(p.getRegister());
                resultPay.setText(""+p.getPay());
            }
            return v;
        }

    }

    class menu {

        private String url;
        private int status;
        private String requester;
        private String register;
        private int pay;

        public menu(String _url, int _status, String _requester, String _register, int _pay){
            this.url = _url;
            this.status = _status;
            this.requester = _requester;
            this.register = _register;
            this.pay = _pay;
        }

        public String getUrl() {
            return url;
        }

        public int getStatus() {
            return status;
        }
        public String getRequester(){
            return requester;
        }
        public String getRegister(){
            return register;
        }
        public int getPay(){
            return pay;
        }

    }

}
