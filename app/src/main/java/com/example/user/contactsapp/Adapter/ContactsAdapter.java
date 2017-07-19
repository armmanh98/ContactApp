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
    private ItemLongClickListener mClickListener;


    public ContactsAdapter(Context context, List<Contact> data, ItemLongClickListener mClickListener) {
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

        holder.name.setText(mData.get(position).getName());
        holder.number.setText(String.valueOf(mData.get(position).getNumber()));
        holder.age.setText(String.valueOf(mData.get(position).getAge()));
        holder.gender.setText(mData.get(position).getGender());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        LinearLayout linearLayout;
        TextView name;
        TextView number;
        TextView age;
        TextView gender;

        ViewHolder(View itemView)  {

            super(itemView);
            linearLayout = itemView.findViewById(R.id.mainActivity_linearLayout_of_contact) ;
            name = itemView.findViewById(R.id.mainActivity_textView_name);
            number = itemView.findViewById(R.id.mainActivity_textView_phoneNumber);
            age = itemView.findViewById(R.id.mainActivity_textView_age);
            gender = itemView.findViewById(R.id.mainActivity_textView_gender);

            itemView.setOnLongClickListener(this);

        }


        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) mClickListener.onItemLongClick(view, getAdapterPosition(),mData.get(getAdapterPosition()));
            return false;
        }
    }

    public interface ItemLongClickListener {

        void onItemLongClick(View view, int position, Contact contact);
    }

    public void removeItem(int position) {
        mData.remove(position);
    }
}
