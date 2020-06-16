package com.example.profilte;

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
import android.widget.ListView;
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

public class showCaptionBoard extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    // TODO : assets 폴더에 있는 경우 "", 그 외 경로기입
    private static String DB_PATH = "";
    // TODO : assets 폴더에 있는 DB명 또는 별도의 데이터베이스 파일이름
    private static String DB_NAME ="CaptionForYou.db";
    private SQLiteDatabase profiledb;
    private static final String TAG = "showCaptionBoard";

    int cursorCount =0;
    int workingOnBoardcheck = 0;
    int resultOKCheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_caption_board);

        // 리사이클러뷰의 notify()처럼 데이터가 변했을 때 성능을 높일 때 사용한다.

        Intent intent = getIntent(); /*데이터 수신*/
        String command = intent.getExtras().getString("command"); /*String형*/
        //만약에 command가 request 이면..working이면..
        //ProfileDatabaseManager databaseManager = ProfileDatabaseManager.getInstance(this);


        DataBaseAdapter mDbHelper = new DataBaseAdapter(this);
        mDbHelper.createDatabase();
        profiledb = mDbHelper.open();

        String SQL = null;

        if(command.equals("requestBoard")){
            SQL = "select * from subBoard where requestNickname = 'moon'";
            workingOnBoardcheck = 0;
        }
        else if(command.equals("workingOnBoard")){
            SQL = "select * from subBoard where registerNickname = 'moon' and status = 2";
            workingOnBoardcheck = 1;
        }
        else if(command.equals("workedBoard")){
            SQL = "select * from subBoard where registerNickname = 'moon' and status = 3";
            workingOnBoardcheck = 0;
        }

        Cursor profile = profiledb.rawQuery(SQL, null);
        cursorCount = profile.getCount();
        ArrayList<menu> m_orders = new ArrayList<menu>();

        int i =0;

        if(profile != null)
        {
            while (profile.moveToNext())
            {
                menu m = new menu(profile.getString(1), profile.getInt(2),profile.getString(5)
                        , profile.getInt(7));
                m_orders.add(m);
                i++;
            }
        }

        MenuAdapting m_adapter = new MenuAdapting(this, R.layout.onedata_inrow, m_orders) ;
        ListView listview = (ListView) findViewById(R.id.listview) ;
        listview.setAdapter(m_adapter) ;

    }

    public void wantToConfirm(final int position){

            Intent intent = new Intent(this, AskingConfirmPopUp.class);
            intent.putExtra("data", position);
            startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                //등록 완료 버튼 누른 position 받기
                int position =  data.getExtras().getInt("data");

                String SQL = "select * from subBoard where registerNickname = 'moon' and status = 2";
                Cursor profile = profiledb.rawQuery(SQL, null);

                int i = 0;
                int specific_no_from_position = 0;
                if(profile != null)
                {
                    while (profile.moveToNext())
                    {
                        if(i==position){
                            specific_no_from_position = profile.getInt(0);
                        }
                        i++;
                    }
                }

                DataBaseAdapter mDbHelper = new DataBaseAdapter(this);
                mDbHelper.getDatabase();
                profiledb = mDbHelper.write();
//                ContentValues values = new ContentValues();
//                values.put("status",3);
//                profiledb.update("subBoard",values,"_no = ?","+specific_no_from_positon+");
                //데이터 갱신
                SQL = "update subBoard set status = 3 where _no = "+specific_no_from_position+"";
                profiledb.execSQL(SQL);


                mDbHelper = new DataBaseAdapter(this);
                mDbHelper.createDatabase();
                profiledb = mDbHelper.open();
                SQL = "select registerNum from userInfo where nickname = 'moon'";
                profile = profiledb.rawQuery(SQL, null);
                profile.moveToFirst();
                int registerNumNow = profile.getInt(0);
                registerNumNow++;

                mDbHelper = new DataBaseAdapter(this);
                mDbHelper.getDatabase();
                profiledb = mDbHelper.write();
                SQL = "update userInfo set registerNum = "+registerNumNow+" where nickname = 'moon'";
                profiledb.execSQL(SQL);


                //버튼 바꿔야함
                resultOKCheck = 1;

            }
        }
    }

    private boolean openDatabase() {
//        sampleDB =  SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        DataBaseAdapter mDbHelper = new DataBaseAdapter(this);
        mDbHelper.createDatabase();
        profiledb = mDbHelper.open();

        //profiledb=SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return true;
    }


    private class MenuAdapting extends ArrayAdapter<menu> {

        private ArrayList<menu> items;

        public MenuAdapting(Context context, int textViewResourceId, ArrayList<menu> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.onedata_inrow, null);
            }

            menu p = items.get(position);

            if (p != null) {

                YouTubeThumbnailView thumbnailView = (YouTubeThumbnailView) v.findViewById(R.id.video_thumbnail_image_view);
                final Button button = (Button)v.findViewById(R.id.checkButton);
                TextView language = (TextView)v.findViewById(R.id.language);
                TextView resultPay = (TextView)v.findViewById(R.id.pay);

                final String urlYouTube = p.getUrl().substring(p.getUrl().indexOf("=")+1);
                Glide.with(showCaptionBoard.this).load("https://img.youtube.com/vi/"+urlYouTube+"/0.jpg").override(300,300).into(thumbnailView);
//
//                thumbnailView.initialize(YouTubeConfig.getApiKey(), new YouTubeThumbnailView.OnInitializedListener() {
//                    @Override
//                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
//
//                        youTubeThumbnailLoader.setVideo(urlYouTube);
//
//                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
//                            @Override
//                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                                Log.d(TAG, "onThumbnailLoaded");
//                                youTubeThumbnailLoader.release();
//                            }
//
//                            @Override
//                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//                                Log.d(TAG, "onThumbnailfail");
//                            }
//                        });
//                        //     readyForLoadingYoutubeThumbnail = true;
//
//                    }
//
//                    @Override
//                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//                        if (youTubeInitializationResult.isUserRecoverableError()) {
//                            Log.d(TAG, "error Dialog");
//                        } else {
//                            String errorMessage = String.format("에러", youTubeInitializationResult.toString());
//                            Log.d(TAG, errorMessage);
//                        }
//
//                        //    readyForLoadingYoutubeThumbnail = true;
//                    }
//                });
                if (p.getStatus() == 1) button.setText("접수");
                if (p.getStatus() == 2) button.setText("접수 완료");
                else if (p.getStatus() == 3) button.setText("자막 등록 완료");

                if(workingOnBoardcheck == 1) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //we are passing the position which is to be removed in the method
                            wantToConfirm(position);
                            //이거 왜 버튼 안바뀌지ㅠㅠ
                            if(resultOKCheck == 1){
                                button.setText("자막 등록 완료");
                                resultOKCheck = 0;
                            }
                        }
                    });
                }
                language.setText(p.getLanguage());
                resultPay.setText(""+p.getPay());
            }
            return v;
        }

    }

    class menu {

        private String url;
        private int status;
        private String language;
        private int pay;

        public menu(String _url, int _status, String _language, int _pay){
            this.url = _url;
            this.status = _status;
            this.language = _language;
            this.pay = _pay;
        }

        public String getUrl() {
            return url;
        }
        public int getStatus() {
            return status;
        }
        public String getLanguage() {return language;}
        public int getPay(){
            return pay;
        }

    }

}
