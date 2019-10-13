package com.example.pocketbook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FamilyActivity extends AppCompatActivity {

    private EditText famCode;
    private Button mJoin;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private EditText newFamCode;
    private Button mCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        famCode = findViewById(R.id.famCode);
        mJoin = findViewById(R.id.mJoin);
        newFamCode = findViewById(R.id.newFamCode);
        mCreate = findViewById(R.id.mCreate);

        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getCurrentUser().getUid();

        mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String jFamCode = famCode.getText().toString();
                if(TextUtils.isEmpty(jFamCode)){
                    Toast.makeText(FamilyActivity.this, "Please Enter a Family Code", Toast.LENGTH_LONG).show();
                } else {
                    mRef.child("Family").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(jFamCode)){
                                mRef.child("Family").child(jFamCode).child(uid).setValue("True");
                                mRef.child("Users").child(uid).child("Family").setValue(jFamCode);
                                Intent mainIntent = new Intent(FamilyActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            } else {
                                Toast.makeText(FamilyActivity.this, "Family does not exist. Please enter a valid code.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String famCode = newFamCode.getText().toString();

                if(TextUtils.isEmpty(famCode)){
                    Toast.makeText(FamilyActivity.this, "Please enter a family code.", Toast.LENGTH_SHORT).show();
                } else {
                    mRef.child("Family").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(famCode)) {
                                Toast.makeText(FamilyActivity.this, "Family Code Already Taken", Toast.LENGTH_LONG).show();
                            } else {
                                mRef.child("Family").child(famCode).child(uid).setValue("True");
                                mRef.child("Family").child(famCode).child("total_expense").setValue(0);
                                mRef.child("Users").child(uid).child("Family").setValue(famCode);
                                Intent mainIntent = new Intent(FamilyActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });




    }
}
