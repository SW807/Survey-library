package dk.aau.cs.psylog.sensor.survey_library;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class NumberRangeDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle intent = this.getActivity().getIntent().getExtras();

        String text = intent.getString("text");
        int min = intent.getInt("min");
        int max = intent.getInt("max");
        String minLabel = intent.getString("minLabel");
        String maxLabel = intent.getString("maxLabel");


        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(text);



        // Create the AlertDialog object and return it
        return builder.create();
    }
}
