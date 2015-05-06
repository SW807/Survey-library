package dk.aau.cs.psylog.sensor.survey_library;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

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
    private static final String MULTIPLE_CHOICE_ANSWER_TABLE = "multiple_choice_answer";
    private static final String NUMBER_RANGE_TABLE = "number_range";
    private static final String PLAIN_TEXT_TABLE = "plain_text";


    private static final String QUESTION_ID_COLUMN = "question_id";
    private static final String QUESTION_TYPE_ID_COLUMN = "question_type_id";
    private static final String QUESTION_TEXT_COLUMN = "question_text";

    private static final String MULTIPLE_CHOICE_SINGLESELELECTION_COLUMN = "single_selection";
    private static final String MULTIPLE_CHOICE_ANSWER_CHOICE_COLUMN = "multiple_choice_choice";
    private static final String MULTIPLE_CHOICE_CHOICES_CHOICE_TEXT_COLUMN = "choice_text";
    private static final String MULTIPLE_CHOICE_CHOICES_CHOICE_ID_COLUMN = "choice_id";

    private static final String NUMBER_RANGE_MIN_COLUMN = "min";
    private static final String NUMBER_RANGE_MAX_COLUMN = "max";
    private static final String NUMBER_RANGE_MIN_LABEL_COLUMN = "min_label";
    private static final String NUMBER_RANGE_MAX_LABEL_COLUMN = "max_label";

    private static final String ANSWERS_ANSWER_ID_COLUMN = "answer_id";
    private static final String ANSWERS_ANSWERED_COLUMN = "answered";
    private static final String ANSWERS_QUESTION_ID_COLUMN = "question_id";
    private static final String ANSWERS_QUESTION_TYPE_ID_COLUMN = "question_type_id";

    private static final String MULTIPLE_CHOICE_ANSWER_ANSWER_ID_COLUMN = "answer_id";



    public DatabaseHelper(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    public int getNextQuestionId() {
        Cursor c = contentResolver.query(Uri.parse(MODULE_URI + QUESTIONS_TABLE), new String[]{"MAX ( " + QUESTION_ID_COLUMN + " )"}, null, null, null);
        // if no questions return 0, else increment the largest index of existing questions
        return c.moveToFirst() ? c.getInt(0) + 1 : 0;
    }

    public int getNextAnswerId() {
        Cursor c = contentResolver.query(Uri.parse(MODULE_URI + ANSWERS_TABLE), new String[]{"MAX ( " + ANSWERS_ANSWER_ID_COLUMN + ")"}, null, null, null);
        // if no answers return 0, else increment the largest index of existing questions
        return c.moveToFirst() ? c.getInt(0) + 1 : 0;
    }

    public int addQuestion(Question q) {
        int this_id = getNextQuestionId();
        ContentValues contentValues = new ContentValues();

        contentValues.put(QUESTION_ID_COLUMN, this_id);
        contentValues.put(QUESTION_TYPE_ID_COLUMN, q.getQuestionType().ordinal());
        contentValues.put(QUESTION_TEXT_COLUMN, q.getText());

        contentResolver.insert(Uri.parse(MODULE_URI + QUESTIONS_TABLE), contentValues);

        if (q.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
            ContentValues multipleChoiceValues = new ContentValues();

            multipleChoiceValues.put(QUESTION_ID_COLUMN, this_id);
            multipleChoiceValues.put(MULTIPLE_CHOICE_SINGLESELELECTION_COLUMN, ((MultipleChoiceQuestion) q).getSingleSelection());

            int choice_id = 0;
            for (String choice : ((MultipleChoiceQuestion) q).getChoices()) {
                ContentValues choicesValues = new ContentValues();
                choicesValues.put(QUESTION_ID_COLUMN, this_id);
                choicesValues.put(MULTIPLE_CHOICE_CHOICES_CHOICE_ID_COLUMN, choice_id);
                choice_id += 1;
                choicesValues.put(MULTIPLE_CHOICE_CHOICES_CHOICE_TEXT_COLUMN, choice);

                contentResolver.insert(Uri.parse(MODULE_URI + MULTIPLE_CHOICE_CHOICES_TABLE), choicesValues);
            }

            contentResolver.insert(Uri.parse(MODULE_URI + MULTIPLE_CHOICE_TABLE), multipleChoiceValues);
        } else if (q.getQuestionType() == QuestionType.NUMBER_RANGE) {
            ContentValues numberRangeValues = new ContentValues();

            numberRangeValues.put(QUESTION_ID_COLUMN, this_id);
            numberRangeValues.put(NUMBER_RANGE_MIN_COLUMN, ((NumberRangeQuestion) q).getMin());
            numberRangeValues.put(NUMBER_RANGE_MAX_COLUMN, ((NumberRangeQuestion) q).getMax());
            numberRangeValues.put(NUMBER_RANGE_MIN_LABEL_COLUMN, ((NumberRangeQuestion) q).getMinLabel());
            numberRangeValues.put(NUMBER_RANGE_MAX_LABEL_COLUMN, ((NumberRangeQuestion) q).getMaxLabel());

            contentResolver.insert(Uri.parse(MODULE_URI + NUMBER_RANGE_TABLE), numberRangeValues);
        }
        return this_id;
    }

    public void addMultipleChoiceAnswer(int question_id, Integer choice, boolean answered) {
        int answer_id = getNextAnswerId();
        ContentValues answer = new ContentValues();

        answer.put(ANSWERS_ANSWER_ID_COLUMN, answer_id);
        answer.put(ANSWERS_QUESTION_ID_COLUMN, question_id);
        answer.put(ANSWERS_QUESTION_TYPE_ID_COLUMN, QuestionType.MULTIPLE_CHOICE.getValue());
        answer.put(ANSWERS_ANSWERED_COLUMN, answered);

        contentResolver.insert(Uri.parse(MODULE_URI + ANSWERS_TABLE), answer);

        ContentValues multipleChoiceAnswer = new ContentValues();
        multipleChoiceAnswer.put(MULTIPLE_CHOICE_ANSWER_ANSWER_ID_COLUMN, answer_id);
        multipleChoiceAnswer.put(MULTIPLE_CHOICE_ANSWER_CHOICE_COLUMN, choice);

        contentResolver.insert(Uri.parse(MODULE_URI + MULTIPLE_CHOICE_ANSWER_TABLE), multipleChoiceAnswer);

    }

    public void addMultipleChoiceAnswer(int question_id, boolean[] choices, boolean answered) {
        int answer_id =getNextAnswerId();

        ContentValues answer = new ContentValues();

        answer.put(ANSWERS_ANSWER_ID_COLUMN, answer_id);
        answer.put(ANSWERS_QUESTION_ID_COLUMN, question_id);
        answer.put(ANSWERS_QUESTION_TYPE_ID_COLUMN, QuestionType.MULTIPLE_CHOICE.getValue());
        answer.put(ANSWERS_ANSWERED_COLUMN, answered);

        contentResolver.insert(Uri.parse(MODULE_URI + ANSWERS_TABLE), answer);

        for (int i = 0; i < choices.length; i++) {
            if (choices[i] == true) {
                ContentValues multipleChoiceAnswer = new ContentValues();
                multipleChoiceAnswer.put(MULTIPLE_CHOICE_ANSWER_ANSWER_ID_COLUMN, answer_id);
                multipleChoiceAnswer.put(MULTIPLE_CHOICE_ANSWER_CHOICE_COLUMN, i);

                contentResolver.insert(Uri.parse(MODULE_URI + MULTIPLE_CHOICE_ANSWER_TABLE), multipleChoiceAnswer);
            }
        }
    }

    public void getQuestion() {

    }

    public void addChoice() {
    }
}
