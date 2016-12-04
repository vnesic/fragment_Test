package com.example.user.fragment_test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Buljoslav on 26/11/2016.
 */

public class TextActivity extends Activity {
    TextView textView;
    //
    private String[][] cachedText= new String[Const.MaxNumOfTexts][Const.MaxNumOfSubTexts];
    private int lastText=0;
    private int lastSubtext=0;
    private Object lock = new Object();
    Thread t;
    boolean wasClicked=false;
    int width ;
    int height ;
    Button tabButton1,tabButton2,tabButton3,tabButton4,tabButton5;
    TableLayout tableLayout;
    SeekBar seekBar;
    View v;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main3);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        WindowManager w= getWindowManager();
        Display d = w.getDefaultDisplay();

        width = d.getWidth();
        height = d.getHeight();
        tabButtonAling();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.NOTIFICATION_TEXT);
        registerReceiver(myRecieverD, filter);

        textView = (TextView) findViewById(R.id.text);
        textView.setMovementMethod(new ScrollingMovementMethod());

        int subTextIndex = getIntent().getIntExtra("index",0);

        lastText=subTextIndex;
        lastSubtext=getIntent().getIntExtra("lastPos",0);

        /////////[lastSubtext][lastText]//////////

        final ProgressDialog dialog = ProgressDialog.show(this, "Initalising list", "Please wait", true);

        if(cachedText[lastSubtext][lastText]!=null) {
            textView.setText(cachedText[lastText][lastText]);
            textView.setVisibility(View.VISIBLE);
        }else {

            Intent mServiceIntent = new Intent(this, ParsingService.class);
            mServiceIntent.putExtra("kind", Const.TEXT);
            mServiceIntent.putExtra("index", lastSubtext);
            startService(mServiceIntent);
        }


        t= new Thread(new Runnable() {

            public void run() {
                try {
                    synchronized (lock) {
                        lock.wait(100000);
                    }
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();


    }

    BroadcastReceiver myRecieverD = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(Const.NOTIFICATION_TEXT)){

                String text=intent.getStringExtra("text_intent");

                String[] parts = text.split("#");

                int j=0;
                for(int i=0;i<parts.length;i++) {

                    if(!parts[i].equals("")){
                        cachedText[lastSubtext][j] = parts[i];
                        j++;
                    }


                }

                textView.setText(cachedText[lastSubtext][lastText]);


                synchronized (lock){
                    lock.notify();
                }
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myRecieverD != null) {
            unregisterReceiver(myRecieverD);
        }
    }

    private SpannableStringBuilder  addClickablePart(Spannable charSequence) {
        SpannableStringBuilder  ssb = new SpannableStringBuilder(charSequence);

        int idx1 = charSequence.toString().indexOf("(");
        int idx2 = 0;
        while (idx1 != -1) {
            idx2 = charSequence.toString().indexOf(")", idx1) + 1;

            final String clickString = charSequence.toString().substring(idx1, idx2);
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    Toast.makeText(getApplicationContext(), clickString,
                            LENGTH_SHORT).show();
                }
            }, idx1, idx2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            idx1 = charSequence.toString().indexOf("(", idx2);
        }

        return ssb;
    }

    String getFootNote(int n) {

        String aDataRow = "";
        String aBuffer = "#";
        int textNr=n+1;
        try{
            InputStream raw = getAssets().open("text"+textNr+".txt");
            BufferedReader myReader = new BufferedReader(new InputStreamReader(raw, "UTF8"));
            while ((aDataRow = myReader.readLine()) != null) {

                if (aDataRow.contains(Const.TEXT_DELIMITERS[0])) {
                    aDataRow = myReader.readLine();
                    do{
                        if(aDataRow!=null) aBuffer += aDataRow + "\n";
                        if(aDataRow==null)break;
                        aDataRow = myReader.readLine();
                    }
                    while (!aDataRow.contains(Const.TEXT_DELIMITERS[1]));

                    aBuffer += "#";
                }

            }
        }catch (IOException e){}

        return aBuffer;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, "AAAAAAAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
        onTouchHandle();
        return super.onTouchEvent(event);
    }

    public void onTouchHandle() {

        if(!wasClicked){
            textView.setHeight((int)(height*0.8));
            tableLayout=(TableLayout)findViewById(R.id.topLayout);
            seekBar=(SeekBar)findViewById(R.id.pager_scoller);
            tableLayout.setVisibility(View.VISIBLE);
            seekBar.setVisibility(View.VISIBLE);
            tabButtonAling();
            wasClicked=true;
        }else{
            textView.setHeight(height);
            tableLayout.setVisibility(View.INVISIBLE);
            seekBar.setVisibility(View.INVISIBLE);
        }

    }

    void tabButtonAling(){
        tabButton1=(Button)findViewById(R.id.backButton);
        tabButton2=(Button)findViewById(R.id.contentButton);
        tabButton3=(Button)findViewById(R.id.bookMarkButton);
        tabButton4=(Button)findViewById(R.id.settingsButton);
        tabButton5=(Button)findViewById(R.id.starButton);

        tabButton1.setWidth((int)(width*0.2));
        tabButton2.setWidth((int)(width*0.2));
        tabButton3.setWidth((int)(width*0.2));
        tabButton4.setWidth((int)(width*0.2));
        tabButton5.setWidth((int)(width*0.2));
    }


}
