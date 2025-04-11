package com.example.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TBRActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button buttonAddNew;

    Button buttonDeleteAll;

    TBRAdapter tbrAdapter;

    static private final int ADD_ITEM_REQUEST_CODE = 1;
    static private final String TAG = "TBR";
    static private String FILE_NAME = "TBR.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbr);

        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<TBRBook> list = new ArrayList<>();
        tbrAdapter = new TBRAdapter(list);
        recyclerView.setAdapter(tbrAdapter);


        buttonAddNew = findViewById(R.id.buttonAddNew);
        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Entered TBRAcitivity.onActivityResult");
                Intent intent = new Intent(TBRActivity.this, AddBookActivity.class);
                startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        buttonDeleteAll = findViewById(R.id.buttonDeleteAll);
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tbrAdapter.clear();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == ADD_ITEM_REQUEST_CODE){
            Log.i(TAG, "Entered TBRAcitivity.onActivityResult");
            TBRBook item = new TBRBook();
            item.setNameAndAuthor(data.getStringExtra("nameAndAuthor"));
            item.setDone(data.getBooleanExtra("done", false));
            tbrAdapter.add(item);
        }
    }

    private void loadItems(){
        BufferedReader reader = null;
        try{
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(fileInputStream));
            List<TBRBook> data = new ArrayList<>();

            String line = null;
            while((line = reader.readLine()) != null){
                TBRBook item = new TBRBook();
                item.setNameAndAuthor(line);
                item.setDone(Boolean.parseBoolean(reader.readLine()));
                data.add(item);
            }
            reader.close();
            tbrAdapter.addData(data);
        }catch (IOException exception){
            exception.printStackTrace();
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
            }
        }
    }

    private void writeItems(){
        PrintWriter writer = null;
        try{
            deleteFile(FILE_NAME);
            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fileOutputStream)));

            List<TBRBook> data = tbrAdapter.getData();

            for(TBRBook item : data){
                writer.println(item.getNameAndAuthor());
                writer.println(item.isDone());
            }

            writer.close();
        }catch (FileNotFoundException exception){
            exception.printStackTrace();
            if(writer != null){
                writer.close();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tbrAdapter.getItemCount() == 0){
            loadItems();
        }
    }
}