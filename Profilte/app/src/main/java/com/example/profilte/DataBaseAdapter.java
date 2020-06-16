package com.example.profilte;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

public class DataBaseAdapter {
    protected static final String TAG = "DataAdapter";


    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public DataBaseAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }


    public DataBaseAdapter createDatabase() throws SQLException
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

    public DataBaseAdapter getDatabase()throws SQLException
    {
        try
        {
            mDbHelper.getDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToGetDatabase");
            throw new Error("UnableToGetDatabase");
        }
        return this;

    }
    public SQLiteDatabase open() throws SQLException
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
        return mDb;
    }

    public SQLiteDatabase write() throws SQLException
    {
        try
        {
            mDb = mDbHelper.getWritableDatabase();

        }catch (SQLException mSQLException)
        {
            Log.e(TAG, "write >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return mDb;
    }

    public void close()
    {
        mDbHelper.close();
    }

}
