package dk.aau.cs.psylog.sensor.survey_library;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import dk.aau.cs.psylog.survey_library.R;

public class NumberRangeDialog extends DialogFragment {
    private int answer = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle intent = this.getActivity().getIntent().getExtras();

        String text = intent.getString("text");
        final int min = intent.getInt("min");
        int max = intent.getInt("max");
        String minLabel = intent.getString("minLabel");
        String maxLabel = intent.getString("maxLabel");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(text);

        LayoutInflater layoutInflater = (LayoutInflater)this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.number_range_question_layout, null);

        setTextViews(minLabel, maxLabel, view);

        setSeekBar(min, max, view);

        final Context context = this.getActivity();
        setButton(view, (Activity) context);

        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void setButton(View view, final Activity context) {
        Button button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //answer
                //Gem i DB!!!
                ((Activity)context).finish();
            }
        });
    }

    private void setSeekBar(final int min, int max, View view) {
        SeekBar seekBar = (SeekBar)view.findViewById(R.id.seekBar);
        seekBar.setMax(max);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                answer = progress+min;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setTextViews(String minLabel, String maxLabel, View view) {
        TextView minLabelView = (TextView)view.findViewById(R.id.minLabel);
        minLabelView.setText(minLabel);
        TextView maxLabelView = (TextView)view.findViewById(R.id.maxLabel);
        maxLabelView.setText(maxLabel);
    }
}