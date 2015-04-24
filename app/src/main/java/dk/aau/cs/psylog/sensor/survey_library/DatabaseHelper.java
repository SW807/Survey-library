package dk.aau.cs.psylog.sensor.survey_library;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import dk.aau.cs.psylog.module_lib.DBAccessContract;

public class DatabaseHelper {
    private Context context;
    private ContentResolver contentResolver;
    private static final String MODULE_NAME = "survey_library";
    private static final String MODULE_URI = DBAccessContract.DBACCESS_CONTENTPROVIDER + MODULE_NAME + "_";
    private static final String QUESTIONS_TABLE = "questions";
    private static final String ANSWERS_TABLE = "answers";
    private static final String MULTIPLE_CHOICE_CHOICES_TABLE = "multiple_choice_choices";
    private static final String MULTIPLE_CHOICE_TABLE = "multiple_choice";
    private static final String NUMBER_RANGE_TABLE = "number_range";
    private static final String PLAIN_TEXT_TABLE = "plain_text";

    private static final String QUESTION_ID_COLUMN = "question_id";
    private static final String QUESTION_TYPE_ID_COLUMN = "question_type_id";
    private static final String QUESTION_TEXT_COLUMN = "question_text";

    public DatabaseHelper(Context context){
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    public void addQuestion(int questionTypeId, String question){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION_TYPE_ID_COLUMN, questionTypeId);
        contentValues.put(QUESTION_TEXT_COLUMN, question);

        contentResolver.insert(Uri.parse(MODULE_URI + QUESTIONS_TABLE), contentValues);
    }

    public void addAnswer(){

    }

    public void getQuestion(){

    }

    public void addChoice(){}
}
