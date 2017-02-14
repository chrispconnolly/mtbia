package com.example.chrisconnolly.concussionapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends ActionBarActivity {
    //region Member variables
    ExpandableListAdapter _listAdapter;
    ExpandableListView _menuExpandableListView;
    List<String> _listDataHeader;
    HashMap<String, List<String>> _listDataChild;
    SlidingPaneLayout _slidingPaneLayout;
    Button _dateButton;
    int _year, _month, _date;
    String _today;
    ConcussionDataSource _concussionDataSource;
    Resources _resources;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _menuExpandableListView = (ExpandableListView) findViewById(R.id.menuExpandableListView);
        _resources = getResources();
        _slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingPaneLayout);
        _concussionDataSource = new ConcussionDataSource(this);
        _concussionDataSource.open();
        Calendar calendar = Calendar.getInstance();
        _year = calendar.get(Calendar.YEAR);
        _month = calendar.get(calendar.MONTH);
        _date = calendar.get(calendar.DATE);
        ((Button)findViewById(R.id.menuButton)).setText("< Menu");

        buildMenu();

        new CountDownTimer(5000,5000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                LinearLayout splashLayout = (LinearLayout)findViewById(R.id.splashLayout);
                if(splashLayout.getVisibility() == View.VISIBLE)
                    _slidingPaneLayout.openPane();
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings)
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        _concussionDataSource.close();
        super.onStop();
    }

    @Override
    public void onStart() {
        _concussionDataSource.open();
        super.onStart();
    }

    @Override
    public void onPause() {
        _concussionDataSource.close();
        super.onPause();
    }

    @Override
    public void onResume() {
        _concussionDataSource.open();
        super.onResume();
    }
    //endregion

    //region Events
    public void pickDate(View view)
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        _year = year;
                        _month = month;
                        _date = date;
                        _today = getDateString();

                        List<String> list = Arrays.asList(_resources.getStringArray(R.array.EnterSymptomsArray));
                        list = new ArrayList(list);  //The list return from asList is immutable.
                        list.add(0, "for: " + _today);
                        _listDataChild.put(_listDataHeader.get(0), list);
                        _menuExpandableListView.setAdapter(_listAdapter);
                        _menuExpandableListView.expandGroup(0);

                        Concussion concussion = _concussionDataSource.retrieveConcussion(_year, _month, _date);
                        if(concussion == null) {
                            _concussionDataSource.createConcussion(_year, _month, _date);
                            concussion = _concussionDataSource.retrieveConcussion(_year, _month, _date);
                        }
                        loadControls(concussion);
                    }
                }, _year, _month, _date);
        datePickerDialog.setTitle("Select Date.");
        datePickerDialog.show();
    }

    public void save(View view)
    {
        CheckBox checkBox = (CheckBox)view;
        int isChecked = checkBox.isChecked() ? 1 : 0;
        String dbFieldName = checkBox.getText().toString().replace(" ", "").replace("/", "");
        _concussionDataSource.updateConcussion(_year, _month, _date, dbFieldName, isChecked);
    }

    public void menu(View view){
        _slidingPaneLayout.openPane();
    }

    public void actionBarClick(MenuItem menuItem){
        for(int i=0; i<_menuExpandableListView.getCount(); i++)
            _menuExpandableListView.collapseGroup(i);
        switch(menuItem.getItemId()){
            case(R.id.action_edit) :
                _slidingPaneLayout.openPane();
                _menuExpandableListView.expandGroup(0);
                break;
            case(R.id.action_chart) :
                setLayoutVisibility("Chart");
                buildChartView();
                _slidingPaneLayout.closePane();
                break;
            case(R.id.action_table) :
                setLayoutVisibility("Table");
                buildTableView();
                _slidingPaneLayout.closePane();
                break;
            case(R.id.action_email) :
                emailSymptoms();
                break;
            case(R.id.action_settings) :
                _slidingPaneLayout.openPane();
                _menuExpandableListView.expandGroup(2);
                break;
            case(R.id.action_about) :
                setLayoutVisibility("About");
                _slidingPaneLayout.closePane();
                break;
        }
    }

    public void delete(View view){
        ImageButton deleteButton = (ImageButton)view;
        final String day = deleteButton.getTag().toString();
        AlertDialog deleteDialog = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Delete symptoms for " + day + "?")
                .setIcon(R.drawable.ic_action_discard)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String[] split = day.split("/");
                        int year = Integer.parseInt(split[2]);
                        int month = Integer.parseInt(split[0])-1;
                        int date = Integer.parseInt(split[1]);
                        Concussion concussion = _concussionDataSource.retrieveConcussion(
                                year, month, date);
                        _concussionDataSource.deleteConcussion(concussion);
                        buildTableView();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .show();
    }
    //endregion

    //region Utility Methods
    private void buildMenu()
    {
        _listDataHeader = Arrays.asList(_resources.getStringArray(R.array.MenuArray));
        _listDataChild = new HashMap<>();
        _listDataChild.put(_listDataHeader.get(0), Arrays.asList(new String[] {"SELECT DATE"}));
        _listDataChild.put(_listDataHeader.get(1), Arrays.asList(_resources.getStringArray(R.array.TrackSymptomsArray)));
        _listDataChild.put(_listDataHeader.get(2), Arrays.asList(_resources.getStringArray(R.array.SettingsArray)));
        _listAdapter = new ExpandableListAdapter(this, _listDataHeader, _listDataChild);
        _menuExpandableListView.setAdapter(_listAdapter);

        _menuExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String layoutName = (String)_listAdapter.getChild(groupPosition, childPosition);
                setLayoutVisibility(layoutName);

                if(layoutName.contains("for: "))
                    layoutName = "SELECT DATE";
                if(layoutName.contains("Daily Alerts"))
                    layoutName = "Alerts";
                switch (layoutName) {
                    case "Chart":
                        buildChartView();
                        _slidingPaneLayout.closePane();
                        break;
                    case "Table":
                        buildTableView();
                        _slidingPaneLayout.closePane();
                        break;
                    case "SELECT DATE":
                        pickDate(null);
                        break;
                    case "Alerts":
                        pickTime();
                        break;
                    case "Email Symptoms":
                        emailSymptoms();
                        break;
                    default :
                        _slidingPaneLayout.closePane();
                        break;
                }
                return false;
            }
        });
        if(Boolean.valueOf(Configuration.read(this, "dailyAlerts"))) {
            setAlertText();
            _menuExpandableListView.collapseGroup(2);
        }
    }

    private void pickTime()
    {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Configuration.write(MainActivity.this, "dailyAlerts", "true");
                Configuration.write(MainActivity.this, "hour", Integer.toString(selectedHour));
                Configuration.write(MainActivity.this, "minute", Integer.toString(selectedMinute));
                AlarmReceiver alarmReceiver = new AlarmReceiver();
                alarmReceiver.setAlarm(MainActivity.this);
                setAlertText();
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlarmReceiver alarmReceiver = new AlarmReceiver();
                        alarmReceiver.cancelAlarm(MainActivity.this);
                        Configuration.write(MainActivity.this, "dailyAlerts", "false");
                        resetAlertText();
                    }
                });

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private void setLayoutVisibility(String layoutName)
    {
        LinearLayout splashLayout = (LinearLayout)findViewById(R.id.splashLayout);
        LinearLayout physicalLayout = (LinearLayout)findViewById(R.id.physicalLayout);
        LinearLayout cognitiveLayout = (LinearLayout)findViewById(R.id.cognitiveLayout);
        LinearLayout emotionalLayout = (LinearLayout)findViewById(R.id.emotionalLayout);
        LinearLayout sleepLayout = (LinearLayout)findViewById(R.id.sleepLayout);
        LinearLayout chartLayout = (LinearLayout)findViewById(R.id.chartLayout);
        LinearLayout tableLayout = (LinearLayout)findViewById(R.id.tableLayout);
        LinearLayout aboutLayout = (LinearLayout)findViewById(R.id.aboutLayout);

        splashLayout.setVisibility(View.GONE);
        physicalLayout.setVisibility(View.GONE);
        cognitiveLayout.setVisibility(View.GONE);
        emotionalLayout.setVisibility(View.GONE);
        sleepLayout.setVisibility(View.GONE);
        tableLayout.setVisibility(View.GONE);
        chartLayout.setVisibility(View.GONE);
        aboutLayout.setVisibility(View.GONE);

        switch (layoutName) {
            case "Physical":
                physicalLayout.setVisibility(View.VISIBLE);
                break;
            case "Cognitive":
                cognitiveLayout.setVisibility(View.VISIBLE);
                break;
            case "Emotional":
                emotionalLayout.setVisibility(View.VISIBLE);
                break;
            case "Sleep":
                sleepLayout.setVisibility(View.VISIBLE);
                break;
            case "Chart":
                chartLayout.setVisibility(View.VISIBLE);
                break;
            case "Table":
                tableLayout.setVisibility(View.VISIBLE);
                break;
            case "About":
                aboutLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void loadControls(Concussion concussion)
    {
        setCheckBox(R.id.headacheCheckBox, concussion.getHeadache());
        setCheckBox(R.id.nauseaCheckBox, concussion.getNausea());
        setCheckBox(R.id.vomitingCheckBox, concussion.getVomiting());
        setCheckBox(R.id.balanceProblemsCheckBox, concussion.getBalanceProblems());
        setCheckBox(R.id.dizzinessCheckBox, concussion.getDizziness());
        setCheckBox(R.id.visualProblemsCheckBox, concussion.getVisualProblems());
        setCheckBox(R.id.fatigueCheckBox, concussion.getFatigue());
        setCheckBox(R.id.sensitivityToLightCheckBox, concussion.getSensitivityToLight());
        setCheckBox(R.id.sensitivityToNoiseCheckBox, concussion.getSensitivityToNoise());
        setCheckBox(R.id.numbnessTinglingCheckBox, concussion.getNumbnessTingling());
        setCheckBox(R.id.feelingMentallyFoggyCheckBox, concussion.getFeelingMentallyFoggy());
        setCheckBox(R.id.feelingSlowedDownCheckBox, concussion.getFeelingSlowedDown());
        setCheckBox(R.id.difficultyConcentratingCheckBox, concussion.getDifficultyConcentrating());
        setCheckBox(R.id.difficultyRememberingCheckBox, concussion.getDifficultyRemembering());
        setCheckBox(R.id.irritabilityCheckBox, concussion.getIrritability());
        setCheckBox(R.id.sadnessCheckBox, concussion.getSadness());
        setCheckBox(R.id.moreEmotionalCheckBox, concussion.getMoreEmotional());
        setCheckBox(R.id.nervousnessCheckBox, concussion.getNervousness());
        setCheckBox(R.id.drowsinessCheckBox, concussion.getDrowsiness());
        setCheckBox(R.id.sleepingLessThanUsualCheckBox, concussion.getSleepingLessThanUsual());
        setCheckBox(R.id.sleepingMoreThanUsualCheckBox, concussion.getSleepingMoreThanUsual());
        setCheckBox(R.id.troubleFallingAsleepCheckBox, concussion.getTroubleFallingAsleep());
    }

    private void setCheckBox(int id, int value){
        ((CheckBox) findViewById(id)).setChecked(value == 1);
    }

    private void setAlertText(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Configuration.read(this, "hour")));
        calendar.set(Calendar.MINUTE, Integer.parseInt(Configuration.read(this, "minute")));
        List<String> settingsArrayList = Arrays.asList(_resources.getStringArray(R.array.SettingsArray));
        settingsArrayList = new ArrayList(settingsArrayList);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        settingsArrayList.set(0, "Daily Alerts: " + simpleDateFormat.format(calendar.getTime()));
        _listDataChild.put(_listDataHeader.get(2), settingsArrayList);
        _menuExpandableListView.setAdapter(_listAdapter);
        _menuExpandableListView.expandGroup(2);
    }

    private void resetAlertText(){
        _listDataChild.put(_listDataHeader.get(2), Arrays.asList(_resources.getStringArray(R.array.SettingsArray)));
        _menuExpandableListView.setAdapter(_listAdapter);
        _menuExpandableListView.expandGroup(2);
    }

    private String getDateString(){
        return _month+1 + "/" + _date + "/" + _year;
    }

    private String getTableDate(String day){
        String[] split = day.split("/");
        return Integer.parseInt(split[1])+1 + "/" + split[2] + "/" + split[0];
    }

    //This formatting is to work-around a chart bug: https://github.com/lecho/hellocharts-android/commit/d36ee53d7e33dfa968e30aeb0a1ae30c0765efb5
    private String getChartDate(String day){
        day = day.substring(5);
        String[] split = day.split("/");
        return (Integer.parseInt(split[0]) + 1) + "/" + Integer.parseInt(split[1]);
    }

    private void emailSymptoms(){
        String day = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Concussion Symptoms as of " + day);

        String emailBody = "";
        List<Concussion> concussions = _concussionDataSource.getAllConcussions("DESC");
        String newLine = System.getProperty("line.separator");
        for(int i=0; i<concussions.size(); i++) {
            Concussion concussion = concussions.get(i);
            emailBody += getTableDate(concussion.getDay()) + " - " + concussion.getNumberOfSymptoms() + " symptoms" + newLine;
            emailBody += concussion.getAllSymptoms() + System.getProperty("line.separator") + newLine + newLine;
        }
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        try {
            startActivity(Intent.createChooser(intent, "Email Symptoms"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void buildTableView(){
        ExpandableListView tableExpandableListView = (ExpandableListView) findViewById(R.id.tableExpandableListView);
        List<String> listDataHeader = new ArrayList<>();
        HashMap<String, List<String>> listDataChild = new HashMap<>();

        List<Concussion> concussions = _concussionDataSource.getAllConcussions("DESC");
        if(concussions.size() == 0)
            listDataHeader.add("There are no entries.");

        for(int i=0; i<concussions.size(); i++) {
            Concussion concussion = concussions.get(i);
            concussion.getDay();
            listDataHeader.add(getTableDate(concussion.getDay()) + " - " + concussion.getNumberOfSymptoms() + " symptoms");
            listDataChild.put(listDataHeader.get(i), Arrays.asList(concussion.getAllSymptoms()));
        }
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        tableExpandableListView.setAdapter(listAdapter);
    }

    public void buildChartView(){
        LineChartView lineChartView = (LineChartView) findViewById(R.id.lineChartView);
        TextView emptyTextView = (TextView) findViewById(R.id.emptyTextView);
        List<Concussion> concussions = _concussionDataSource.getAllConcussions("ASC");
        List<PointValue> values = new ArrayList<>();

        if(concussions.size() < 2) {
            emptyTextView.setVisibility(View.VISIBLE);
            lineChartView.setVisibility(View.GONE);
            return;
        }
        else{
            emptyTextView.setVisibility(View.GONE);
            lineChartView.setVisibility(View.VISIBLE);
        }

        Axis xAxis = new Axis();
        xAxis.setTextColor(Color.BLACK);
        xAxis.setHasTiltedLabels(true);
        List<AxisValue> xAxisValues = new ArrayList<>();

        for(int i=0; i<concussions.size(); i++) {
            Concussion concussion = concussions.get(i);
            values.add(new PointValue(i, concussion.getNumberOfSymptoms()));

            AxisValue axisValue = new AxisValue(i);
            axisValue.setLabel(getChartDate(concussion.getDay()));
            xAxisValues.add(i, axisValue);
        }

        Axis yAxis = new Axis();
        yAxis.setTextColor(Color.BLACK);
        List<AxisValue> yAxisValues = new ArrayList<>();
        for(int i=0; i<22; i++){
            AxisValue axisValue = new AxisValue(i);
            axisValue.setLabel(Integer.toString(i));
            yAxisValues.add(i, axisValue);
        }

        Line line = new Line(values).setColor(Color.rgb(0, 148, 138)).setCubic(true);
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        yAxis.setValues(yAxisValues);
        data.setAxisYLeft(yAxis);
        xAxis.setValues(xAxisValues);
        data.setAxisXBottom(xAxis);

        lineChartView.setLineChartData(data);
    }

    //endregion
}
