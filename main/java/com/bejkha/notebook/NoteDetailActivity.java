package com.bejkha.notebook;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NoteDetailActivity extends AppCompatActivity {
    public static final String NEW_NOTE_EXTRA="New note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        createAndAddFragment();
    }

    private void createAndAddFragment(){
        //grab intent and fragment to launch  from our MainActivity list fragment
        Intent intent = getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch =
                (MainActivity.FragmentToLaunch) intent.getSerializableExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA);
        //grabbing our fragment manager and our fragment transaction so that we can add or edit our fragment dynamically
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //choose the correct fragment to load
        switch(fragmentToLaunch){
            case EDIT:
                //create and add  note edit fragment to note detail activity
                NoteEditFragment noteEditFragment = new NoteEditFragment();
                setTitle(R.string.EditFragmentTitle);
                fragmentTransaction.add(R.id.note_container, noteEditFragment , "NOTE_EDIT_FRAGMENT");
                break;
            case VIEW:
                //create and add  note view fragment to note detail activity
                NoteViewFragment noteViewFragment = new NoteViewFragment();
                setTitle(R.string.ViewFragmentTitle);
                fragmentTransaction.add(R.id.note_container, noteViewFragment , "NOTE_VIEW_FRAGMENT");
                break;
            case CREATE:
                //create and add  note edit fragment to note detail activity
                NoteEditFragment noteCreateFragment = new NoteEditFragment();
                setTitle(R.string.CreateFragmentTilte);

                Bundle bundle = new Bundle();
                bundle.putBoolean(NEW_NOTE_EXTRA, true);
                noteCreateFragment.setArguments(bundle);

                fragmentTransaction.add(R.id.note_container, noteCreateFragment , "NOTE_CREATE_FRAGMENT");
                break;
        }
        //commit the
        fragmentTransaction.commit();
    }

}
