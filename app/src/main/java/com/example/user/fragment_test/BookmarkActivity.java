package com.example.user.fragment_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
        if(UserSettings.bookmarkArray.length!=0) {
            for (int i = 0; i < UserSettings.bookmarkArray.length; ++i) {
                if(UserSettings.bookmarkArray[i]==true)
                list.add(Const.TITLES[i]);
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
                    intent.setClass(getApplicationContext(), TextActivity.class);
                    intent.putExtra("index", get_index(list.get(position)));
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

    int get_index(String n){

        for(int i=0;i<Const.TITLES.length;i++){
            if(Const.TITLES[i].equals(n))return i;
        }
        return 0;
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
