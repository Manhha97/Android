package com.luyendd.learntoeic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.luyendd.learntoeic.obj.ResultTest;
import com.luyendd.learntoeic.obj.Topic;
import com.luyendd.learntoeic.obj.Voca;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectDataBase extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private static String DB_NAME = "toeic600.db";
    private static String TABLE_DATA_RESULT_TEST = "data_result_test";
    private String IS_FAVORITE = "daluu";
    private SQLiteDatabase myDataBase;
    private Context myContext;

    public ConnectDataBase(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = myContext.getDatabasePath(DB_NAME).toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
        } else {
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }

        }
    }

    private boolean checkDataBase() {
        //  this.getReadableDatabase();
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public List<Topic> getListTopic() throws SQLException {
        openDataBase();
        List<Topic> topicList = new ArrayList<>();
        String sql = "select * from topic";
        Cursor cursor = myDataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            topicList.add(new Topic(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        close();
        return topicList;

    }

    public List<Voca> getVocaFromTopic(int topic) throws SQLException {
        openDataBase();
        List<Voca> vocaList = new ArrayList<>();
        String sql = "select * from mytoeic600 where topic = " + topic;
        Cursor cursor = myDataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            vocaList.add(new Voca(cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2), cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6)
                    ,cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
            cursor.moveToNext();
        }
        close();
        return vocaList;

    }

    public List<Voca> getListFavorite() throws SQLException {
        openDataBase();
        List<Voca> vocaList = new ArrayList<>();
        String sql = "select * from mytoeic600 where favourite = 1";
        Cursor cursor = myDataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            vocaList.add(new Voca(cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2), cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6)
                    ,cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
            cursor.moveToNext();
        }
        close();
        return vocaList;

    }

    public Voca getVocaFromID(int id) throws SQLiteException{
        try {
            openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from mytoeic600 where id =" + id;
        Cursor cursor = myDataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        Voca voca = new Voca(cursor.getInt(0),
                cursor.getInt(1), cursor.getInt(2), cursor.getString(3),
                cursor.getString(4),cursor.getString(5),cursor.getString(6)
                ,cursor.getString(7), cursor.getString(8), cursor.getInt(9));
        close();
        return voca;
    }

    public boolean insertResultData(ResultTest rs) {
        try {
            openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("_topic_id", rs.getIdTopic());
        contentValues.put("correct", rs.getCorrect());
        contentValues.put("total", rs.getTotal());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        contentValues.put("date", currentDateandTime);

        boolean result = myDataBase.insert(TABLE_DATA_RESULT_TEST, null, contentValues) > 0;
        close();
        return result;
    }

    public List<ResultTest> getListResultByTopic (int topic) {
        try {
            openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<ResultTest> resultTestList = new ArrayList<>();
        String sql = "Select * from " + TABLE_DATA_RESULT_TEST + " where _topic_id ='" + topic + "'";
        Cursor cursor = myDataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            resultTestList.add(new ResultTest(cursor.getInt(0),
                    cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),
                    cursor.getString(4)));
            cursor.moveToNext();
        }

        close();
        return resultTestList;

    }

    public void UpdateVoca(Voca voca) {
        try {
            openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put("favourite", voca.getFavorite());
        myDataBase.update("mytoeic600", cv, "id="+voca.getId(), null);
        close();
    }

    public List<Voca> getRandomListQuiz() {
        try {
            openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Voca> vocaList = new ArrayList<>();
        String sql = "select * from mytoeic600 ORDER BY RANDOM() LIMIT 20";
        Cursor cursor = myDataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            vocaList.add(new Voca(cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2), cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6)
                    ,cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
            cursor.moveToNext();
        }
        close();
        return vocaList;
    }

}
