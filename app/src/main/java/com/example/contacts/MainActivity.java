package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, AddContactFragment.AddContactFragmentListener {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new MainFragment())
                .commit();
    }

    @Override
    public void gotoAddContact() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddContactFragment())
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void sendContact(Contact contact) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DetailsFragment.newInstance(contact))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void backContact() {
        getSupportFragmentManager().popBackStack();

    }


}