package com.example.profilte;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class noticePopUp extends Activity {

    int subnumber;
    private SQLiteDatabase profiledb;
    private static final String TAG = "notice_popup";
    String registerNickname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_hisprofile_popup);
        Intent intent = getIntent();

        subnumber = intent.getExtras().getInt("subNumber");

        DataBaseAdapter mDbHelper = new DataBaseAdapter(this);
        mDbHelper.createDatabase();
        profiledb = mDbHelper.open();

        String SQL = null;
        SQL = "select * from subBoard where _no = "+subnumber+"";

        Cursor notice = profiledb.rawQuery(SQL, null);

        notice.moveToFirst();

        String url = notice.getString(1);
        registerNickname = notice.getString(3);
        String language = notice.getString(5);
        String contents = notice.getString(6);
        int tax = notice.getInt(7);


        YouTubeThumbnailView thumbnailView = (YouTubeThumbnailView) findViewById(R.id.video_thumbnail_image_view_notice);

        final String urlYouTube = url.substring(url.indexOf("=")+1);
        Glide.with(this).load("https://img.youtube.com/vi/"+urlYouTube+"/0.jpg").override(400,340).into(thumbnailView);


        EditText editUrl = (EditText) findViewById(R.id.urlLinkFromNotice);
        editUrl.setText(url);
        TextView editLan = (TextView) findViewById(R.id.languageFromNotice);
        editLan.setText(language);
        TextView editCont = (TextView) findViewById(R.id.contentsFromNotice);
        editCont.setText(contents);
        TextView editTax =(TextView) findViewById(R.id.payFromNotice);
        editTax.setText(""+tax);


//400 340 size


//        thumbnailView.initialize(YouTubeConfig.getApiKey(), new YouTubeThumbnailView.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
//
//                youTubeThumbnailLoader.setVideo(urlYouTube);
//
//                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
//                    @Override
//                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                        Log.d(TAG, "onThumbnailLoaded");
//                        youTubeThumbnailLoader.release();
//                    }
//
//                    @Override
//                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//                        Log.d(TAG, "onThumbnailfail");
//                    }
//                });
//                //     readyForLoadingYoutubeThumbnail = true;
//
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//                if (youTubeInitializationResult.isUserRecoverableError()) {
//                    Log.d(TAG, "error Dialog");
//                } else {
//                    String errorMessage = String.format("에러", youTubeInitializationResult.toString());
//                    Log.d(TAG, errorMessage);
//                }
//
//                //    readyForLoadingYoutubeThumbnail = true;
//            }
//        });


    }

    public void noticeClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    //이거 프로필탭에서 nickname 을 받아와가주고 보여줘야함
    //현재 nickname 안받고 그냥 id sooyoung 해서 보여줬음
    //그래서 여기서 nickname을 보내야한다
    public void goToRegistersProfile(View v){
        Intent activityIntent = new Intent(this, MainActivity.class);
        startActivity(activityIntent);
    }

    public void goToRate(View v){
        Intent intent = new Intent(this, RatePopUp.class);
        intent.putExtra("registerNickname", registerNickname);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //nothing to do
            }
        }
    }


}
