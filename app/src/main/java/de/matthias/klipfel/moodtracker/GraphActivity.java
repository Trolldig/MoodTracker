package de.matthias.klipfel.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        barChart = (BarChart) findViewById(R.id.bargraph);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0f,10));
        barEntries.add(new BarEntry(1f,30));
        barEntries.add(new BarEntry(2f,80));
        barEntries.add(new BarEntry(3f,60));
        barEntries.add(new BarEntry(4f,40));
        barEntries.add(new BarEntry(5f,50));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");
        theDates.add("August");
        theDates.add("September");

        BarData theData = new BarData(barDataSet);
        theData.setBarWidth(0.9f);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
    }
}
