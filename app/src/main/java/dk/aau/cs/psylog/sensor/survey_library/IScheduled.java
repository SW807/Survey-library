package dk.aau.cs.psylog.sensor.survey_library;

import android.app.Notification;
import android.content.Context;

public interface IScheduled {

    public Long getTime();

    public void updateTime();

    public Notification getNotification(Context context);

    public int getId();
}
