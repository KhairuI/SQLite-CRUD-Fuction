package com.example.sqlite01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    private ListView listView;
    // private RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    private Toolbar toolbar;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

      listView= findViewById(R.id.listViewId);
      toolbar= findViewById(R.id.showActivityToolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Data");
        //  recyclerView= findViewById(R.id.recycleViewId);
        databaseHelper= new DatabaseHelper(this);
        
        loadData();

       /* Cursor cursor= databaseHelper.display();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new MyAdapter(this,cursor);
        recyclerView.setAdapter(adapter);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);

        MenuItem menuItem= menu.findItem(R.id.searchId);
        SearchView searchView= (SearchView) menuItem.getActionView();

        //SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void loadData() {

        ArrayList<String> arrayList= new ArrayList<>();
        Cursor cursor= databaseHelper.display();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                arrayList.add("ID: "+cursor.getString(0)+"\n"+
                        "Name: "+ cursor.getString(1)+"\n"+
                        "Mark: "+cursor.getString(2)+"\n"+
                        "Gender: "+cursor.getString(3));

            }
        }

        adapter= new ArrayAdapter<String>(this,R.layout.menu_item,R.id.menuId,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String select= parent.getItemAtPosition(position).toString();
                //String select= parent.getSelectedItemId(id);
                Toast.makeText(ShowActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
