package com.example.kstedman.mathapplication.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.kstedman.mathapplication.models.WolframPushModel;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.example.kstedman.mathapplication.util.ItemTouchHelperAdapter;
import com.example.kstedman.mathapplication.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FirebaseResponseListAdapter extends FirebaseRecyclerAdapter<WolframPushModel, FirebaseResponseViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private ChildEventListener mChildEventListener;
    private ArrayList<WolframPushModel>

    public FirebaseResponseListAdapter(Class<WolframPushModel> modelClass, int modelLayout, Class<FirebaseResponseViewHolder> veiwHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, veiwHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
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
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        getRef(position).removeValue();
    }
}
