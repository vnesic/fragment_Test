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
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.TextUtils;
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
import static com.example.user.fragment_test.R.*;
import static com.example.user.fragment_test.R.color.aseSection;
import static com.example.user.fragment_test.R.color.astPageNight;
import static com.example.user.fragment_test.R.color.astTextNight;

/**
 * Created by Buljoslav on 26/11/2016.
 */

public class TextActivity extends Activity {

    PopupWindow popUp;
    TextView textView;
    static final String DEBUG_TAG="[VNesic]:TextAct";
    boolean firstEntry=false;
    GestureDetector mGestureDetector;
    private static Spanned[] cachedText = new Spanned[Const.MaxNumOfTexts];
    private SpannableString[] footnotes=new SpannableString[Const.MaxNumberofFootnotes];
    private static String[] footnotesString=new String[Const.MaxNumberofFootnotes];
    private boolean click=false;
    private int lastText = 0;
    private int lastSubtext = 0;
    private Object lock = new Object();
    Thread t;
    LinearLayout mTableLayout;
    long timePassed=0;
    boolean wasClicked = false;
    int width;
    int height;
    SpannableString toDisplay;
    Button tabButton1, tabButton2, tabButton3, tabButton4, tabButton5,tabButton6,nextButton,prevButton;
    View viewBack,viewContent,viewBookmark,viewSettings,viewSearch,viewNext,viewPrev;
    TableLayout tableLayout;
    SeekBar seekBar;
    View v;
    private GestureDetectorCompat mDetector;
    boolean isFirstEntry=true;
    private TextView tv;
    private LinearLayout layout;
    private WindowManager.LayoutParams params;
    boolean sub_or_text=false;//false subtext,text-true


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
        mTableLayout=(LinearLayout)findViewById(id.pager_scollerTab);
        tableLayout=(TableLayout)findViewById(id.topLayout);
        width = d.getWidth();
        height = d.getHeight();
        tabButtonAling();
        v=findViewById(id.textFrame);
   //     UserSettings.Font=UserSettings.fonts.TIMES;

        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.NOTIFICATION_TEXT);
        registerReceiver(myRecieverD, filter);
        popUp = new PopupWindow(this);



        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("Hi this is a sample text for popup window");
        layout.addView(tv, params);
        popUp.setContentView(layout);
        textView = (TextView) findViewById(id.text);
        textView.setMovementMethod(new ScrollingMovementMethod());
        tv.setTextColor(Color.WHITE);
        int subTextIndex = getIntent().getIntExtra("index", 0);
        int pageNumber=getIntent().getIntExtra("pageNum",0);
        UserSettings.currentPageNumber=pageNumber;
        lastText = subTextIndex;
       // lastSubtext = getIntent().getIntExtra("lastPos", 0);
        /////////[lastSubtext][lastText]//////////
        textView.setPadding(0,40,0,0);

        final ProgressDialog dialog = ProgressDialog.show(this, "Учитавање текста...", "Молимо Вас сачекајте", true);


            if (UserSettings.cachedText[lastText][UserSettings.currentPageNumber] != null) {




                final SpannableString spannableString = new SpannableString(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]);


                if(FootNotes.footNotes[lastText]!=null)
                    for(int i=0;i<FootNotes.footNotes[lastText].length;i++) {

                        if (FootNotes.footNotes[lastText][i] != null) {
                            final int startIndex, endIndex;
                            if (lastText == 1) {
                                startIndex = String.valueOf(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]).indexOf(FootNotes.text1Footnotes[i]);
                                endIndex = startIndex + FootNotes.text1Footnotes[i].length();
                                final int index=i;

                                if (startIndex >= 0) //could cause trouble | CHECK IF FOOTNOTE WAS FOUND, IF NOT..MEH
                                    spannableString.setSpan(new ClickableSpan() {
                                        @Override
                                        public void onClick(View widget) {
                                            if (click) {
                                                popUp.showAtLocation((RelativeLayout) findViewById(id.textFrame), Gravity.BOTTOM, 10, 10);
                                                //    public void update(int x, int y, int width, int height) {
                                                String tempS =FootNotes.text1[index]; //spannableString.subSequence(startIndex, endIndex).toString();
                                                Log.i("VNESIC DEB","PRE svega " +"H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));

                                                if(tempS.charAt(0)=='h'&&tempS.charAt(1)=='t'){
                                                    Log.i("VNESIC DEB","H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempS));
                                                    startActivity(browserIntent);
                                                }else {
                                                    tv.setText(tempS);
                                                    tv.setTextColor(Color.WHITE);

                                                    popUp.update((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.7), (int) (height * 0.7));
                                                    click = false;
                                                }
                                            } else {
                                                popUp.dismiss();
                                                click = true;
                                            }
                                        }

                                        @Override
                                        public void updateDrawState(TextPaint ds) {
                                            super.updateDrawState(ds);
                                            // this is where you set link color, underline, typeface etc.
                                            int linkColor = ContextCompat.getColor(getApplicationContext(), color.colorPrimary);
                                            ds.setColor(linkColor);
                                            ds.setUnderlineText(false);
                                        }
                                    }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            } else if (lastText == 8) {
                                startIndex = String.valueOf(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]).indexOf(FootNotes.text8Footnotes[i]);
                                endIndex = startIndex + FootNotes.text8Footnotes[i].length();
                                final int index=i;

                                if (startIndex >= 0) //could cause trouble | CHECK IF FOOTNOTE WAS FOUND, IF NOT..MEH
                                    spannableString.setSpan(new ClickableSpan() {
                                        @Override
                                        public void onClick(View widget) {
                                            if (click) {
                                                popUp.showAtLocation((RelativeLayout) findViewById(id.textFrame), Gravity.BOTTOM, 10, 10);
                                                //    public void update(int x, int y, int width, int height) {
                                                String tempS = FootNotes.text8[index];//spannableString.subSequence(startIndex, endIndex).toString();
                                                tv.setText(tempS);
                                                tv.setTextColor(Color.WHITE);

                                                popUp.update((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.7), (int) (height * 0.7));
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
                                            int linkColor = ContextCompat.getColor(getApplicationContext(), color.colorPrimary);
                                            ds.setColor(linkColor);
                                            ds.setUnderlineText(false);
                                        }
                                    }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            }

                        }


                    }

                textView.setText(TextUtils.concat( UserSettings.cachedSubtitles[lastText][UserSettings.currentPageNumber],spannableString));

                textView.setMovementMethod(LinkMovementMethod.getInstance());

                dialog.dismiss();
            } else {

                Intent mServiceIntent = new Intent(this, ParsingService.class);
                mServiceIntent.putExtra("kind", Const.TEXT);
                mServiceIntent.putExtra("index", lastSubtext);
                mServiceIntent.putExtra("text", lastText);

                startService(mServiceIntent);


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


            }



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
 //               String[] parts = text.split("#");
                String[] fnTemp;
                fnTemp = footN.split("#");
                int j=0;
   /*           for(int i=0;i<parts.length;i++) {

                    if (!parts[i].equals("")) {

                        UserSettings.cachedText[lastSubtext][j] =new SpannedString(parts[i]);
                        j++;
                    }
                }

*/
             //   UserSettings.cachedText[lastSubtext][UserSettings.currentPageNumber] = Html.fromHtml(text);
               /* j=0;
                if(fnTemp!=null)
                for(int i=0;i<fnTemp.length;i++) {

                    if (!fnTemp[i].equals("")) {
                        UserSettings.footnotesString[j] = fnTemp[i];
                        j++;
                    }
                }
                */

                final SpannableString spannableString = new SpannableString(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]);


                if(FootNotes.footNotes[lastText]!=null)
                for(int i=0;i<FootNotes.footNotes[lastText].length;i++) {

                    if (FootNotes.footNotes[lastText][i] != null) {
                        final int startIndex,endIndex;
                        if(lastText==1) {
                            startIndex = String.valueOf(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]).indexOf(FootNotes.text1Footnotes[i]);
                            endIndex = startIndex + FootNotes.text1Footnotes[i].length();
                            final int index=i;
                            if(startIndex>=0) //could cause trouble | CHECK IF FOOTNOTE WAS FOUND, IF NOT..MEH
                                spannableString.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        if (click) {
                                            popUp.showAtLocation((RelativeLayout)findViewById(id.textFrame), Gravity.BOTTOM, 10, 10);
                                            //    public void update(int x, int y, int width, int height) {
                                            String tempS=FootNotes.text1[index];//spannableString.subSequence(startIndex,endIndex).toString();
                                            Log.i("VNESIC DEB","PRE svega " +"H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));

                                            if(tempS.charAt(0)=='h'&&tempS.charAt(1)=='t'){
                                                Log.i("VNESIC DEB","H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));
                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempS));
                                                startActivity(browserIntent);
                                            }else {
                                                tv.setText(tempS);
                                                tv.setTextColor(Color.WHITE);

                                                popUp.update((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.7), (int) (height * 0.7));
                                                click = false;
                                            }
                                        } else {
                                            popUp.dismiss();
                                            click = true;
                                        }
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        // this is where you set link color, underline, typeface etc.
                                        int linkColor = ContextCompat.getColor(getApplicationContext(), color.colorPrimary);
                                        ds.setColor(linkColor);
                                        ds.setUnderlineText(false);
                                    }
                                }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }else if(lastText==8){
                             startIndex = String.valueOf(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]).indexOf(FootNotes.text8Footnotes[i]);
                            endIndex = startIndex + FootNotes.text8Footnotes[i].length();
                            final int index=i;

                            if(startIndex>=0) //could cause trouble | CHECK IF FOOTNOTE WAS FOUND, IF NOT..MEH
                                spannableString.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        if (click) {
                                            popUp.showAtLocation((RelativeLayout)findViewById(id.textFrame), Gravity.BOTTOM, 10, 10);
                                            //    public void update(int x, int y, int width, int height) {
                                            String tempS=FootNotes.text8[index];//spannableString.subSequence(startIndex,endIndex).toString();
                                            if(tempS.charAt(0)=='h'&&tempS.charAt(1)=='t'){
                                                Log.i("VNESIC DEB","H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));
                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempS));
                                                startActivity(browserIntent);
                                            }else {
                                                tv.setText(tempS);
                                                tv.setTextColor(Color.WHITE);

                                                popUp.update((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.7), (int) (height * 0.7));
                                                click = false;
                                            }
                                        } else {
                                            popUp.dismiss();
                                            click = true;
                                        }
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        // this is where you set link color, underline, typeface etc.
                                        int linkColor = ContextCompat.getColor(getApplicationContext(), color.colorPrimary);
                                        ds.setColor(linkColor);
                                        ds.setUnderlineText(false);
                                    }
                                }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }

                    }else {
                        break;
                    }
                }

                textView.setText(TextUtils.concat( UserSettings.cachedSubtitles[lastText][UserSettings.currentPageNumber],spannableString));
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


    public void onTouchHandle() {

        if (!wasClicked) {
            textView.setHeight((int) (height * 0.75));
            tableLayout = (TableLayout) findViewById(id.topLayout);
            tableLayout.setVisibility(View.VISIBLE);
            mTableLayout.setVisibility(View.VISIBLE);
            mTableLayout.setMinimumHeight((int)(height*0.1));
            tableLayout.setMinimumHeight((int)(height*0.125));
            textView.setPadding(0,0,0,0);

            tabButtonAling();
            viewNext.setVisibility(View.VISIBLE);
            viewPrev.setVisibility(View.VISIBLE);
            wasClicked = true;
        } else {
            textView.setHeight(height);
            tableLayout.setVisibility(View.INVISIBLE);
            mTableLayout.setVisibility(View.INVISIBLE);
            mTableLayout.setMinimumHeight(0);
            textView.setPadding(0,60,0,0);
            viewNext.setVisibility(View.INVISIBLE);

            viewPrev.setVisibility(View.INVISIBLE);
            wasClicked = false;
        }

    }

    void tabButtonAling() {
        tabButton1 = (Button) findViewById(id.backButton);
        tabButton2 = (Button) findViewById(id.contentButton);
        tabButton3 = (Button) findViewById(id.bookMarkButton);
        tabButton4 = (Button) findViewById(id.settingsButton);
        tabButton5 = (Button) findViewById(id.searchButton);
        tabButton6 =(Button)findViewById(id.dayNightButton);
        prevButton=(Button)findViewById(id.prevButton);
        nextButton=(Button)findViewById(id.nextButton);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int test=UserSettings.currentPageNumber-1;
                if(test > 0 && UserSettings.cachedText[lastText][test]!=null ){
                    UserSettings.currentPageNumber--;//should not go under 0



                    final SpannableString spannableString = new SpannableString(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]);


                    if(FootNotes.footNotes[lastText]!=null)
                        for(int i=0;i<FootNotes.footNotes[lastText].length;i++) {

                            if (FootNotes.footNotes[lastText][i] != null) {
                                final int startIndex, endIndex;
                                if (lastText == 1) {
                                    startIndex = String.valueOf(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]).indexOf(FootNotes.text1Footnotes[i]);
                                    endIndex = startIndex + FootNotes.text1Footnotes[i].length();
                                   final int index=i;
                                    if (startIndex >= 0) //could cause trouble | CHECK IF FOOTNOTE WAS FOUND, IF NOT..MEH
                                        spannableString.setSpan(new ClickableSpan() {
                                            @Override
                                            public void onClick(View widget) {
                                                if (click) {
                                                    popUp.showAtLocation((RelativeLayout) findViewById(id.textFrame), Gravity.BOTTOM, 10, 10);
                                                    //    public void update(int x, int y, int width, int height) {
                                                    String tempS =FootNotes.text1[index];// spannableString.subSequence(startIndex, endIndex).toString();
                                                    Log.i("VNESIC DEB","PRE svega " +"H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));

                                                    if(tempS.charAt(0)=='h'&&tempS.charAt(1)=='t'){
                                                        Log.i("VNESIC DEB","H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));
                                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempS));
                                                        startActivity(browserIntent);
                                                    }else {
                                                        tv.setText(tempS);
                                                        tv.setTextColor(Color.WHITE);

                                                        popUp.update((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.7), (int) (height * 0.7));
                                                        click = false;
                                                    }
                                                } else {
                                                    popUp.dismiss();
                                                    click = true;
                                                }
                                            }

                                            @Override
                                            public void updateDrawState(TextPaint ds) {
                                                super.updateDrawState(ds);
                                                // this is where you set link color, underline, typeface etc.
                                                int linkColor = ContextCompat.getColor(getApplicationContext(), color.colorPrimary);
                                                ds.setColor(linkColor);
                                                ds.setUnderlineText(false);
                                            }
                                        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                } else if (lastText == 8) {
                                    startIndex = String.valueOf(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]).indexOf(FootNotes.text8Footnotes[i]);
                                    endIndex = startIndex + FootNotes.text8Footnotes[i].length();
                                    final int index=i;
                                    if (startIndex >= 0) //could cause trouble | CHECK IF FOOTNOTE WAS FOUND, IF NOT..MEH
                                        spannableString.setSpan(new ClickableSpan() {
                                            @Override
                                            public void onClick(View widget) {
                                                if (click) {
                                                    popUp.showAtLocation((RelativeLayout) findViewById(id.textFrame), Gravity.BOTTOM, 10, 10);
                                                    //    public void update(int x, int y, int width, int height) {
                                                    String tempS = FootNotes.text8[index];//spannableString.subSequence(startIndex, endIndex).toString();
                                                    if(tempS.charAt(0)=='h'&&tempS.charAt(1)=='t'){
                                                        Log.i("VNESIC DEB","H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));
                                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempS));
                                                        startActivity(browserIntent);
                                                    }else {
                                                        tv.setText(tempS);
                                                        tv.setTextColor(Color.WHITE);

                                                        popUp.update((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.7), (int) (height * 0.7));
                                                        click = false;
                                                    }
                                                } else {
                                                    popUp.dismiss();
                                                    click = true;
                                                }
                                            }

                                            @Override
                                            public void updateDrawState(TextPaint ds) {
                                                super.updateDrawState(ds);
                                                // this is where you set link color, underline, typeface etc.
                                                int linkColor = ContextCompat.getColor(getApplicationContext(), color.colorPrimary);
                                                ds.setColor(linkColor);
                                                ds.setUnderlineText(false);
                                            }
                                        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                }

                            }


                        }







                    textView.setText(TextUtils.concat( UserSettings.cachedSubtitles[lastText][UserSettings.currentPageNumber],spannableString));

                    textView.setMovementMethod(LinkMovementMethod.getInstance());


                }

                //++add color/font settings
                //++ send intent to open current Page
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int test=UserSettings.currentPageNumber+1;
                if(test<UserSettings.cachedText[lastText].length && UserSettings.cachedText[lastText][test]!=null){
                 UserSettings.currentPageNumber++;//should not go over lengt()-1;







                    final SpannableString spannableString = new SpannableString(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]);


                    if(FootNotes.footNotes[lastText]!=null)
                        for(int i=0;i<FootNotes.footNotes[lastText].length;i++) {
                            if (FootNotes.footNotes[lastText][i] != null) {
                                final int startIndex, endIndex;
                                if (lastText == 1) {
                                    startIndex = String.valueOf(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]).indexOf(FootNotes.text1Footnotes[i]);
                                    endIndex = startIndex + FootNotes.text1Footnotes[i].length();
                                    final int index=i;
                                    if (startIndex >= 0) //could cause trouble | CHECK IF FOOTNOTE WAS FOUND, IF NOT..MEH
                                        spannableString.setSpan(new ClickableSpan() {
                                            @Override
                                            public void onClick(View widget) {
                                                if (click) {
                                                    popUp.showAtLocation((RelativeLayout) findViewById(id.textFrame), Gravity.BOTTOM, 10, 10);
                                                    //    public void update(int x, int y, int width, int height) {
                                                    String tempS = FootNotes.text1[index];//spannableString.subSequence(startIndex, endIndex).toString();
                                                    Log.i("VNESIC DEB","PRE svega " +"H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));

                                                    if(tempS.charAt(0)=='h'&&tempS.charAt(1)=='t'){
                                                        Log.i("VNESIC DEB","H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));
                                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempS));
                                                        startActivity(browserIntent);
                                                    }else {
                                                        tv.setText(tempS);
                                                        tv.setTextColor(Color.WHITE);

                                                        popUp.update((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.7), (int) (height * 0.7));
                                                        click = false;
                                                    }
                                                } else {
                                                    popUp.dismiss();
                                                    click = true;
                                                }
                                            }

                                            @Override
                                            public void updateDrawState(TextPaint ds) {
                                                super.updateDrawState(ds);
                                                // this is where you set link color, underline, typeface etc.
                                                int linkColor = ContextCompat.getColor(getApplicationContext(), color.colorPrimary);
                                                ds.setColor(linkColor);
                                                ds.setUnderlineText(false);
                                            }
                                        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                } else if (lastText == 8) {
                                    startIndex = String.valueOf(UserSettings.cachedText[lastText][UserSettings.currentPageNumber]).indexOf(FootNotes.text8Footnotes[i]);
                                    endIndex = startIndex + FootNotes.text8Footnotes[i].length();
                                    final int index=i;

                                    if (startIndex >= 0) //could cause trouble | CHECK IF FOOTNOTE WAS FOUND, IF NOT..MEH
                                        spannableString.setSpan(new ClickableSpan() {
                                            @Override
                                            public void onClick(View widget) {
                                                if (click) {
                                                    popUp.showAtLocation((RelativeLayout) findViewById(id.textFrame), Gravity.BOTTOM, 10, 10);
                                                    //    public void update(int x, int y, int width, int height) {
                                                    String tempS = FootNotes.text8[index];//spannableString.subSequence(startIndex, endIndex).toString();
                                                    if(tempS.charAt(0)=='h'&&tempS.charAt(1)=='t'){
                                                        Log.i("VNESIC DEB","H :"+tempS.charAt(0)+"  T: "+tempS.charAt(1));
                                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempS));
                                                        startActivity(browserIntent);
                                                    }else {
                                                        tv.setText(tempS);
                                                        tv.setTextColor(Color.WHITE);

                                                        popUp.update((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.7), (int) (height * 0.7));
                                                        click = false;
                                                    }
                                                } else {
                                                    popUp.dismiss();
                                                    click = true;
                                                }
                                            }

                                            @Override
                                            public void updateDrawState(TextPaint ds) {
                                                super.updateDrawState(ds);
                                                // this is where you set link color, underline, typeface etc.
                                                int linkColor = ContextCompat.getColor(getApplicationContext(), color.colorPrimary);
                                                ds.setColor(linkColor);
                                                ds.setUnderlineText(false);
                                            }
                                        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                }

                            }


                        }









                    textView.setText(TextUtils.concat( UserSettings.cachedSubtitles[lastText][UserSettings.currentPageNumber],spannableString));

                    textView.setMovementMethod(LinkMovementMethod.getInstance());


                }

                //++add color/font settings

                //++ send intent to open current Page
            }
        });
        viewBack = findViewById(id.backButton);
        viewContent = findViewById(id.contentButton);
        viewBookmark =  findViewById(id.bookMarkButton);
        viewSettings = findViewById(id.settingsButton);
        viewSearch=findViewById(id.searchButton);
        viewNext=findViewById(id.nextButton);
        viewPrev=findViewById(id.prevButton);

        viewBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        if(UserSettings.day_night){//active image //FALSE=day
                            viewBack.setBackgroundResource(drawable.button_back_night_active);

                        }else {
                            viewBack.setBackgroundResource(drawable.button_back_active);

                        }
                        onBackPressed();
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        if(UserSettings.day_night){//active image //FALSE=day
                            viewBack.setBackgroundResource(drawable.button_back_night_normal);

                        }else {
                            viewBack.setBackgroundResource(drawable.button_back_normal);

                        }
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        viewContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        if(UserSettings.day_night){//active image //FALSE=day
                            viewContent.setBackgroundResource(drawable.button_contents_night_active);

                        }else {
                            viewContent.setBackgroundResource(drawable.button_contents_active);

                        }
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        if(UserSettings.day_night){//active image //FALSE=day
                            viewContent.setBackgroundResource(drawable.button_contents_night_normal);

                        }else {
                            viewContent.setBackgroundResource(drawable.button_contents_normal);

                        }
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        viewBookmark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        if(UserSettings.day_night){//active image //FALSE=day
                            viewBookmark.setBackgroundResource(drawable.button_bookmark_night_active);

                        }else {
                            viewBookmark.setBackgroundResource(drawable.button_bookmark_active);

                        }

                        if(UserSettings.existElem(lastText,UserSettings.currentPageNumber)){

                            UserSettings.removeElem(lastText,UserSettings.currentPageNumber);
                            Toast.makeText(TextActivity.this, "Text removed from bookmarks", Toast.LENGTH_SHORT).show();

                        }else {

                            UserSettings.bookmarkItems.add(new BookmarkItem(lastText,UserSettings.currentPageNumber));
                            Toast.makeText(TextActivity.this, "Text bookmarked", Toast.LENGTH_SHORT).show();

                        }


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        if(UserSettings.day_night){//active image //FALSE=day
                            viewBookmark.setBackgroundResource(drawable.button_bookmark_night_normal);

                        }else {
                            viewBookmark.setBackgroundResource(drawable.button_bookmark_normal);

                        }
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        viewSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        if(UserSettings.day_night){//active image //FALSE=day
                            viewSettings.setBackgroundResource(drawable.button_options_night_active);

                        }else {
                            viewSettings.setBackgroundResource(drawable.button_options_active);

                        }
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        if(UserSettings.day_night){//normal image
                            viewSettings.setBackgroundResource(drawable.button_options_night_normal);

                        }else {
                            viewSettings.setBackgroundResource(drawable.button_options_normal);

                        }
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });
        viewSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(UserSettings.day_night){//active image //FALSE=day
                            viewSearch.setBackgroundResource(drawable.button_search_night_active);

                        }else {
                            viewSearch.setBackgroundResource(drawable.button_search_active);

                        }


                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        if(UserSettings.day_night){//active image //FALSE=day
                            viewSearch.setBackgroundResource(drawable.button_search_night_normal);

                        }else {
                            viewSearch.setBackgroundResource(drawable.button_search_normal);

                        }
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


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


        tabButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!UserSettings.day_night){
                    view.setBackgroundResource(R.drawable.button_mode_night_active);
                    nightModeOn();
                    UserSettings.day_night=!UserSettings.day_night;
                }else {
                    view.setBackgroundResource(drawable.button_mode_active);
                    dayModeOn();
                    UserSettings.day_night=!UserSettings.day_night;
                }
            }
        });
    }

    void putSettings(){ //ischanged proverava da li je iz settings-a postavljeno .


        if(UserSettings.isChanged){



            UserSettings.setToDisplay();


            textView.setTextSize(TypedValue.DENSITY_DEFAULT,UserSettings.displayFontSize);
            switch (UserSettings.displayBackgroundColor){

                case RED:
                    textView.setBackgroundColor(Color.RED);
                    break;
                case BLUE:

                    textView.setBackgroundColor(Color.BLUE);
                    break;
                case YELLOW:

                    textView.setBackgroundColor(getResources().getColor(astTextNight));
                    break;
                case WHITE:

                    textView.setBackgroundColor(Color.WHITE);
                    break;
            }
            switch (UserSettings.displayFontColor){

                case RED:
                    textView.setTextColor(Color.RED);
                    break;
                case BLUE:

                    textView.setTextColor(Color.BLUE);
                    break;
                case YELLOW:

                    textView.setTextColor(getResources().getColor(astPageNight));
                    break;
                case BLACK:

                    textView.setTextColor(Color.BLACK);
                    break;
            }
            switch (UserSettings.displayFont){

                case CALIBRI:
                    Typeface type1 = Typeface.createFromAsset(getAssets(),"fonts/calibri.ttf");
                    textView.setTypeface(type1);
                    break;
                case ARIAL:
                    Typeface type2 = Typeface.createFromAsset(getAssets(),"fonts/arial.ttf");
                    textView.setTypeface(type2);
                    break;
                case GABRIOLA:
                    Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/gabriola.ttf");
                    textView.setTypeface(type3);
                    break;
                case DEFAULT:
                   // Typeface type5 = Typeface.createFromAsset(getAssets(),"fonts/gabriola.ttf");
                    textView.setTypeface(Typeface.DEFAULT);
                    break;
                case PALATINO:
                    Typeface type5 = Typeface.createFromAsset(getAssets(),"fonts/palatino_linotype.ttf");
                    textView.setTypeface(type5);
                    break;
            }
            UserSettings.isChanged=false;
        }

    }

    void nightModeOn(){

        textView.setBackgroundColor(getResources().getColor(aseSection));
        textView.setTextColor(getResources().getColor(astPageNight));
        viewNext.setBackgroundColor(getResources().getColor(aseSection));
        nextButton.setTextColor(getResources().getColor(astPageNight));
        prevButton.setTextColor(getResources().getColor(astPageNight));
        viewPrev.setBackgroundColor(getResources().getColor(aseSection));
        v.setBackgroundColor(getResources().getColor(aseSection));
        viewBack.setBackgroundResource(drawable.button_back_night_normal);
        viewSettings.setBackgroundResource(drawable.button_options_night_normal);
        viewBookmark.setBackgroundResource(drawable.button_bookmark_night_normal);
        viewContent.setBackgroundResource(drawable.button_contents_night_normal);
        viewSearch.setBackgroundResource(drawable.button_search_night_normal);
    }

    void dayModeOn(){

        textView.setBackgroundColor(getResources().getColor(astTextNight));
        textView.setTextColor(Color.BLACK);
        viewNext.setBackgroundColor(getResources().getColor(astTextNight));
        viewPrev.setBackgroundColor(getResources().getColor(astTextNight));
        nextButton.setTextColor(Color.BLACK);
        prevButton.setTextColor(Color.BLACK);
        v.setBackgroundColor(getResources().getColor(astTextNight));
        viewBack.setBackgroundResource(drawable.button_back_normal);
        viewSettings.setBackgroundResource(drawable.button_options_normal);
        viewBookmark.setBackgroundResource(drawable.button_bookmark_normal);
        viewContent.setBackgroundResource(drawable.button_contents_normal);
        viewSearch.setBackgroundResource(drawable.button_search_normal);

    }
    }

