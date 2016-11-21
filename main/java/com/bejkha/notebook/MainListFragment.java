package com.bejkha.notebook;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
public class MainListFragment extends ListFragment {
    private  ArrayList<Note> notes;
    private  NoteAdapter noteAdapter;


    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

       NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        notes = dbAdapter.getAllNotes();
        dbAdapter.close();
        noteAdapter = new NoteAdapter(getActivity(),notes);
        setListAdapter(noteAdapter);

        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.black));
        getListView().setDividerHeight(1);

        registerForContextMenu(getListView());
    }
    @Override
    public void onListItemClick (ListView l , View v , int position , long id  ){
        super.onListItemClick(l,v,position,id);

        LaunchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW,position);
    }

    private void  LaunchNoteDetailActivity(MainActivity.FragmentToLaunch ftl,int position){
        //grab the note information
        Note note = (Note) getListAdapter().getItem(position);
        //create  a new intent that launch our noteDetailActivity
        Intent intent = new Intent(getActivity() , NoteDetailActivity.class);

        //pass  the information of the note we clicked on
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA, note.getTitle());
        intent.putExtra(MainActivity.NOTE_BODY_EXTRA, note.getBody());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA, note.getId());

        switch(ftl){
            case VIEW:intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.EDIT);
                break;
        }


        startActivity(intent);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        //give me  the position  of any note i longPressedOn
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;
        Note note = (Note) getListAdapter().getItem(rowPosition);
        //return to us the id of the item menu we selected
        switch (item.getItemId()){
            //if we press  edit
            case R.id.edit:
                LaunchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT,rowPosition);
                Log.d("Menu click ","You pressed edit");
                return true;
            case R.id.delete:
                NotebookDbAdapter dbAdapter= new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteNote(note.getId());

                notes.clear();
                notes.addAll(dbAdapter.getAllNotes());
                noteAdapter.notifyDataSetChanged();

                dbAdapter.close();
        }
        return super.onContextItemSelected(item);
    }
}
