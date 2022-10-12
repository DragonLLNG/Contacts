package com.example.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
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
                FormBody formBody = new FormBody.Builder()
                        .add("Name", mContact.getName())
                        .build();
                Request request = new Request.Builder()
                        .url("https://www.theappsdr.com/contact/json/delete?Name")
                        .post(formBody)
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        System.out.println("Failure");
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()){
                            System.out.println("You response is succesful");
                            ResponseBody responseBody = response.body();
                            String body = responseBody.string();
                            Log.d("demo", "onResponse: "+body);
                        }


                    }
                });
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