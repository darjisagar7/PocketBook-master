package com.example.pocketbook;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FrameLayout mainFrame;
    private BubbleNavigationConstraintView navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        mainFrame = findViewById(R.id.mainFrame);
        navigationBar = findViewById(R.id.navigationBar);




        final StatisticFragment statisticFragment = new StatisticFragment();
        final ProfileFragment profileFragment = new ProfileFragment();
        final AddFragments addFragment = new AddFragments();
        final BotFragment botFragment = new BotFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,statisticFragment).commit();

        navigationBar.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position){
                    case 0:
                        statisticFragment.setArguments(getIntent().getExtras());
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,statisticFragment).commit();
                        break;

                    case 1:
                        profileFragment.setArguments(getIntent().getExtras());
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,profileFragment).commit();
                        break;

                    case 2:
                        addFragment.setArguments(getIntent().getExtras());
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,addFragment).commit();
                        break;

                    case 3:
                        botFragment.setArguments(getIntent().getExtras());
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,botFragment).commit();
                        break;


                }
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(LoginIntent);
            finish();
        }
    }
}
