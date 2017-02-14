package com.example.chrisconnolly.concussionapp;

public class Concussion {

    //region Member Declarations
    private String day;
    private int headache;
    private int nausea;
    private int vomiting;
    private int balanceProblems;
    private int dizziness;
    private int visualProblems;
    private int fatigue;
    private int sensitivityToLight;
    private int sensitivityToNoise;
    private int numbnessTingling;
    private int feelingMentallyFoggy;
    private int feelingSlowedDown ;
    private int difficultyConcentrating;
    private int difficultyRemembering;
    private int irritability;
    private int sadness;
    private int moreEmotional;
    private int nervousness;
    private int drowsiness;
    private int sleepingLessThanUsual;
    private int sleepingMoreThanUsual;
    private int troubleFallingAsleep;
    //endregion

    //region Getters and Setters
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getHeadache(){
        return headache;
    }

    public void setHeadache(int headache) {
        this.headache = headache;
    }

    public int getNausea(){
        return nausea;
    }

    public void setNausea(int nausea) {
        this.nausea = nausea;
    }
    public int getVomiting(){
        return vomiting;
    }

    public void setVomiting(int vomiting) {
        this.vomiting = vomiting;
    }

    public int getBalanceProblems(){
        return balanceProblems;
    }

    public void setBalanceProblems(int balanceProblems) {
        this.balanceProblems = balanceProblems;
    }
    public int getDizziness(){
        return dizziness;
    }

    public void setDizziness(int dizziness) {
        this.dizziness = dizziness;
    }
    public int getVisualProblems(){
        return visualProblems;
    }

    public void setVisualProblems(int visualProblems) {
        this.visualProblems = visualProblems;
    }

    public int getFatigue(){
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public int getSensitivityToLight(){
        return sensitivityToLight;
    }

    public void setSensitivityToLight(int sensitivityToLight) {
        this.sensitivityToLight = sensitivityToLight;
    }

    public int getSensitivityToNoise(){
        return sensitivityToNoise;
    }

    public void setSensitivityToNoise(int sensitivityToNoise) {
        this.sensitivityToNoise = sensitivityToNoise;
    }

    public int getNumbnessTingling(){
        return numbnessTingling;
    }

    public void setNumbnessTingling(int numbnessTingling) {
        this.numbnessTingling = numbnessTingling;
    }

    public int getFeelingMentallyFoggy(){
        return feelingMentallyFoggy;
    }

    public void setFeelingMentallyFoggy(int feelingMentallyFoggy) {
        this.feelingMentallyFoggy = feelingMentallyFoggy;
    }

    public int getFeelingSlowedDown(){
        return feelingSlowedDown;
    }

    public void setFeelingSlowedDown(int feelingSlowedDown) {
        this.feelingSlowedDown = feelingSlowedDown;
    }

    public int getDifficultyConcentrating(){
        return difficultyConcentrating;
    }

    public void setDifficultyConcentrating(int difficultyConcentrating) {
        this.difficultyConcentrating = difficultyConcentrating;
    }

    public int getDifficultyRemembering(){
        return difficultyRemembering;
    }

    public void setDifficultyRemembering(int difficultyRemembering) {
        this.difficultyRemembering = difficultyRemembering;
    }

    public int getIrritability(){
        return irritability;
    }

    public void setIrritability(int irritability) {
        this.irritability = irritability;
    }

    public int getSadness(){
        return sadness;
    }

    public void setSadness(int sadness) {
        this.sadness = sadness;
    }

    public int getMoreEmotional(){
        return moreEmotional;
    }

    public void setMoreEmotional(int moreEmotional) {
        this.moreEmotional = moreEmotional;
    }

    public int getNervousness(){
        return nervousness;
    }

    public void setNervousness(int nervousness) {
        this.nervousness = nervousness;
    }

    public int getDrowsiness(){
        return drowsiness;
    }

    public void setDrowsiness(int drowsiness) {
        this.drowsiness = drowsiness;
    }

    public int getSleepingLessThanUsual(){
        return sleepingLessThanUsual;
    }

    public void setSleepingLessThanUsual(int sleepingLessThanUsual) {
        this.sleepingLessThanUsual = sleepingLessThanUsual;
    }

    public int getSleepingMoreThanUsual(){
        return sleepingMoreThanUsual;
    }

    public void setSleepingMoreThanUsual(int sleepingMoreThanUsual) {
        this.sleepingMoreThanUsual = sleepingMoreThanUsual;
    }

    public int getTroubleFallingAsleep(){
        return troubleFallingAsleep;
    }

    public void setTroubleFallingAsleep(int troubleFallingAsleep) {
        this.troubleFallingAsleep = troubleFallingAsleep;
    }

    public int getNumberOfSymptoms(){
        int numberOfSymptoms  =
            headache +
            nausea +
            vomiting +
            balanceProblems +
            dizziness +
            visualProblems +
            fatigue +
            sensitivityToLight +
            sensitivityToNoise +
            numbnessTingling +
            feelingMentallyFoggy +
            feelingSlowedDown  +
            difficultyConcentrating +
            difficultyRemembering +
            irritability +
            sadness +
            moreEmotional +
            nervousness +
            drowsiness +
            sleepingLessThanUsual +
            sleepingMoreThanUsual +
            troubleFallingAsleep;
        return numberOfSymptoms;
    }

    public String getAllSymptoms(){
        String allSymptoms = "";
        allSymptoms += intToBool(headache) ? "headache, " : "";
        allSymptoms += intToBool(vomiting) ? "vomiting, " : "";
        allSymptoms += intToBool(balanceProblems) ? "balance problems, " : "";
        allSymptoms += intToBool(dizziness) ? "dizziness, " : "";
        allSymptoms += intToBool(visualProblems) ? "visual problems, " : "";
        allSymptoms += intToBool(fatigue) ? "fatigue, " : "";
        allSymptoms += intToBool(sensitivityToLight) ? "sensitivity to light, " : "";
        allSymptoms += intToBool(sensitivityToNoise) ? "sensitivity to noise, " : "";
        allSymptoms += intToBool(numbnessTingling) ? "numbness/tingling, " : "";
        allSymptoms += intToBool(feelingMentallyFoggy) ? "feeling mentally foggy, " : "";
        allSymptoms += intToBool(feelingSlowedDown) ? "feeling slowed down, " : "";
        allSymptoms += intToBool(difficultyConcentrating) ? "difficulty concentrating, " : "";
        allSymptoms += intToBool(difficultyRemembering) ? "difficulty remembering, " : "";
        allSymptoms += intToBool(irritability) ? "irritability, " : "";
        allSymptoms += intToBool(sadness) ? "sadness, " : "";
        allSymptoms += intToBool(moreEmotional) ? "more emotional, " : "";
        allSymptoms += intToBool(nervousness) ? "nervousness, " : "";
        allSymptoms += intToBool(drowsiness) ? "drowsiness, " : "";
        allSymptoms += intToBool(sleepingLessThanUsual) ? "sleeping less than usual, " : "";
        allSymptoms += intToBool(sleepingMoreThanUsual) ? "sleeping more than usual, " : "";
        allSymptoms += intToBool(troubleFallingAsleep) ? "trouble falling asleep, " : "";
        return allSymptoms;
    }
    //endregion

    private boolean intToBool(int in){
        return in == 1 ? true : false;
    }

    @Override
    public String toString() {
        return day;
    }
}

