package dk.aau.cs.psylog.sensor.survey_library;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public abstract class Question implements IScheduled {
    private String text;
    private QuestionType questionType;
    private static final String QUESTION_TITLE = "Psylog spørgsmål:";

    public Question(String text, QuestionType questionType) {
        this.text = text;
        this.questionType = questionType;
    }

    protected Notification.Builder getNotifcationBuilder(Context context, int icon) {
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(QUESTION_TITLE)
                .setContentText(text)
                .setSmallIcon(icon);
        return builder;
    }

    protected PendingIntent getPendingIntent(Context context, Class<?> _class) {
        Intent resultIntent = new Intent(context, _class);
        return PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
    }
}
