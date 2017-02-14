package com.example.chrisconnolly.concussionapp;

import android.provider.BaseColumns;

public final class ConcussionContract {
    public ConcussionContract() {}

    public static abstract class ConcussionEntry implements BaseColumns {
        public static final String TABLE_NAME = "Concussion";
        public static final String COLUMN_NAME_DAY = "Day";
        public static final String COLUMN_NAME_HEADACHE = "Headache";
        public static final String COLUMN_NAME_NAUSEA = "Nausea";
        public static final String COLUMN_NAME_VOMITING = "Vomiting";
        public static final String COLUMN_NAME_BALANCE_PROBLEMS = "BalanceProblems";
        public static final String COLUMN_NAME_DIZZINESS = "Dizziness";
        public static final String COLUMN_NAME_VISUAL_PROBLEMS = "VisualProblems";
        public static final String COLUMN_NAME_FATIGUE = "Fatigue";
        public static final String COLUMN_NAME_SENSITIVITY_TO_LIGHT = "SensitivityToLight";
        public static final String COLUMN_NAME_SENSITIVITY_TO_NOISE = "SensitivityToNoise";
        public static final String COLUMN_NAME_NUMBNESS_TINGLING = "NumbnessTingling";
        public static final String COLUMN_NAME_FEELING_MENTALLY_FOGGY = "FeelingMentallyFoggy";
        public static final String COLUMN_NAME_FEELING_SLOWED_DOWN = "FeelingSlowedDown";
        public static final String COLUMN_NAME_DIFFICULTY_CONCENTRATING = "DifficultyConcentrating";
        public static final String COLUMN_NAME_DIFFICULTY_REMEMBERING = "DifficultyRemembering";
        public static final String COLUMN_NAME_IRRITABILITY = "Irritability";
        public static final String COLUMN_NAME_SADNESS = "Sadness";
        public static final String COLUMN_NAME_MORE_EMOTIONAL = "MoreEmotional";
        public static final String COLUMN_NAME_NERVOUSNESS = "Nervousness";
        public static final String COLUMN_NAME_DROWSINESS = "Drowsiness";
        public static final String COLUMN_NAME_SLEEPING_LESS_THAN_USUAL = "SleepingLessThanUsual";
        public static final String COLUMN_NAME_SLEEPING_MORE_THAN_USUAL = "SleepingMoreThanUsual";
        public static final String COLUMN_NAME_TROUBLE_FALLING_ASLEEP = "TroubleFallingAsleep";
    }
}
