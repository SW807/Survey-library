package dk.aau.cs.psylog.survey_library;

import android.app.Notification;

public abstract class Task implements IScheduled{
    private Long time;

    @Override
    public Long getTime() {
        return time;
    }

    @Override
    public void updateTime() {
    }

    @Override
    public Notification getNotification() {
        return null;
    }
}
