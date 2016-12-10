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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    static final String DEBUG_TAG="[VNesic]:TextAct";
    boolean firstEntry=false;
    GestureDetector mGestureDetector;
   // private String[][] cachedText = new String[Const.MaxNumOfTexts][Const.MaxNumOfSubTexts];
    private String[] footnotes=new String[Const.MaxNumberofFootnotes];
    private int lastText = 0;
    private int lastSubtext = 0;
    private Object lock = new Object();
    Thread t;
    LinearLayout mTableLayout;
    long timePassed=0;
    SeekBar mSeekBar;
    boolean wasClicked = false;
    int width;
    int height;
    SpannableString toDisplay;
    Button tabButton1, tabButton2, tabButton3, tabButton4, tabButton5;
    TableLayout tableLayout;
    SeekBar seekBar;
    View v;
    private GestureDetectorCompat mDetector;
    boolean isFirstEntry=true;
    @Override


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main3);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();
        mTableLayout=(LinearLayout)findViewById(R.id.pager_scollerTab);
        tableLayout=(TableLayout)findViewById(R.id.topLayout);
        width = d.getWidth();
        height = d.getHeight();
        tabButtonAling();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.NOTIFICATION_TEXT);
        registerReceiver(myRecieverD, filter);

        mSeekBar=(SeekBar)findViewById(R.id.pager_scoller);
        mSeekBar.setMinimumHeight(0);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d(DEBUG_TAG,"WUTEVA : "+i);
                int h=textView.getLineHeight();
                int hh=textView.getHeight();
                int t=textView.getTop();
                int hhh=textView.getBottom();
                int moveTo=(int)(((hhh-t))*(i/100));
                textView.scrollTo(0,moveTo);
                ;}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        textView = (TextView) findViewById(R.id.text);
        textView.setMovementMethod(new ScrollingMovementMethod());

        int subTextIndex = getIntent().getIntExtra("index", 0);

        lastText = subTextIndex;
        lastSubtext = getIntent().getIntExtra("lastPos", 0);
        /////////[lastSubtext][lastText]//////////
        textView.setPadding(0,40,0,0);

        final ProgressDialog dialog = ProgressDialog.show(this, "Учитавање текста...", "Молимо Вас сачекајте", true);

     /*   if (cachedText[lastSubtext][lastText] != null) {
            textView.setText(cachedText[lastText][lastText]);
            textView.setVisibility(View.VISIBLE);
        } else {
*/
            Intent mServiceIntent = new Intent(this, ParsingService.class);
            mServiceIntent.putExtra("kind", Const.TEXT);
            mServiceIntent.putExtra("index", lastSubtext);
            startService(mServiceIntent);
  //      }


        t = new Thread(new Runnable() {

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

        mGestureDetector = new GestureDetector(this,new GestureDetector.OnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }
        });

// set the on Double tap listener
        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                onTouchHandle();
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                // if the second tap hadn't been released and it's being moved
                onTouchHandle();

                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

        });

        textView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });


    }

    BroadcastReceiver myRecieverD = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Const.NOTIFICATION_TEXT)) {

                String text = intent.getStringExtra("text_intent");

                String[] parts = text.split("#");

                int j = 0;
           /*     for (int i = 0; i < parts.length; i++) {

                    if (!parts[i].equals("")) {
                        cachedText[lastSubtext][j] = parts[i];
                        j++;
                    }


                }*/

                for(int i=0;i<parts.length;i++) {
                    if (parts[i].contains(Const.FOOTNOTE_DELIMITERS[0])) {

                        createLink(textView, parts[i], "<footnote>", new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // your action
                                Toast.makeText(getApplicationContext(), "Start Sign up activity",
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                // this is where you set link color, underline, typeface etc.
                                int linkColor = ContextCompat.getColor(getApplicationContext(), R.color.afTitle);
                                ds.setColor(linkColor);
                                ds.setUnderlineText(false);
                            }
                        });
                    } else {

//                        textView.setText(cachedText[lastSubtext][lastText]);

                    }
                }
                synchronized (lock) {
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
    public static void createLink(TextView targetTextView, String completeString,String partToClick, ClickableSpan clickableAction) {

        SpannableString spannableString = new SpannableString(completeString);

        // make sure the String is exist, if it doesn't exist
        // it will throw IndexOutOfBoundException
        int startPosition = completeString.indexOf(partToClick);
        int endPosition = completeString.lastIndexOf(partToClick) + partToClick.length();

        spannableString.setSpan(clickableAction, startPosition, endPosition,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        targetTextView.setText(spannableString);
        targetTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }
    private SpannableStringBuilder addClickablePart(String charSequence) {

        SpannableStringBuilder ssb = new SpannableStringBuilder(charSequence);

        int idx1 = charSequence.toString().indexOf(Const.FOOTNOTE_DELIMITERS[0]);
        int idx2 = 0;
        while (idx1 != -1) {
            idx2 = charSequence.toString().indexOf(Const.FOOTNOTE_DELIMITERS[1], idx1) + 1;

            final String clickString = charSequence.toString().substring(idx1, idx2);
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    Toast.makeText(getApplicationContext(), clickString,
                            LENGTH_SHORT).show();
                }
            }, idx1, idx2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            idx1 = charSequence.toString().indexOf(Const.FOOTNOTE_DELIMITERS[0], idx2);
        }

        return ssb;
    }

    String getFootNote(int n) {

        String aDataRow = "";
        String aBuffer = "#";
        int textNr = n + 1;
        try {
            InputStream raw = getAssets().open("text" + textNr + ".txt");
            BufferedReader myReader = new BufferedReader(new InputStreamReader(raw, "UTF8"));
            while ((aDataRow = myReader.readLine()) != null) {

                if (aDataRow.contains(Const.TEXT_DELIMITERS[0])) {
                    aDataRow = myReader.readLine();
                    do {
                        if (aDataRow != null) aBuffer += aDataRow + "\n";
                        if (aDataRow == null) break;
                        aDataRow = myReader.readLine();
                    }
                    while (!aDataRow.contains(Const.TEXT_DELIMITERS[1]));

                    aBuffer += "#";
                }

            }
        } catch (IOException e) {
        }

        return aBuffer;
    }

    public void onTouchHandle() {

        if (!wasClicked) {
            textView.setHeight((int) (height * 0.75));
            tableLayout = (TableLayout) findViewById(R.id.topLayout);
            tableLayout.setVisibility(View.VISIBLE);
            mTableLayout.setVisibility(View.VISIBLE);
            mTableLayout.setMinimumHeight((int)(height*0.1));
            tableLayout.setMinimumHeight((int)(height*0.125));
            textView.setPadding(0,0,0,0);
            mSeekBar.setMinimumHeight(60);
            mSeekBar.setVisibility(View.VISIBLE);
            tabButtonAling();

            wasClicked = true;
        } else {
            textView.setHeight(height);
            tableLayout.setVisibility(View.INVISIBLE);
            mTableLayout.setVisibility(View.INVISIBLE);
            mTableLayout.setMinimumHeight(0);
            textView.setPadding(0,60,0,0);
            mSeekBar.setVisibility(View.INVISIBLE);
            mSeekBar.setMinimumHeight(1);
            wasClicked = false;
        }

    }

    void tabButtonAling() {
        tabButton1 = (Button) findViewById(R.id.backButton);
        tabButton2 = (Button) findViewById(R.id.contentButton);
        tabButton3 = (Button) findViewById(R.id.bookMarkButton);
        tabButton4 = (Button) findViewById(R.id.settingsButton);
        tabButton5 = (Button) findViewById(R.id.starButton);


        tabButton1.setWidth((int) (width * 0.2));
        tabButton2.setWidth((int) (width * 0.2));
        tabButton3.setWidth((int) (width * 0.2));
        tabButton4.setWidth((int) (width * 0.2));
        tabButton5.setWidth((int) (width * 0.2));

        tabButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tabButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

}