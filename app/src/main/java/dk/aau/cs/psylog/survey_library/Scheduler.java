package dk.aau.cs.psylog.survey_library;

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
    private ArrayList<Task> tasks;
    final Thread thread = new Thread(new RunTask(this));

    @Override
    public void onCreate() {
        tasks = new ArrayList<Task>();
        super.onCreate();
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

        Context ctx;
        private RunTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            while (true) {
                Task task = tasks.get(0);
                sleep();
                // Show question or other activity
                // Update task time
            }
        }

        private void sleep(){
            long diff = tasks.get(0).getNextTime() - new Date().getTime();
            if (diff > 0)
                SystemClock.sleep(diff);
        }
    }

    private class TaskComparator implements Comparator<Task> {
        @Override
        public int compare(Task lhs, Task rhs) {
            return lhs.getNextTime().compareTo(rhs.getNextTime());
        }
    }


}
