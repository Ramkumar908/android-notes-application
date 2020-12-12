package com.android.ram.rideindia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.android.ram.rideindia.db.UserRegisterDbContract.userRegister;

import java.io.FileInputStream;


public class UserRegisterDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "User_Register";
    private static final int DATABASE_VERSION = 1;

    public UserRegisterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + UserRegisterDbContract.userRegister.TABLE_NAME + " ("
                + UserRegisterDbContract.userRegister._ID + " INTEGER PRIMARY KEY  AUTOINCREMENT ,"
                + UserRegisterDbContract.userRegister.USERNAME + " TEXT NOT NULL ,"
                + UserRegisterDbContract.userRegister.USEREMAIL + " TEXT NOT NULL ,"
                + UserRegisterDbContract.userRegister.USERPASSWORD + " TEXT NOT NULL,"
                + UserRegisterDbContract.userRegister.MAILCODE + " TEXT NOT NULL);";


        String CREATE_ADD_STUDENT_TABLE_QUERY = "create table " + userRegister.ADD_STUDENT_TABLE_NAME + "("
                + userRegister._ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + userRegister.SNAME + "TEXT NOT NULL," + userRegister.SFATHERSNAME + "TEXT NOT NULL,"
                + userRegister.SADMNO + "TEXT NOT NULL," + userRegister.SDOB + "TEXT NOT NULL," + userRegister.SADDRESS + "TEXT NOT NULL,"
                + userRegister.SBRANCH + "TEXT NOT NULL," + userRegister.SSEM + "TEXT NOT NULL, Simage BLOB NOT NULL);";

        db.execSQL(CREATE_TABLE_QUERY);
        db.execSQL(CREATE_ADD_STUDENT_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        //String DROP_TABLE_QUERY="DROP TABLE IF EXISTS "+UserRegisterDbContract.userRegister.REGISTER_TABLE_NAME+"";

    }

//    public boolean checkUser(String username,String userpassword)
//    {
//        String table_name=UserRegisterDbContract.userRegister.TABLE_NAME;
//        String  username_field=UserRegisterDbContract.userRegister.USERNAME;
//        String  userpassword_field=UserRegisterDbContract.userRegister.USERPASSWORD;
//        SQLiteDatabase db=this.getWritableDatabase();
//        String searchQuery="SELECT * FROM "+table_name+" WHERE  "+username_field+"=? AND "+userpassword_field+"=?";
//
//        Cursor cursor=db.rawQuery(searchQuery,new String[] {username,userpassword});
//     Log.e("UserRegisterDbHelper","the cursor count is"+cursor.getCount());
//        Log.e("UserRegisterDbHelper","the query is "+searchQuery);
//
//        if(cursor.getCount()>0)
//
//            return  true;
//        else
//            return false;
//    }

    public Boolean checkusernamepassword(String username, String password) {
        Log.e("UserRegisterDbHelper", "the username reached is " + username);
        Log.e("UserRegisterDbHelper", "the password reached is " + password);


        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM  Users WHERE user_name=? AND userr_password=?", new String[]{username, password});
        //Log.e("UserRegisterDbHelper","the cursor count is"+cursor.getColumnCount());
//        Log.e("UserRegisterDbHelper","the query is "+searchQuery);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public boolean insertNewStudent(String name, String fathersname, String admNo, String dob, String address, String branch, String semester, String x) {
        try {
            Log.e("UserRegisterActivity","hello insert function is calling and try to run ");
            SQLiteDatabase db = this.getWritableDatabase();
            FileInputStream fis = new FileInputStream(x);
            byte[] imgByte = new byte[fis.available()];
            fis.read(imgByte);
            ContentValues values = new ContentValues();
            values.put(userRegister.SNAME, name);
            values.put(userRegister.SFATHERSNAME, fathersname);
            values.put(userRegister.SADMNO, admNo);
            values.put(userRegister.SDOB, dob);
            values.put(userRegister.SADDRESS, address);
            values.put(userRegister.SBRANCH, branch);
            values.put(userRegister.SSEM, semester);
            values.put(String.valueOf(userRegister.SIMAGE), imgByte);
            db.insert(userRegister.ADD_STUDENT_TABLE_NAME, null, values);
            fis.close();
            return  true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}
