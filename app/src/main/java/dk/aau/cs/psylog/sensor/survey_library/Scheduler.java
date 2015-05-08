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

        tasks = new ArrayList();
        initialize();
        sortAfterTime();

        thread = new Thread(new RunTask(context));
        thread.start();

    }

    private void initialize() {
        boolean initializeDB = false;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        if (initializeDB) {

            MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion("Her er et multiplechoice spørgsmål, vælg en", true, new String[]{"Valgmulighed 1", "Valgmulighed 2"},0);
            MultipleChoiceQuestion multipleChoiceQuestion2 = new MultipleChoiceQuestion("Her er et multiplechoice spørgsmål, vælg flere.", false, new String[]{"Valgmulighed 1", "Valgmulighed 2","Valgmulighed 3", "Valgmulighed 4"},1);
            PlainTextQuestion plaintext1 = new PlainTextQuestion("Tekst spørgsmål, eksempelvis dagbog", 2);
            NumberRangeQuestion numberRangeQuestion = new NumberRangeQuestion("Spørgsmål med skala", 1, 10, "Laveste mulighed", "Højeste mulighed",3);


            databaseHelper.addQuestion(multipleChoiceQuestion);
            databaseHelper.addQuestion(multipleChoiceQuestion2);
            databaseHelper.addQuestion(plaintext1);
            databaseHelper.addQuestion(numberRangeQuestion);
        }
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
                NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

                //Handles when the service is stopped from the Psylog SettingsActivity
                try {
                    sleep();
                } catch (InterruptedException e) {
                    mNotifyMgr.cancelAll();
                    return;
                }
                IScheduled task = tasks.get(0);

                mNotifyMgr.notify(task.getId(), task.getNotification(context));


                //Update task
                task.updateTime();
                sortAfterTime();
            }
        }

        private void sleep() throws InterruptedException {
            Long diff = tasks.get(0).getTime() - new Date().getTime();
            if (diff > 0)
                Thread.sleep(diff);
        }
    }

    private class TaskComparator implements Comparator<IScheduled> {
        @Override
        public int compare(IScheduled lhs, IScheduled rhs) {
            return lhs.getTime().compareTo(rhs.getTime());
        }
    }


}
