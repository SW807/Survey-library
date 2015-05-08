package dk.aau.cs.psylog.sensor.survey_library.questions;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import dk.aau.cs.psylog.sensor.survey_library.Constants;

public class PlainTextQuestion extends Question {
    private Long time;

    public PlainTextQuestion(String text, int id) {
        super(text, QuestionType.PLAIN_TEXT, id);
    }

    @Override
    public void addQuestionData(Intent i) {
        i.putExtra(Constants.TEXT, getText());
        i.putExtra(Constants.ID, getId());
    }

    @Override
    public Long getTime() {
        return time;
    }

    @Override
    public void updateTime() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, 5);
        time = now.getTimeInMillis();
    }

    @Override
    public Notification getNotification(Context context) {
        Notification.Builder notificationBuilder = getNotifcationBuilder(context, android.R.drawable.ic_popup_reminder);
        PendingIntent pendingIntent = getPendingIntent(context, PlainTextDialog.class, this);
        notificationBuilder.setContentIntent(pendingIntent);
        return notificationBuilder.build();
    }
}
