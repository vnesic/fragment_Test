package com.example.user.fragment_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import static com.example.user.fragment_test.UserSettings.cachedText;

import static com.example.user.fragment_test.UserSettings.cachedSubtitles;

/**
 * Created by Buljoslav on 22/01/2017.
 */

public class SearchActivity extends Activity {
    EditText searchBar;
    List<Elem> myList = new ArrayList<Elem>();
    ListView listview;
    ArrayList<String> temp_list = new ArrayList<String>();
    Button searchButton;
    String text;
    private Button backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=8){
            this.setContentView(R.layout.android_tablet_search);
        }else{
            this.setContentView(R.layout.search_layout);

        }
        backButton=(Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchBar=(EditText)findViewById(R.id.search_bar);
        listview=(ListView)findViewById(R.id.listview);
        searchButton=(Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text=searchBar.getText().toString();
                search(text);
                final ArrayList<String> list =temp_list;
                final ArrayAdapter adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, list);
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
            }
        });

    }

    void search(String text) {

        if(!text.equals("")) {
            int lastIndex = 0;
            int count = 0;

            for (int i = 0; i < Const.MaxNumOfTexts; i++) {

                Elem e = new Elem(i);

                for (int j = 0; j < Const.MaxNumOfSubTexts; j++) {

                    while (lastIndex != -1) {
                        if (cachedText[i][j] != null) {
                            lastIndex = (cachedText[i][j].toString()).indexOf(text, lastIndex);
                            int tempIndex = lastIndex;
                            if (lastIndex != -1) {
                                count++;
                                lastIndex += text.length();
                                e.insertPage(j);
                                e.insertText(cachedText[i][j].toString(), tempIndex);
                                //    temp_list.add(cachedText[i][j].toString());
                            }
                        } else {
                            break;
                        }
                    }
                }
            }

            lastIndex = 0;
            count = 0;


            for (int i = 0; i < Const.MaxNumOfTexts; i++) {

                Elem e = new Elem(i);

                for (int j = 0; j < Const.MaxNumOfSubTexts; j++) {

                    while (lastIndex != -1) {
                        if (cachedText[i][j] != null) {
                            lastIndex = (cachedSubtitles[i][j].toString()).indexOf(text, lastIndex);
                            int tempIndex = lastIndex;
                            if (lastIndex != -1) {
                                count++;
                                lastIndex += text.length();
                                e.insertPage(j);
                                e.insertText(cachedText[i][j].toString(), tempIndex);
                                //         temp_list.add(cachedText[i][j].toString());
                            }
                        } else {
                            break;
                        }
                    }
                    myList.add(e);
                }
            }
        }
    }






    class Elem {
        int textNumber;
        List<Integer> pages = new ArrayList<Integer>();
        List<String> textToShow = new ArrayList<String>();
        Elem(int n){
            textNumber=n;

        }
        void insertPage(int n){
            pages.add(n);
        }
        void insertText(String s,int i){//cachedText[i][j],cashedSub[i],[j], j
            String ss="";
            if((i-15)<0) {
                if((i+15)>s.length()){
                ss = s.substring(0,i)+s.substring(i, s.length()-1);}
                else{
                    ss = s.substring(0,i)+s.substring(i, i+15);}

            }else {
            if((i+15)>s.length()){
                ss = s.substring(i-15,i)+s.substring(i, s.length()-1);}
            else{
                ss = s.substring(i-15,i)+s.substring(i, i+15);}
            }
            textToShow.add(ss);
            temp_list.add(ss);
        }

    }



}
