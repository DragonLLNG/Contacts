package com.example.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class AddContactFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.new_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText nameInput = view.findViewById(R.id.editTextTextPersonName);
        EditText emailInput = view.findViewById(R.id.editTextTextPersonEmail);
        EditText phoneInput = view.findViewById(R.id.editTextTextPersonPhone);
        EditText phoneTypeInput = view.findViewById(R.id.editTextTextPersonPhoneType);


        Button cancel = view.findViewById(R.id.cancelBttn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.backContact();

            }
        });

        Button submit = view.findViewById(R.id.submitBttn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String type = phoneTypeInput.getText().toString();


                if (name.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(), "Empty name", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if (email.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(), "Empty email address", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if (phone.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(), "Empty phone number", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if (type.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(), "Empty phone type", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                FormBody formBody = new FormBody.Builder()
                        .add("name", name)
                        .add("email", email)
                        .add("phone", phone)
                        .add("type", type)
                        .build();
                Request request = new Request.Builder()
                        .url("https://www.theappsdr.com/contact/json/create")
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
                        if (response.isSuccessful()) {
                            System.out.println("You response is successful");
                            ResponseBody responseBody = response.body();
                            String body = responseBody.string();
                            Log.d("demo", "onResponse: " + body);
                            mListener.backContact();
                        }
                    }
                });

                System.out.println("Everything is entered correct");
            }

        });

    }

    public interface AddContactFragmentListener{
        void backContact();

    }
    AddContactFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddContactFragmentListener) context;
    }




}