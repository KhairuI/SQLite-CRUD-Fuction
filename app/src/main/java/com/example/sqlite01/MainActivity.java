package com.example.sqlite01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,UpdateDialogue.updateDialogueListener
        ,DeleteDialogue.deleteDialogueListener{

    DatabaseHelper databaseHelper;
    private AppCompatEditText nameEditText,markEditText,genderEditText;
    private MaterialButton addButton,displayButton,displayRecycleButton,updateButton,deleteButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= findViewById(R.id.mainActivityToolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SQLite");

        databaseHelper= new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase= databaseHelper.getWritableDatabase();

        nameEditText= findViewById(R.id.nameId);
        markEditText= findViewById(R.id.markId);
        genderEditText= findViewById(R.id.genderId);
        addButton= findViewById(R.id.addButtonId);
        displayButton= findViewById(R.id.displayButtonId);
        updateButton= findViewById(R.id.updateButtonId);
        displayRecycleButton= findViewById(R.id.displayRecycleButtonId);
        deleteButton= findViewById(R.id.deleteButtonId);
        addButton.setOnClickListener(this);
        displayButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        displayRecycleButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        final String name= nameEditText.getEditableText().toString();
        final String mark= markEditText.getEditableText().toString();
        final String gender= genderEditText.getEditableText().toString();

        if(v.getId()==R.id.addButtonId){
            //Toast.makeText(MainActivity.this, ""+name+"\n"+mark+"\n"+gender, Toast.LENGTH_SHORT).show();


            if(name.equals("") || mark.equals("") || gender.equals("")){
                Toast.makeText(MainActivity.this, "Filled all TextField", Toast.LENGTH_SHORT).show();
            }
            else {
                insertData(name,mark,gender);
            }
        }
        else if(v.getId()==R.id.displayButtonId){

            Cursor cursor = databaseHelper.display();
            if(cursor.getCount()==0){
                //there is no data
                showData("Error","No Data Found");
                return;
            }

            StringBuffer stringBuffer= new StringBuffer();
            while (cursor.moveToNext()){

                stringBuffer.append("ID: "+cursor.getString(0)+"\n");
                stringBuffer.append("Name: "+cursor.getString(1)+"\n");
                stringBuffer.append("Mark: "+cursor.getString(2)+"\n");
                stringBuffer.append("Gender: "+cursor.getString(3)+"\n\n");

            }
            showData("ResultSet",stringBuffer.toString());
        }
        else if(v.getId()==R.id.updateButtonId){

            openDialogue();
        }
        else if(v.getId()==R.id.displayRecycleButtonId){
            Intent intent= new Intent(MainActivity.this,ShowActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.deleteButtonId){

            openDeleteDialogue();

        }

    }

    private void openDeleteDialogue() {
        DeleteDialogue deleteDialogue= new DeleteDialogue();
        deleteDialogue.show(getSupportFragmentManager(),"delete dialogue");
    }

    private void openDialogue() {

        UpdateDialogue updateDialogue= new UpdateDialogue();
        updateDialogue.show(getSupportFragmentManager(),"update dialogue");

    }

    //retrive data in dialogue....
    private void showData(String title, String resultset) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(resultset);
        builder.setCancelable(true);
        builder.show();


    }



    private void insertData(String name, String mark, String gender) {
        long rowId= databaseHelper.insert(name,mark,gender);
        if(rowId==-1){
            Toast.makeText(MainActivity.this, "Inserted Failed", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(MainActivity.this, "Inserted successfully Row No: "+rowId+"", Toast.LENGTH_SHORT).show();
            nameEditText.getEditableText().clear();
            markEditText.getEditableText().clear();
            genderEditText.getEditableText().clear();
        }

    }

    @Override
    public void updateText(String id, String name, String mark, String gender) {

        Boolean isUpdate= databaseHelper.update(id,name,mark,gender);
        if(isUpdate==true){
            Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Updated Failed", Toast.LENGTH_SHORT).show();
        }

       // Toast.makeText(MainActivity.this, ""+id+"\n"+name+"\n"+mark+"\n"+gender, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteText(String id) {
       // Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        int value= databaseHelper.delete(id);
        if(value>0){
            Toast.makeText(this, "Delete Successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
