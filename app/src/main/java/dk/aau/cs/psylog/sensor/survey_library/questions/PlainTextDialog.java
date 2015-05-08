package dk.aau.cs.psylog.sensor.survey_library.questions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dk.aau.cs.psylog.sensor.survey_library.Constants;
import dk.aau.cs.psylog.sensor.survey_library.DatabaseHelper;
import dk.aau.cs.psylog.survey_library.R;

public class PlainTextDialog extends DialogFragment {
    private int questionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle intent = this.getActivity().getIntent().getExtras();

        String text = intent.getString(Constants.TEXT);
        questionId = intent.getInt(Constants.QUESTION_ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(text);

        LayoutInflater layoutInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.plain_text_question_layout, null);

        final Context context = this.getActivity();
        setButton(view, (Activity) context);

        builder.setView(view);

        return builder.create();
    }

    private void setButton(final View view, final Activity context) {
        Button button = (Button) view.findViewById(R.id.plain_text_question_ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.addPlainTextQuestion(questionId, ((EditText)view.findViewById(R.id.plain_text_question_editText)).getText().toString(), true );
                ((Activity) context).finish();
            }
        });
    }

}
