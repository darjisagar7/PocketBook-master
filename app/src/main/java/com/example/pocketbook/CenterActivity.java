package com.example.pocketbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CenterActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView1;
    private ImageButton imageButton;
    private Button button;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private DatabaseReference familyRefrence;
    private ProgressDialog mProgress;
    private StorageReference storageReference;
    private Button upload;
    private static final int CAMERA_REQUEST_CODE=1;
    private ProgressDialog mProgress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        textView = findViewById(R.id.reason);
        textView1 = findViewById(R.id.amount);
        button = findViewById(R.id.submit_frorm_add);
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        familyRefrence = FirebaseDatabase.getInstance().getReference().child("Family");
        upload = findViewById(R.id.uploadbtn);
        storageReference = FirebaseStorage.getInstance().getReference();
        mProgress1 = new ProgressDialog(this);

        final String uid = mAuth.getCurrentUser().getUid();
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("position");
        mProgress = new ProgressDialog(this);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Updating your details");
                mProgress.show();
                final int amt = Integer.parseInt(textView1.getText().toString());
                final String purpose = textView.getText().toString();
                mRef.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String family = dataSnapshot.child("Family").getValue().toString();
                        long te = (long) dataSnapshot.child("total_expense").getValue();
                        te = te + amt;
                        mRef.child("Users").child(uid).child("total_expense").setValue(te);
                        mRef.child("Users").child(uid).child(message).child(date).child(purpose).setValue(amt);
                        familyRefrence.child(family).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                long total_expense = (long) dataSnapshot.child("total_expense").getValue();
                                total_expense = total_expense + amt;
                                familyRefrence.child(family).child("total_expense").setValue(total_expense);
                                mProgress.dismiss();
                                Intent mainIntent = new Intent(CenterActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();

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
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode==RESULT_OK){

            mProgress1.setMessage("Uploading Image...");
            mProgress1.show();

            Uri uri = data.getData();
            StorageReference filepath = storageReference.child("photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress1.dismiss();
                    Toast.makeText(CenterActivity.this,"Uploading successfully....",Toast.LENGTH_LONG).show();
                }
            });

        }

    }
}
