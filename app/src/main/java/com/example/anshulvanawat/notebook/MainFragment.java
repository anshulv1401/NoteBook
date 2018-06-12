package com.example.anshulvanawat.notebook;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */


public class MainFragment extends ListFragment {

    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        NotebookDbAdapter dbAdapter=new NotebookDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();

        notes = dbAdapter.getAllnotes();
        noteAdapter = new NoteAdapter(getActivity(), notes);

        dbAdapter.close();


        setListAdapter(noteAdapter);
        //getListView().setDivider(ContextCompat.getDrawable(getActivity()),android.R.color.black);
        //getListView().setDivider

        registerForContextMenu(getListView());


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        lauchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW,position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu,v,menuInfo);

        MenuInflater menuInflater=getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        //give me the position of whatever note i long pressed on
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int rowPosition=info.position;

        Note note=(Note) getListAdapter().getItem(rowPosition);


        //gives the id of the item selected in the contextMenu
        switch (item.getItemId()){

            case R.id.edit:
                lauchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT,rowPosition);
                return true;

            case R.id.delete:
                NotebookDbAdapter dbAdapter=new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteNote(note.getNoteId());
                notes.clear();
                notes.addAll(dbAdapter.getAllnotes());
                noteAdapter.notifyDataSetChanged();
                dbAdapter.close();
        }
        return super.onContextItemSelected(item);


    }


    private void lauchNoteDetailActivity(MainActivity.FragmentToLaunch ftl, int position) {

        //grab the note information associated with whatever note item we clicked on
        Note note = (Note) getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA, note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA, note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA, note.getCategory());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA, note.getNoteId());

        switch(ftl){
            case VIEW:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.EDIT);
                break;
        }
        startActivity(intent);
    }
}



