package com.example.kstedman.mathapplication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kstedman.mathapplication.R;
import com.example.kstedman.mathapplication.WolframConstants;
import com.example.kstedman.mathapplication.adapters.WolframCustomAdapter;
import com.example.kstedman.mathapplication.adapters.WolframListAdapter;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.example.kstedman.mathapplication.services.WolframService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static java.security.AccessController.getContext;

public class SolveActivity extends AppCompatActivity {
    public static final String TAG = SolveActivity.class.getSimpleName();

//    private SharedPreferences mSharedPreferences;
//    private String mRecentTopic;
    private String mSearchedTopic;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.saveSolutionButton) Button mSaveSolution;

    private WolframListAdapter mAdapter;
    private WolframResponseModel mResponse = new WolframResponseModel();
    public ArrayList<WolframResponseModel> mResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);
        ButterKnife.bind(this);

        mSaveSolution = (Button) findViewById(R.id.saveSolutionButton);

        Intent intent = getIntent();
        String equation = intent.getStringExtra("question");

        getSolutions(equation, "Math");

        mSaveSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SolveActivity","Add to Firebase");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                DatabaseReference responseRef = FirebaseDatabase.getInstance().getReference(WolframConstants.FIREBASE_CHILD_QUESTIONS).child(uid);
                DatabaseReference pushRef = responseRef.push();
                String pushId = pushRef.getKey();
                mResponse.setPushId(pushId);
                pushRef.setValue(mResponse);

                responseRef.push().setValue(mResults);
            }
        });
    }

    private void getSolutions(String questionEquation, String questionTopic){
        final WolframService wolframService = new WolframService();

        wolframService.solveMathEquation(questionEquation, new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mResults = wolframService.processAnswer(response);

                SolveActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        mAdapter = new WolframListAdapter(getApplicationContext(), mResults);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManger = new LinearLayoutManager(SolveActivity.this);
                        mRecyclerView.setLayoutManager(layoutManger);
                        mRecyclerView.setHasFixedSize(true);

                    }
                });
            }
        });
    }
}
