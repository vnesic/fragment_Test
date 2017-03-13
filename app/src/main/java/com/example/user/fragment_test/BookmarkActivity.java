package com.example.user.fragment_test;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.user.fragment_test.FeedReaderContract.FeedEntry.TABLE_NAME;

/**
 * Created by Buljoslav on 26/12/2016.
 */

public class BookmarkActivity extends Activity {

    ListView listview;
    final ArrayList<String> list = new ArrayList<String>();
    String file_name="";
    String file_name2="";
    FeedReaderDbHelper mDbHelper;
    SQLiteDatabase db ;
    Button back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bookmark_layout);
        file_name="fileTe";
        file_name2="filet2";
        listview = (ListView) findViewById(R.id.listview);

        mDbHelper = new FeedReaderDbHelper(getApplicationContext());

        db= mDbHelper.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (mCursor.moveToFirst())
        {
            //procitaj iz baze
            //vidi ima li svega i ovamo,ako nema dodaj
            //ucitaj iz baze u listu.
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    FeedReaderContract.FeedEntry._ID,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
            };

// How you want the results sorted in the resulting Cursor
            String sortOrder =
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";
            mCursor = db.query(
                    FeedReaderContract.FeedEntry.TABLE_NAME,  // The table to query
                    projection,                               // The columns to return
                    null,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );
            List itemIds = new ArrayList<>();
            while(mCursor.moveToNext()) {
                long itemId = mCursor.getLong(
                        mCursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));

                long itemId2 = mCursor.getLong(
                        mCursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));

                if(!UserSettings.existElem((int)itemId2,(int)itemId)){
                    UserSettings.addElem((int)itemId2,(int)itemId);
                }
                itemIds.add(itemId);
            }
            mCursor.close();


        } else
        {
            // I AM EMPTY
        }


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


    @Override
    protected void onDestroy() {
        if(db.isOpen())
        db.delete(TABLE_NAME, null, null);
        ContentValues values;
        for(int i=0;i<UserSettings.bookmarkItems.size();i++){
            values= new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, UserSettings.bookmarkItems.get(i).textNum);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, UserSettings.bookmarkItems.get(i).pageNum);
            long newRowId = db.insert(TABLE_NAME, null, values);
        }

        mDbHelper.close();
        //fill_File();String yourFilePath = getApplicationContext().getFilesDir() + "/" + file_name2;

        super.onDestroy();
      //  File yourFile = new File( yourFilePath );
      //  deleteDirectory(yourFile);
    }

    @Override
    protected void onStop()
    {
    /*    if(db.isOpen())
        db.delete(TABLE_NAME, null, null);
        ContentValues values;
        for(int i=0;i<UserSettings.bookmarkItems.size();i++){
            values= new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, UserSettings.bookmarkItems.get(i).textNum);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, UserSettings.bookmarkItems.get(i).pageNum);
            long newRowId = db.insert(TABLE_NAME, null, values);
        }

        mDbHelper.close();*/
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
