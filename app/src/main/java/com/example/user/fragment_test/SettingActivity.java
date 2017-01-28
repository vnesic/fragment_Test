package com.example.user.fragment_test;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
    private Button yellowFont,blackFont,redFont;
    private Button backgroundYellow,backgroundWhite;
    private Button dummyButton,sizeUPButton,sizeDownButton,okButton;
    private Button calibriFont,gabriolaFont,palatinoFont,defaultFont,arialFont;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.settings_layout);
   /*     listview = (ListView) findViewById(R.id.listViewFonts);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < Const.FONTS.length; ++i) {
            list.add(Const.FONTS[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

    */

        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();

        width = d.getWidth();
        height = d.getHeight();

        init();


        yellowFont.setOnClickListener(new View.OnClickListener() {
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
        arrangeLayout();

    }

    void arrangeLayout(){

        backButton.setWidth((int) (width * 0.15));
        backButtonDummy.setWidth((int) (width * 0.15));
        title.setWidth((int) (width * 0.6));

        yellowFont.setWidth((int) (yellowFont.getHeight()));
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
        yellowFont=(Button)findViewById(R.id.yellowFont);
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

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(UserSettings.isChanged) {
            UserSettings.setToDisplay();
        }

    }
}
