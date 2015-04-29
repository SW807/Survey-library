package dk.aau.cs.psylog.sensor.survey_library;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class NumberRangeQuestion extends Question {
    private int min;
    private int max;
    private String minLabel;
    private String maxLabel;
    private Long time;

    public NumberRangeQuestion(String text, int min, int max, String minLabel, String maxLabel) {
        super(text, QuestionType.NUMBER_RANGE);
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
        i.putExtra("min",min);
        i.putExtra("max",max);
        i.putExtra("minLabel",minLabel);
        i.putExtra("maxLabel",maxLabel);
        i.putExtra("text",text);

    }
}
