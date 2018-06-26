package com.example.lz.firstproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class calendar extends AppCompatActivity {

    String date;
    DB database;
    ArrayList<listviewcode> eventsOfDay;
    custom_adapter arrayAdapter;
    ListView the_list_cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        database = new DB(this,null,null,2);

        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                month = month + 1;
                date = (year + "-" + month + "-" + dayOfMonth);
                eventsOfDay = database.printEvents(date);

                arrayAdapter = new custom_adapter(getApplicationContext(),eventsOfDay);
                the_list_cal = findViewById(R.id.the_list_cal);
                the_list_cal.setAdapter(arrayAdapter);
                the_list_cal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(calendar.this);
                        final int positionToRemove = position;

                        alertDialogBuilder.setTitle("Are you sure you want to delete?");
                        alertDialogBuilder
                                .setMessage("Please select one")
                                .setCancelable(true)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,int id) {
                                        database.deleteItem(eventsOfDay.get(positionToRemove));
                                        eventsOfDay.remove(positionToRemove);
                                        arrayAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });
            }
        });
    }
}
