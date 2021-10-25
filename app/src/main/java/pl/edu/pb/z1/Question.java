package pl.edu.pb.z1;

public class Question {
    private int qustionId;
    private boolean trueAnswer;

    public Question (int qustionId, boolean trueAnswer)
    {
        this.qustionId = qustionId;
        this.trueAnswer = trueAnswer;
    }
    public boolean isTrueAnswer()
    {
        return this.trueAnswer;
    }
    public int getQuestionId()
    {
        return this.qustionId;
    }
}
