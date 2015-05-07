package dk.aau.cs.psylog.sensor.survey_library;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import dk.aau.cs.psylog.module_lib.ISensor;

public class Scheduler implements ISensor {
    String TAG = "SurveyScheduler";
    private ArrayList<IScheduled> tasks;
    Thread thread;
    Context context;

    public Scheduler(Context context) {
        this.context = context;

        Log.d(TAG, "startSensor");
        tasks = new ArrayList();
        initialize();
        sortAfterTime();

        thread = new Thread(new RunTask(context));
        thread.start();

    }

    private void initialize() {

        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion("Her er det du skal svare på.", true, new String[]{"Option 1", "Option 2"},0);
        MultipleChoiceQuestion multipleChoiceQuestion2 = new MultipleChoiceQuestion("MULTI - Her er det du skal svare på.", false, new String[]{"Option 1", "Option 2", "Option 3", "Option 4"},1);
        PlainTextQuestion plaintext = new PlainTextQuestion("Skriv en bog",2);
        NumberRangeQuestion numberRangeQuestion = new NumberRangeQuestion("Hvor glad er du?", 1, 10, "Ked af det", "Rigtig glad",3);

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        multipleChoiceQuestion.setId(databaseHelper.addQuestion(multipleChoiceQuestion));
        multipleChoiceQuestion2.setId(databaseHelper.addQuestion(multipleChoiceQuestion2));
        plaintext.setId(databaseHelper.addQuestion(plaintext));
        numberRangeQuestion.setId(databaseHelper.addQuestion(numberRangeQuestion));

        tasks.addAll(databaseHelper.getQuestions());
    }

    public void add(IScheduled task) {
        tasks.add(task);
        sortAfterTime();
    }

    public void remove(IScheduled task) {
        tasks.remove(task);
        sortAfterTime();
    }


    private void sortAfterTime() {
        Collections.sort(this.tasks, new TaskComparator());
    }

    @Override
    public void startSensor() {
        Log.d(TAG, "startSensor");
        tasks = new ArrayList();
        initialize();
        sortAfterTime();

        thread = new Thread(new RunTask(context));
        thread.start();
    }

    @Override
    public void stopSensor() {
        thread.interrupt();
    }

    @Override
    public void sensorParameters(Intent intent) {

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
                NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                mNotifyMgr.notify(task.getId(), task.getNotification(context));


                //Update task
                task.updateTime();
                sortAfterTime();
            }
        }

        private void sleep() {
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
