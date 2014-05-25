package com.linn.geoquiz.app.Controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.linn.geoquiz.app.Model.TrueFalse;
import com.linn.geoquiz.app.R;


public class QuizActivity extends ActionBarActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "Index";
    private static final String KEY_CHEATER = "IsCheater";
    private static final String KEY_CHEATLIST = "CheatList";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
        new TrueFalse(R.string.question_africa, false),
        new TrueFalse(R.string.question_americas, true),
        new TrueFalse(R.string.question_asia, true),
        new TrueFalse(R.string.question_mideast, false),
        new TrueFalse(R.string.question_oceans, true),
    };

    private boolean[] mHasCheatedList = new boolean[mQuestionBank.length];

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d(TAG, "onActivityResult");
        if (data ==null){
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        mHasCheatedList[mCurrentIndex] = mIsCheater;

    }

    private void updateQuestion(){
        Log.d(TAG, "Updating question text for question #"+ mCurrentIndex);
        int question = mQuestionBank[mCurrentIndex].getmQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismTrueQuestion();
        int messageResId=0;

        Log.d(TAG, "mIsCheater is " + mIsCheater);



        if (mHasCheatedList[mCurrentIndex]){
            messageResId = R.string.judgment_toast;

        }else {

            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId,Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        //setContentView is called when screen is rotated.
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                Log.d(TAG, "mCurrnetIndex is " + mCurrentIndex);
                updateQuestion();
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex=(mCurrentIndex+1) % mQuestionBank.length;
                Log.d(TAG, "mCurrnetIndex is " + mCurrentIndex);
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent (QuizActivity.this, CheatActivity.class);

                boolean answerIsTure = mQuestionBank[mCurrentIndex].ismTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTure);

                startActivityForResult(i, 0);
            }
        });

        if (savedInstanceState != null){
            Log.d(TAG, "savedInstanceState: " + savedInstanceState.toString());
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            //mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
            mHasCheatedList = savedInstanceState.getBooleanArray(KEY_CHEATLIST);
            mIsCheater = mHasCheatedList[mCurrentIndex];
            for (boolean value : mHasCheatedList) {
                Log.d(TAG, "LIST:" + value);
            }

        } else {
            Log.d(TAG, "savedInstanceState was NULL");
        }
        updateQuestion();
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");


        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);

        savedInstanceState.putBooleanArray(KEY_CHEATLIST, mHasCheatedList);
        Log.d(TAG, "savedInstanceState: " + savedInstanceState.toString());

        for (boolean value : mHasCheatedList) {
            Log.d(TAG, "LIST:" + value);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "QuizActivity onStart() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "QuizActivity onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "QuizActivity onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "QuizActivity onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "QuizActivity onDestroy() called");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
