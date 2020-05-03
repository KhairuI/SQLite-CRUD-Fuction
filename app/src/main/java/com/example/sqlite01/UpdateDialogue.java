package com.example.sqlite01;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.DialogFragment;

public class UpdateDialogue extends DialogFragment {
    private AppCompatEditText idEditText,nameEditText,markEditText,genderEditText;
    private updateDialogueListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterCompat= getActivity().getLayoutInflater();
        View view= inflaterCompat.inflate(R.layout.update_dialogue,null);

        builder.setView(view).setTitle("Update Data").setCancelable(true).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id= idEditText.getEditableText().toString();
                String name= nameEditText.getEditableText().toString();
                String mark= markEditText.getEditableText().toString();
                String gender= genderEditText.getEditableText().toString();
                listener.updateText(id,name,mark,gender);

            }
        });

        idEditText= view.findViewById(R.id.studentUpdateId);
        nameEditText= view.findViewById(R.id.nameUpdateId);
        markEditText= view.findViewById(R.id.markUpdateId);
        genderEditText= view.findViewById(R.id.genderUpdateId);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener= (updateDialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +"Must Implement updateDialogue Listener");
        }
    }

    public interface updateDialogueListener{
        void updateText(String id,String name,String mark,String gender);
    }
}
