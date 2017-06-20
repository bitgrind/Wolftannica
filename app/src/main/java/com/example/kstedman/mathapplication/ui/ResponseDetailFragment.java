package com.example.kstedman.mathapplication.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kstedman.mathapplication.R;
import com.example.kstedman.mathapplication.WolframConstants;
import com.example.kstedman.mathapplication.models.WolframPushModel;
import com.example.kstedman.mathapplication.models.WolframResponseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResponseDetailFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.responseValueView) TextView mResponseValue;
    @Bind(R.id.responseTitleView) TextView mResponseTitle;
    @Bind(R.id.step1TitleText) TextView mResponseStep1Title;
    @Bind(R.id.step1ValueText) TextView mResponseStep1Value;
    @Bind(R.id.step2TitleText) TextView mResponseStep2Title;
    @Bind(R.id.step2ValueText) TextView mResponseStep2Value;
    @Bind(R.id.step3TitleText) TextView mResponseStep3Title;
    @Bind(R.id.step3ValueText) TextView mResponseStep3Value;
    @Bind(R.id.saveSolutionButton) Button mSaveSolutionButton;

    private WolframPushModel mResponse;
    private ArrayList<WolframResponseModel> mResponses;
    private int mPosition;

    public static ResponseDetailFragment newInstance(ArrayList<WolframResponseModel> responses, Integer position) {
        ResponseDetailFragment responseDetailFragment = new ResponseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(WolframConstants.EXTRA_KEY_RESPONSES, Parcels.wrap(responses));
        args.putInt(WolframConstants.EXTRA_KEY_POSITION, position);

        responseDetailFragment.setArguments(args);
        return responseDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("SolutionDetail", "Solution Detail onCreate");
        super.onCreate(savedInstanceState);
        mResponses = Parcels.unwrap(getArguments().getParcelable(WolframConstants.EXTRA_KEY_RESPONSES));
        mPosition = getArguments().getInt(WolframConstants.EXTRA_KEY_POSITION);
        mResponse = mResponse;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_response_detail, container, false);
        ButterKnife.bind(this, view);

        Log.v("detail frag", "this is where we set text");
//        mResponseValue.setText(responseArray.getValue());
//        mResponseTitle.setText(responseArray.getTitle());

        mSaveSolutionButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        if(v == mSaveSolutionButton){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            Log.d("SaveSolution", "Save Solution");
            DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference(WolframConstants.FIREBASE_CHILD_QUESTIONS).child(uid);
            DatabaseReference pushRef = questionRef.push();
            String pushId = pushRef.getKey();
            mResponse.setPushId(pushId);

            pushRef.setValue(mResponse);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
