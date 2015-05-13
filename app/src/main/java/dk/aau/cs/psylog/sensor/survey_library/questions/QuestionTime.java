package dk.aau.cs.psylog.sensor.survey_library.questions;

import android.util.Log;
import android.util.Pair;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionTime {
    public Pair<Integer, Integer> getStartTime() {
        return startTime;
    }

    private Pair<Integer, Integer> startTime;

    public int getInterval() {
        return interval;
    }

    public int getAllowedHourStart() {
        return allowedHourStart;
    }

    public int getAllowedHourEnd() {
        return allowedHourEnd;
    }

    private int interval;
    private int allowedHourStart;
    private int allowedHourEnd;

    public QuestionTime(String startTime, int interval, int allowedHourStart, int allowedHourEnd) {
        this.startTime = parseStartTime(startTime);
        this.interval = interval;
        this.allowedHourStart = allowedHourStart;
        this.allowedHourEnd = allowedHourEnd;
    }

    public Long calculateNextTime(Long time) {
        if (time==null) {
            Calendar now = Calendar.getInstance();
            time = now.getTimeInMillis();
        }
        //return time + 60*1000*60*24; //24 hours
        return time + 10*1000;

        // The following needs more testing - but we have no time :(((((((
        /*
        Calendar nextTime = Calendar.getInstance();

        if(time==null) {
            Calendar now = Calendar.getInstance();
            nextTime.set(Calendar.HOUR_OF_DAY, startTime.first);
            nextTime.set(Calendar.MINUTE, startTime.second);
            Log.d("TIMEX", startTime.first + ":" + startTime.second);
            Log.d("TIMEX", "nextTime wat " + nextTime.getTime().toString());
            Long diff = now.getTimeInMillis() - nextTime.getTimeInMillis();
            if (diff > 0){
                nextTime.add(Calendar.DATE, 1);
            }
            Log.d("TIMEX", "time==null " + nextTime.getTime().toString());
            return nextTime.getTimeInMillis();
        }

        nextTime.setTimeInMillis(time);

        Log.d("TIMEX", "nextTime = time " + nextTime.getTime().toString());

        checkInterval(nextTime);

        Log.d("TIMEX", "nextTime += interval " + nextTime.getTime().toString());

        int hour = nextTime.get(Calendar.HOUR_OF_DAY);
        if (allowedHourStart >= 0 && allowedHourEnd >= 0) {
            if (hour < allowedHourEnd && hour > allowedHourStart){
                checkInterval(nextTime);
            }
            else
            {
                nextTime.set(Calendar.HOUR_OF_DAY, startTime.first);
                nextTime.set(Calendar.MINUTE, startTime.second);
            }
        }

        Log.d("TIMEX", "nextTime = startTime " + nextTime.getTime().toString());

        return nextTime.getTimeInMillis();*/
    }

    private void checkInterval(Calendar nextTime) {
        if (interval > 0)
            nextTime.add(Calendar.MINUTE, interval);
        else if (interval <= 0)
            nextTime.add(Calendar.HOUR_OF_DAY, 24);
    }

    private Pair<Integer,Integer> parseStartTime(String date) throws IllegalArgumentException
    {
        Pattern pattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()){
            String[] time = date.split(":");
            return new Pair<>(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        }
        throw new IllegalArgumentException("Illegal time format of: "  + date + ". Should be HH:mm.");
    }
}
