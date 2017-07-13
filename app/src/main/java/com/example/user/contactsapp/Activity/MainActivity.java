package com.example.user.contactsapp.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.contactsapp.Contact.Contact;
import com.example.user.contactsapp.Fragments.ContactsListFragment;
import com.example.user.contactsapp.Fragments.EditContactFragment;
import com.example.user.contactsapp.R;

public class MainActivity extends AppCompatActivity  {

    Fragment contactsListFragment;
    Fragment contactsEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsListFragment = new ContactsListFragment();
        contactsEditFragment = new EditContactFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.mainActivity_list_place, contactsListFragment).commit();


    }

    public void ReplaceEditFragment(Contact contact) {

        Bundle b = new Bundle();
        b.putSerializable("Passed Contact", contact);

        contactsEditFragment.setArguments(b);
        getFragmentManager().beginTransaction()
                .replace(R.id.mainActivity_list_place, contactsEditFragment).commit();
    }


}
