package com.example.user.fragment_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Buljoslav on 26/12/2016.
 */

public class BookmarkActivity extends Activity {

    ListView listview;
    final ArrayList<String> list = new ArrayList<String>();

    Button back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bookmark_layout);
        listview = (ListView) findViewById(R.id.listview);
       /// UserSettings.checked=check();
        if(UserSettings.checked)UserSettings.bookmarkItems.clear();

        load_FromFile();

        //postavi fleg,ako je menjan i ima nesto u fajlu,isprazni bookmark items

        if(UserSettings.bookmarkItems.size()!=0) {
            for (int i = 0; i < UserSettings.bookmarkItems.size(); ++i) {
                list.add(UserSettings.bookmarkItems.get(i).bookmarkName);
              }
        }else {
            list.add("No bookmarks");
        }

        final BookmarkActivity.StableArrayAdapter adapter = new BookmarkActivity.StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                    Intent intent = new Intent();

                    UserSettings.bookmarkItems.get(position);
                    intent.setClass(getApplicationContext(), TextActivity.class);
                    intent.putExtra("index", UserSettings.bookmarkItems.get(position).textNum);
                //    UserSettings.currentPageNumber=UserSettings.bookmarkItems.get(position).pageNum;
                    intent.putExtra("pageNum",UserSettings.bookmarkItems.get(position).pageNum);
                    startActivity(intent);

            }

        });
        back=(Button)findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

/*
* bookmark item : public int textNum;

                  public int pageNum;

                  #textNum#pageNum#;
                  #  int  #  int  # String
* */

    boolean check(){
        try {

            InputStream inputStream = openFileInput("apologet_bookmarks");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                receiveString = bufferedReader.readLine();

                if(receiveString!=null){
                    if(receiveString.contains("changed"))return true;
                }
                inputStream.close();
            }
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        return false;

    }
    void fill_File(){
        String data="#";
        String changed="!changed!\n";
            try {
                FileOutputStream outputStreamWriter = openFileOutput("apologet_bookmarks", Context.MODE_PRIVATE);
                outputStreamWriter.write(changed.getBytes());

                if(UserSettings.bookmarkItems.size()!=0)
                for(int i=0;i<UserSettings.bookmarkItems.size();i++) {
                    data+=UserSettings.bookmarkItems.get(i).textNum+"#"+UserSettings.bookmarkItems.get(i).pageNum;
                    outputStreamWriter.write(data.getBytes());
                    data="#";
                }
                outputStreamWriter.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }

    }


    void load_FromFile(){

        try {
            InputStream inputStream = openFileInput("apologet_bookmarks");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                int textNum,pageNum;
                String [] values;
                receiveString = bufferedReader.readLine();//preskace red
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                 //   stringBuilder.append(receiveString);
                    values=receiveString.split("#");
                    textNum=Integer.parseInt(values[1]);
                    pageNum=Integer.parseInt(values[2]);
                    UserSettings.addElem(textNum,pageNum);
                }

                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fill_File();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        UserSettings.checked=check();
        if(UserSettings.checked)UserSettings.bookmarkItems=null;
        load_FromFile();
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
}
