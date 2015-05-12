package dk.aau.cs.psylog.sensor.survey_library.questions;

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
        Calendar prev = Calendar.getInstance();
        prev.setTimeInMillis(time);

        if (interval > 0)
            prev.add(Calendar.MINUTE, interval);

        int hour = prev.get(Calendar.HOUR_OF_DAY);
        if (!(allowedHourStart>0 || allowedHourEnd>0) && (hour < allowedHourStart || hour > allowedHourEnd)) {
            prev.set(Calendar.HOUR_OF_DAY, startTime.first);
            prev.set(Calendar.MINUTE, startTime.second);
        }

        return prev.getTimeInMillis();
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
