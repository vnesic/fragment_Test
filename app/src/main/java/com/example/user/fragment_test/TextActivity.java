package com.example.user.fragment_test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
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
    View viewBack,viewNightDay,viewContent,viewContentSecond,viewBookmark,viewSettings,viewSearch,viewNext,viewPrev,viewText;
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
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=8){
            this.setContentView(R.layout.android_tablet3);
        }else{
            this.setContentView(R.layout.activity_main3);
        }
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

        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.NOTIFICATION_TEXT);
        registerReceiver(myRecieverD, filter);
        popUp = new PopupWindow(this);



        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("Hi this is a sample text for popup window");
        layout.addView(tv, params);
        popUp.setContentView(layout);
        textView = (TextView) findViewById(id.textview);
        viewText=findViewById(id.textview);

        textView.setMovementMethod(new ScrollingMovementMethod());
        tv.setTextColor(Color.WHITE);
        int subTextIndex = getIntent().getIntExtra("index", 0);
        int pageNumber=getIntent().getIntExtra("pageNum",0);
        UserSettings.currentPageNumber=pageNumber;
        lastText = subTextIndex;

            changeImage();
            textView.setHeight((int) (height * 0.8));
            tableLayout = (TableLayout) findViewById(id.topLayout);
            tableLayout.setVisibility(View.VISIBLE);
            tableLayout.setMinimumHeight((int)(height*0.125));
            textView.setPadding(0,0,0,0);
            tabButtonAling();
            wasClicked = true;
            changeImage();
            textView.setPadding(0,0,0,0);
            wasClicked = false;

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

//                onTouchHandle();
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                // if the second tap hadn't been released and it's being moved
 //               onTouchHandle();

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
            UserSettings.currentFontSize=(int)textView.getTextSize();
            UserSettings.firstPass=false;
        }else{
            putSettings();
        }
        float si=textView.getTextSize();
        float si2=UserSettings.displayFontSize;

        textView.setTextSize(TypedValue.DENSITY_DEFAULT,textView.getTextSize());


        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }


    BroadcastReceiver myRecieverD = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Const.NOTIFICATION_TEXT)) {

                String text = intent.getStringExtra("text_intent");
                String footN=intent.getStringExtra("foot_note");
                String[] fnTemp;
                fnTemp = footN.split("#");
                int j=0;

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
             changeImage();
            textView.setHeight((int) (height * 0.8));
            tableLayout = (TableLayout) findViewById(id.topLayout);
            tableLayout.setVisibility(View.VISIBLE);
            tableLayout.setMinimumHeight((int)(height*0.125));
            textView.setPadding(0,0,0,0);
            tabButtonAling();
            wasClicked =!wasClicked;
        } else {
            changeImage();
            textView.setPadding(0,0,0,0);
            wasClicked = !wasClicked;
        }

    }

    private void changeImage() {

        viewBack = findViewById(id.backButton);
        viewContent = findViewById(id.contentButton);
        viewBookmark =  findViewById(id.bookMarkButton);
        viewSettings = findViewById(id.settingsButton);
        viewSearch=findViewById(id.searchButton);
        viewNext=findViewById(id.nextButton);
        viewPrev=findViewById(id.prevButton);
        viewContentSecond=findViewById(id.secondContent);
        viewNightDay=findViewById(id.dayNightButton);
        if (!wasClicked) {

            if(UserSettings.day_night) {
                viewBack.setBackgroundResource(drawable.button_back_night_normal);
                viewSettings.setBackgroundResource(drawable.button_options_night_normal);
                viewBookmark.setBackgroundResource(drawable.button_bookmark_night_normal);
                viewSearch.setBackgroundResource(drawable.button_search_night_normal);
                viewNightDay.setBackgroundResource(drawable.button_mode_night_active);
                viewContent.setBackgroundColor(getResources().getColor(aseSection));
                viewContentSecond.setBackgroundColor(getResources().getColor(aseSection));

            }else{
                viewContent.setBackgroundColor(getResources().getColor(astTextNight));
                viewContentSecond.setBackgroundColor(getResources().getColor(astTextNight));

                viewBack.setBackgroundResource(drawable.button_back_normal);
                viewSettings.setBackgroundResource(drawable.button_options_normal);
                viewBookmark.setBackgroundResource(drawable.button_bookmark_normal);
                viewSearch.setBackgroundResource(drawable.button_search_normal);
                viewNightDay.setBackgroundResource(drawable.button_mode_active);
            }
        }else {
            if(UserSettings.day_night){
                viewBookmark.setBackgroundColor(getResources().getColor(aseSection));
                viewBack.setBackgroundColor(getResources().getColor(aseSection));
                viewContent.setBackgroundColor(getResources().getColor(aseSection));
                viewSettings.setBackgroundColor(getResources().getColor(aseSection));
                viewSearch.setBackgroundColor(getResources().getColor(aseSection));
                viewNext.setBackgroundColor(getResources().getColor(aseSection));
                viewPrev.setBackgroundColor(getResources().getColor(aseSection));
                viewContentSecond.setBackgroundColor(getResources().getColor(aseSection));
                viewNightDay.setBackgroundColor(getResources().getColor(aseSection));
                viewContent.setBackgroundColor(getResources().getColor(aseSection));
                viewContentSecond.setBackgroundColor(getResources().getColor(aseSection));
                textView.setBackgroundColor(getResources().getColor(aseSection));
                textView.setTextColor(getResources().getColor(astPageNight));
                nextButton.setTextColor(getResources().getColor(astPageNight));
                prevButton.setTextColor(getResources().getColor(astPageNight));
                viewPrev.setBackgroundColor(getResources().getColor(aseSection));
                v.setBackgroundColor(getResources().getColor(aseSection));
            }else {
                viewBookmark.setBackgroundColor(getResources().getColor(astTextNight));
                viewBack.setBackgroundColor(getResources().getColor(astTextNight));
                viewContent.setBackgroundColor(getResources().getColor(astTextNight));
                viewSettings.setBackgroundColor(getResources().getColor(astTextNight));
                viewSearch.setBackgroundColor(getResources().getColor(astTextNight));
                viewNext.setBackgroundColor(getResources().getColor(astTextNight));
                viewPrev.setBackgroundColor(getResources().getColor(astTextNight));
                viewContentSecond.setBackgroundColor(getResources().getColor(astTextNight));
                viewNightDay.setBackgroundColor(getResources().getColor(astTextNight));
            }
        }
    }

    void tabButtonAling() {
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/gabriola.ttf");

        tabButton1 = (Button) findViewById(id.backButton);
        tabButton3 = (Button) findViewById(id.bookMarkButton);
        tabButton4 = (Button) findViewById(id.settingsButton);
        tabButton5 = (Button) findViewById(id.searchButton);
        tabButton6 =(Button)findViewById(id.dayNightButton);
        prevButton=(Button)findViewById(id.prevButton);
        nextButton=(Button)findViewById(id.nextButton);

        prevButton.setTypeface(type);
        nextButton.setTypeface(type);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int test=UserSettings.currentPageNumber-1;
                if(test >= 0 && UserSettings.cachedText[lastText][test]!=null ){
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

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int test=UserSettings.currentPageNumber+1;
                if(test<UserSettings.cachedText[lastText].length && UserSettings.cachedText[lastText][test]!=null){
                    UserSettings.currentPageNumber++;

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

                    textView.scrollTo(0,0);
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
        viewContentSecond = findViewById(id.secondContent);

        viewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTouchHandle();
            }
        });

        viewContentSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTouchHandle();
            }
        });

        viewBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(wasClicked) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // PRESSED
                            if (UserSettings.day_night) {//active image //FALSE=day
                                viewBack.setBackgroundResource(drawable.button_back_night_active);

                            } else {
                                viewBack.setBackgroundResource(drawable.button_back_active);

                            }
                            onBackPressed();
                            return true; // if you want to handle the touch event
                        case MotionEvent.ACTION_UP:
                            // RELEASED
                            if (UserSettings.day_night) {//active image //FALSE=day
                                viewBack.setBackgroundResource(drawable.button_back_night_normal);

                            } else {
                                viewBack.setBackgroundResource(drawable.button_back_normal);

                            }
                            return true; // if you want to handle the touch event
                    }
                }else{
                    onTouchHandle();
                }
                return false;

            }
        });

        viewBookmark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (wasClicked) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            // PRESSED
                            if (UserSettings.day_night) {//active image //FALSE=day
                                viewBookmark.setBackgroundResource(drawable.button_bookmark_night_active);

                            } else {
                                viewBookmark.setBackgroundResource(drawable.button_bookmark_active);

                            }

                            if (UserSettings.existElem(lastText, UserSettings.currentPageNumber)) {

                                UserSettings.removeElem(lastText, UserSettings.currentPageNumber);
                                Toast.makeText(TextActivity.this, "Text removed from bookmarks", Toast.LENGTH_SHORT).show();

                            } else {

                                UserSettings.bookmarkItems.add(new BookmarkItem(lastText, UserSettings.currentPageNumber));
                                Toast.makeText(TextActivity.this, "Text bookmarked", Toast.LENGTH_SHORT).show();

                            }


                            return true; // if you want to handle the touch event
                        case MotionEvent.ACTION_UP:
                            // RELEASED
                            if (UserSettings.day_night) {//active image //FALSE=day
                                viewBookmark.setBackgroundResource(drawable.button_bookmark_night_normal);

                            } else {
                                viewBookmark.setBackgroundResource(drawable.button_bookmark_normal);

                            }
                            return true; // if you want to handle the touch event
                    }
                    return false;
                } else {
                    onTouchHandle();
                    return false;

                }
            }
        });


        viewSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (wasClicked) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // PRESSED
                            if (UserSettings.day_night) {//active image //FALSE=day
                                viewSettings.setBackgroundResource(drawable.button_options_night_active);

                            } else {
                                viewSettings.setBackgroundResource(drawable.button_options_active);

                            }
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), SettingActivity.class);
                            startActivity(intent);
                            return true; // if you want to handle the touch event
                        case MotionEvent.ACTION_UP:
                            // RELEASED
                            if (UserSettings.day_night) {//normal image
                                viewSettings.setBackgroundResource(drawable.button_options_night_normal);

                            } else {
                                viewSettings.setBackgroundResource(drawable.button_options_normal);

                            }
                            return true; // if you want to handle the touch event
                    }
                    return false;
                }else{
                    onTouchHandle();
                    return  false;                       }
            }
        });
        viewSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(wasClicked) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (UserSettings.day_night) {//active image //FALSE=day
                                viewSearch.setBackgroundResource(drawable.button_search_night_active);

                            } else {
                                viewSearch.setBackgroundResource(drawable.button_search_active);

                            }


                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), SearchActivity.class);
                            startActivity(intent);

                            return true; // if you want to handle the touch eventd
                        case MotionEvent.ACTION_UP:
                            if (UserSettings.day_night) {//active image //FALSE=day
                                viewSearch.setBackgroundResource(drawable.button_search_night_normal);

                            } else {
                                viewSearch.setBackgroundResource(drawable.button_search_normal);

                            }
                            return true; // if you want to handle the touch event
                    }
                    return false;
                }else{
                    onTouchHandle();
                    return false;
                }
            }
        });


        tabButton1.setWidth((int) (width * 0.05));
        //  tabButton2.setWidth((int) (width * 0.05));
        tabButton3.setWidth((int) (width * 0.05));
        tabButton4.setWidth((int) (width * 0.05));
        tabButton5.setWidth((int) (width * 0.05));
        tabButton6.setWidth((int) (width * 0.05));

        tabButton1.setHeight((int) (height * 0.05));
        //       tabButton2.setHeight((int) (height * 0.05));
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
                if(wasClicked) {
                    if (!UserSettings.day_night) {
                        view.setBackgroundResource(R.drawable.button_mode_night_active);
                        nightModeOn();
                        UserSettings.day_night = !UserSettings.day_night;
                    } else {
                        view.setBackgroundResource(drawable.button_mode_active);
                        dayModeOn();
                        UserSettings.day_night = !UserSettings.day_night;
                    }
                }else {
                    onTouchHandle();
                }
            }
        });
    }

    void putSettings(){ //ischanged proverava da li je iz settings-a postavljeno .


        if(UserSettings.isChanged){



            UserSettings.setToDisplay();


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

                    textView.setTextColor(Color.GRAY);
                    break;
                case YELLOW:

                    textView.setTextColor(Color.GRAY);
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
        float si=textView.getTextSize();
        float si2=UserSettings.displayFontSize;

        textView.setTextSize(TypedValue.DENSITY_DEFAULT,UserSettings.displayFontSize);

    }

    void nightModeOn(){
        viewContent.setBackgroundColor(getResources().getColor(aseSection));
        viewContentSecond.setBackgroundColor(getResources().getColor(aseSection));
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
//        viewContent.setBackgroundResource(drawable.button_contents_night_normal);
        viewSearch.setBackgroundResource(drawable.button_search_night_normal);
        putSettings();
    }

    void dayModeOn(){
        viewContent.setBackgroundColor(getResources().getColor(astTextNight));
        viewContentSecond.setBackgroundColor(getResources().getColor(astTextNight));
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
//        viewContent.setBackgroundResource(drawable.button_contents_normal);
        viewSearch.setBackgroundResource(drawable.button_search_normal);
        putSettings();

    }






    private class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());


        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDoubleTap(MotionEvent e) {

           //     onTouchHandle();

                return false;
            }

            @Override

            public boolean onDoubleTapEvent(MotionEvent e) {
                // if the second tap hadn't been released and it's being moved

             //   onTouchHandle();

                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }



}
