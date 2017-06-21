package com.example.kstedman.mathapplication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kstedman.mathapplication.R;
import com.example.kstedman.mathapplication.WolframConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.solveEquationButton) Button mSolveEquationButton;
    @Bind(R.id.aboutButton) Button mAboutButton;
    @Bind(R.id.createButton) Button mCreateButton;
    @Bind(R.id.contactButton) Button mContactButton;
    @Bind(R.id.signInButton) Button mSignInButton;
    @Bind(R.id.inputEquation) TextView mInputEquation;

    @Bind(R.id.saveSolutionButton) Button mSavedSolutionButton;

    private TextView mPageTitle;
    private String mathEquation;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

//    private SharedPreferences mSharedPreference;
//    private SharedPreferences.Editor mEditor;

    private DatabaseReference mSearchedTopicReference;

    private ValueEventListener mSearchTopicReferenceListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };
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

//        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreference.edit();


        mPageTitle = (TextView) findViewById(R.id.pageTitle);
        Typeface leixoFont = Typeface.createFromAsset(getAssets(), "fonts/leixo.ttf");

        mPageTitle.setTypeface(leixoFont);
        mSolveEquationButton.setOnClickListener(this);
        mAboutButton.setOnClickListener(this);
        mContactButton.setOnClickListener(this);
        mCreateButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        mSavedSolutionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.v("MainActivity Click", v.toString());
        if(v == mSolveEquationButton) {
            mathEquation = mInputEquation.getText().toString();
            if(mathEquation.equals("")){
                Log.d("MainActivity", "no question");
                Toast.makeText(MainActivity.this, "Ask A Question", Toast.LENGTH_SHORT).show();
            } else {
                saveTopicToFirebase(mathEquation);
                Intent intent = new Intent(MainActivity.this, SolveActivity.class);
                intent.putExtra("question", mathEquation);
                startActivity(intent);
            }

//            if(!(mathEquation).equals("")) {
//                addToSharedPreferences(mathEquation);
//            }
        }

        if(v == mAboutButton) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        if(v == mContactButton) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/keith-stedman"));
            startActivity(intent);
        }

        if(v == mCreateButton) {
            Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        }

        if(v == mSignInButton) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
