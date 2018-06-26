package com.example.lz.firstproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class CreateItem extends AppCompatActivity {
    int Year, Month, Day, date;
    private TextView showDate;
    private Button enterDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        showDate = (TextView) findViewById(R.id.showDate);
        enterDate = (Button) findViewById(R.id.enterDate);
        enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Year = c.get(Calendar.YEAR);
                Month = c.get(Calendar.MONTH);
                Day = c.get(Calendar.DAY_OF_MONTH);
                showDialog(0);
            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, Year, Month, Day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Day = selectedDay;
            Month = selectedMonth + 1;
            Year = selectedYear;
            showDate.setText(Month + " / " + Day + " / " + Year);
        }
    };

    public void saveButtonClicked(View view) {

        RadioGroup rg = findViewById(R.id.radiogroup);
        String selectedRadioValue = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        String d_text = ((EditText) findViewById(R.id.d_text)).getText().toString();
        String editText2 = ((EditText) findViewById(R.id.editText2)).getText().toString();

        if (editText2.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a task", Toast.LENGTH_SHORT).show();
        } else if (d_text.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
        }else if (showDate.getText().toString().equals("mm/dd/yyyy")){
            Toast.makeText(getApplicationContext(), "Please enter a date", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent();
            i.putExtra(Intent_Constants.INTENT_MESSAGE_FIELD, editText2);
            i.putExtra(Intent_Constants.INTENT_RADIO, selectedRadioValue);
            i.putExtra(Intent_Constants.INTENT_DESCRIPTION, d_text);
            i.putExtra(Intent_Constants.DAY, Day);
            i.putExtra(Intent_Constants.MONTH, Month);
            i.putExtra(Intent_Constants.YEAR, Year);
            setResult(Intent_Constants.INTENT_REQUEST_CODE, i);
            finish();
        }
    }
}


