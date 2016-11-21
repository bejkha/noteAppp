package com.bejkha.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by COOLER MASTER on 09/07/2016.
 */
public class NotebookDbAdapter {
    private static final String DATABASE_NAME ="notebook.db";
    private static final int DATABASE_VERSION = 1;

    public static final String NOTE_TABLE = "note";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "bodyText";
    public static final String COLUMN_DATE = "DATE";

    private String[] allColumns = {COLUMN_ID, COLUMN_TITLE, COLUMN_BODY, COLUMN_DATE};

    public static final String CreateTableNote = "create table "+ NOTE_TABLE +" ( "
            + COLUMN_ID +" integer primary key autoincrement, "
            + COLUMN_TITLE +" text not null, "+ COLUMN_BODY +" text not null, "
            + COLUMN_DATE +");";

    private SQLiteDatabase sqlDB;
    private Context context;

    private NotebookDbHelper notebookDbHelper;

    public NotebookDbAdapter(Context ctx){
        context = ctx;
    }

    public NotebookDbAdapter open() throws android.database.SQLException {
        notebookDbHelper = new NotebookDbHelper(context);
        sqlDB = notebookDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        notebookDbHelper.close();
    }

    public Note createNote(String title, String body){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE,title);
        values.put(COLUMN_BODY,body);
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+"");

        long insertId = sqlDB.insert(NOTE_TABLE, null, values);

        Cursor cursor = sqlDB.query(NOTE_TABLE,allColumns,COLUMN_ID+ " = "+ insertId, null, null, null, null);

        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();

        return newNote;
    }

    public long deleteNote(long idToDelete){
        return sqlDB.delete(NOTE_TABLE,COLUMN_ID+" = "+ idToDelete, null);
    }

    public long updateNote(long idToUpdate, String newTitle, String newBody){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE,newTitle);
        values.put(COLUMN_BODY,newBody);
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+"");

        return sqlDB.update(NOTE_TABLE, values,COLUMN_ID+" = "+idToUpdate, null);
    }

    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> notes = new ArrayList<Note>();
        //grab all the information  from our database
        Cursor cursor = sqlDB.query(NOTE_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Note note = cursorToNote(cursor);
            notes.add(note);
        }

        cursor.close();

        return notes;
    }

    private Note cursorToNote(Cursor cursor){
        Note newNote =  new Note(cursor.getString(1), cursor.getString(2), cursor.getLong(0) , cursor.getLong(3));
        return newNote;
    }

    private static class NotebookDbHelper extends SQLiteOpenHelper {

        NotebookDbHelper(Context ctx){
            super(ctx , DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate (SQLiteDatabase db){
            //create table note
            db.execSQL(CreateTableNote);
        }
        @Override
        public void onUpgrade (SQLiteDatabase db , int oldVersion , int newVersion){
            Log.w(NotebookDbHelper.class.getName(),"Update db from version "+ oldVersion+ "to"+
                    newVersion +" witch will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+ NOTE_TABLE);
            onCreate(db);
        }
    }

}
