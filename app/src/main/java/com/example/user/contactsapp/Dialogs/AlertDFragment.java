package com.example.user.contactsapp.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.user.contactsapp.Interfaces.DialogClickListener;
import com.example.user.contactsapp.R;

/**
 * Created by User on 7/12/2017.
 */

public class AlertDFragment extends DialogFragment {


    public static final String FROM_FOR_ALERT_FRAGMENT_KEY = "from  where  replace or add alertFragment";
    public static final String ID_OF_LONG_CLICKED_ITEM_KEY = "Id of long clicked item";
    public static final String POSITION_OF_LONG_CLICKED_ITEM_KEY = "Position of long clicked item";

    private DialogClickListener callback;

    public static AlertDFragment newInstance(int numberOfRequest) {
        AlertDFragment myFragment = new AlertDFragment();

        Bundle args = new Bundle();
        args.putInt(FROM_FOR_ALERT_FRAGMENT_KEY, numberOfRequest);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            callback = (DialogClickListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getString(R.string.class_cast_exception_calling_fragment));
        }


    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())


                .setMessage(R.string.message_alert_fragment)

                .setPositiveButton(R.string.positive_button_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onEditClick(getArguments().getInt(ID_OF_LONG_CLICKED_ITEM_KEY));


                    }
                })

                .setNegativeButton(R.string.negative_button_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onDeleteClick(getArguments().getInt(ID_OF_LONG_CLICKED_ITEM_KEY), getArguments().getInt(POSITION_OF_LONG_CLICKED_ITEM_KEY));


                    }
                }).create();
    }

}
