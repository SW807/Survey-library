package dk.aau.cs.psylog.sensor.survey_library;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

public class MultipleChoiceQuestion extends Question{
    private String[] choices;
    private boolean singleSelection;

    public MultipleChoiceQuestion(String text, boolean singleSelection, String[] choices){
        super(text, QuestionType.MULTIPLE_CHOICE);
        this.singleSelection = singleSelection;
        this.choices = choices;
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
        Notification.Builder notificationBuilder = getNotifcationBuilder(context, android.R.drawable.ic_popup_reminder);
        PendingIntent pendingIntent = getPendingIntent(context, MultipleChoiceDialog.class);
        notificationBuilder.setContentIntent(pendingIntent);
        return notificationBuilder.build();
    }
}
