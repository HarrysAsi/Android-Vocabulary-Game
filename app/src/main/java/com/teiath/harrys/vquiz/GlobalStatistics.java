package com.teiath.harrys.vquiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class GlobalStatistics extends Fragment {

    public static final String MY_JSON_DATA_TAG = "MyJsonData";
    public static final String MY_DATA = "MyData";
    //Member variables
    private ListView mListView;
    private SwipeRefreshLayout mSwipeContainer;

    //Data from db;
    public ArrayList<ScoreObject> mScoresData = new ArrayList<>();
    final DatabaseGlobalData data = new DatabaseGlobalData();
    //Handling each fragment
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View topicView = inflater.inflate(R.layout.fragment_global_statistics, container, false);
        final SharedPreferences mPrefs = inflater.getContext().getSharedPreferences(MY_JSON_DATA_TAG, MODE_PRIVATE);
        //Connecting with listview
        mListView = topicView.findViewById(R.id.global_stats_list_view);
        mSwipeContainer = topicView.findViewById(R.id.swipeContainer);
        //Adapter for data
        final ArrayAdapter<ScoreObject> adapter = new GlobalStatsAdapter(getActivity(), data.getData());

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                String json = mPrefs.getString(MY_DATA, ""); // myObject - instance of MyObject
                refreshAdapter(json);
                mListView.setAdapter(adapter);
                mSwipeContainer.setRefreshing(false);
            }
        });
        //Colors for refreshing
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        adapter.clear();
        doRequestData();
        String json = mPrefs.getString(MY_DATA, "");
        refreshAdapter(json);
        mListView.setAdapter(adapter);

        return topicView;

    }

    //Function which gets the data from server and handle them from SharedPreferences
    public void refreshAdapter(String json) {
        //Request to database
        doRequestData();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                ScoreObject dt = new ScoreObject(
                        obj.getString("gender"), //gender
                        obj.getString("username"), //username
                        obj.getString("mode"), //mode
                        obj.getString("score")); //score
                data.setData(dt);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void doRequestData() {
        SharedPreferences mPrefs = this.getContext().getSharedPreferences(MY_JSON_DATA_TAG, MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = mPrefs.edit();
        final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String url = "http://79.167.39.214:80/AndroidVquiz/getall.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prefsEditor.putString(MY_DATA, response);
                prefsEditor.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("exception", "Exception " + error);
            }
        });
        queue.add(stringRequest);
    }
}