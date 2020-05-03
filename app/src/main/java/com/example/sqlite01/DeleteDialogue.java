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
import androidx.fragment.app.DialogFragment;

public class DeleteDialogue extends DialogFragment {

    private AppCompatEditText idEditText;
    private deleteDialogueListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterCompat= getActivity().getLayoutInflater();
        View view= inflaterCompat.inflate(R.layout.delete_dialogue,null);

        builder.setView(view).setTitle("Delete Data").setCancelable(true).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id= idEditText.getEditableText().toString();
                listener.deleteText(id);

            }

        });
        idEditText= view.findViewById(R.id.deleteId);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener= (deleteDialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +"Must Implement updateDialogue Listener");
        }
    }

    public interface deleteDialogueListener{
        void deleteText(String id);
    }
}
