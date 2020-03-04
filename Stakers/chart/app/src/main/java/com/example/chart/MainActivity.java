package com.example.chart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
      PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pieChart=findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setExtraOffsets(5,10,5,5);

        //friction cofficient is to rotate the circle as speed you want
        pieChart.setDragDecelerationFrictionCoef(0.95f);

        //it will look like piechart when it is false
        pieChart.setDrawHoleEnabled(false);

        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);



        //to add value to the chart

        ArrayList<PieEntry> yvalue=new ArrayList<>();


        yvalue.add(new PieEntry(34f,"Bangladesh"));
        yvalue.add(new PieEntry(23f,"Usa"));
        yvalue.add(new PieEntry(14f,"UK"));
        yvalue.add(new PieEntry(35f,"India"));
        yvalue.add(new PieEntry(40f,"Russia"));
        yvalue.add(new PieEntry(23f,"japan"));

        Description description=new Description();

        description.setText("Piechart,Countries");
        description.setTextSize(15);
        pieChart.setDescription(description);

         pieChart.animateY(1000,Easing.EaseInOutCubic);


        PieDataSet dataSet=new PieDataSet(yvalue,"Countries");
        //it is to set the space between to slice in pie chart
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data=new PieData(dataSet);

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

    }
}
