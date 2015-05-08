package dk.aau.cs.psylog.sensor.survey_library.questions;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import dk.aau.cs.psylog.sensor.survey_library.Constants;
import dk.aau.cs.psylog.survey_library.R;


public class TransparentActivity extends Activity {

    static String TAG = "TransparentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);

        try {
            Bundle extras = getIntent().getExtras();
            String classname = extras.getString(Constants.CLASS);
            Class<?> DialogType = Class.forName(classname);


            DialogFragment dialog = (DialogFragment) DialogType.newInstance();
            dialog.show(getFragmentManager(), TAG);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transparent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
