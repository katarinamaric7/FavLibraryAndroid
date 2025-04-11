package com.example.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;


public class AddBookActivity extends AppCompatActivity {

    private EditText editText;

    private RadioButton radioButtonYes;

    private Button buttonAdd;

    private Button buttonCancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        editText = findViewById(R.id.editTextItem);
        radioButtonYes = findViewById(R.id.radioButtonDone);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonCancel = findViewById(R.id.buttonCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("nameAndAuthor", editText.getEditableText().toString());
                data.putExtra("done", radioButtonYes.isChecked());
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}