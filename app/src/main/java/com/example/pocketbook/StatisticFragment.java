package com.example.pocketbook;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends Fragment {


    public StatisticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_statistic, container, false);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long total = (long)dataSnapshot.child("total_expense").getValue();

                DecoView arcView =  view.findViewById(R.id.dynamicArcView);
                arcView.setHorizGravity(DecoView.HorizGravity.GRAVITY_HORIZONTAL_FILL);
                arcView.setVertGravity(DecoView.VertGravity.GRAVITY_VERTICAL_TOP);

                arcView.addSeries(new SeriesItem.Builder(Color.argb(255,218,218,218)).setRange(0, 100, 100).setInitialVisibility(false).setLineWidth(64f).build());

                SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255,64,196,0)).setRange(0,100,0).setInitialVisibility(false).setLineWidth(64f).build();
                SeriesItem seriesItem2 = new SeriesItem.Builder(Color.argb(255,10,10,255)).setRange(0,100,0).setInitialVisibility(false).setLineWidth(64f).build();
                SeriesItem seriesItem3 = new SeriesItem.Builder(Color.argb(255,255,15,15)).setRange(0,100,0).setInitialVisibility(false).setLineWidth(64f).build();
                int series1Index = arcView.addSeries(seriesItem1);
                int series2Index = arcView.addSeries(seriesItem2);
                int series3Index = arcView.addSeries(seriesItem3);

                arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true).setDelay(1000).setDuration(2000).build());
                arcView.addEvent(new DecoEvent.Builder(75).setIndex(series1Index).setDelay(2000).build());
                arcView.addEvent(new DecoEvent.Builder(50).setIndex(series2Index).setDelay(4000).build());
                arcView.addEvent(new DecoEvent.Builder(25).setIndex(series3Index).setDelay(6000).build());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
