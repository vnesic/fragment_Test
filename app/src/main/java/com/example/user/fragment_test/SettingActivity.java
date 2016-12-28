package com.example.user.fragment_test;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Buljoslav on 10/12/2016.
 */

public class SettingActivity extends Activity {



    private Button backButton,backButtonDummy;
    int width=0;
    int height=0;

    private TextView title,backgroundColor,textColor,textSize;
    private Button yellowColorButton,whiteColorButton,whiteColor2Button;
    private Button backgroundYellow,backgroundWhite;
    private Button dummyButton,sizeUPButton,sizeDownButton,okButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.settings_layout);

        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();

        width = d.getWidth();
        height = d.getHeight();

        init();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sizeUPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.incrementFont();
            }
        });
        sizeDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.decrementFont();
            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSettings.isChanged=true;
                UserSettings.setToDisplay();

            }
        });

        backgroundWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setBackgroudWhite();
            }
        });


        backgroundYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setBackgroudYellow();
            }
        });

        arrangeLayout();

    }

    void arrangeLayout(){

        backButton.setWidth((int) (width * 0.15));
        backButtonDummy.setWidth((int) (width * 0.15));
        title.setWidth((int) (width * 0.6));

        yellowColorButton.setWidth((int) (yellowColorButton.getHeight()));
        whiteColorButton.setWidth((int) (whiteColorButton.getHeight()));
        whiteColor2Button.setWidth((int) (whiteColor2Button.getHeight()));
        backgroundWhite.setWidth((int) (backgroundWhite.getHeight()));
        backgroundYellow.setWidth((int) (backgroundYellow.getHeight()));;
 //       dummyButton.setWidth((int) (width * 0.3));
   //     sizeUPButton.setWidth((int) (width * 0.25));
   //     sizeDownButton.setWidth((int) (width * 0.25));

        backgroundColor.setWidth((int) (width * 0.2));
        textColor.setWidth((int) (width * 0.2));
        textSize.setWidth((int) (width * 0.2));
        okButton.setWidth(okButton.getHeight());
    }

    void init(){

        title=(TextView)findViewById(R.id.subtitleTitle);
        backButton=(Button)findViewById(R.id.backButton);
        backButtonDummy=(Button)findViewById(R.id.backButtonDummy);
        okButton=(Button)findViewById(R.id.okbutton);
        yellowColorButton=(Button)findViewById(R.id.yellowColor);
        whiteColorButton=(Button)findViewById(R.id.whiteColor);
        whiteColor2Button=(Button)findViewById(R.id.whiteColor2);
        backgroundWhite=(Button)findViewById(R.id.backgroundWhite);
        backgroundYellow=(Button)findViewById(R.id.backgroundYellow);
   //     dummyButton=(Button)findViewById(R.id.dummyButton);
        sizeUPButton=(Button)findViewById(R.id.sizeUP);
        sizeDownButton=(Button)findViewById(R.id.sizeDOWN);
        backgroundColor=(TextView)findViewById(R.id.backgroundColor);
        textColor=(TextView)findViewById(R.id.textColor);
        textSize=(TextView)findViewById(R.id.textSize);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(UserSettings.isChanged) {
        }else {
            UserSettings.resetToDefault();
        }

    }
}
