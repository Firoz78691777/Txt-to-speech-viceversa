package com.store.texttospeechandviceversa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageButton speakBtn1,voiceBtn1;
    TextView opTxt;
    String text;
    final int REQUEST_CODE_TO_SPEECH_TO_TEXT=1;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speakBtn1 = findViewById(R.id.speakBtn);
        voiceBtn1 = findViewById(R.id.voiceBtn);
        opTxt = findViewById(R.id.txtOP);

        speakBtn1.setOnClickListener(v -> promptSpeectToText());
        voiceBtn1.setOnClickListener(v -> textToSpeechMethod());
    }

    public void promptSpeectToText(){
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"U can speak now");
        try {
            startActivityForResult(speechIntent,REQUEST_CODE_TO_SPEECH_TO_TEXT);
        }catch (ActivityNotFoundException e ){
            Toast.makeText(getApplicationContext(),"Speak again",Toast.LENGTH_SHORT).show();
        }
    }

    public void textToSpeechMethod(){
        textToSpeech  = new TextToSpeech(getApplicationContext(), status -> {
            if (status!=TextToSpeech.ERROR)  textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_TO_SPEECH_TO_TEXT:{
                if (resultCode==RESULT_OK && data!=null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    opTxt.setText(result.get(0));
                    text = opTxt.getText().toString();
                }
            }
        }
    }
}