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

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getMinLabel() {
        return minLabel;
    }

    public String getMaxLabel() {
        return maxLabel;
    }

    @Override
    public Long getTime() {
        return time;
    }

    @Override
    public void updateTime() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, 30);
        time = now.getTimeInMillis();
    }

    @Override
    public Notification getNotification(Context context) {
        Notification.Builder notificationBuilder = getNotifcationBuilder(context, android.R.drawable.ic_popup_reminder);
        PendingIntent pendingIntent = getPendingIntent(context, NumberRangeDialog.class, this);
        notificationBuilder.setContentIntent(pendingIntent);
        return notificationBuilder.build();
    }

    @Override
    public void addQuestionData(Intent i) {
        i.putExtra("min", min);
        i.putExtra("max", max);
        i.putExtra("minLabel", minLabel);
        i.putExtra("maxLabel", maxLabel);
        i.putExtra("text", getText());
    }
}
