package com.example.lz.firstproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class custom_adapter extends ArrayAdapter<listviewcode>{
    private List<listviewcode> item= new ArrayList<>();

    public custom_adapter(@NonNull Context context, ArrayList<listviewcode> item) {
        super(context, 0 , item);
        this.item = item;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
        }

        listviewcode currenttask = item.get(position);

        TextView task = convertView.findViewById(R.id.task);
        task.setText(currenttask.getTask());

        TextView importance = convertView.findViewById(R.id.importance);
        importance.setText(currenttask.getImportance());

        TextView description = convertView.findViewById(R.id.description);
        description.setText(currenttask.getDescription());

        return convertView;
    }
}
