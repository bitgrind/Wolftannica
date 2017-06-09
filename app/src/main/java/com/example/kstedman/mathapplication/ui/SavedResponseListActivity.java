package com.example.kstedman.mathapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.kstedman.mathapplication.R;
import com.example.kstedman.mathapplication.WolframConstants;
import com.example.kstedman.mathapplication.adapters.FirebaseResponseViewHolder;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedResponseListActivity extends AppCompatActivity {
    private DatabaseReference mResponseReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_response_detail);
        ButterKnife.bind(this);

        mResponseReference = FirebaseDatabase.getInstance().getReference(WolframConstants.FIREBASE_CHILD_SEARCHED_TOPIC);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<WolframResponseModel, FirebaseResponseViewHolder>(WolframResponseModel.class, R.layout.solve_list_item, FirebaseResponseViewHolder.class, mResponseReference){
            @Override
            protected void populateViewHolder(FirebaseResponseViewHolder viewHolder, WolframResponseModel model, int position) {
                viewHolder.bindResponse(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
