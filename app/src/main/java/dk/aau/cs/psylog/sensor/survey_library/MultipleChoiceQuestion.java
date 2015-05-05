package dk.aau.cs.psylog.sensor.survey_library;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class MultipleChoiceQuestion extends Question{
    private String[] choices;
    private boolean singleSelection;
    private Long time;

    public MultipleChoiceQuestion(String text, boolean singleSelection, String[] choices){
        super(text, QuestionType.MULTIPLE_CHOICE);
        this.singleSelection = singleSelection;
        this.choices = choices;
    }

    public boolean getSingleSelection()
    {
        return singleSelection;
    }

    public String[] getChoices()
    {
        return choices;
    }

    @Override
    public Long getTime() {
        return time;
    }

    @Override
    public void updateTime() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, 10);
        time = now.getTimeInMillis();
    }

    @Override
    public Notification getNotification(Context context) {
        Notification.Builder notificationBuilder = getNotifcationBuilder(context, android.R.drawable.ic_popup_reminder);
        PendingIntent pendingIntent = getPendingIntent(context, MultipleChoiceDialog.class, this);
        notificationBuilder.setContentIntent(pendingIntent);
        return notificationBuilder.build();
    }

    @Override
    public void addQuestionData(Intent i)
    {
        i.putExtra("choices",choices);
        i.putExtra("singleSelection",singleSelection);
        i.putExtra("text",getText());
    }
}
