package com.bejkha.notebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteViewFragment extends Fragment {


    public NoteViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout=inflater.inflate(R.layout.fragment_note_view, container, false);

        TextView title = (TextView) fragmentLayout.findViewById(R.id.ViewItemTitle);
        TextView body = (TextView) fragmentLayout.findViewById(R.id.ViewItemBody);
       // ImageView icon = (ImageView) fragmentLayout.findViewById(R.id.ViewItemImg);

        Intent intent = getActivity().getIntent();

        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA));
        body.setText(intent.getExtras().getString(MainActivity.NOTE_BODY_EXTRA));
       // icon.setImageResource(R.drawable.in);

        // Inflate the layout for this fragment
        return fragmentLayout;
    }

}
