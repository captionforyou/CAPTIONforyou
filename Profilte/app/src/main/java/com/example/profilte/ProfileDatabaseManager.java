package com.example.profilte;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ProfileDatabaseManager {
        static final String DB_app = "CaptionForYou.db";   //DB이름
        static final String TABLE_profile = "userInfo"; //Table 이름
        static final int DB_VERSION = 1;			//DB 버전
        public static final String COL_1 = "id";
        public static final String COL_2 = "password";
        public static final String COL_3 = "nickname";
        public static final String COL_4 = "points";
        public static final String COL_5 = "money";
        public static final String COL_6 = "requestNum";
        public static final String COL_7 = "registerNum";
        public static final String COL_8 = "ratingCompleteness";
        public static final String COL_9 = "ratingClarity";
        public static final String COL_10 = "ratingTime";
        public static final String COL_11 = "noticeCnt";


    Context myContext = null;

        private static ProfileDatabaseManager myDBManager = null;
        private SQLiteDatabase mydatabase = null;

        //MovieDatabaseManager 싱글톤 패턴으로 구현
        public static ProfileDatabaseManager getInstance(Context context)
        {
            if(myDBManager == null)
            {
                myDBManager = new ProfileDatabaseManager(context);
            }

            return myDBManager;
        }

        private ProfileDatabaseManager(Context context)
        {
            myContext = context;

            //DB Open
            mydatabase = context.openOrCreateDatabase(DB_app, Context.MODE_PRIVATE,null);

            //Table 생성
            //그냥 no라고 했던거 _no
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_profile +
                    "(" + "_no"+ " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "id"+" VARCHAR(16)," +
                            "password VARCHAR(16),"+
                            "nickname VARCHAR(16),"+
                            "points INTEGER,"+
                            "money INTEGER,"+
                            "requestNum	INTEGER,"+
                            "registerNum INTEGER,"+
                            "ratingCompleteness	INTEGER,"+
                            "ratingClarity INTEGER,"+
                            "ratingTime	INTEGER,"+
                            "noticeCnt INTEGER);");

            ContentValues cv = new ContentValues();
            cv.put(COL_1,"sooyoung");
            cv.put(COL_2,"1234");
            cv.put(COL_3,"moon");
            cv.put(COL_4,3000);
            cv.put(COL_5,1000);
            cv.put(COL_6,4);
            cv.put(COL_7,5);
            cv.put(COL_8,4);
            cv.put(COL_9,5);
            cv.put(COL_10,2);
            cv.put(COL_11,3);
            mydatabase.insert(TABLE_profile, null, cv);
        }
}
