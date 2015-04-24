package dk.aau.cs.psylog.sensor.survey_library;

import android.app.Notification;
import android.content.Context;

public class PlainTextQuestion extends Question{

    public PlainTextQuestion(String text, QuestionType questionType) {
        super(text, questionType);
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
}
