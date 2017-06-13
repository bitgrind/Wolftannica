package com.example.kstedman.mathapplication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

import android.graphics.Typeface;
import android.widget.TextView;

import com.example.kstedman.mathapplication.R;
import com.example.kstedman.mathapplication.WolframConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.solveEquationButton) Button mSolveEquationButton;
    @Bind(R.id.aboutButton) Button mAboutButton;
    @Bind(R.id.convertButton) Button mConvertButton;
    @Bind(R.id.contactButton) Button mContactButton;
    @Bind(R.id.solveButton) Button mSolveButton;
    @Bind(R.id.inputEquation) TextView mInputEquation;

    @Bind(R.id.saveSolutionButton) Button mSavedSolutionButton;

    private TextView mPageTitle;

//    private SharedPreferences mSharedPreference;
//    private SharedPreferences.Editor mEditor;

    private DatabaseReference mSearchedTopicReference;

    private ValueEventListener mSearchTopicReferenceListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedTopicReference = FirebaseDatabase.getInstance().getReference().child(WolframConstants.FIREBASE_CHILD_SEARCHED_TOPIC);

        mSearchTopicReferenceListener = mSearchedTopicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot topicSnapshot : dataSnapshot.getChildren()){
                    String equation = topicSnapshot.getValue().toString();
                    Log.d("TopicUpdated", equation);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("MainValueEventError", "onCancelled Triggered");
            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreference.edit();


        mPageTitle = (TextView) findViewById(R.id.pageTitle);
        Typeface leixoFont = Typeface.createFromAsset(getAssets(), "fonts/leixo.ttf");

        mPageTitle.setTypeface(leixoFont);
        mSolveEquationButton.setOnClickListener(this);
        mAboutButton.setOnClickListener(this);
        mContactButton.setOnClickListener(this);
        mConvertButton.setOnClickListener(this);
        mSolveButton.setOnClickListener(this);
        mSavedSolutionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.v("MainActivity Click", v.toString());
        Log.v("PrefTopicKey", WolframConstants.PREFERENCES_TOPIC_KEY);

        if(v == mSolveEquationButton) {
            String mathEquation = mInputEquation.getText().toString();

            saveTopicToFirebase(mathEquation);

//            if(!(mathEquation).equals("")) {
//                addToSharedPreferences(mathEquation);
//            }

            Intent intent = new Intent(MainActivity.this, SolveActivity.class);
            intent.putExtra("question", mathEquation);
            startActivity(intent);
        }

        if(v == mAboutButton) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        if(v == mContactButton) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/keith-stedman"));
            startActivity(intent);
        }

        if(v == mConvertButton) {
            Intent intent = new Intent(MainActivity.this, ConvertActivity.class);
            startActivity(intent);
        }

        if(v == mSolveButton) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }

        if(v == mSavedSolutionButton) {
            Intent intent = new Intent(MainActivity.this, SavedResponseListActivity.class);
            startActivity(intent);
        }
    }

    public void saveTopicToFirebase(String topic) {
//        mSearchedTopicReference.push().setValue(topic);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedTopicReference.removeEventListener(mSearchTopicReferenceListener);
    }
}
