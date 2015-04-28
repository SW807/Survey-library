package dk.aau.cs.psylog.sensor.survey_library;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Scheduler extends Service {
    private ArrayList<IScheduled> tasks;
    final Thread thread = new Thread(new RunTask(this));

    @Override
    public void onCreate() {
        tasks = new ArrayList();
        initialize();
        sortAfterTime();
        super.onCreate();
    }

    private void initialize()
    {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion("Her er det du skal svare på.", true, new String[]{"Option 1", "Option 2"});

        tasks.add(multipleChoiceQuestion);
    }

    public void add(IScheduled task){
        tasks.add(task);
        sortAfterTime();
    }

    public void remove(IScheduled task){
        tasks.remove(task);
        sortAfterTime();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread.start();
        return START_NOT_STICKY;
    }

    private void sortAfterTime(){
        Collections.sort(this.tasks, new TaskComparator());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class RunTask implements Runnable {

        Context context;
        private RunTask(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            while (true) {
                sleep();
                IScheduled task = tasks.get(0);

                // Show question or other activity
                NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(1, task.getNotification(context));


                //Update task
                task.updateTime();
                sortAfterTime();
            }
        }

        private void sleep(){
            Long diff = tasks.get(0).getTime() - new Date().getTime();
            if (diff > 0)
                SystemClock.sleep(diff);
        }
    }

    private class TaskComparator implements Comparator<IScheduled> {
        @Override
        public int compare(IScheduled lhs, IScheduled rhs) {
            return lhs.getTime().compareTo(rhs.getTime());
        }
    }


}
