package dk.aau.cs.psylog.sensor.survey_library;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

public class NumberRangeQuestion extends Question{
    private int min;
    private int max;
    private String minLabel;
    private String maxLabel;

    public NumberRangeQuestion(String text, QuestionType questionType, int min, int max, String minLabel, String maxLabel) {
        super(text, questionType);
        this.min = min;
        this.max = max;
        this.minLabel = minLabel;
        this.maxLabel = maxLabel;
    }

    @Override
    public Long getTime() {
        return null;
    }

    @Override
    public void updateTime() {

    }

    @Override
    public Notification getNotification(Context context) {
        return null;
    }

    @Override
    public void addQuestionData(Intent i) {

    }
}
