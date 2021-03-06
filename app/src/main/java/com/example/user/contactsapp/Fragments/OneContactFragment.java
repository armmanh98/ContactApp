package com.example.user.contactsapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.contactsapp.Adapter.MyPagerAdapter;
import com.example.user.contactsapp.DataBasa.DatabaseHandler;
import com.example.user.contactsapp.Image.Image;
import com.example.user.contactsapp.R;

import java.util.ArrayList;

import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.AGE_OF_EDITABLE_ITEM;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.GENDER_OF_EDITABLE_ITEM;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.ID_OF_EDITABLE_ITEM;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.NAME_OF_EDITABLE_ITEM;
import static com.example.user.contactsapp.Fragments.AddOrEditContactFragment.NUMBER_OF_EDITABLE_ITEM;

/**
 * Created by User on 7/19/2017.
 */

public class OneContactFragment extends Fragment {


    ArrayList<Image> images;
    DatabaseHandler db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHandler(getActivity());

        TextView name = view.findViewById(R.id.fragment_one_contact_name_tv);
        ViewPager mPager =  view.findViewById(R.id.pager);
        TextView number = view.findViewById(R.id.fragment_one_contact_number_tv);
        TextView age = view.findViewById(R.id.fragment_one_contact_age_tv);
        TextView gender = view.findViewById(R.id.fragment_one_contact_gender_tv);

        name.setText(getArguments().getString(NAME_OF_EDITABLE_ITEM));
        number.setText(getArguments().getString(NUMBER_OF_EDITABLE_ITEM));
        age.setText(getArguments().getString(AGE_OF_EDITABLE_ITEM));
        gender.setText(getArguments().getString(GENDER_OF_EDITABLE_ITEM));

        images = (ArrayList<Image>) db.getAllImages(getArguments().getInt(ID_OF_EDITABLE_ITEM));
        mPager.setAdapter(new MyPagerAdapter(getFragmentManager(),images));

    }

    public static OneContactFragment newInstance(Bundle args) {

        OneContactFragment fragment = new OneContactFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
