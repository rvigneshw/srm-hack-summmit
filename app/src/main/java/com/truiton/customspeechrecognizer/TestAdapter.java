package com.truiton.customspeechrecognizer;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class TestAdapter
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public TestAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException
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

    public TestAdapter open() throws SQLException
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

    public String[] getVideo(String str){
        try
        {String[] data=new String[3];
            String sql ="SELECT * FROM video WHERE text LIKE '%"+str+"%' GROUP by starttime LIMIT 5 ";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToFirst();

                data[0]=mCur.getString(mCur.getColumnIndex("starttime"));
                data[1]=mCur.getString(mCur.getColumnIndex("uri"));
                data[2]=mCur.getString(mCur.getColumnIndex("mode"));

            }
            return data;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public String[] getAudio(String str)
    {
        try
        {String[] data=new String[3];
            String sql ="SELECT * FROM audio WHERE text LIKE '%"+str+"%' GROUP by starttime LIMIT 5 ";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null && mCur.moveToFirst() )
            {
                data[0]=mCur.getString(mCur.getColumnIndex("starttime"));
                data[1]=mCur.getString(mCur.getColumnIndex("uri"));
                data[2]=mCur.getString(mCur.getColumnIndex("mode"));
            }
            return data;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}