package com.example.emotionalsupportapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteFriendDialog extends AppCompatDialogFragment {

    private EditText editFriendId;
    public FriendDialogListener friendDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        editFriendId = view.findViewById(R.id.edit_friendId);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String friendId = editFriendId.getText().toString();
                        friendDialogListener.deleteFriendId(friendId);
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
        void deleteFriendId(String friendId);
    }
}
