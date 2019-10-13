package com.example.pocketbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder>{

    private List<Settings> settingsList;
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView headings;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            headings = itemView.findViewById(R.id.rViewText);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            mAuth = FirebaseAuth.getInstance();
        }
    }

    public SettingsAdapter(List<Settings> settingsList, Context context) {
        this.settingsList = settingsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_row, viewGroup, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final Settings settings = settingsList.get(i);
        myViewHolder.headings.setText(settings.getHeading());
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getCurrentUser().getUid();

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "You Clicked"+settings.getHeading(), Toast.LENGTH_SHORT).show();
                String action = settings.getHeading();
                if(action == "View your Spending"){
                    mRef.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalExpense = (long) dataSnapshot.child("total_expense").getValue();
                            Toast.makeText(context, ""+totalExpense, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else if(action == "View your family spending"){
                    mRef.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String family = dataSnapshot.child("Family").getValue().toString();
                            mRef.child("Family").child(family).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    long amt = (long) dataSnapshot.child("total_expense").getValue();
                                    Toast.makeText(context, ""+amt, Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else if(action == "Log out"){
                    mAuth.signOut();
                    Intent mainIntent = new Intent(context, LoginActivity.class);
                    context.startActivity(mainIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return settingsList.size();
    }
}

