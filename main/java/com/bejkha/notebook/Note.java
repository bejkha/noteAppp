package com.bejkha.notebook;




/**
 * Created by COOLER MASTER on 08/07/2016.
 */
public class Note {
    private String Title , Body;
    private long noteId, dateCreatedMilli;


    public Note (String title , String body ){
        this.Title = title;
        this.Body = body;

        this.noteId =  0;
        this.dateCreatedMilli= 0;
    }

    public Note (String title , String body  , long noteid , long datecrea){
        this.Title = title;
        this.Body = body;
        this.noteId =  noteid;
        this.dateCreatedMilli= datecrea;
    }

    public String getTitle(){
        return Title;
    }

    public String getBody(){
        return Body;
    }

    public long getId() { return noteId; }

    public long getDateCreatedMilli() { return dateCreatedMilli; }

    public String toString(){ return "ID : "+ noteId +"Tiltle : "+ Title
            + "text : "+ Body  + "Date : "+ dateCreatedMilli; }

}
