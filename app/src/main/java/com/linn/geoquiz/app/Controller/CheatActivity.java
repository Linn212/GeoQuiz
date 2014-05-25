package com.linn.geoquiz.app.Controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linn.geoquiz.app.R;

public class CheatActivity extends ActionBarActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.linn.geoquiz.controller.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.linn.geoquiz.controller.answer_shown";
    private boolean mAnswerIsTrue;
    private Button mShowAnswer;
    private TextView mAnswerTextView;
    private boolean mIsAnswerShown;

    private static final String TAG = "CheatActivity";

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        Log.d(TAG, "setAnswerShownResult: isAnswerShown= " + isAnswerShown);
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
                mIsAnswerShown = true;
                Log.d(TAG, "setAnswerShownResult(true)");
            }
        });

        if (savedInstanceState != null){
            Log.d(TAG, "savedInstanceState: " + savedInstanceState.toString());
            mIsAnswerShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN, false);
            setAnswerShownResult(mIsAnswerShown);
        } else {
            Log.d(TAG, "savedInstanceState was NULL");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState, mIsAnswerShown=" + mIsAnswerShown);

        savedInstanceState.putBoolean(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cheat, menu);
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
