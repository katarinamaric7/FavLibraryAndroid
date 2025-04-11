package com.example.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.database.AppDatabase;
import com.example.myproject.database.Item;


public class MyJournalActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    Button buttonAddNew;

    ImageButton refreshButton;

    MyJournalAdapter adapter;

    private AppDatabase appDatabase;
    static private final int ADD_ITEM_REQUEST_CODE = 1;
    static private final String TAG = "MyJournal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_journal);

        recyclerView = findViewById(R.id.recyclerViewMJ);
        refreshButton = findViewById(R.id.refreshButton);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyJournalActivity.this, "Page refreshed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyJournalActivity.this, MyJournalActivity.class);
                startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyJournalAdapter(this);
        recyclerView.setAdapter(adapter);

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        buttonAddNew = findViewById(R.id.buttonAddNewMJ);
        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Entered MyJournalActivity.onActivityResult");
                Intent intent = new Intent(MyJournalActivity.this, AddNewPageActivity.class);
                startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_ITEM_REQUEST_CODE) {
            Log.i(TAG, "Entered MyJournalActivity.onActivityResult");
            Item item = new Item();
            item.setDescription(data.getStringExtra("description"));
            item.setDate(data.getStringExtra("date"));
            item.setTime(data.getStringExtra("time"));

            AppDatabase.databaseWriteExecutor.execute(() -> {
                appDatabase.itemDao().insert(item);
            });
        }

    }





}