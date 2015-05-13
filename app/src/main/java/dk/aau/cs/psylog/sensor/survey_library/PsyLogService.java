package dk.aau.cs.psylog.sensor.survey_library;

import android.util.Log;

import dk.aau.cs.psylog.module_lib.SensorService;
import dk.aau.cs.psylog.sensor.survey_library.scheduler.Scheduler;

public class PsyLogService extends SensorService {
    @Override
    public void setSensor() {
        Log.d("SurveryScheduler", "service-setSensor");
        sensor = new Scheduler(this);
    }
}
