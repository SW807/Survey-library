package dk.aau.cs.psylog.sensor.survey_library;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MultipleChoiceDialog extends DialogFragment implements Parcelable{

    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.d("DIALOG", "Nu kører onCreate metoden.");
        show(getFragmentManager(),"TAG");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("DIALOG", "Nu kører onCreateDialog metoden.");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("WHAT UP")
                .setPositiveButton("SAHIT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("FUCK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
