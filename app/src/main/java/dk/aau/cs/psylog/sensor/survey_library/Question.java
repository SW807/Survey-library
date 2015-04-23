package dk.aau.cs.psylog.sensor.survey_library;

public abstract class Question {
    private String text;
    private QuestionType questionType;

    public Question(String text, QuestionType questionType){
        this.text = text;
        this.questionType = questionType;
    }
}
