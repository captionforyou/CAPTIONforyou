package com.example.profilte;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;

public class RatePopUp extends Activity {


    private SQLiteDatabase profiledb;
    float completenessRate;
    float clarityRate;
    String hisNickName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_rate_click_popup);
        Intent intent = getIntent();

        hisNickName = intent.getExtras().getString("registerNickname");


        RatingBar rbForCompleteness = (RatingBar) findViewById(R.id.ratingBarForCompleteness);

        rbForCompleteness.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                completenessRate = rating;
            }
        });

        rbForCompleteness = (RatingBar) findViewById(R.id.ratingBarForClarity);

        rbForCompleteness.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                clarityRate = rating;
            }
        });

    }

    public void finishRating(View v){

        DataBaseAdapter mDbHelper = new DataBaseAdapter(this);
        mDbHelper.createDatabase();
        profiledb = mDbHelper.open();

        String SQL = "select * from userInfo where nickname = '"+hisNickName+"'";
        Cursor profile = profiledb.rawQuery(SQL, null);
        profile.moveToFirst();
        float ratingCompleteness = profile.getFloat(8);
        float ratingClarity = profile.getFloat(9);
        int ratingNum = profile.getInt(12);

        float newRateForCompleteness = (ratingCompleteness*ratingNum + completenessRate)/(float)(ratingNum+1);
        float newRateForClarity = (ratingClarity*ratingNum+clarityRate)/(float)(ratingNum+1);
        ratingNum = ratingNum+1;

        //rating num, 평가들 update

        mDbHelper = new DataBaseAdapter(this);
        mDbHelper.getDatabase();
        profiledb = mDbHelper.write();

        SQL = "update userInfo set ratingCompleteness = "+newRateForCompleteness+", ratingClarity = "+newRateForClarity+", " +
                "ratingCnt = "+ratingNum+" where nickname = '" + hisNickName + "'";
        profiledb.execSQL(SQL);

        //데이터 전달하기
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();

    }
}
