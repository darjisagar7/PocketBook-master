package com.example.pocketbook;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragments extends Fragment {


    public AddFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_fragments, container, false);

        int images[] = {
                R.drawable.ic_bills_24dp,R.drawable.ic_card_travel_black_24dp,R.drawable.ic_shopping_cart_black_24dp,
                R.drawable.ic_fuel_black_24dp,R.drawable.ic_health_24dp,R.drawable.ic_entertainment_24dp,
                R.drawable.ic_restaurant_black_24dp,R.drawable.ic_shopping_basket_black_24dp,R.drawable.ic_emi_24dp
        };

        String names[] = {
                "Bills","Travel","Shopping","Fuels","Health","Entertainment","Restaurant","Groceries","EMI"
        };

        int color[] = {
                R.color.blue_active,R.color.blue_active,R.color.blue_active,
                R.color.blue_active,R.color.blue_active,R.color.blue_active,
                R.color.blue_active,R.color.blue_active,R.color.blue_active
        };

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter myAdapter =new MyAdapter(getContext(),images,names,color);
        recyclerView.setAdapter(myAdapter);
        return view;
    }

}
