package dk.aau.cs.psylog.survey_library;

import android.app.Notification;

public interface IScheduled {

    public Long getTime();

    public void updateTime();

    public Notification getNotification();
}
