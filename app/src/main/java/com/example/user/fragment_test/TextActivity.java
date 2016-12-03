package com.example.user.fragment_test;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Buljoslav on 26/11/2016.
 */

public class TextActivity extends FragmentActivity {
    TextView textView;
    //
    private String[][] cachedText= new String[Const.MaxNumOfTexts][Const.MaxNumOfSubTexts];
    private int lastText=0;
    private int lastSubtext=0;
    private Object lock = new Object();
    Thread t;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main3);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.NOTIFICATION_TEXT);
        registerReceiver(myRecieverD, filter);

        textView = (TextView) findViewById(R.id.text);
        textView.setMovementMethod(new ScrollingMovementMethod());

        int subTextIndex = getIntent().getIntExtra("index",0);
        //subTextIndex++;
        lastText=subTextIndex;
        lastSubtext=getIntent().getIntExtra("lastPos",0);

        final ProgressDialog dialog = ProgressDialog.show(this, "Initalising list", "Please wait", true);


        Intent mServiceIntent = new Intent(this, ParsingService.class);
        mServiceIntent.putExtra("kind", Const.TEXT);
        mServiceIntent.putExtra("index",subTextIndex);
        startService(mServiceIntent);



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

        if(cachedText[lastText][subTextIndex]==null) {
            //add to cached text the text
            textView.setText(Shakespeare.DIALOGUE[subTextIndex]);
            textView.setVisibility(View.VISIBLE);
        }else{
            lastSubtext=subTextIndex;
            textView.setText(cachedText[lastText][subTextIndex]);
            textView.setVisibility(View.VISIBLE);
        }
    }

    BroadcastReceiver myRecieverD = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Const.NOTIFICATION_TEXT)){

                String text=intent.getStringExtra("text_intent");

                String[] parts = text.split("#");

                for(int i=0;i<parts.length;i++) {
                    if(!parts[i].equals(""))cachedText[lastSubtext][i]=parts[i];
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
}
