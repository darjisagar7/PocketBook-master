package com.example.pocketbook;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private CircleImageView mProfileImage;
    private TextView mName;
    private TextView mFamily;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private Button mLogout;
    private RecyclerView mView;
    private SettingsAdapter settingsAdapter;
    private List<Settings> settingsList = new ArrayList<>();


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mProfileImage = view.findViewById(R.id.mProfileImage);
        mName = view.findViewById(R.id.mName);
        mFamily = view.findViewById(R.id.mFamily);

        //Configure Recycler View
        mView = view.findViewById(R.id.mView);
        settingsAdapter = new SettingsAdapter(settingsList, getActivity());
        mView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mView.setItemAnimator(new DefaultItemAnimator());
        mView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        mView.setAdapter(settingsAdapter);
        prepareSettingsData();




        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct != null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            mName.setText(personName);
            Picasso.get().load(personPhoto).into(mProfileImage);

            mRef.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String famCode = dataSnapshot.child("Family").getValue().toString();
                    mFamily.setText(famCode);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        settingsList = new ArrayList<>();

    }

    private void prepareSettingsData() {

        Settings settings = new Settings("View your Spending");
        settingsList.add(settings);

        settings = new Settings("View your family spending");
        settingsList.add(settings);

        settings = new Settings("Log out");
        settingsList.add(settings);

    }
}
