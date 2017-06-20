package com.example.kstedman.mathapplication.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.parceler.Parcels;

import com.example.kstedman.mathapplication.R;
import com.example.kstedman.mathapplication.WolframConstants;
import com.example.kstedman.mathapplication.models.WolframPushModel;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.example.kstedman.mathapplication.ui.ResponseDetailActivity;
import com.example.kstedman.mathapplication.util.ItemTouchHelperViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class FirebaseResponseViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;
    public ImageView mResponseImageView;

    public FirebaseResponseViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindResponse(WolframPushModel model) {
        mResponseImageView = (ImageView) mView.findViewById(R.id.responseImageView);
        ImageView responseImage = (ImageView) mView.findViewById(R.id.solveImageView);
        TextView titleTextView = (TextView) mView.findViewById(R.id.solveTitleView);
        TextView valueTextView = (TextView) mView.findViewById(R.id.solveValueView);

//        Picasso.with(mContext).load(response.getImage()).resize(MAX_WIDTH,MAX_HEIGHT).centerCrop().into(responseImage);
//        JSONObject queryResultJSON = wolframJSON.getJSONObject("queryresult");
//        JSONArray responseArray = queryResultJSON.getJSONArray("pods");

        ArrayList<WolframResponseModel> responseArray = model.getResponseArray();
        titleTextView.setText(responseArray.get(0).getTitle());
        valueTextView.setText(responseArray.get(0).getValue());
    }

    @Override
    public void onItemSelected() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }

//    @Override
//    public void onClick(View view) {
//        final ArrayList<WolframResponseModel> responses = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(WolframConstants.FIREBASE_CHILD_SEARCHED_TOPIC);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot){
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Log.d("FirebaseViewHolderSnap", snapshot.getValue(WolframResponseModel.class).toString());
////                    responses.add(snapshot.getValue(WolframResponseModel.class));
//                }
//
//                int itemPosition = getLayoutPosition();
//
//                Intent intent = new Intent(mContext, ResponseDetailActivity.class);
//                intent.putExtra("position", itemPosition + "");
//                intent.putExtra("responses", Parcels.wrap(responses));
//
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("FireBase ViewHolder","Db error");
//            }
//        });
//    }
}
