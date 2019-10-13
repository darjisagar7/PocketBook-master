package com.example.pocketbook;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class BotFragment extends Fragment implements AIListener {


    public BotFragment() {
        // Required empty public constructor
    }

    ImageButton listen;
    TextView output;
    AIService aiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bot, container, false);



        listen=  view.findViewById(R.id.listen);
        output=  view.findViewById(R.id.textView);

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aiService.startListening();
            }
        });

        int permission = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.RECORD_AUDIO);
        if(permission != PackageManager.PERMISSION_GRANTED)
        {
            makeRequest();
        }

        final AIConfiguration config = new AIConfiguration("da71e37216b54eef998512c3c41e6620",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(getContext(), config);

        aiService.setListener(this);


        return view;
    }



    private void makeRequest() {
        ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.RECORD_AUDIO},101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 101:{
                if(grantResults.length==0||grantResults[0]!=PackageManager.PERMISSION_GRANTED)
                {
                    Log.i("denied","permission is denied by the user");
                }
                else
                {
                    Log.i("granted","permission is granted");
                }
                return;
            }
        }
    }

    @Override
    public void onResult(AIResponse response) {

        final Result result = response.getResult();
//        Log.e("answer", "Action: " + result.getAction());
        final String speech = result.getFulfillment().getSpeech();
        Log.e("question","query "+result.getResolvedQuery());
        Log.e("answeeerrrrrrr", "Speech: " + speech);
        output.setText(result.getFulfillment().getSpeech().toString());
    }

    @Override
    public void onError(AIError error) {
        output.setText(error.toString());
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }


}