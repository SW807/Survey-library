package dk.aau.cs.psylog.sensor.survey_library.questions;

public enum QuestionType {
    PLAIN_TEXT(0),
    NUMBER_RANGE(1),
    MULTIPLE_CHOICE(2);

    private int value;

    private QuestionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
