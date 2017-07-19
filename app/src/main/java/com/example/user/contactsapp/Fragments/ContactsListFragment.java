package com.example.user.contactsapp.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.contactsapp.Adapter.ContactsAdapter;
import com.example.user.contactsapp.Contact.Contact;
import com.example.user.contactsapp.DataBasa.DatabaseHandler;
import com.example.user.contactsapp.Dialogs.AlertDFragment;
import com.example.user.contactsapp.Interfaces.DialogClickListener;
import com.example.user.contactsapp.R;

import static com.example.user.contactsapp.Dialogs.AlertDFragment.ID_OF_LONG_CLICKED_ITEM_KEY;
import static com.example.user.contactsapp.Dialogs.AlertDFragment.POSITION_OF_LONG_CLICKED_ITEM_KEY;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.AGE_OF_EDITABLE_ITEM;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.FROM_WHERE_ADD_OR_EDT_FRAGMENT_KEY;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.GENDER_OF_EDITABLE_ITEM;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.ID_OF_EDITABLE_ITEM;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.NAME_OF_EDITABLE_ITEM;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.NUMBER_OF_EDITABLE_ITEM;

/**
 * Created by User on 7/12/2017.
 */

public class ContactsListFragment extends Fragment  implements DialogClickListener {

    private ContactsAdapter contactsAdapter;
    private AlertDFragment alertdFragment;
    private OneContactFragment oneContactFragment;
    DatabaseHandler db;

    public static final String FROM_WHERE_CONTACT_LIST_FRAGMENT_KEY = "from  where  replace or add contactListFragment";
    public static final String TAG_FOR_ALERT_FRAGMENT = "Alert Dialog Fragment";

    public static ContactsListFragment newInstance() {

        Bundle args = new Bundle();

        ContactsListFragment fragment = new ContactsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    public static ContactsListFragment newInstance(int numberOfRequest) {
        ContactsListFragment myFragment = new ContactsListFragment();

        Bundle args = new Bundle();
        args.putInt(FROM_WHERE_CONTACT_LIST_FRAGMENT_KEY, numberOfRequest);
        myFragment.setArguments(args);

        return myFragment;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alertdFragment = AlertDFragment.newInstance(5);
        alertdFragment.setTargetFragment(this,5);

        db = new DatabaseHandler(getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.mainActivity_recyclerView_of_contacts_list);
        Button btnAddContact = view.findViewById(R.id.list_fragment_btn_add);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        contactsAdapter = new ContactsAdapter(getActivity(), db.getAllContacts(), new ContactsAdapter.ItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position, Contact contact) {

                alertdFragment.getArguments().putInt(ID_OF_LONG_CLICKED_ITEM_KEY, contact.getId());
                alertdFragment.getArguments().putInt(POSITION_OF_LONG_CLICKED_ITEM_KEY, position);

                alertdFragment.show(getFragmentManager(), TAG_FOR_ALERT_FRAGMENT);
            }
        }, new ContactsAdapter.ItemClickListener() {

            @Override
            public void onItemClick(View view, int position, Contact contact) {

                Bundle bundle = new Bundle();
                bundle.putString(NAME_OF_EDITABLE_ITEM,contact.getName());
                bundle.putString(NUMBER_OF_EDITABLE_ITEM,contact.getNumber());
                bundle.putString(AGE_OF_EDITABLE_ITEM,contact.getAge());
                bundle.putString(GENDER_OF_EDITABLE_ITEM,contact.getGender());

                oneContactFragment = OneContactFragment.newInstance(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.mainActivity_list_place, oneContactFragment);
                transaction.addToBackStack(null);


                transaction.commit();


            }

        });
        contactsAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(contactsAdapter);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt(FROM_WHERE_ADD_OR_EDT_FRAGMENT_KEY,1);

                Fragment addContactFragment = AddOrEditContactFragment.newInstance(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.mainActivity_list_place, addContactFragment);
                transaction.addToBackStack(null);


                transaction.commit();
            }
        });
    }

    @Override
    public void onEditClick(int id) {
        Bundle bundle = new Bundle();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        bundle.putInt(FROM_WHERE_ADD_OR_EDT_FRAGMENT_KEY,2);
        bundle.putInt(ID_OF_EDITABLE_ITEM,db.getContact(id).getId());
        bundle.putString(NAME_OF_EDITABLE_ITEM,db.getContact(id).getName());
        bundle.putString(NUMBER_OF_EDITABLE_ITEM,db.getContact(id).getNumber());
        bundle.putString(AGE_OF_EDITABLE_ITEM,db.getContact(id).getAge());
        bundle.putString(GENDER_OF_EDITABLE_ITEM,db.getContact(id).getGender());

        contactsAdapter.notifyDataSetChanged();
        Fragment addContactFragment = AddOrEditContactFragment.newInstance(bundle);

        transaction.replace(R.id.mainActivity_list_place, addContactFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    @Override
    public void onDeleteClick(int id,int position) {
        db.deleteContact(id);
        contactsAdapter.removeItem(position);
        contactsAdapter.notifyDataSetChanged();
    }

}
