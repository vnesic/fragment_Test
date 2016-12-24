package com.example.user.fragment_test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Buljoslav on 24/12/2016.
 */

public class AuthorActivity extends Activity {

    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.author);

        mTextView=(TextView)findViewById(R.id.authorBiography);
        mTextView.setText(Const.authorBio[0]);
    }


}
