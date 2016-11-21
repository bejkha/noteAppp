package com.bejkha.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by COOLER MASTER on 08/07/2016.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    public static class holdView {
        TextView title;
        TextView body;
        //ImageView icon;

    }

    public NoteAdapter (Context context, ArrayList<Note> notes){
        super(context,0,notes);
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent){
        //Get  the item for this position
        Note note = getItem(position);
        //create a holdView
        holdView holdview;

        if ( convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_list_layout,parent, false);
            holdview = new holdView();
            holdview.title = (TextView) convertView.findViewById(R.id.ListItemTitle);
            holdview.body = (TextView) convertView.findViewById(R.id.ListItemBody);
           // holdview.icon = (ImageView) convertView.findViewById(R.id.ListItemImg);

            convertView.setTag(holdview);

        }else{
            holdview = (holdView) convertView.getTag();
        }

        holdview.title.setText(note.getTitle());
        holdview.body.setText(note.getBody());


        return convertView;
   }
}
