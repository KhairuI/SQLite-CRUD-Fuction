package com.example.sqlite01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName="student.db";
    public static final String tableName= "student_info";
    public static final String studentId="_id";
    public static final String studentName="name";
    public static final String studentMark="mark";
    public static final String studentGender="gender";
    public static final int versionNo = 1;
    public static final String createTable ="CREATE TABLE "+tableName+"("+studentId+" INTEGER PRIMARY KEY AUTOINCREMENT, " + ""+studentName+" VARCHAR(255), "+studentMark+" INTEGER ,"+studentGender+" VARCHAR(10) );";

    public static final String dropTable= "DROP TABLE IF EXISTS "+tableName;
    public static final String displayTable= " SELECT * FROM "+tableName;
    private Context context;

    public DatabaseHelper( Context context) {
        super(context, databaseName, null, versionNo);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //this method is called only first time at the time of table creating........

        try {
            Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show();
            db.execSQL(createTable);

        }catch (Exception e){
            Toast.makeText(context, ""+e, Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {

            Toast.makeText(context, "On Upgrade", Toast.LENGTH_SHORT).show();
            db.execSQL(dropTable);
            onCreate(db);
        }
        catch (Exception e){
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    public long insert(String name,String mark,String gender){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        //to insert data use the "ContentValues" object..........

        ContentValues contentValues= new ContentValues();
        contentValues.put(studentName,name);
        contentValues.put(studentMark,mark);
        contentValues.put(studentGender,gender);

        long rowId= sqLiteDatabase.insert(tableName,null,contentValues);
        return rowId;
    }

    public Cursor display(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        //to display all data from database use "Cursor" library..........

        Cursor cursor=  sqLiteDatabase.rawQuery(displayTable,null);
        return  cursor;

    }
    public boolean update(String id, String name, String mark, String gender){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        //to insert data use the "ContentValues" object..........

        ContentValues contentValues= new ContentValues();
        contentValues.put(studentId,id);
        contentValues.put(studentName,name);
        contentValues.put(studentMark,mark);
        contentValues.put(studentGender,gender);
        sqLiteDatabase.update(tableName,contentValues,"_id = ?",new String[]{id});
        return true;

    }

    public int delete(String id){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        int value= sqLiteDatabase.delete(tableName,studentId+" = ?",new String[]{id});
        return  value;


    }

}
