package com.example.user.contactsapp.Dialogs;

import android.annotation.SuppressLint;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.contactsapp.R;

/**
 * Created by User on 7/21/2017.
 */

public class MyDialogFragment extends DialogFragment {

    View.OnClickListener clickPositiveButton;
    View.OnClickListener clickNegativeButton;
    Button positiveButton;
    Button negativeButton;
    EditText addDescriptionEdt;
    Bundle bundle;

    public EditText getAddDescriptionEdt() {
        return addDescriptionEdt;
    }

    @SuppressLint("ValidFragment")
    public MyDialogFragment(AlertFragmentSetImageDescriptionBuilder builder) {

        this.clickPositiveButton = builder.clickPositiveButton;
        this.clickNegativeButton = builder.clickNegativeButton;
        this.bundle = builder.bundle;
        this.setArguments(bundle);

    }

    public MyDialogFragment() {

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        positiveButton = view.findViewById(R.id.fragment_image_description_btn_ok);
        negativeButton = view.findViewById(R.id.fragment_image_description_btn_cancel);
        addDescriptionEdt = view.findViewById(R.id.fragment_image_description_edt_description);

        positiveButton.setOnClickListener(clickPositiveButton);
        negativeButton.setOnClickListener(clickNegativeButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_description, container,
                false);
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static class AlertFragmentSetImageDescriptionBuilder {

        View.OnClickListener clickPositiveButton;
        View.OnClickListener clickNegativeButton;
        Bundle bundle;

        public AlertFragmentSetImageDescriptionBuilder(Bundle bundle) {
            this.bundle = bundle;
        }

        public AlertFragmentSetImageDescriptionBuilder clickListenerPositiveButton(View.OnClickListener clickPostiveButton) {
            this.clickPositiveButton = clickPostiveButton;
            return this;
        }

        public AlertFragmentSetImageDescriptionBuilder clickListenerNegativeButton(View.OnClickListener clickNegativeButton) {
            this.clickNegativeButton = clickNegativeButton;
            return this;
        }


        public MyDialogFragment build() {
            return new MyDialogFragment(this);
        }


    }


}


