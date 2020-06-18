package com.example.profilte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "firstTest" ;
    //  SubBoardDBHelper dbHelper = null ;
    private SQLiteDatabase profiledb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //=================================
        //notice
        findViewById(R.id.registerToNotice).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                createNotificationChannel();

                Bitmap mLargeIcoForNoti =
                        BitmapFactory.decodeResource(getResources(),R.drawable.notice);

                PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this,0,
                        new Intent(MainActivity.this,noticeActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.profileimage)
                                .setContentTitle("caption_for_you")
                                .setContentText("자막 요청 접수")
                                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                                .setLargeIcon(mLargeIcoForNoti)
                                .setVisibility (NotificationCompat.VISIBILITY_PUBLIC)
                                //.setPriority(NotificationCompat.PRIORITY_MAX)
                                .setAutoCancel(true)
                                .setContentIntent(mPendingIntent);

                NotificationManager mNotificationManager =
                        (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                mNotificationManager.notify(0,mBuilder.build());

            }
        });
        //=================================



        //ProfileDatabaseManager databaseManager = ProfileDatabaseManager.getInstance(this);
        boolean isOpen = openDatabase();
        if(isOpen) {
            putToprofile();
        }

    }

    public void clickIRequest(View view){
        Intent activityIntent = new Intent(this, showCaptionBoard.class);
        String command = "requestBoard";
        activityIntent.putExtra("command", command);
        startActivity(activityIntent);
    }

    public void clickWorking(View view){
        Intent activityIntent = new Intent(this, showCaptionBoard.class);
        String command = "workingOnBoard";
        activityIntent.putExtra("command", command);
        startActivity(activityIntent);
    }

    public void clickIWorked(View view){
        Intent activityIntent = new Intent(this, showCaptionBoard.class);
        String command = "workedBoard";
        activityIntent.putExtra("command", command);
        startActivity(activityIntent);
    }

    public void clickgoToNotice(View view){
        Intent activityIntent = new Intent(this, noticeActivity.class);
        startActivity(activityIntent);
    }

    //====================================
    //notice
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "captionforyou";
            String description = "testing";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //======================================

    private boolean openDatabase() {
//        sampleDB =  SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        DataBaseAdapter mDbHelper = new DataBaseAdapter(this);
        mDbHelper.createDatabase();
        profiledb = mDbHelper.open();

        //profiledb=SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return true;
    }

    private void putToprofile() {

        String SQL = "select * from userInfo where id = 'sooyoung'";
        Cursor profile = profiledb.rawQuery(SQL, null);

        profile.moveToFirst();

            String id = "sooyoung";
            String nickname = profile.getString(3);
            int points = profile.getInt(4);
            int money = profile.getInt(5);
            int requestNum = profile.getInt(6);
            int registerNum = profile.getInt(7);
            int ratingCom = profile.getInt(8);
            int ratingCl = profile.getInt(9);
            int ratingT = profile.getInt(10);

        Intent intent = new Intent() ;

        TextView editID = (TextView) findViewById(R.id.id) ;
        editID.setText(id);
        TextView editNickname = (TextView) findViewById(R.id.nickname) ;
        editNickname.setText(nickname);
        TextView editPoints = (TextView) findViewById(R.id.points) ;
        editPoints.setText(""+points);
        TextView editMoney = (TextView) findViewById(R.id.money) ;
        editMoney.setText(""+money);
        TextView editRequestC = (TextView) findViewById(R.id.requestCount) ;
        editRequestC.setText("요청수"+requestNum+"건");
        TextView editRegisterC = (TextView) findViewById(R.id.registerCount) ;
        editRegisterC.setText("등록수"+registerNum+"건");
        TextView editComplete = (TextView) findViewById(R.id.complete) ;
        editComplete.setText(""+ratingCom+" 점");
        TextView editClarity = (TextView) findViewById(R.id.clarity) ;
        editClarity.setText(""+ratingCl+" 점");
        TextView editTime = (TextView) findViewById(R.id.time) ;
        editTime.setText(""+ratingT+" 점");

        profile.close();

    }

}


