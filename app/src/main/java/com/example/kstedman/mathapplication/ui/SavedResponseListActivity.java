package com.example.kstedman.mathapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.kstedman.mathapplication.R;
import com.example.kstedman.mathapplication.WolframConstants;
import com.example.kstedman.mathapplication.adapters.FirebaseResponseViewHolder;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedResponseListActivity extends AppCompatActivity {
    private DatabaseReference mResponseRef;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mResponseRef = FirebaseDatabase.getInstance().getReference().child("questions");
        setUpFirebaseAdapter();
        Log.d("FirebaseReference", mResponseRef.toString());
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<WolframResponseModel, FirebaseResponseViewHolder> (WolframResponseModel.class, R.layout.solve_list_item, FirebaseResponseViewHolder.class, mResponseRef) {

            @Override
            protected void populateViewHolder(FirebaseResponseViewHolder viewHolder, WolframResponseModel model, int position) {
                Log.v("FirebaseViewHolderPOP", "Populate View Holder");
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
