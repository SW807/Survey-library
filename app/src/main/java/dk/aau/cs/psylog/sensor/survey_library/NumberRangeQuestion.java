package dk.aau.cs.psylog.sensor.survey_library;

public class NumberRangeQuestion extends Question{
    private int min;
    private int max;
    private String minLabel;
    private String maxLabel;

    public NumberRangeQuestion(String text, QuestionType questionType, int min, int max, String minLabel, String maxLabel) {
        super(text, questionType);
        this.min = min;
        this.max = max;
        this.minLabel = minLabel;
        this.maxLabel = maxLabel;
    }
}
