package dk.aau.cs.psylog.sensor.survey_library;

public class MultipleChoiceQuestion extends Question{
    private String[] choices;
    private boolean singleSelection;

    public MultipleChoiceQuestion(String text, boolean singleSelection, String[] choices){
        super(text, QuestionType.MULTIPLE_CHOICE);
        this.singleSelection = singleSelection;
        this.choices = choices;
    }
}
