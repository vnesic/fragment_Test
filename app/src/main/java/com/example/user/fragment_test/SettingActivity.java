package com.example.user.fragment_test;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Buljoslav on 10/12/2016.
 */

public class SettingActivity extends Activity {



    private Button backButton,backButtonDummy;
    int width=0;
    int height=0;
    ListView listview;

    private TextView title,backgroundColor,textColor,textSize;
    private Button grayFont,blackFont,redFont;
    private Button backgroundYellow,backgroundWhite;
    private Button dummyButton,sizeUPButton,sizeDownButton,okButton;
    private Button calibriFont,gabriolaFont,palatinoFont,defaultFont,arialFont;
    private View View_grayFont,View_blackFont,View_redFont;
    private View View_backgroundYellow,View_backgroundWhite;
    private View View_dummyButton,View_sizeUPButton,View_sizeDownButton,View_okButton;
    private View View_calibriFont,View_gabriolaFont,View_palatinoFont,View_defaultFont,View_arialFont;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=8){
            this.setContentView(R.layout.android_tablet_settings);
        }else{
            this.setContentView(R.layout.settings_layout);
        }

        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();

        width = d.getWidth();
        height = d.getHeight();

        init();


        grayFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setFontYellow();
            }
        });

        blackFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setFontBlack();
            }
        });

        redFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setFontRed();
            }
        });


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



        calibriFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setFontCALIBRI();
            }
        });
        gabriolaFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setFontGABRIOLA();
            }
        });
        palatinoFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setFontPALATINO();
            }
        });
        defaultFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setFontDEFAULT();
            }
        });
        arialFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettings.setFontARIAL();
            }
        });



        View_okButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        // PRESSED
                            View_okButton.setBackgroundResource(R.drawable.button_st_ok_active);


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                            View_okButton.setBackgroundResource(R.drawable.button_st_ok_normal);

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        View_grayFont.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        // PRESSED
                        View_okButton.setBackgroundResource(R.drawable.button_st_fc_3_active);


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        View_okButton.setBackgroundResource(R.drawable.button_st_fc_3_normal);

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        View_blackFont.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        // PRESSED
                        View_okButton.setBackgroundResource(R.drawable.button_st_fc_1_active);


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        View_okButton.setBackgroundResource(R.drawable.button_st_fc_1_normal);

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        View_redFont.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        // PRESSED
                        View_okButton.setBackgroundResource(R.drawable.button_st_fc_4_active);


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        View_okButton.setBackgroundResource(R.drawable.button_st_fc_4_normal);

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        View_backgroundWhite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        // PRESSED
                        View_okButton.setBackgroundResource(R.drawable.button_st_bgc_2_active);


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        View_okButton.setBackgroundResource(R.drawable.button_st_bgc_2_normal);

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        View_backgroundYellow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        // PRESSED
                        View_okButton.setBackgroundResource(R.drawable.button_st_bgc_1_active);


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        View_okButton.setBackgroundResource(R.drawable.button_st_bgc_1_normal);

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        View_sizeUPButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        // PRESSED
                        View_okButton.setBackgroundResource(R.drawable.button_st_s_plus_active);


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        View_okButton.setBackgroundResource(R.drawable.button_st_s_plus_normal);

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        View_sizeDownButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        // PRESSED
                        View_okButton.setBackgroundResource(R.drawable.button_st_s_minus_active);


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        View_okButton.setBackgroundResource(R.drawable.button_st_s_minus_normal);

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });






        arrangeLayout();

    }

    void arrangeLayout(){

        backButton.setWidth((int) (width * 0.15));
        backButtonDummy.setWidth((int) (width * 0.15));
        title.setWidth((int) (width * 0.6));

        grayFont.setWidth((int) (grayFont.getHeight()));
        blackFont.setWidth((int) (blackFont.getHeight()));
        redFont.setWidth((int) (redFont.getHeight()));
        backgroundWhite.setWidth((int) (backgroundWhite.getHeight()));
        backgroundYellow.setWidth((int) (backgroundYellow.getHeight()));;

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
        grayFont=(Button)findViewById(R.id.grayFont);
        blackFont=(Button)findViewById(R.id.blackFont);
        redFont=(Button)findViewById(R.id.redFont);
        backgroundWhite=(Button)findViewById(R.id.backgroundWhite);
        backgroundYellow=(Button)findViewById(R.id.backgroundYellow);
        sizeUPButton=(Button)findViewById(R.id.sizeUP);
        sizeDownButton=(Button)findViewById(R.id.sizeDOWN);
        backgroundColor=(TextView)findViewById(R.id.backgroundColor);
        textColor=(TextView)findViewById(R.id.textColor);
        textSize=(TextView)findViewById(R.id.textSize);
        calibriFont=(Button)findViewById(R.id.Calibri);
        gabriolaFont=(Button)findViewById(R.id.Gabriola);
        palatinoFont=(Button)findViewById(R.id.palatino_Linotype);
        defaultFont=(Button)findViewById(R.id.defaultFont);
        arialFont=(Button)findViewById(R.id.Arial);



        View_okButton=findViewById(R.id.okbutton);
        View_grayFont=findViewById(R.id.grayFont);
        View_blackFont=findViewById(R.id.blackFont);
        View_redFont=findViewById(R.id.redFont);
        View_backgroundWhite=findViewById(R.id.backgroundWhite);
        View_backgroundYellow=findViewById(R.id.backgroundYellow);
        View_sizeUPButton=findViewById(R.id.sizeUP);
        View_sizeDownButton=findViewById(R.id.sizeDOWN);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(UserSettings.isChanged) {
            UserSettings.setToDisplay();
        }

    }
}
