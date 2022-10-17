package com.example.contacts;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private final OkHttpClient client = new OkHttpClient();

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

        ImageView trash = convertView.findViewById(R.id.trash);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormBody formBody = new FormBody.Builder()
                        .add("id", contact.getId())
                        .build();

                Request request = new Request.Builder()
                        .url("https://www.theappsdr.com/contact/json/delete")
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        System.out.println("You response is successful");
                        ResponseBody responseBody = response.body();
                        String body = responseBody.string();
                        Log.d("demo", "onResponse: " + body);

                    }
                });

            }
        });

        name.setText(contact.getName());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
        phoneType.setText(contact.getPhoneType());

        return convertView;
    }

}

