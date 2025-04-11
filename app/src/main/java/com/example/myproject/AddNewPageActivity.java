package com.example.myproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class AddNewPageActivity extends AppCompatActivity {

    private EditText editTextItem;
    private static TextView textViewDate;
    private static TextView textViewTime;
    private Button buttonDate;
    private Button buttonTime;
    private Button buttonCancel;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_page);
        editTextItem = findViewById(R.id.editTextItemMJ);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        buttonDate = findViewById(R.id.buttonDate);
        buttonTime = findViewById(R.id.buttonTime);
        buttonAdd = findViewById(R.id.buttonAddMJ);
        buttonCancel = findViewById(R.id.buttonCancelMJ);

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("description", editTextItem.getEditableText().toString());
                data.putExtra("date", textViewDate.getText());
                data.putExtra("time", textViewTime.getText());
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            textViewDate.setText(day + "." + (month+1) + "." + year + ".");
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            textViewTime.setText(hourOfDay + ":" + minute);
        }
    }
}