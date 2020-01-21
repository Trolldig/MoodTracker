package de.matthias.klipfel.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import de.matthias.klipfel.moodtracker.database.MoodEntry;
import de.matthias.klipfel.moodtracker.database.MoodEntryViewModel;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;


public class GraphActivity extends AppCompatActivity {

    private MoodEntryViewModel xmoodEntryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        xmoodEntryViewModel = ViewModelProviders.of(this).get(MoodEntryViewModel.class);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        //Koordinatensystem soll line chart sein
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 10d, 20d);

        //Tap zeigt Linie mit X und Y Werten der Linien
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Trend of Sales of the Most Popular Products of ACME Corp.");

        cartesian.yAxis(0).title("Number of Bottles Sold (thousands)");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<MoodEntry> moodData = getListOfMoodEntriesMonth(1);
        MoodEntry moodEntry = moodData.get(0);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry(String.valueOf(moodEntry.getDay()),
                moodEntry.getPA(), moodEntry.getNA()));
        seriesData.add(new CustomDataEntry("1987", 7.1, 4.0));
        seriesData.add(new CustomDataEntry("1988", 8.5, 6.2));
        seriesData.add(new CustomDataEntry("1989", 9.2, 11.8));
        seriesData.add(new CustomDataEntry("1990", 10.1, 13.0));
        seriesData.add(new CustomDataEntry("1991", 11.6, 13.9));
        seriesData.add(new CustomDataEntry("1992", 16.4, 18.0));
        seriesData.add(new CustomDataEntry("1993", 18.0, 23.3));
        seriesData.add(new CustomDataEntry("1994",
                moodEntry.getPA(), moodEntry.getNA()));
        seriesData.add(new CustomDataEntry("1995", 12.0, 18.0));
        seriesData.add(new CustomDataEntry("1996", 3.2, 15.1));
        seriesData.add(new CustomDataEntry("1997", 4.1, 11.3));
        seriesData.add(new CustomDataEntry("1998", 6.3, 14.2));
        seriesData.add(new CustomDataEntry("1999", 9.4, 13.7));
        seriesData.add(new CustomDataEntry("2000", 11.5, 9.9));
        seriesData.add(new CustomDataEntry("2001", 13.5, 12.1));
        seriesData.add(new CustomDataEntry("2002", 14.8, 13.5));
        seriesData.add(new CustomDataEntry("2003", 16.6, 15.1));
        seriesData.add(new CustomDataEntry("2004", 18.1, 17.9));
        seriesData.add(new CustomDataEntry("2005", 17.0, 18.9));
        seriesData.add(new CustomDataEntry("2006", 16.6, 20.3));
        seriesData.add(new CustomDataEntry("2007", 14.1, 20.7));
        seriesData.add(new CustomDataEntry("2008", 15.7, 21.6));
        seriesData.add(new CustomDataEntry("2009", 12.0, 22.5));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Brandy");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Whiskey");
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

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }

    private List<MoodEntry> getListOfMoodEntriesMonth (int month){
        return xmoodEntryViewModel.getEntriesMonth(month);
    }
}
