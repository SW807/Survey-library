package dk.aau.cs.psylog.sensor.survey_library.questions;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import dk.aau.cs.psylog.sensor.survey_library.Constants;
import dk.aau.cs.psylog.sensor.survey_library.scheduler.IScheduled;

public abstract class Question implements IScheduled {
    private int id;
    private String text;
    private QuestionType questionType;
    private QuestionTime questionTime;
    protected Long time;

    public Question(String text, QuestionType questionType, int id, QuestionTime questionTime) {
        this.text = text;
        this.questionType = questionType;
        this.id = id;
        this.questionTime = questionTime;
        updateTime();
    }

    @Override
    public Long getTime() {
        return time;
    }

    @Override
    public void updateTime() {
        time = questionTime.calculateNextTime(time);
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public String getText() {
        return text;
    }

    protected Notification.Builder getNotifcationBuilder(Context context, int icon) {
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(Constants.DIALOG_TITLE)
                .setContentText(text)
                .setAutoCancel(true)
                .setSmallIcon(icon);
        return builder;
    }

    protected PendingIntent getPendingIntent(Context context, Class<?> _class, Question q) {
        Intent resultIntent = new Intent(context, TransparentActivity.class);
        resultIntent.putExtra("class", _class.getName());
        q.addQuestionData(resultIntent);
        return PendingIntent.getActivity(context, id, resultIntent, PendingIntent.FLAG_ONE_SHOT); //requestCode is set to id so the pendingIntents are different when matching
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void addQuestionData(Intent i);
}
