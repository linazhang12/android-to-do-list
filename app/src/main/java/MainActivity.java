package com.example.lz.firstproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ListView the_list;
    ArrayList <ToDoTask> item =new ArrayList<ToDoTask>();
    custom_adapter arrayAdapter;
    DB database;
    String todayDate;
    String taskName;
    String importanceSelection;
    String date;
    String d_text;
    int todayYear;
    int todayMonth;
    int todayDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);

        database = new DB(this,null,null,2);

        //get today's date to display today's events
        Calendar c = Calendar.getInstance();
        todayYear = c.get(Calendar.YEAR);
        todayMonth = c.get(Calendar.MONTH)+ 1;
        todayDay = c.get(Calendar.DAY_OF_MONTH);
        todayDate = todayYear + "-" + todayMonth + "-" + todayDay;

        item = database.printEvents(todayDate);

        arrayAdapter = new custom_adapter(getApplicationContext(),item);
        the_list = findViewById(R.id.the_list);
        the_list.setAdapter(arrayAdapter);

        //delete on click
        the_list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                final int positionToRemove = position;

                alertDialogBuilder.setTitle("Are you sure you want to delete?")
                        .setMessage("Please select one")
                        .setCancelable(true)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,int id) {
                                database.deleteItem(item.get(positionToRemove));
                                item.remove(positionToRemove);
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

    //go to calendar
    public void onClickCal(View view) {
        Intent i = new Intent(this, calendar.class);
        startActivity(i);
    }

    //add task
    public void onClick(View view){
        Intent i = new Intent(this, CreateItem.class);
        startActivityForResult(i,Intent_Constants.INTENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Intent_Constants.INTENT_REQUEST_CODE == resultCode){

            //retrieves data from CreateItem
            taskName =data.getStringExtra(Intent_Constants.INTENT_MESSAGE_FIELD);
            importanceSelection =data.getStringExtra(Intent_Constants.INTENT_RADIO);
            d_text=data.getStringExtra(Intent_Constants.INTENT_DESCRIPTION);
            date = data.getStringExtra(Intent_Constants.DATE);

            ToDoTask listitem = new ToDoTask(taskName, importanceSelection, d_text, date);

            //database addItem function return a bool
            boolean insertData = database.addItem(listitem);
            if (insertData){
                Toast.makeText(getApplicationContext(), "Task added successfully!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Uh-oh, an error!", Toast.LENGTH_SHORT).show();
            }
            arrayAdapter.notifyDataSetChanged();
        }
    }
}