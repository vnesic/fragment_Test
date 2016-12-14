package com.example.user.fragment_test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.security.AccessController.getContext;

public class DetailsActivity extends Activity {

    final ArrayList<String> list = new ArrayList<String>();

    ListView listview;

    int lastPos=0;
    int width;
    int height;
    TextView title;
    Button backButton;
    Button backButtonDummy;
    boolean isAuthor=false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    StableArrayAdapter adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Details Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
    private Object lock = new Object();

    void initFunction(ArrayList<String> list){

        adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), TextActivity.class);
                intent.putExtra("index", position);
                intent.putExtra("lastPos",lastPos);
                startActivity(intent);
            }

        });
        adapter.notifyDataSetChanged();
    }

    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main2);
        Button bDummy=(Button)findViewById(R.id.backButtonDummy);
        bDummy.setVisibility(View.INVISIBLE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.NOTIFICATION_SUB);
        registerReceiver(myRecieverD, filter);

        listview = (ListView) findViewById(R.id.listview2);
        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();

        width = d.getWidth();
        height = d.getHeight();
        int subTextIndex = getIntent().getIntExtra("index",0);
       // subTextIndex++;
        lastPos=subTextIndex;

        title=(TextView)findViewById(R.id.subtitleTitle);
        title.setText(Const.TITLES[subTextIndex]);

        backButton=(Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        backButtonDummy=(Button)findViewById(R.id.backButtonDummy);
        if(subTextIndex==0){
            isAuthor=true;
        }else {
            isAuthor=false;
        }
        if(!isAuthor) {
            Intent mServiceIntent = new Intent(this, ParsingService.class);
            mServiceIntent.putExtra("kind", Const.SUBTITLE);
            mServiceIntent.putExtra("index", subTextIndex);
            startService(mServiceIntent);
            tabButtonAling();
       /*for (int i = 0; i < Shakespeare.SUBTITLES.length; ++i) {
            list.add(Shakespeare.SUBTITLES[i]);
        }*/
            final ProgressDialog dialog = ProgressDialog.show(this, "Учитавање садржаја...", "Молимо Вас сачекајте", true);

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


        }else {

            setContentView(R.layout.author);
            TextView biography=(TextView) findViewById(R.id.authorBiography);
            biography.setText(Const.authorBio[0]);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    BroadcastReceiver myRecieverD = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Const.NOTIFICATION_SUB)) {
                String text = intent.getStringExtra("subtitle_intent");

                String[] parts = text.split("#");

                for(int i=0;i<parts.length;i++) {
                    if(!parts[i].equals(""))list.add(parts[i]);
                }
                synchronized (lock){
                    lock.notify();
                }
                initFunction(list);

            }
        }
    };

    void tabButtonAling() {
        backButton = (Button) findViewById(R.id.backButton);
        backButtonDummy = (Button) findViewById(R.id.backButtonDummy);
        backButton.setWidth((int) (width * 0.2));
        backButtonDummy.setWidth((int) (width * 0.2));
        title.setWidth((int) (width * 0.6));
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myRecieverD != null) {
            unregisterReceiver(myRecieverD);
        }
    }

}
