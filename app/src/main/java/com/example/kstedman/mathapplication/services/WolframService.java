package com.example.kstedman.mathapplication.services;

import android.util.Log;

import com.example.kstedman.mathapplication.WolframConstants;
import com.example.kstedman.mathapplication.models.WolframResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WolframService {

    public static void solveMathEquation(String questionEquation, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        //Build the URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(WolframConstants.WOLFRAM_URL).newBuilder();
        urlBuilder.addQueryParameter("appid", WolframConstants.WOLFRAM_KEY);
        urlBuilder.addQueryParameter("output", "json");
        urlBuilder.addQueryParameter("input", questionEquation);
        String url = urlBuilder.build().toString();

        //Build the Request
        Request request = new Request.Builder().url(url).build();

        //Make the Call
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<WolframResponseModel> processAnswer(Response response){
        ArrayList<WolframResponseModel> results = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            Log.d("wolfservice json: ", jsonData);
            if(response.isSuccessful()) {
                JSONObject wolframJSON = new JSONObject(jsonData);
                JSONObject queryResultJSON = wolframJSON.getJSONObject("queryresult");
                JSONArray responseArray = queryResultJSON.getJSONArray("pods");

                for(int i  = 0; i < responseArray.length(); i++) {
                    JSONObject resultJSON = responseArray.getJSONObject(i);
                    JSONArray subpodJSON = resultJSON.getJSONArray("subpods");

                    String responseTitle = resultJSON.getString("title");

                    for(int k = 0; k < subpodJSON.length(); k++) {
                        JSONObject subpodObj = subpodJSON.getJSONObject(0);
                        String responseValue = subpodObj.getString("plaintext");
                        JSONObject subpodImageJSON = subpodObj.getJSONObject("img");
                        String imageURL = subpodImageJSON.getString("src");

                        if(responseValue != null) {
                            WolframResponseModel responseObj = new WolframResponseModel(responseTitle, responseValue, imageURL);

                            results.add(responseObj);
                        }
                    }


                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
        return results;
    };
}
