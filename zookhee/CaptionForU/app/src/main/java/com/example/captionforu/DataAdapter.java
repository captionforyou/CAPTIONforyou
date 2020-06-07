package com.example.captionforu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter {
    protected static final String TAG = "DataAdapter";


    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public DataAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public void insertNewUser(String id, String password,String nickname) {
        String sql ="SELECT * FROM " + "userinfo";

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("password", password);
        contentValues.put("nickname", nickname);
        contentValues.put("points", 0);
        contentValues.put("money", 0);
        contentValues.put("requestNum", 0);
        contentValues.put("registerNum", 0);
        contentValues.put("ratingCompleteness", 0);
        contentValues.put("ratingClarity", 0);
        contentValues.put("ratingTime", 0);
        contentValues.put("noticeCnt", 0);

        mDb.insert("userinfo", null, contentValues);

    }

    public void insertNewSubBoard(String id,String nickname,String link, String language,String contents,Integer pay) {
        String sql ="SELECT * FROM " + "subBoard";

        ContentValues contentValues = new ContentValues();
        contentValues.put("link",link);
        contentValues.put("requestNickname", nickname);
        contentValues.put("language", language);
        contentValues.put("contents", contents);
        contentValues.put("tax", pay);
        contentValues.put("status", 1);
        Log.d("timetest",""+(int) (System.currentTimeMillis()));
        contentValues.put("time",(int) (System.currentTimeMillis()));
        mDb.insert("subBoard", null, contentValues);

    }

    public List getTableData()
    {
        try
        {
            // Table 이름 -> antpool_bitcoin 불러오기
            String sql ="SELECT * FROM " + "userinfo";

            // 모델 넣을 리스트 생성
            List userList = new ArrayList();

            // TODO : 모델 선언
            UserInfo user = null;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    // TODO : 커스텀 모델 생성
                    user = new UserInfo();

                    // TODO : Record 기술
                    // id, name, account, privateKey, secretKey, Comment
                    user.setno(mCur.getInt(0));
                    user.setid(mCur.getString(1));
                    user.setpw(mCur.getString(2));
                    user.setnn(mCur.getString(3));
                    user.setpoints(mCur.getInt(4));
                    user.setmoney(mCur.getInt(5));
                    user.setrequestNum(mCur.getInt(6));
                    user.setregiseterNum(mCur.getInt(7));
                    user.setratingCompletenss(mCur.getDouble(8));
                    user.setratingClarity(mCur.getDouble(9));
                    user.setratingTime(mCur.getInt(10));
                    user.setnoticeCnt(mCur.getInt(11));
                    // 리스트에 넣기
                    userList.add(user);
                }

            }
            return userList;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public List getBoardData()
    {
        try
        {
            // Table 이름 -> antpool_bitcoin 불러오기
            String sql ="SELECT * FROM " + "subBoard";

            // 모델 넣을 리스트 생성
            List boardList = new ArrayList();

            // TODO : 모델 선언
            SubBoard board = null;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    // TODO : 커스텀 모델 생성
                    board = new SubBoard();

                    // TODO : Record 기술
                    // id, name, account, privateKey, secretKey, Comment
                    board.setno(mCur.getInt(0));
                    board.setlink(mCur.getString(1));
                    board.setstatus(mCur.getInt(2));
                    board.setregisterNickname(mCur.getString(3));
                    board.setrequestNickname(mCur.getString(4));
                    board.setlanguage(mCur.getString(5));
                    board.setcontents(mCur.getString(6));
                    board.settax(mCur.getInt(7));
                    // 리스트에 넣기
                    boardList.add(board);
                }

            }
            return boardList;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

}
