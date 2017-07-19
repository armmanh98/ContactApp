package com.example.user.contactsapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ItemLongClickListener mLongClickListener;
    private ItemClickListener mClickListener;


    public ContactsAdapter(Context context, List<Contact> data, ItemLongClickListener mLongClickListener, ItemClickListener mClickListener) {
        this.mLongClickListener = mLongClickListener;
        this.mInflater = LayoutInflater.from(context);
        this.mClickListener = mClickListener;
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

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        TextView name;
        TextView number;

        ViewHolder(View itemView)  {

            super(itemView);

            name = itemView.findViewById(R.id.mainActivity_textView_name);
            number = itemView.findViewById(R.id.mainActivity_textView_phoneNumber);

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);

        }


        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) mLongClickListener.onItemLongClick(view, getAdapterPosition(),mData.get(getAdapterPosition()));
            return false;
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(),mData.get(getAdapterPosition()));
        }
    }

    public interface ItemLongClickListener {

        void onItemLongClick(View view, int position, Contact contact);

    }
    public interface ItemClickListener {

        void onItemClick(View view, int position, Contact contact);

    }

    public void removeItem(int position) {
        mData.remove(position);
    }
}
