package com.example.user.contactsapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.contactsapp.Contact.Contact;
import com.example.user.contactsapp.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 7/12/2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Contact> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Contact contact;


    public ContactsAdapter(Context context, List<Contact> data, ItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

    }

    public Contact getItem(int id) {
        return mData.get(id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.contact_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        this.contact = mData.get(position);

        holder.name.setText(mData.get(position).getName());
        holder.number.setText(String.valueOf(mData.get(position).getNumber()));
        holder.age.setText(String.valueOf(mData.get(position).getAge()));
        holder.gender.setText(mData.get(position).getGender());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public LinearLayout linearLayout;
        public TextView name;
        public TextView number;
        public TextView age;
        public TextView gender;

        public ViewHolder(View itemView)  {

            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.mainActivity_linearLayout_of_contact) ;
            name = (TextView) itemView.findViewById(R.id.mainActivity_textView_name);
            number = (TextView) itemView.findViewById(R.id.mainActivity_textView_phoneNumber);
            age = (TextView) itemView.findViewById(R.id.mainActivity_textView_age);
            gender = (TextView) itemView.findViewById(R.id.mainActivity_textView_gender);

            linearLayout.setOnLongClickListener(this);

        }


        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) mClickListener.onItemLongClick(view, getAdapterPosition(),mData.get(getAdapterPosition()));
            return false;
        }
    }

//    public void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }



    public interface ItemClickListener {
        void onItemLongClick(View view, int position, Contact contact);
    }
    public void removeItem(int position) {
        mData.remove(position);
    }
}
