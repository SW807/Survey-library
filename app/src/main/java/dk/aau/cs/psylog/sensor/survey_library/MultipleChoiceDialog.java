package dk.aau.cs.psylog.sensor.survey_library;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MultipleChoiceDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle intent = this.getActivity().getIntent().getExtras();

        String text = intent.getString("text");
        CharSequence[] choices = intent.getStringArray("choices");
        Boolean singleSelection = intent.getBoolean("singleSelection");


        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(text);

        boolean[] answers = new boolean[choices.length];
        int checked =0;

        if(singleSelection) {
            builder.setSingleChoiceItems(choices,checked,  new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // database
                }
            });
        }
        else
        {
            builder.setMultiChoiceItems(choices,answers, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    // database
                }
            });
        }


        // Create the AlertDialog object and return it
        return builder.create();
    }
}
