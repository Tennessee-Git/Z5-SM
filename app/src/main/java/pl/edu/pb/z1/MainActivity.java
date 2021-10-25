package pl.edu.pb.z1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String QUIZ_TAG = "MainActivity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final int REQUEST_CODE_PROMPT = 0;
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.z1";

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;

    private int currentIndex = 0;
    private boolean answerWasShown;

    private TextView questionTextView;
    private Question[] questions = new Question[]
            {
                    new Question(R.string.q_1,  true),
                    new Question(R.string.q_2,  false),
                    new Question(R.string.q_3,true),
                    new Question(R.string.q_4, true),
                    new Question(R.string.q_5, false)
            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "Wywołana została metoda onCreate");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
        {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        promptButton = findViewById(R.id.tip_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });
        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });

        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(QUIZ_TAG, "Wywołana została metoda onStart");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(QUIZ_TAG,"Wywołana została metoda onResume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(QUIZ_TAG,"Wywołana została metoda onPause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(QUIZ_TAG,"Wywołana została metoda onStop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(QUIZ_TAG, "Wywołana została metoda onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywołana została metoda onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode != RESULT_OK)
            return;
        if(requestCode == REQUEST_CODE_PROMPT)
        {
            if(data == null)
                return;
            answerWasShown = data.getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);
        }
    }

    private void setNextQuestion()
    {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    private void checkAnswerCorrectness(boolean userAnswer)
    {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown)
        {
            resultMessageId = R.string.answer_was_shown;
        }
        else
        {
            if(userAnswer == correctAnswer)
                resultMessageId = R.string.correct_answer;
            else
                resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this,resultMessageId, Toast.LENGTH_SHORT).show();
    }
}
