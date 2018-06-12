package com.example.anshulvanawat.notebook;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {
    private ImageButton noteCatButton;
    private EditText title,message;
    private Note.Category savedButtonCategory;
    private AlertDialog categoryDialogObject,confirmDialogObject;
    private static final String MODIFIED_CATAGORY="modified catagory";
    private boolean newNote=false;
    private long noteId=0;

    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState!=null){
            savedButtonCategory=(Note.Category)savedInstanceState.get(MODIFIED_CATAGORY);

        }
        //grab the bundle to check weather we are creating a new note or not
        Bundle bundle=this.getArguments();
        if(bundle!=null){
            newNote=bundle.getBoolean(NoteDetailActivity.NEW_NOTE_EXTRA,false);
        }


        // Inflate the layout for this fragment
        View fragmentLayout =inflater.inflate(R.layout.fragment_note_edit,container,false);

        title=(EditText)fragmentLayout.findViewById(R.id.editNoteTitle);
        message=(EditText)fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCatButton=(ImageButton)fragmentLayout.findViewById(R.id.editNoteButton);
        Button savedButton=(Button)fragmentLayout.findViewById((R.id.saveNote));


        //populate widgets with note data
        Intent intent=getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA,""));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA,""));
        noteId=intent.getExtras().getLong(MainActivity.NOTE_ID_EXTRA,0);


        //if we grabed a category from our bundle than we know we changed orientation and saved information
        //so set our image button background to that catagory
        if(savedButtonCategory!=null){
            noteCatButton.setImageResource(Note.categoryToDrawable(savedButtonCategory));
        }
       else if(!newNote){//otherwise we came from our list fragment so just do everything normal
            Note.Category noteCat = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_EXTRA);
            savedButtonCategory = noteCat;
            noteCatButton.setImageResource(Note.categoryToDrawable(noteCat));
        }


        buildCategoryDialog();
        buildConfirmDialog();

        noteCatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                categoryDialogObject.show();
            }
        });
        savedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               confirmDialogObject.show();
            }
        });


        return fragmentLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstatnceState){
        super.onSaveInstanceState(savedInstatnceState);

        savedInstatnceState.putSerializable(MODIFIED_CATAGORY,savedButtonCategory);


    }


    private void buildCategoryDialog(){
        final String[] categories=new String[]{"Personal","Technical","Quote","Finance"};

        AlertDialog.Builder categoryBuilder=new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose Note Type");
        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {

                //to dissmiss our dialog window

                categoryDialogObject.cancel();

                switch(item){

                    case 0:
                        savedButtonCategory=Note.Category.PERSONAL;
                        noteCatButton.setImageResource(R.drawable.logo_1);
                        break;
                    case 1:
                        savedButtonCategory=Note.Category.TECHNICAL;
                        noteCatButton.setImageResource(R.drawable.logo_2);
                        break;
                    case 2:
                        savedButtonCategory=Note.Category.QUOTE;
                        noteCatButton.setImageResource(R.drawable.logo_3);
                        break;
                    case 3:
                        savedButtonCategory=Note.Category.FINANCE;
                        noteCatButton.setImageResource(R.drawable.logo_4);
                        break;

                }

            }
        });
        categoryDialogObject =categoryBuilder.create();
    }


    private void buildConfirmDialog(){
        AlertDialog.Builder confirmBuilder=new AlertDialog.Builder(getActivity());
        confirmBuilder.setTitle("Are you sure?");
        confirmBuilder.setMessage("Are you sure you want to save the note?");

        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                NotebookDbAdapter dbAdapter=new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                if(newNote){
                    //if new note then add it on our database
                    dbAdapter.createNote(title.getText()+"",message.getText()+"",
                            (savedButtonCategory==null)?Note.Category.PERSONAL:savedButtonCategory);

                }else{
                    //else its's an old note, so update it
                    dbAdapter.updateNote(noteId,title.getText()+"",message.getText()+"",savedButtonCategory);

                }
                //if confirm button is hitted then this activity will be closed
                dbAdapter.close();

                MainActivity.refreshActivity=true;
                getActivity().finish();


            }
        });

        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing here
            }
        });

        confirmDialogObject=confirmBuilder.create();


    }





}
