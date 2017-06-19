package com.example.kstedman.mathapplication.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.kstedman.mathapplication.models.WolframPushModel;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.example.kstedman.mathapplication.ui.ResponseDetailFragment;

import java.util.ArrayList;

public class ResponsePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<WolframResponseModel> mReponse;

    public ResponsePagerAdapter(FragmentManager fm, ArrayList<WolframResponseModel> responses) {
        super(fm);
        mReponse = responses;
        Log.v("pagerAdapter", "page adapter loading");
    }

    @Override
    public Fragment getItem(int position) {
        return ResponseDetailFragment.newInstance(mReponse.get(position));
    }

    @Override
    public int getCount() {
        return mReponse.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mReponse.get(position).getTitle();
    }
}
