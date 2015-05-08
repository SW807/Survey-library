package dk.aau.cs.psylog.sensor.survey_library.questions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dk.aau.cs.psylog.sensor.survey_library.Constants;
import dk.aau.cs.psylog.sensor.survey_library.DatabaseHelper;

public class MultipleChoiceDialog extends DialogFragment {

    boolean[] multiSelctionAnswers;
    int singleSelctionChoice;
    Boolean singleSelection;
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle intent = this.getActivity().getIntent().getExtras();

        String text = intent.getString(Constants.TEXT);
        CharSequence[] choices = intent.getStringArray(Constants.CHOICES);
        singleSelection = intent.getBoolean(Constants.SINGLE_SELECTION);
        id = intent.getInt(Constants.ID);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(text);

        multiSelctionAnswers = new boolean[choices.length];
        handleChoices(choices, singleSelection, builder);

        Button button = new Button(this.getActivity());
        handleButton(button);

        builder.setView(button);


        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void handleChoices(final CharSequence[] choices, Boolean singleSelection, AlertDialog.Builder builder) {
        int checked = 0;

        if (singleSelection) {
            builder.setSingleChoiceItems(choices, checked, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    singleSelctionChoice = which;
                }
            });
        } else {
            builder.setMultiChoiceItems(choices, null, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    multiSelctionAnswers[which] = isChecked;
                }
            });
        }
    }

    private void handleButton(Button button) {
        button.setText("OK");

        final Context context = this.getActivity();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

                if (singleSelection)
                    databaseHelper.addMultipleChoiceAnswer(id, singleSelctionChoice, true);
                else {
                    databaseHelper.addMultipleChoiceAnswer(id, multiSelctionAnswers, true);
                }

                ((Activity) context).finish();
            }
        });
    }
}
