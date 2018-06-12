package com.example.anshulvanawat.notebook;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by Anshul vanawat on 07-07-16.
 */
public class NoteAdapter extends ArrayAdapter<Note>{

    public static class ViewHolder{
        TextView title;
        TextView note;
        ImageView noteIcon;
    }
    public NoteAdapter(Context context,ArrayList<Note> notes)
    {
        super(context, 0, notes);
    }
    @Override
    public View getView(int position , View convertView, ViewGroup parent){
        //get the data item for this postion
        Note note=getItem(position);

        //create a new view holder
        ViewHolder viewHolder;

        //check if existing view is reused, otherwise inflate a new one
        if(convertView==null){
            //if we don't have view that is being used create one, and make sure you create a
            //view holder along with it to save our view references to

            viewHolder=new ViewHolder();

            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_row,parent,false);
            viewHolder.title=(TextView)convertView.findViewById(R.id.listItemNoteTitle);
            viewHolder.note=(TextView)convertView.findViewById(R.id.listItemNoteBody);
            viewHolder.noteIcon=(ImageView)convertView.findViewById(R.id.listItemNoteImg);

            //use set tag to remember our view older which is holdin gour references to our widgit
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        //Grab references of views so we can pooulate them with specific note row data


        //fill each new referenced view with dara associated with note it's referenceing
        viewHolder.title.setText(note.getTitle());
        viewHolder.note.setText(note.getMessage());
        viewHolder.noteIcon.setImageResource(note.getAssociateDrawable());

        //now that we modified the view to diaplay appropriate data,
        //return it so ir will be displayed
        return convertView;


    }
}
