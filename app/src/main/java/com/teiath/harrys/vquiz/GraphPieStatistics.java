package com.teiath.harrys.vquiz;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.android.volley.VolleyLog.TAG;


public class GraphPieStatistics extends Fragment {
    //Tags
    public static final String MODES_TAG = "Modes";
    public static final String BEGINNER_MODE_TAG = "Beginner";
    public static final String INTERMEDIATE_MODE_TAG = "Intermediate";
    public static final String EXPERT_MODE_TAG = "Expert";
    //Member variables
    private PieChart mPieChart;
    private SwipeRefreshLayout mSwipeContainer;


    //Handling each fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View topicView = inflater.inflate(R.layout.fragment_graph_pie, container, false);

        mPieChart = (PieChart) topicView.findViewById(R.id.chart_pie);
        mSwipeContainer = (SwipeRefreshLayout) topicView.findViewById(R.id.swipe_container_graph_pie);

        mPieChart.setRotationEnabled(false);
        mPieChart.setHoleRadius(0);
        mPieChart.setTransparentCircleAlpha(0);
        mPieChart.setContentDescription("1312");

        //Refresh Listenser
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializePieChart();
                mSwipeContainer.setRefreshing(false);
            }
        });
        //Colors for refreshing
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        initializePieChart();

        return topicView;
    }

    private void initializePieChart(){
        //url php paths - get the data from the database
        String urlBeginner = "http://79.167.39.214:80/AndroidVquiz/getBeginners.php";
        String urlInter = "http://79.167.39.214:80/AndroidVquiz/getIntermediates.php";
        String urlExpert = "http://79.167.39.214:80/AndroidVquiz/getExperts.php";
        doRequestData(urlBeginner, BEGINNER_MODE_TAG);
        doRequestData(urlInter, INTERMEDIATE_MODE_TAG);
        doRequestData(urlExpert, EXPERT_MODE_TAG);

        //Get results from shared preferences
        final SharedPreferences mPrefs = getContext().getSharedPreferences(MODES_TAG, MODE_PRIVATE);
        String beginnersNumb = mPrefs.getString(BEGINNER_MODE_TAG, "0");
        String intermediatesNumb = mPrefs.getString(INTERMEDIATE_MODE_TAG, "0");
        String expertsNumb = mPrefs.getString(EXPERT_MODE_TAG, "0");

        ArrayList<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(Integer.parseInt(beginnersNumb),"Beginner"));
        data.add(new PieEntry(Integer.parseInt(intermediatesNumb),"Intermediate"));
        data.add(new PieEntry(Integer.parseInt(expertsNumb),"Expert"));
        PieDataSet pieDataSet = new PieDataSet(data,"");
        //pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);

        //Colors for pie chart
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = mPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setTextSize(15);

        //Create pie data object
        PieData pieData = new PieData(pieDataSet);
        mPieChart.setData(pieData);
        mPieChart.invalidate();
    }

    public void doRequestData(String url,final String tag) {
        SharedPreferences mPrefs = this.getContext().getSharedPreferences(MODES_TAG, MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = mPrefs.edit();
        final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prefsEditor.putString(tag, response);
                prefsEditor.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        });
        queue.add(stringRequest);
    }
}