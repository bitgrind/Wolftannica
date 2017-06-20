package com.example.kstedman.mathapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.kstedman.mathapplication.models.WolframPushModel;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.example.kstedman.mathapplication.ui.ResponseDetailActivity;
import com.example.kstedman.mathapplication.util.ItemTouchHelperAdapter;
import com.example.kstedman.mathapplication.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseResponseListAdapter extends FirebaseRecyclerAdapter<WolframPushModel, FirebaseResponseViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private ChildEventListener mChildEventListener;
    private ArrayList<WolframPushModel> mResponses = new ArrayList<>();

    public FirebaseResponseListAdapter(Class<WolframPushModel> modelClass, int modelLayout, Class<FirebaseResponseViewHolder> veiwHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, veiwHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mResponses.add(dataSnapshot.getValue(WolframPushModel.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseResponseViewHolder viewHolder, WolframPushModel model, int position) {
        viewHolder.bindResponse(model);
        viewHolder.mResponseImageView.setOnTouchListener(new View.OnTouchListener() {
           @Override
            public boolean onTouch(View v, MotionEvent event) {
               if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                   mOnStartDragListener.onStartDrag(viewHolder);
               }
               return false;
           }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ResponseDetailActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("response", Parcels.wrap(mResponses));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mResponses, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mResponses.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for(WolframPushModel model: mResponses) {
            int index = mResponses.indexOf(model);
            DatabaseReference ref = getRef(index);
            model.setIndex(Integer.toString(index));
            ref.setValue(model);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }
}
