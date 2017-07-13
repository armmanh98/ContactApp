package com.example.user.contactsapp.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.example.user.contactsapp.Fragments.ContactsListFragment;
import com.example.user.contactsapp.Interfaces.DialogClickListener;

/**
 * Created by User on 7/12/2017.
 */

public class AlertDFragment extends DialogFragment {
    ContactsListFragment contactsListFragment;
    private DialogClickListener callback;

    public static AlertDFragment newInstance(int someInt) {
        AlertDFragment myFragment = new AlertDFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (DialogClickListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DialogClickListener interface");
        }
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())

                .setMessage("Contact")

                // Positive button
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onEditClick();

                        Log.i("TAG", getTargetFragment() + " ::::");
                    }
                })

                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        callback.onDeleteClick();


                        Log.i("TAG", contactsListFragment + " ::::");
                    }
                }).create();
    }

}
