package com.example.billzg.todoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> dataList = new ArrayList<>();
    private MyDBHandler dbHandler ;
    private int myId;

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> data, int uId) {
        super(context, resource, data);

        this.context = context;
        this.dataList = data;
        myId = uId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if ( listItem == null ){
            listItem = LayoutInflater.from(this.context).inflate(R.layout.item_list, parent, false);
        }

        final String currentLine = dataList.get(position);

        TextView myTxt = (TextView) listItem.findViewById(R.id.textViewDesc);

        myTxt.setText(currentLine);

        //makes a toast for each
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello from normal click");
                Toast.makeText(context, currentLine, Toast.LENGTH_SHORT).show();

            }
        });
        //delete an entry on longClickListener
        listItem.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        dbHandler = new MyDBHandler(context, null, null, 1);
                        dbHandler.deleteThisItem(currentLine, myId);
                        System.out.println("hello from long click");
                        //Toast.makeText(context, "entry Deleted", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
        );


        return listItem;
    }

}
