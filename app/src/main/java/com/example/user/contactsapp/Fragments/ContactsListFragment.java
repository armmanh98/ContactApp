package com.example.user.contactsapp.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.contactsapp.Adapter.ContactsAdapter;
import com.example.user.contactsapp.Contact.Contact;
import com.example.user.contactsapp.Dialogs.AlertDFragment;
import com.example.user.contactsapp.Interfaces.DialogClickListener;
import com.example.user.contactsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 7/12/2017.
 */

public class ContactsListFragment extends Fragment  implements DialogClickListener {

    private RecyclerView recyclerView;
    private Button btnAddContact;
    private List<Contact> contacts;
    private int myPosition;
    private ContactsAdapter contactsAdapter;
    private AlertDFragment alertdFragment;
//    FragmentManager fm = getFragmentManager();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    public static ContactsListFragment newInstance(int someInt) {
        ContactsListFragment myFragment = new ContactsListFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contacts = new ArrayList<>();

        alertdFragment = AlertDFragment.newInstance(5);
        alertdFragment.setTargetFragment(this,5);


        contacts.add(new Contact("Arman",555,0,"Male"));
        contacts.add(new Contact("Arman",555,1,"Male"));
        contacts.add(new Contact("Arman",555,2,"Male"));
        contacts.add(new Contact("Arman",555,3,"Male"));
        contacts.add(new Contact("Arman",555,4,"Male"));
        contacts.add(new Contact("Arman",555,5,"Male"));
        contacts.add(new Contact("Arman",555,6,"Male"));
        contacts.add(new Contact("Arman",555,7,"Male"));
        contacts.add(new Contact("Arman",555,8,"Male"));

        recyclerView = (RecyclerView)  view.findViewById(R.id.mainActivity_recyclerView_of_contacts_list);
        btnAddContact = (Button) view.findViewById(R.id.list_fragment_btn_add);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        contactsAdapter = new ContactsAdapter(getActivity(), contacts, new ContactsAdapter.ItemClickListener() {
            @Override
            public void onItemLongClick(View view, int position, Contact contact) {
                Log.i("TAG",":::  " + contact.getName() );
                myPosition = position;

                alertdFragment.getArguments().putInt("Position of item",position);

                alertdFragment.show(getFragmentManager(), "Alert Dialog Fragment");
            }
        });

        recyclerView.setAdapter(contactsAdapter);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addContactFragment = AddContactFragment.newInstance(5);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.mainActivity_list_place, addContactFragment);
                transaction.addToBackStack(null);


                transaction.commit();
            }
        });
    }

    @Override
    public void onEditClick() {

    }

    @Override
    public void onDeleteClick() {

    }
//    public void onUserSelectValue(int selectedValue) {
//        // TODO add your implementation.
//        Log.i("TAG","::: " + selectedValue);
//        if(selectedValue == -2) {
//
//            contactsAdapter.removeItem(myPosition);
//            contactsAdapter.notifyDataSetChanged();
//
//        }
//    }


}
