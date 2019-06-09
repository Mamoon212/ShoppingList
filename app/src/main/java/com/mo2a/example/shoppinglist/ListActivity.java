package com.mo2a.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mo2a.example.shoppinglist.data.DatabaseHandler;
import com.mo2a.example.shoppinglist.model.Item;
import com.mo2a.example.shoppinglist.ui.Popup;
import com.mo2a.example.shoppinglist.ui.RecyclerviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    private RecyclerView recyclerView;
    private RecyclerviewAdapter recyclerviewAdapter;
    private List<Item> itemList;
    private DatabaseHandler handler;
    private FloatingActionButton fab;
    private Popup popup;
    private Button editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        popup= new Popup();
        handler= new DatabaseHandler(this);
        recyclerView= findViewById(R.id.recyclerView);
        fab= findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList= new ArrayList<>();
        itemList= handler.getAllItems();
        recyclerviewAdapter= new RecyclerviewAdapter(this,itemList);
        recyclerView.setAdapter(recyclerviewAdapter);
        recyclerviewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.createPopupDialog(ListActivity.this);
            }
        });

        
        
    }
}
