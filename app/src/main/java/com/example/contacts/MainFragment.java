package com.example.contacts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Contact> contacts = new ArrayList<>();


    private static final String ARG_CONTACT = "param";

    private Contact mContact;

    public MainFragment() {
    }


    public static MainFragment newInstance(Contact contact) {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.contacts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Request request = new Request.Builder().url("https://www.theappsdr.com/contacts/json").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        String body = response.body().string();
                        JSONObject json = new JSONObject(body);

                        JSONArray contactArray = json.getJSONArray("contacts");


                        for (int i=0; i<contactArray.length();i++){
                            JSONObject contactObject = contactArray.getJSONObject(i);

                            Contact contact = new Contact();
                            contact.setId(contactObject.getString("Cid"));
                            contact.setName(contactObject.getString("Name"));
                            contact.setEmail(contactObject.getString("Email"));
                            contact.setPhone(contactObject.getString("Phone"));
                            contact.setPhoneType(contactObject.getString("PhoneType"));

                            contacts.add(contact);

                        }

                        // Log.d("demo", "onResponse: "+contacts.get(0));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ListView listView = view.findViewById(R.id.listView);
                                ContactAdapter adapter;
                                adapter = new ContactAdapter(getActivity().getBaseContext(), R.layout.contact_details, contacts);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Contact contact = adapter.getItem(position);
                                        mListener.sendContact(contact);
                                       // adapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        Button add = view.findViewById(R.id.addBttn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddContact();
            }
        });

    }


    public interface MainFragmentListener{
        void gotoAddContact();
        void sendContact(Contact contact);

    }
    MainFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (MainFragmentListener) context;
    }

}
