package com.example.androidtest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.*;
import android.os.Environment;
import android.util.Log;

import java.io.*;

import java.io.IOException;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Yanqing Ma on 11/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "db.db";
    public static final String DBLOCATION = "/data/data/com.example.androidtest/databases/";
    private Context myContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.myContext = context;
    }



    // override onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    // override onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // check DB exists or not by trying to open it. Return true if it exists
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DBLOCATION + DBNAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        }catch(SQLiteException e) //database does't exist yet.
        {
            System.out.println("database does not exist");
            return false;

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }



    // copy database into our locally just created database
    public void createDataBase() throws IOException {

        if (this.checkDataBase()) {
            mDatabase = this.getReadableDatabase();
        }
        //do nothing - database already exist
        else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            mDatabase = this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

            String create_record_table = "CREATE TABLE IF NOT EXISTS " + "record" + "(" +
                    "username TEXT, " +
                    "comments TEXT, " +
                    "time INTEGER, " + "isSuccess INTEGER)";


            mDatabase.execSQL(create_record_table);


        }






    // open database and copy database into local db
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DBNAME);

        // Path to the just created empty db
        String outFileName = DBLOCATION + DBNAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    // concatenate dblocation and database name and try to open this path
    public void openDatabase() {
        String myPath = DBLOCATION + DBNAME;
        mDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }


    // if db is open, close it
    public void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
        super.close();
    }


    // get a list of current usernames stored inside database. Cursor loops through username columns and add to list
    public List<String> getList() {
        String user = null;
        List<String> userlist = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        cursor.moveToFirst();
        /*int count = cursor.getCount();
        System.out.println(cursor.getCount());*/
        while (!cursor.isAfterLast()) {
            user = cursor.getString(cursor.getColumnIndex("username"));
            userlist.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return userlist;
    }




    /**
     * for a given user, find all of his study records from record table and return a list which
     * contains all of his study time
     * @param  username  username
     * @return a list of all study time
     */
    public List<Integer> getStudyTimeList(String username) {

        List<Integer> timeList = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + "time" + " FROM " + "record" + " WHERE " + "username" + " =?" + " AND" + " isSuccess" + " =" + " 1" , new String[]{username});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int current = cursor.getInt(cursor.getColumnIndex("time"));
            timeList.add(current);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return timeList;
    }



    /**
     * for a given user, find his rank of study frequency among all of the users
     * @param  username  username
     * @return the rank of this user's study frequency
     */
    public int getRank(String username) {

        List<Integer> timeList = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + "frequency" + " FROM " + "user" , null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int current = cursor.getInt(cursor.getColumnIndex("frequency"));
            timeList.add(current);
            cursor.moveToNext();
        }
        cursor.close();



        Cursor c2 = db.rawQuery("SELECT " + "frequency" + " FROM " + "user" + " WHERE " + "username" + " =?" , new String[]{username});
        c2.moveToFirst();
        int user = 0;

        while (!c2.isAfterLast()) {

            user = c2.getInt(c2.getColumnIndex("frequency"));
            break;

        }
        c2.close();
        closeDatabase();


        Collections.sort(timeList, Collections.reverseOrder());
        return timeList.indexOf(user);
    }







    /**
     * get a user's total_study_time and frequency in user table
     *
     * @param  username  username
     * @param  parameter  total_time or study frequency
     */
    public int getStat(String username, String parameter)
    {
        if(parameter == null)
            return 0;


        String selectString = "SELECT * FROM " + "user" + " WHERE " + "username" + " =?";
        mDatabase = this.getReadableDatabase();

        Cursor c = mDatabase.rawQuery(selectString, new String[]{username});
        if(c.getCount() <= 0){
            c.close();
            return 0;
        }

        c.moveToFirst();
        int ret = 0;

        while (!c.isAfterLast()) {
            ret = c.getInt(c.getColumnIndex(parameter));
            break;
        }
        c.close();
        closeDatabase();
        return ret;


    }



    // insert a username and a password
    public void insertEntry(String userName,String password) throws IOException {


        ContentValues newValues = new ContentValues();
        mDatabase = this.getWritableDatabase();
        newValues.put("username", userName);
        newValues.put("password", password);

        // Insert the row into your table
        mDatabase.insert("user", null, newValues);




        //String origin = "/Users/marriema/Desktop/final/app/src/main/assets/db.db";
        //SQLiteDatabase disk = SQLiteDatabase.openDatabase(origin, null, SQLiteDatabase.OPEN_READWRITE);
        //disk.insert("user", null, newValues);
        //disk.close();

        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }



    // insert a studying record, including username, comments, time, isSuccess
    public void insertRecord(String userName,String comment, int time, int success) throws IOException {


        ContentValues newValues = new ContentValues();
        mDatabase = this.getWritableDatabase();
        newValues.put("username", userName);
        newValues.put("comments", comment);
        newValues.put("time", time);
        newValues.put("isSuccess", success);
        // Insert the row into your table
        mDatabase.insert("record", null, newValues);


    }



    // helper function taking in a username and checks if it exists or not
    public boolean isNameExisted(String value)
    {
        String selectString = "SELECT * FROM " + "user" + " WHERE " + "username" + " =?";
        mDatabase = this.getReadableDatabase();

        Cursor c = mDatabase.rawQuery(selectString, new String[]{value});
        if(c.getCount() <= 0){
            c.close();
            return false;
        }
        c.close();
        return true;

    }


    // take in a pair of name and password and check if user's password and username is correct
    public boolean ifExists(String name, String password)
    {

        Cursor cursor = null;
        mDatabase = this.getReadableDatabase();

        String checkQuery = "SELECT " + "*" + " FROM " + "user" + " WHERE " + "username" + " =?" + "AND " + "password" + " =?";
        cursor= mDatabase.rawQuery(checkQuery, new String[]{name, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }



    /**
     * update a record's isSuccess field inside of table Record
     *
     * @param  name  username
     * @param  com  comments
     * @param  time    studying time
     * @param  success successOrNot

     */
    public void ifRecordExist(String name, String com, int time, int success)
    {

        Cursor cursor = null;
        mDatabase = this.getWritableDatabase();

        String checkQuery = "SELECT " + "rowid" + ",*" + " FROM " + "record" + " WHERE " + "username" + " =?" + "AND " + "comments" + " =?";
        cursor= mDatabase.rawQuery(checkQuery,new String[]{name, com});
        cursor.moveToFirst();

        while (!cursor.isAfterLast() && cursor.getCount() >= 1) {
            if((cursor.getInt(cursor.getColumnIndex("time")) == time) && (cursor.getInt(cursor.getColumnIndex("isSuccess")) == 1))
            {
                ContentValues cv = new ContentValues();
                int id = cursor.getInt(cursor.getColumnIndex("rowid"));

                cv.put("isSuccess", 0);

                mDatabase.update("record", cv, "rowid=" + id, null);
                Log.d(getClass().getName(), "value = " + cursor.getInt(cursor.getColumnIndex("isSuccess")));
                int lala = cursor.getInt(cursor.getColumnIndex("isSuccess"));

                break;
            }
            cursor.moveToNext();

        }

        cursor.close();

    }






    /**
     * update user table's total study time and frequency field for one user
     *
     * @param  username  username
     * @param  paramater  total_time or study frequency
     * @param  increment    increment

     */
    public void change(String username, String paramater, int increment)
    {
        Cursor cursor = null;
        mDatabase = this.getWritableDatabase();

        String checkQuery = "SELECT " + "rowid" + ",*" + " FROM " + "user" + " WHERE " + "username" + " =?";
        cursor= mDatabase.rawQuery(checkQuery,new String[]{username});
        cursor.moveToFirst();

        while (!cursor.isAfterLast() && cursor.getCount() >= 1) {

            int id = cursor.getInt(cursor.getColumnIndex("rowid"));
            ContentValues newValues = new ContentValues();
            newValues.put(paramater, cursor.getInt(cursor.getColumnIndex(paramater))+increment);

            // Insert the row into your table
            mDatabase.update("user",newValues, "rowid="+id, null);
            break;

        }

        cursor.close();

    }
    }


