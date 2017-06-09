package com.example.kstedman.mathapplication.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

public class WolframCustomAdapter extends ArrayAdapter {
    private Context mContext;
    private String mResponse[];
    private String mValue[];

    public WolframCustomAdapter(Context mContext, int resource, String[] mResponse, String[] mValue){
        super(mContext, resource);
        this.mContext = mContext;
        this.mResponse = mResponse;
        this.mValue = mValue;
    }

    @Override
    public Object getItem(int position) {
        String responseTitle = mResponse[position];
        String responseValue = mValue[position];

        return responseTitle;
    }

    @Override
    public int getCount() {
        return mResponse.length;
    }
}
