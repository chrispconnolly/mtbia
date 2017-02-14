package com.example.chrisconnolly.concussionapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.chrisconnolly.concussionapp.ConcussionContract.ConcussionEntry;

public class ConcussionDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Concussion.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ConcussionEntry.TABLE_NAME;
    //region SQL Create
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ConcussionEntry.TABLE_NAME + " (" +
                    ConcussionEntry.COLUMN_NAME_DAY + TEXT_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_HEADACHE + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_NAUSEA + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_VOMITING + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_BALANCE_PROBLEMS + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_DIZZINESS + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_VISUAL_PROBLEMS + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_FATIGUE + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_SENSITIVITY_TO_LIGHT + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_SENSITIVITY_TO_NOISE + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_NUMBNESS_TINGLING + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_FEELING_MENTALLY_FOGGY + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_FEELING_SLOWED_DOWN + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_DIFFICULTY_CONCENTRATING + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_DIFFICULTY_REMEMBERING + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_IRRITABILITY + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_SADNESS + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_MORE_EMOTIONAL + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_NERVOUSNESS + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_DROWSINESS + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_SLEEPING_LESS_THAN_USUAL + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_SLEEPING_MORE_THAN_USUAL + INTEGER_TYPE + COMMA_SEP +
                    ConcussionEntry.COLUMN_NAME_TROUBLE_FALLING_ASLEEP + INTEGER_TYPE +
                    " )";
    //endregion
    
    public ConcussionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
