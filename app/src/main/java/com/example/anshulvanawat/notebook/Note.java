package com.example.anshulvanawat.notebook;



/**
 * Created by Anshul vanawat on 07-07-16.
 */
public class Note {
    private String title,message;
    private long noteId,dateCreatedMilli;
    public enum Category{PERSONAL,TECHNICAL, QUOTE ,FINANCE}
    private Category category;//will store any enum Category,example QUOTE

    public Note(String title,String message,Category category){
        this.title=title;
        this.message=message;
        this.category=category;
        this.noteId=0;
        this.dateCreatedMilli=0;
    }

    public Note(String title,String message,Category category,long noteId,long dateCreatedMilli){
        this.title=title;
        this.noteId=noteId;
        this.message=message;
        this.category=category;
        this.dateCreatedMilli=dateCreatedMilli;
    }

    public String getTitle(){
        return title;
    }
    public String getMessage(){
        return message;
    }
    public Category getCategory(){
        return category;
    }
    public long getNoteId(){
        return noteId;
    }
    public long getDateCreatedMilli(){
        return dateCreatedMilli;
    }

    public String toString(){
        return "ID: "+noteId+" Title: " + title+" Message: "+message+" date: "+dateCreatedMilli+" IconId: "+category.name();
    }

    public int getAssociateDrawable(){
        return categoryToDrawable(category);
    }

    public static int categoryToDrawable(Category noteCategory){

        switch (noteCategory){
            case PERSONAL:
                return R.drawable.logo_1;
            case TECHNICAL:
                return R.drawable.logo_2;
            case QUOTE:
                return R.drawable.logo_3;
            case FINANCE:
                return R.drawable.logo_4;
        }
        return R.drawable.logo_1;
    }


}
