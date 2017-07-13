package com.example.user.contactsapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.user.contactsapp.Contact.Contact;
import com.example.user.contactsapp.R;

/**
 * Created by User on 7/13/2017.
 */

public class EditContactFragment extends Fragment {

    private EditText etName;
    private EditText etNumber;
    private EditText etAge;
    private EditText etGender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_contact,container,false);
    }

    @Override
    public  void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert savedInstanceState != null;
        Contact mContact = (Contact) savedInstanceState.get("Passed Contact");

        etName = (EditText) view.findViewById(R.id.fragmentEdit_name);
        etNumber = (EditText) view.findViewById(R.id.fragmentEdit_number);
        etAge = (EditText) view.findViewById(R.id.fragmentEdit_age);
        etGender = (EditText) view.findViewById(R.id.fragmentEdit_gender);

//        etName.setHint(mContact.getName());
//        etNumber.setHint(mContact.getNumber());
//        etAge.setHint(mContact.);
//        etGender.setHint(mContact.);

    }
}
