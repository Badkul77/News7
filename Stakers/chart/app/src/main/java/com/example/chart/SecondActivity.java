package com.example.chart;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity //implements OnChartGestureListener, OnChartValueSelectedListener//
 {

    private static final String TAG = "SecondActivity";
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        lineChart=findViewById(R.id.linechart);

//        lineChart.setOnChartGestureListener(SecondActivity.this);
   //     lineChart.setOnChartValueSelectedListener(SecondActivity.this);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        ArrayList<Entry> yvalue=new ArrayList<>();
        yvalue.add(new Entry(0,60f));
        yvalue.add(new Entry(1,50f));
        yvalue.add(new Entry(2,30f));
        yvalue.add(new Entry(3,40f));
        yvalue.add(new Entry(4,70f));
        yvalue.add(new Entry(5,80f));
        yvalue.add(new Entry(6,90f));

        LineDataSet set1=new LineDataSet(yvalue,"Data Set 1");

        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(set1);

        LineData data=new LineData(dataSets);

        lineChart.setData(data);
    }

}
