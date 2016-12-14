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
    private Button yellowColorButton,whiteColorButton,yellow2ColorButton,whiteColor2Button;
    private Button textBlack,textBrown,textBlue,textRed;
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
                ////send intent to put current settings into the text view
            }
        });
        arrangeLayout();

    }

    void arrangeLayout(){

        backButton.setWidth((int) (width * 0.15));
        backButtonDummy.setWidth((int) (width * 0.15));
        title.setWidth((int) (width * 0.6));

        yellowColorButton.setWidth((int) (width * 00.10));
        whiteColorButton.setWidth((int) (width * 0.10));
        yellow2ColorButton.setWidth((int) (width * 0.10));
        whiteColor2Button.setWidth((int) (width * 0.10));
        textBlack.setWidth((int) (width * 0.10));;
        textBrown.setWidth((int) (width * 0.10));
        textBlue.setWidth((int) (width * 0.10));;
        textRed.setWidth((int) (width * 0.10));
 //       dummyButton.setWidth((int) (width * 0.3));
   //     sizeUPButton.setWidth((int) (width * 0.25));
   //     sizeDownButton.setWidth((int) (width * 0.25));

        backgroundColor.setWidth((int) (width * 0.3));
        textColor.setWidth((int) (width * 0.3));
        textSize.setWidth((int) (width * 0.45));
    }

    void init(){

        title=(TextView)findViewById(R.id.subtitleTitle);
        backButton=(Button)findViewById(R.id.backButton);
        backButtonDummy=(Button)findViewById(R.id.backButtonDummy);
        okButton=(Button)findViewById(R.id.okbutton);
        yellowColorButton=(Button)findViewById(R.id.yellowColor);
        whiteColorButton=(Button)findViewById(R.id.whiteColor);
        yellow2ColorButton=(Button)findViewById(R.id.yellowColor2);
        whiteColor2Button=(Button)findViewById(R.id.whiteColor2);
        textBlack=(Button)findViewById(R.id.texBlack);
        textBrown=(Button)findViewById(R.id.textBrown);
        textBlue=(Button)findViewById(R.id.textBlue);
        textRed=(Button)findViewById(R.id.textRed);
   //     dummyButton=(Button)findViewById(R.id.dummyButton);
        sizeUPButton=(Button)findViewById(R.id.sizeUP);
        sizeDownButton=(Button)findViewById(R.id.sizeDOWN);
        backgroundColor=(TextView)findViewById(R.id.backgroundColor);
        textColor=(TextView)findViewById(R.id.textColor);
        textSize=(TextView)findViewById(R.id.textSize);


    }
}
