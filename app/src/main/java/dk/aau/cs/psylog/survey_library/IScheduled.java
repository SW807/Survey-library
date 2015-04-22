package dk.aau.cs.psylog.survey_library;

import android.app.Notification;

public interface IScheduled {

    public Long getNextTime();

    public Notification getNotification();
}
