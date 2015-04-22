package dk.aau.cs.psylog.survey_library;

import android.app.Notification;

public abstract class Task implements IScheduled{

    @Override
    public Long getNextTime() {
        return null;
    }

    @Override
    public Notification getNotification() {
        return null;
    }
}
