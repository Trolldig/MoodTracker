package de.matthias.klipfel.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import de.matthias.klipfel.moodtracker.database.MoodEntry;
import de.matthias.klipfel.moodtracker.database.MoodEntryViewModel;

import android.os.Bundle;
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
import com.anychart.graphics.vector.Stroke;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GraphActivity extends AppCompatActivity {

    private MoodEntryViewModel xmoodEntryViewModel;
    private TextView textMonth;
    private TextView textYear;
    private EditText editMonth;
    private EditText editYear;
    private AnyChartView anyChartView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        xmoodEntryViewModel = ViewModelProviders.of(this).get(MoodEntryViewModel.class);

        textMonth = findViewById(R.id.textMonth);
        textMonth.setText("Monat: ");
        textYear = findViewById(R.id.textYear);
        textYear.setText("Jahr: ");
        editMonth = findViewById(R.id.month_text_input);
        editYear = findViewById(R.id.year_text_input);
        setCurrentDate();
        editMonth.setText(String.valueOf(month));
        editYear.setText(String.valueOf(year));

        setMoodDataMonth(month);

        setGraph();
    }

    public void confirmChanges(View view) {
        month = Integer.parseInt(editMonth.getText().toString());
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

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(Number x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }

    /**
     * Converts a List of Mood Entry into a List of DataEntries and returns it
     *
     * @param moodEntries List of a month of MoodEntries
     * @return seriesList List for the line graph
     */
    private List<DataEntry> setGraphData(List<MoodEntry> moodEntries){
        if(!moodEntries.isEmpty()){
            List<DataEntry> seriesList = new ArrayList<>();
            for(int i = 0; i < moodEntries.size(); i++){
                MoodEntry moodEntry = moodEntries.get(i);
                seriesList.add(new CustomDataEntry(moodEntry.getDay(),
                        moodEntry.getPA(), moodEntry.getNA()));
            }
            return seriesList;
        }
        return null;
    }

    private void setCurrentDate(){
        //get current date
        Date date = new Date();
        //formats date as described
        DateFormat monthFormat = new SimpleDateFormat("MM");
        DateFormat yearFormat = new SimpleDateFormat("yyyy");

        month = Integer.parseInt(monthFormat.format(date));
        year = Integer.parseInt(yearFormat.format(date));
    }

    private void setMoodDataMonth(int month){
        moodData = getListOfMoodEntriesMonth(month);
    }

    private List<MoodEntry> getListOfMoodEntriesMonth (int month){
        return xmoodEntryViewModel.getEntriesMonth(month);
    }

    private void setGraph(){

        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

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

        setMoodDataMonth(month);

        seriesData = setGraphData(moodData);

        set = Set.instantiate();
        set.data(seriesData);
        series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

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
}
