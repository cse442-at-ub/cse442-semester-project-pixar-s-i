package com.example.emotionalsupportapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class FriendDialog extends AppCompatDialogFragment {

    private EditText editFriendId;
    public FriendDialogListener friendDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        editFriendId = view.findViewById(R.id.edit_friendId);

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String friendId = editFriendId.getText().toString();
                        friendDialogListener.applyTexts(friendId);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            friendDialogListener = (FriendDialogListener) getTargetFragment();
        } catch (ClassCastException e){
            throw new ClassCastException(e.getMessage());
        }
    }

    public interface FriendDialogListener{
        void applyTexts(String friendId);
    }
}
