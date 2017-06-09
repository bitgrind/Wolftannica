package com.example.kstedman.mathapplication.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kstedman.mathapplication.R;
import com.example.kstedman.mathapplication.adapters.ResponsePagerAdapter;
import com.example.kstedman.mathapplication.models.WolframResponseModel;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResponseDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private ResponsePagerAdapter adapterViewPager;
    ArrayList<WolframResponseModel> mResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_detail);
        Log.v("ResponseDetail", "This is the Response Detail Activity");
        ButterKnife.bind(this);

        mResponses = Parcels.unwrap(getIntent().getParcelableExtra("response"));

        Log.d("mResponseParcels", mResponses.toString());

        int startingPosition = Integer.parseInt(getIntent().getStringExtra("positionNum"));

        Log.v("DetailActivityIntentNum", Integer.toString(startingPosition));

        adapterViewPager = new ResponsePagerAdapter(getSupportFragmentManager(), mResponses);

        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
