package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, AddContactFragment.AddContactFragmentListener, DetailsFragment.DetailFragmentListener {

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
    public void gobackContact() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void sendDeleteContact(Contact contact) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, MainFragment.newInstance(contact))
                .addToBackStack(null)
                .commit();
    }
}