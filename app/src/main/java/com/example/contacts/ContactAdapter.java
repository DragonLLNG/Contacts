package com.example.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_row,parent,false);
        }
        Contact contact = getItem(position);


        TextView name = convertView.findViewById(R.id.textViewName);
        TextView email = convertView.findViewById(R.id.textViewNameEmail);
        TextView phone = convertView.findViewById(R.id.textViewNamePhone);
        TextView phoneType = convertView.findViewById(R.id.textViewNamePhoneType);

        name.setText(contact.getName());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
        phoneType.setText(contact.getPhoneType());

        return convertView;
    }
}
