package com.darclabs.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.darclabs.trivia.data.AnswerListAsyncResponse;
import com.darclabs.trivia.data.QuestionBank;
import com.darclabs.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView;
    private TextView questionCounterTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connecting all the UI objects to the objects created above
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        questionCounterTextView = findViewById(R.id.counter_text);
        questionTextView = findViewById(R.id.question_textview);

        //Listeners for the buttons
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);



        //Pass the Async interface in to be able to receive an actual populated list
            questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            //The populated question list can be accessed in here
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                //Get question from the list and display it, uses first index if app just started.
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                //Displays what question you are on out of the entire list.
                questionCounterTextView.setText(currentQuestionIndex +" / " +questionArrayList.size());


            }
        });



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.prev_button:
                if(currentQuestionIndex > 0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.next_button:
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
                break;
            case R.id.true_button:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                updateQuestion();
                break;
        }




    }
    //Checks the user's answer by comparing the userAnswer to the answerIsTrue value from the array list
    private void checkAnswer(boolean userAnswer) {
        boolean answserIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
        int toastMessageId =  0;
        if (userAnswer == answserIsTrue){
            fadeView();
            toastMessageId = R.string.correct_answer;
        }else{
            shakeAnimation();
            toastMessageId = R.string.wrong_answer;
        }
        Toast.makeText(MainActivity.this,toastMessageId,
                Toast.LENGTH_LONG)
                .show();
    }

    //Fetches question either previous or next
    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);

        questionCounterTextView.setText(currentQuestionIndex +" / " +questionList.size());

    }
    //Method for fade animation used on card view for correct answers
    private void fadeView(){
        final CardView cardView = findViewById(R.id.cardView);
        //Creates an AlphaAnimation called "alphaAnimation" and sets its to fade from fully visible to nothing.
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        //Animation attributes
        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        //Passing the animation to the cardView
        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //Not used.
            }
        });
    }

    //Method for the shake animation used on the card view
    private void shakeAnimation(){
        //Instantiate the animation class and load the xml
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //Not used.
            }
        });

    }
}