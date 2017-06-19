package com.example.kstedman.mathapplication.adapters;

import android.content.Context;

import com.example.kstedman.mathapplication.models.WolframPushModel;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.example.kstedman.mathapplication.util.ItemTouchHelperAdapter;
import com.example.kstedman.mathapplication.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class FirebaseResponseListAdapter extends FirebaseRecyclerAdapter<WolframPushModel, FirebaseResponseViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseResponseListAdapter(Class<WolframPushModel> modelClass, int modelLayout, Class<FirebaseResponseViewHolder> veiwHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, veiwHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    protected void populateViewHolder(FirebaseResponseViewHolder viewHolder, WolframPushModel model, int position) {
        viewHolder.bindResponse(model);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
