package dk.aau.cs.psylog.sensor.survey_library;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

public class PlainTextQuestion extends Question{

    public PlainTextQuestion(String text, QuestionType questionType) {
        super(text, questionType);
    }

    @Override
    public void addQuestionData(Intent i) {

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
