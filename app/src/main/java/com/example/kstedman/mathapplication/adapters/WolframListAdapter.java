package com.example.kstedman.mathapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kstedman.mathapplication.R;
import com.example.kstedman.mathapplication.models.WolframPushModel;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.example.kstedman.mathapplication.ui.ResponseDetailActivity;
import com.example.kstedman.mathapplication.ui.ResponseDetailFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WolframListAdapter extends RecyclerView.Adapter<WolframListAdapter.WolframViewHolder> {
    private ArrayList<WolframResponseModel> mResponses = new ArrayList<>();
    private Context mContext;

    public WolframListAdapter(Context context, ArrayList<WolframResponseModel> responses) {
        mContext = context;
        mResponses = responses;
    }

    @Override
    public WolframListAdapter.WolframViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.solve_list_item, parent, false);
        WolframViewHolder viewHolder = new WolframViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WolframListAdapter.WolframViewHolder holder, int position) {
        holder.bindWolframResponse(mResponses.get(position));
    }

    @Override
    public int getItemCount() {
        return mResponses.size();
    }

    public class WolframViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.solveValueView) TextView mSolveValue;
        @Bind(R.id.solveTitleView) TextView mSolveTitle;
        @Bind(R.id.solveImageView) ImageView mSolveImage;

        private int mOrientation;

        private Context mContext;

        private void createDetailFragment(int position){
            ResponseDetailFragment detailFragment = ResponseDetailFragment.newInstance(mResponses, position);
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.responseDetailContainer, detailFragment);
            ft.commit();
        }

        public WolframViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            itemView.setOnClickListener(this);

            mOrientation = itemView.getResources().getConfiguration().orientation;
            if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(0);
            }
        }

        public void bindWolframResponse(WolframResponseModel responseModel) {
            mSolveValue.setText(responseModel.getValue());
            mSolveTitle.setText(responseModel.getTitle());
            Picasso.with(mContext).load(responseModel.getImage()).into(mSolveImage);
        }

        @Override
        public void onClick(View v) {
            Log.v("ListAdapter", "This is the onclick"+v);
//            int itemPosition = getLayoutPosition();
//            Log.v("ListAdapter", "This is the onclick position: "+itemPosition);
//            Intent intent = new Intent(mContext, ResponseDetailActivity.class);
//            intent.putExtra("positionNum", Integer.toString(itemPosition));
//            intent.putExtra("response", Parcels.wrap(mResponses));
//
//            mContext.startActivity(intent);
        }
    }

}
