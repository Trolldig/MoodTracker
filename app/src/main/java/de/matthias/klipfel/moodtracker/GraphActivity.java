package de.matthias.klipfel.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import de.matthias.klipfel.moodtracker.database.MoodEntry;
import de.matthias.klipfel.moodtracker.database.MoodEntryViewModel;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.ColoredFill;
import com.anychart.graphics.vector.Stroke;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class GraphActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private MoodEntryViewModel xmoodEntryViewModel;
    private AnyChartView anyChartView;
    private FloatingActionButton chooseDateButton;

    private int day;
    private int month;
    private int year;
    private List<MoodEntry> moodData;
    private List<DataEntry> seriesData;
    private Set set;
    private Cartesian cartesian;
    private Mapping series1Mapping;
    private Mapping series2Mapping;
    private Line series1;
    private Line series2;

    private boolean graphSet;


    private Calendar from;
    private Calendar to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        xmoodEntryViewModel = ViewModelProviders.of(this).get(MoodEntryViewModel.class);

        chooseDateButton = findViewById(R.id.choose_date_button);
        chooseDateButton.setOnClickListener(this::chooseDate);

        graphSet = false;

        setCurrentDate();


        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
    }

    @Override
    protected void onResume() {
        super.onResume();
        chooseDate(anyChartView);
    }

    private void chooseDate(View view) {
        setCurrentDate();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                year, // Initial year selection
                month, // Initial month selection
                day // Inital day selection
        );
        dpd.show(getFragmentManager(), null);
    }

    public void confirmChanges(View view) {
        Log.d("Confirm", "month: " + month);
        anyChartView = null;
        moodData = null;
        seriesData = null;
        set = null;
        cartesian = null;
        series1Mapping = null;
        series2Mapping = null;
        series1 = null;
        series2 = null;
        setMoodDataMonth(month);
        setGraph();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        from = Calendar.getInstance();
        from.set(Calendar.YEAR, year);
        from.set(Calendar.MONTH, monthOfYear);
        from.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        from.set(Calendar.HOUR, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        from.set(Calendar.MILLISECOND, 0);
        to = Calendar.getInstance();
        to.set(Calendar.YEAR, yearEnd);
        to.set(Calendar.MONTH, monthOfYearEnd);
        to.set(Calendar.DAY_OF_MONTH, dayOfMonthEnd);
        to.set(Calendar.HOUR, 0);
        to.set(Calendar.MINUTE, 0);
        to.set(Calendar.SECOND, 0);
        to.set(Calendar.MILLISECOND, 0);


        moodData = xmoodEntryViewModel.getEntriesMonth(from, to);
        LiveData<List<MoodEntry>> all = xmoodEntryViewModel.getAll();
        List<MoodEntry> fuckthisshit = all.getValue();
        if (!graphSet) {
            setGraph();
        } else {
            updateGraph();
        }

    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value1) {
            super(x, value);
            setValue("value1", value1);
        }
    }

    /**
     * Converts a List of Mood Entry into a List of DataEntries and returns it
     *
     * @param moodEntries List of a month of MoodEntries
     * @return seriesList List for the line graph
     */
    private List<DataEntry> setGraphData(List<MoodEntry> moodEntries) {
        if (moodEntries != null && !moodEntries.isEmpty()) {
            List<DataEntry> seriesList = new ArrayList<>();
            for (MoodEntry moodEntry : moodEntries) {
                String dateName = "" + moodEntry.getDate().get(Calendar.DAY_OF_MONTH) + "." +
                        (moodEntry.getDate().get(Calendar.MONTH) + 1);
                seriesList.add(new CustomDataEntry(dateName, moodEntry.getPA(), moodEntry.getNA()));
            }
            return seriesList;
        }
        return null;
    }

    private void setCurrentDate() {
        //get current date
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }


    private void setMoodDataMonth(int month) {
        //moodData = getListOfMoodEntriesMonth(month);
    }

    /*
    private List<MoodEntry> getListOfMoodEntriesMonth (int month){
        return xmoodEntryViewModel.getEntriesMonth(month);
    }*/

    private void setGraph() {

        graphSet = true;

        //Koordinatensystem soll line chart sein
        cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 10d, 20d);

        //Tap zeigt Linie mit X und Y Werten der Linien
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Stimmung");

        cartesian.yAxis(0).title("Affekt-Wert");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        cartesian.xAxis(0).title("Tag");

        seriesData = setGraphData(moodData);

        set = Set.instantiate();
        set.data(seriesData);
        series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        series2Mapping = set.mapAs("{ x: 'x', value: 'value1' }");

        series1 = cartesian.line(series1Mapping);
        series1.name("Positiv");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        series2 = cartesian.line(series2Mapping);
        series2.name("Negativ");
        series2.stroke("red");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        //Legende über der Chart
        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    private void updateGraph() {
        Handler handler = new Handler();
        Runnable runnable = () -> {
            seriesData = setGraphData(moodData);
            set.data(seriesData);
        };
        handler.post(runnable);
    }
}
