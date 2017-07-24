package com.example.user.contactsapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.contactsapp.Fragments.ImageFragment;
import com.example.user.contactsapp.Image.Image;

import java.util.ArrayList;

/**
 * Created by User on 7/24/2017.
 */

public  class MyPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Image> imagesOfContact;


    public MyPagerAdapter(FragmentManager fragmentManager,ArrayList<Image> imagesOfContact) {
        super(fragmentManager);
        this.imagesOfContact = imagesOfContact ;

    }

    @Override
    public int getCount() {
        return imagesOfContact.size();
    }

    @Override
    public Fragment getItem(int position) {

        return ImageFragment.newInstance(imagesOfContact.get(position));
    }

}