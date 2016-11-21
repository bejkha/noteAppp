package com.bejkha.notebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {
    private EditText title;
    private EditText body;
    private AlertDialog confirmDialogObject;

    private boolean newNote=false;
    private long noteId=0;


    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if(bundle != null){
            newNote = bundle.getBoolean(NoteDetailActivity.NEW_NOTE_EXTRA, false);
        }

        //inflate our fragment edit layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_note_edit, container, false);

        //grab the references of widgets
        title = (EditText)fragmentLayout.findViewById(R.id.editNoteTitle);
        body = (EditText) fragmentLayout.findViewById(R.id.editNoteBody);
        Button saveButton = (Button) fragmentLayout.findViewById(R.id.saveNote);
        //populate widgets with note  data
        Intent intent=getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA, ""));
        body.setText(intent.getExtras().getString(MainActivity.NOTE_BODY_EXTRA, ""));
        noteId = intent.getExtras().getLong(MainActivity.NOTE_ID_EXTRA,0);
        buildConfirmDialog();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogObject.show();
            }
        });

        return fragmentLayout;
    }
   @Override
    public void onSaveInstanceState(Bundle savedInstanceSate){
        super.onSaveInstanceState(savedInstanceSate);
        //save change b4 changing the orientation
    }


    private void buildConfirmDialog(){
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
        confirmBuilder.setTitle("are you sure ?");
        confirmBuilder.setMessage("are you sure you want to save the note?");

        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //save the information in  our database
               NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                if (newNote){
                    dbAdapter.createNote(title.getText()+"" , body.getText()+"");
                }else{
                    dbAdapter.updateNote(noteId, title.getText()+"", body.getText()+"");
                }
                dbAdapter.close();
                //bring us back to our mainActivity
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing here !
            }
        });
        confirmDialogObject = confirmBuilder.create();
    }
}
