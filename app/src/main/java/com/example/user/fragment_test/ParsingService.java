package com.example.user.fragment_test;


import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Buljoslav on 27/11/2016.
 */

public class ParsingService extends Service {


    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private String textToReturn="";

    private String retString="";
    private String pathToFile="/src/main/assets";


    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {


            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
     /*   Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
*/
        int caseswitch = intent.getIntExtra("kind", 10);

        switch (caseswitch){

            case Const.SUBTITLE:

                int subTextIndex = intent.getIntExtra("index",0);

                retString=parseSubtitles(subTextIndex);
                //send intent to Detailst Activity
                Intent subtitleIntent = new Intent(Const.NOTIFICATION_SUB);
                subtitleIntent.putExtra("subtitle_intent", retString);
                sendBroadcast(subtitleIntent);
                break;

            case Const.TEXT:

                subTextIndex = intent.getIntExtra("index",0);
                //send intent to Text Activity
                retString=parseText(subTextIndex);
                Intent textIntent = new Intent(Const.NOTIFICATION_TEXT);
                textIntent.putExtra("text_intent", retString);
                sendBroadcast(textIntent);
                break;

        }

        // If we get killed, after returning from here, restart
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    String parseSubtitles(int n) {

        String aDataRow = "";
        String aBuffer = "#";

      try {


          InputStream raw = getAssets().open("text" + n + ".txt");
          BufferedReader myReader = new BufferedReader(new InputStreamReader(raw, "UTF8"));

          //how many row
          int ip = 0;

          while ((aDataRow = myReader.readLine()) != null) {
              if (aDataRow.equals(Const.SUBTITLE_DELIMITERS[0])) {
                  aDataRow = myReader.readLine();
                  aBuffer += aDataRow + "#";
                  if (!aDataRow.equals(Const.SUBTITLE_DELIMITERS[1])) {
                      Log.d("PARSER", "Error while parsing");
                  }
              }
          }

      }catch (IOException e){}

        return aBuffer;
    }

    String parseText(int n) {

        String aDataRow = "";
        String aBuffer = "#";
        try{
            InputStream raw = getAssets().open("text"+n+".txt");
            BufferedReader myReader = new BufferedReader(new InputStreamReader(raw, "UTF8"));
                while ((aDataRow = myReader.readLine()) != null) {
                   if(aDataRow!=null)
                    if (aDataRow.equals(Const.TEXT_DELIMITERS[0])) {
                        while (!aDataRow.equals(Const.TEXT_DELIMITERS[1])) {
                            aDataRow = myReader.readLine();
                            aBuffer += aDataRow + "\n";
                        }
                        aBuffer += "#";
                    }
                }
        }catch (IOException e){}

        return aBuffer;
    }


}
