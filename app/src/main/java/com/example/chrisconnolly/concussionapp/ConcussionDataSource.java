package com.example.chrisconnolly.concussionapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.chrisconnolly.concussionapp.ConcussionContract.ConcussionEntry;

public class ConcussionDataSource {
    private SQLiteDatabase database;
    private ConcussionDbHelper concussionDbHelper;
    //region Columns
    private String[] allColumns = {
        ConcussionEntry.COLUMN_NAME_DAY,
        ConcussionEntry.COLUMN_NAME_HEADACHE,
        ConcussionEntry.COLUMN_NAME_NAUSEA,
        ConcussionEntry.COLUMN_NAME_VOMITING,
        ConcussionEntry.COLUMN_NAME_BALANCE_PROBLEMS,
        ConcussionEntry.COLUMN_NAME_DIZZINESS,
        ConcussionEntry.COLUMN_NAME_VISUAL_PROBLEMS,
        ConcussionEntry.COLUMN_NAME_FATIGUE,
        ConcussionEntry.COLUMN_NAME_SENSITIVITY_TO_LIGHT,
        ConcussionEntry.COLUMN_NAME_SENSITIVITY_TO_NOISE,
        ConcussionEntry.COLUMN_NAME_NUMBNESS_TINGLING,
        ConcussionEntry.COLUMN_NAME_FEELING_MENTALLY_FOGGY,
        ConcussionEntry.COLUMN_NAME_FEELING_SLOWED_DOWN,
        ConcussionEntry.COLUMN_NAME_DIFFICULTY_CONCENTRATING,
        ConcussionEntry.COLUMN_NAME_DIFFICULTY_REMEMBERING,
        ConcussionEntry.COLUMN_NAME_IRRITABILITY,
        ConcussionEntry.COLUMN_NAME_SADNESS,
        ConcussionEntry.COLUMN_NAME_MORE_EMOTIONAL,
        ConcussionEntry.COLUMN_NAME_NERVOUSNESS,
        ConcussionEntry.COLUMN_NAME_DROWSINESS,
        ConcussionEntry.COLUMN_NAME_SLEEPING_LESS_THAN_USUAL,
        ConcussionEntry.COLUMN_NAME_SLEEPING_MORE_THAN_USUAL,
        ConcussionEntry.COLUMN_NAME_TROUBLE_FALLING_ASLEEP
    };
    //endregion

    public ConcussionDataSource(Context context) {
        concussionDbHelper = new ConcussionDbHelper(context);
    }

    public void open() throws SQLException {
        database = concussionDbHelper.getWritableDatabase();
    }

    public void close() {
        concussionDbHelper.close();
    }

    public void createConcussion(int year, int month, int date) {
        ContentValues values = new ContentValues();
        values.put(ConcussionEntry.COLUMN_NAME_DAY, dateToString(year, month, date));
        database.insert(ConcussionEntry.TABLE_NAME, null, values);
    }

    public void updateConcussion(Concussion concussion)
    {
        ContentValues values = new ContentValues();
        values.put(ConcussionEntry.COLUMN_NAME_HEADACHE, concussion.getHeadache());
        values.put(ConcussionEntry.COLUMN_NAME_VOMITING, concussion.getVomiting());
        values.put(ConcussionEntry.COLUMN_NAME_BALANCE_PROBLEMS, concussion.getBalanceProblems());
        values.put(ConcussionEntry.COLUMN_NAME_DIZZINESS, concussion.getDizziness());
        values.put(ConcussionEntry.COLUMN_NAME_VISUAL_PROBLEMS, concussion.getVisualProblems());
        values.put(ConcussionEntry.COLUMN_NAME_FATIGUE, concussion.getFatigue());
        values.put(ConcussionEntry.COLUMN_NAME_SENSITIVITY_TO_LIGHT, concussion.getSensitivityToLight());
        values.put(ConcussionEntry.COLUMN_NAME_SENSITIVITY_TO_NOISE, concussion.getSensitivityToNoise());
        values.put(ConcussionEntry.COLUMN_NAME_NUMBNESS_TINGLING, concussion.getNumbnessTingling());
        values.put(ConcussionEntry.COLUMN_NAME_FEELING_MENTALLY_FOGGY, concussion.getFeelingMentallyFoggy());
        values.put(ConcussionEntry.COLUMN_NAME_FEELING_SLOWED_DOWN, concussion.getFeelingSlowedDown());
        values.put(ConcussionEntry.COLUMN_NAME_DIFFICULTY_CONCENTRATING, concussion.getDifficultyConcentrating());
        values.put(ConcussionEntry.COLUMN_NAME_DIFFICULTY_REMEMBERING, concussion.getDifficultyRemembering());
        values.put(ConcussionEntry.COLUMN_NAME_IRRITABILITY, concussion.getIrritability());
        values.put(ConcussionEntry.COLUMN_NAME_SADNESS, concussion.getSadness());
        values.put(ConcussionEntry.COLUMN_NAME_MORE_EMOTIONAL, concussion.getMoreEmotional());
        values.put(ConcussionEntry.COLUMN_NAME_NERVOUSNESS, concussion.getNervousness());
        values.put(ConcussionEntry.COLUMN_NAME_DROWSINESS, concussion.getDrowsiness());
        values.put(ConcussionEntry.COLUMN_NAME_SLEEPING_LESS_THAN_USUAL, concussion.getSleepingLessThanUsual());
        values.put(ConcussionEntry.COLUMN_NAME_SLEEPING_MORE_THAN_USUAL, concussion.getSleepingMoreThanUsual());
        values.put(ConcussionEntry.COLUMN_NAME_TROUBLE_FALLING_ASLEEP, concussion.getTroubleFallingAsleep());
        values.put(ConcussionEntry.COLUMN_NAME_SADNESS, concussion.getSadness());
        String whereClause = "WHERE " + ConcussionEntry.COLUMN_NAME_DAY + " = '" + concussion.getDay() + "'";
        database.update(ConcussionEntry.TABLE_NAME, values, whereClause, null);
    }

    public void updateConcussion(int year, int month, int date, String field, int value)
    {
        ContentValues values = new ContentValues();
        values.put(field, value);
        String whereClause = ConcussionEntry.COLUMN_NAME_DAY + " = '" + dateToString(year, month, date) + "'";
        database.update(ConcussionEntry.TABLE_NAME, values, whereClause, null);
    }

    public Concussion retrieveConcussion(int year, int month, int date)
    {
        Cursor cursor = database.query(ConcussionEntry.TABLE_NAME,
                allColumns, ConcussionEntry.COLUMN_NAME_DAY + " = '" + dateToString(year, month, date) + "'", null,
                null, null, null);
        System.out.println(cursor.getCount() + "!");
        if(cursor.getCount() <= 0)
            return null;
        cursor.moveToFirst();
        Concussion concussion = cursorToConcussion(cursor);
        cursor.close();
        return concussion;
    }

    public void deleteConcussion(Concussion concussion) {
        String day = concussion.getDay();
        System.out.println("Comment deleted with day: " + day);
        database.delete(ConcussionEntry.TABLE_NAME, ConcussionEntry.COLUMN_NAME_DAY
                + " = '" + day + "'", null);
    }

    public List<Concussion> getAllConcussions(String order) {
        List<Concussion> concussions = new ArrayList<>();

        Cursor cursor = database.query(ConcussionEntry.TABLE_NAME,
                allColumns, null, null, null, null, ConcussionEntry.COLUMN_NAME_DAY + " " + order);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Concussion concussion = cursorToConcussion(cursor);
            concussions.add(concussion);
            cursor.moveToNext();
        }
        cursor.close();
        return concussions;
    }

    private Concussion cursorToConcussion(Cursor cursor) {
        String fieldName = ConcussionEntry.COLUMN_NAME_FEELING_SLOWED_DOWN;
        System.out.println(fieldName);
        int i = cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_FEELING_SLOWED_DOWN);
        System.out.println(i+"!");
        Concussion concussion = new Concussion();
        concussion.setDay(cursor.getString(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_DAY)));
        concussion.setHeadache(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_HEADACHE)));
        concussion.setNausea(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_NAUSEA)));
        concussion.setVomiting(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_VOMITING)));
        concussion.setBalanceProblems(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_BALANCE_PROBLEMS)));
        concussion.setDizziness(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_DIZZINESS)));
        concussion.setVisualProblems(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_VISUAL_PROBLEMS)));
        concussion.setFatigue(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_FATIGUE)));
        concussion.setSensitivityToLight(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_SENSITIVITY_TO_LIGHT)));
        concussion.setSensitivityToNoise(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_SENSITIVITY_TO_NOISE)));
        concussion.setNumbnessTingling(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_NUMBNESS_TINGLING)));
        concussion.setFeelingMentallyFoggy(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_FEELING_MENTALLY_FOGGY)));
        concussion.setFeelingSlowedDown(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_FEELING_SLOWED_DOWN)));
        concussion.setDifficultyConcentrating(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_DIFFICULTY_CONCENTRATING)));
        concussion.setDifficultyRemembering(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_DIFFICULTY_REMEMBERING)));
        concussion.setIrritability(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_IRRITABILITY)));
        concussion.setSadness(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_SADNESS)));
        concussion.setMoreEmotional(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_MORE_EMOTIONAL)));
        concussion.setNervousness(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_NERVOUSNESS)));
        concussion.setDrowsiness(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_DROWSINESS)));
        concussion.setSleepingLessThanUsual(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_SLEEPING_LESS_THAN_USUAL)));
        concussion.setSleepingMoreThanUsual(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_SLEEPING_MORE_THAN_USUAL)));
        concussion.setTroubleFallingAsleep(cursor.getInt(cursor.getColumnIndex(ConcussionEntry.COLUMN_NAME_TROUBLE_FALLING_ASLEEP)));
        return concussion;
    }

    private String dateToString(int year, int month, int date){
        return year + "/" + zeroPad(month) + "/" + zeroPad(date);
    }

    private String zeroPad(int in){
        String out = Integer.toString(in);
        if(out.length() < 2)
            out = "0" + out;
        return out;
    }
}
