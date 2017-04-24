/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.user.fragment_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {
    ListView listview;
    View mBookmarkView;

    Button b1,b2,b3,b4,b5,b7,b6,b8,b9,b10,b11,b12,b13,b14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/gabriola.ttf");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=8){
            this.setContentView(R.layout.android_tablet);
        }else{
            this.setContentView(R.layout.activity_main1);
        }
        b1=(Button)findViewById(R.id.text1);
        b2=(Button)findViewById(R.id.text2);
        b3=(Button)findViewById(R.id.text3);
        b4=(Button)findViewById(R.id.text4);
        b5=(Button)findViewById(R.id.text5);
        b6=(Button)findViewById(R.id.text6);
        b7=(Button)findViewById(R.id.text7);
        b8=(Button)findViewById(R.id.text8);
        b9=(Button)findViewById(R.id.text9);
        b10=(Button)findViewById(R.id.text10);
        b11=(Button)findViewById(R.id.text11);
        b12=(Button)findViewById(R.id.text12);
        b13=(Button)findViewById(R.id.text13);
        b14=(Button)findViewById(R.id.text14);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(1);
            }
        });
        b1.setTypeface(type);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(2);
            }
        });
        b2.setTypeface(type);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(3);
            }
        });
        b3.setTypeface(type);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(4);
            }
        });
        b4.setTypeface(type);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(5);
            }
        });
        b5.setTypeface(type);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(7);
            }
        });
        b7.setTypeface(type);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(6);
            }
        });
        b6.setTypeface(type);

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(8);
            }
        });
        b8.setTypeface(type);

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(9);
            }
        });
        b9.setTypeface(type);
        b10.setTypeface(type);
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(10);
            }
        });
        b11.setTypeface(type);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(11);
            }
        });
        b12.setTypeface(type);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(12);
            }
        });
        b13.setTypeface(type);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(13);
            }
        });
        b14.setTypeface(type);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(14);
            }
        });;


        /*
       listview = (ListView) findViewById(R.id.listview);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < Const.TITLES.length; ++i) {
            list.add(Const.TITLES[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {


                if(position!=UserSettings.crossNumber) {
                    Intent intent = new Intent();
                    if (position != UserSettings.authorTextNumber) {
                        intent.setClass(getApplicationContext(), TextActivity.class);
                        intent.putExtra("index", position);
                    } else {

                        intent.setClass(getApplicationContext(), AuthorActivity.class);

                    }
                    startActivity(intent);
                }
            }

        });
*/
        mBookmarkView=findViewById(R.id.bookMarkButton);


        mBookmarkView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        mBookmarkView.setBackgroundResource(R.drawable.button_bookmark_active);
                        Intent intent = new Intent();
                        intent.setClass(getApplication(), BookmarkActivity.class);
                        startActivity(intent);



                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        mBookmarkView.setBackgroundResource(R.drawable.button_bookmark_normal);

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });
        
    }

    void sendIntent(int p){
        int position=p-1;
        if(position!=UserSettings.crossNumber) {
            Intent intent = new Intent();
            if (position != UserSettings.authorTextNumber) {
                intent.setClass(getApplicationContext(), TextActivity.class);
                intent.putExtra("index", position);
            } else {

                intent.setClass(getApplicationContext(), AuthorActivity.class);

            }
            startActivity(intent);
        }



    }
    public class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
        private Context mContext;
        private int id;
        private List <String>items ;
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
