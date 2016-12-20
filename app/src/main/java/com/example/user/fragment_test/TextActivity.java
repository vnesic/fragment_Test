package com.example.user.fragment_test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
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
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

    PopupWindow popUp;
    TextView textView;
    static final String DEBUG_TAG="[VNesic]:TextAct";
    boolean firstEntry=false;
    GestureDetector mGestureDetector;
    private String[][] cachedText = new String[Const.MaxNumOfTexts][Const.MaxNumOfSubTexts];
    private SpannableString[] footnotes=new SpannableString[Const.MaxNumberofFootnotes];
    private String[] footnotesString=new String[Const.MaxNumberofFootnotes];
    private boolean click=false;
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
    Button tabButton1, tabButton2, tabButton3, tabButton4, tabButton5,tabButton6;
    TableLayout tableLayout;
    SeekBar seekBar;
    View v;
    private GestureDetectorCompat mDetector;
    boolean isFirstEntry=true;
    private TextView tv;
    private LinearLayout layout;
    private WindowManager.LayoutParams params;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main3);
        tv = new TextView(this);
        layout = new LinearLayout(this);
        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();
        mTableLayout=(LinearLayout)findViewById(R.id.pager_scollerTab);
        tableLayout=(TableLayout)findViewById(R.id.topLayout);
        width = d.getWidth();
        height = d.getHeight();
        tabButtonAling();

   //     UserSettings.Font=UserSettings.fonts.TIMES;

        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.NOTIFICATION_TEXT);
        registerReceiver(myRecieverD, filter);
        popUp = new PopupWindow(this);
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


        mSeekBar.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
       // mSeekBar.getThumb().setColorFilter(Color.BLACK,PorterDuff.Mode.MULTIPLY);
        mSeekBar.getRootView().setBackgroundColor(Color.BLACK);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("Hi this is a sample text for popup window");
        layout.addView(tv, params);
        popUp.setContentView(layout);
        textView = (TextView) findViewById(R.id.text);
        textView.setMovementMethod(new ScrollingMovementMethod());
        tv.setTextColor(Color.WHITE);
        int subTextIndex = getIntent().getIntExtra("index", 0);

        lastText = subTextIndex;
        lastSubtext = getIntent().getIntExtra("lastPos", 0);
        /////////[lastSubtext][lastText]//////////
        textView.setPadding(0,40,0,0);

        final ProgressDialog dialog = ProgressDialog.show(this, "Учитавање текста...", "Молимо Вас сачекајте", true);

      if (cachedText[lastSubtext][lastText] != null) {
            textView.setText(cachedText[lastText][lastText]);
            textView.setVisibility(View.VISIBLE);
        } else {

            Intent mServiceIntent = new Intent(this, ParsingService.class);
            mServiceIntent.putExtra("kind", Const.TEXT);
            mServiceIntent.putExtra("index", lastSubtext);
            mServiceIntent.putExtra("text", lastText);

            startService(mServiceIntent);
        }


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
                popUp.dismiss();
                return false;
            }
        });

        if(UserSettings.firstPass){
            UserSettings.setDefaultFontSize((int)textView.getTextSize());
            UserSettings.firstPass=false;
        }else{
            putSettings();
        }
        textView.setTextSize(TypedValue.DENSITY_DEFAULT,textView.getTextSize());

    }


    BroadcastReceiver myRecieverD = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
///////////if Const_SETTINGS_INTENT sets values from user settings to text view
//              /activates on OK on click
            if (action.equals(Const.NOTIFICATION_TEXT)) {

                String text = intent.getStringExtra("text_intent");
                String footN=intent.getStringExtra("foot_note");
                String[] parts = text.split("#");
                String[] fnTemp;
                fnTemp = footN.split("#");
                int j=0;
                for(int i=0;i<parts.length;i++) {

                    if (!parts[i].equals("")) {
                        cachedText[lastSubtext][j] = parts[i];
                        j++;
                    }
                }
                j=0;
                if(fnTemp!=null)
                for(int i=0;i<fnTemp.length;i++) {

                    if (!fnTemp[i].equals("")) {
                        footnotesString[j] = fnTemp[i];
                        j++;
                    }
                }


                final SpannableString spannableString = new SpannableString(cachedText[lastSubtext][lastText]);

                int lenght =spannableString.length();

                if(fnTemp!=null)
                for(int i=0;i<footnotesString.length;i++) {

                    if (footnotesString[i] != null) {
                        final int startIndex = cachedText[lastSubtext][lastText].indexOf(footnotesString[i]);
                        final int endIndex = startIndex + footnotesString[i].length();

                        String test = cachedText[lastSubtext][lastText];
                        int testL = cachedText[lastSubtext][lastText].length();
                        if(startIndex>=0) //could cause trouble | CHECK IF FOOTNOTE WAS FOUND, IF NOT..MEH
                        spannableString.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (click) {
                                    popUp.showAtLocation((RelativeLayout)findViewById(R.id.textFrame), Gravity.BOTTOM, 10, 10);
                                    //    public void update(int x, int y, int width, int height) {
                                    String tempS=spannableString.subSequence(startIndex,endIndex).toString();
                                    tv.setText(tempS);
                                    tv.setTextColor(Color.WHITE);

                                    popUp.update((int)(width*0.05),(int)(height*0.05),(int)(width*0.7),(int)(height*0.7));
                                    click = false;
                                } else {
                                    popUp.dismiss();
                                    click = true;
                                }
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                // this is where you set link color, underline, typeface etc.
                                int linkColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
                                ds.setColor(linkColor);
                                ds.setUnderlineText(false);
                            }
                        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else {
                        break;
                    }
                }


                    textView.setText(spannableString);
                textView.setMovementMethod(LinkMovementMethod.getInstance());

                synchronized (lock) {
                    lock.notify();
                }
            }
           // else if(Const.SETTINGS_INTENT){}
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myRecieverD != null) {
            unregisterReceiver(myRecieverD);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        putSettings();

    }

    public static void createLink(TextView targetTextView, String completeString, String partToClick, ClickableSpan clickableAction) {

        SpannableString spannableString = new SpannableString(completeString);
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
        tabButton6 =(Button)findViewById(R.id.dayNightButton);

        /*
        tabButton1.setWidth((int) (width * 0.05));
        tabButton2.setWidth((int) (width * 0.05));
        tabButton3.setWidth((int) (width * 0.05));
        tabButton4.setWidth((int) (width * 0.05));
        tabButton5.setWidth((int) (width * 0.05));
        tabButton6.setWidth((int) (width * 0.05));

        tabButton1.setHeight((int) (height * 0.05));
        tabButton2.setHeight((int) (height * 0.05));
        tabButton3.setHeight((int) (height * 0.05));
        tabButton4.setHeight((int) (height * 0.05));
        tabButton5.setHeight((int) (height * 0.05));
        tabButton6.setHeight((int) (height * 0.05));
*/
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

    void putSettings(){ //ischanged proverava da li je iz settings-a postavljeno .

        if(UserSettings.isChanged){
            UserSettings.setToDisplay();
            textView.setTextSize(TypedValue.DENSITY_DEFAULT,UserSettings.displayFontSize);

            UserSettings.isChanged=false;
        }

    }

}