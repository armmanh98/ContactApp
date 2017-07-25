package com.example.user.contactsapp.Dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.user.contactsapp.R;

/**
 * Created by User on 7/21/2017.
 */

public class DialogFragmentImageUrl extends DialogFragment {

    View.OnClickListener clickPositiveButton;
    View.OnClickListener clickNegativeButton;
    String title;
    Button positiveButton;
    Button negativeButton;

    public Spinner getSpinner() {
        return spinner;
    }

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    @ArrayRes int myArrayRes;


    @SuppressLint("ValidFragment")
    public DialogFragmentImageUrl(AlertFragmentSetImageDescriptionBuilder builder) {

        super();
        this.clickPositiveButton = builder.clickPositiveButton;
        this.clickNegativeButton = builder.clickNegativeButton;
        this.title = builder.title;
        this.myArrayRes = builder.myArrayRes;

    }

    public DialogFragmentImageUrl() {

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        positiveButton = view.findViewById(R.id.fragment_set_image_url_btn_ok);
        negativeButton = view.findViewById(R.id.fragment_set_image_url_btn_cancel);
        spinner = view.findViewById(R.id.fragment_set_image_url_spinner);

        positiveButton.setOnClickListener(clickPositiveButton);
        negativeButton.setOnClickListener(clickNegativeButton);

        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.images_url_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_set_image_url, container,
                false);

        getDialog().setTitle(title);
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public static class AlertFragmentSetImageDescriptionBuilder {

        View.OnClickListener clickPositiveButton;
        View.OnClickListener clickNegativeButton;
        @ArrayRes int myArrayRes;
        String title;

        public AlertFragmentSetImageDescriptionBuilder() {
        }

        public AlertFragmentSetImageDescriptionBuilder clickListenerPositiveButton(View.OnClickListener clickPositiveButton) {
            this.clickPositiveButton = clickPositiveButton;
            return this;
        }

        public AlertFragmentSetImageDescriptionBuilder clickListenerNegativeButton(View.OnClickListener clickNegativeButton) {
            this.clickNegativeButton = clickNegativeButton;
            return this;
        }
        public AlertFragmentSetImageDescriptionBuilder title(String title) {
            this.title = title;
            return this;
        }
        public AlertFragmentSetImageDescriptionBuilder myArrayRes(@ArrayRes int myArrayRes) {
            this.myArrayRes = myArrayRes;
            return this;
        }


        public DialogFragmentImageUrl build() {
            return new DialogFragmentImageUrl(this);
        }


    }


}


