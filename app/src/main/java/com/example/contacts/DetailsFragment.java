package com.example.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import okhttp3.OkHttpClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    //OkHttpClient client = new OkHttpClient();

    private static final String ARG_CONTACT = "param";

    private Contact mContact;

    public DetailsFragment() {
    }


    public static DetailsFragment newInstance(Contact contact) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT,contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContact = (Contact) getArguments().getSerializable(ARG_CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contact_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView name = view.findViewById(R.id.textViewName);
        TextView email = view.findViewById(R.id.textViewNameEmail);
        TextView phone = view.findViewById(R.id.textViewNamePhone);
        TextView type = view.findViewById(R.id.textViewNamePhoneType);

        name.setText(mContact.getName());
        email.setText(mContact.getEmail());
        phone.setText(mContact.getPhone());
        type.setText(mContact.getPhoneType());

        ImageView trash = view.findViewById(R.id.imageView);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.sendDeleteContact(mContact);
            }
        });

    }
    public interface DetailFragmentListener{
        void sendDeleteContact(Contact contact);

    }
    DetailFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (DetailFragmentListener) context;
    }
}