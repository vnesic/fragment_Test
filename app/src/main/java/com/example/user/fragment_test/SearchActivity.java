package com.example.user.fragment_test;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.example.user.fragment_test.UserSettings.cachedText;

import static com.example.user.fragment_test.UserSettings.cachedSubtitles;

/**
 * Created by Buljoslav on 22/01/2017.
 */
/*
*
 String str = "helloslkhellodjladfjhello";
String findStr = "hello";
int lastIndex = 0;
int count = 0;

while(lastIndex != -1){

    lastIndex = str.indexOf(findStr,lastIndex);

    if(lastIndex != -1){
        count ++;
        lastIndex += findStr.length();
    }
}
*
* */
public class SearchActivity extends Activity {
    EditText searchBar;
    List<Elem> myList = new ArrayList<Elem>();
    ListView listview;
    Button searchButton;
    String text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.search_layout);

        searchBar=(EditText)findViewById(R.id.search_bar);
        listview=(ListView)findViewById(R.id.listview);
        searchButton=(Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text=searchBar.getText().toString();
                search(text);

            }
        });

    }

    void search(String text){

        for(int i=0;i<Const.MaxNumOfTexts;i++){
            Elem e=new Elem(i);
            for(int j=0;j<Const.MaxNumOfSubTexts;j++) {

                int lastIndex = 0;
                int count = 0;

                while(lastIndex != -1){

                    lastIndex = (cachedText[i][j].toString()).indexOf(text,lastIndex);
                    int tempIndex=lastIndex;
                    if(lastIndex != -1){
                        count ++;
                        lastIndex += text.length();
                        e.insertPage(j);
                        e.insertText(cachedText[i][j].toString(),tempIndex);
                    }
                }
                lastIndex=0;
                count = 0;

                while(lastIndex != -1){

                    lastIndex = (cachedSubtitles[i][j].toString()).indexOf(text,lastIndex);
                    int tempIndex=lastIndex;
                    if(lastIndex != -1){
                        count ++;
                        lastIndex += text.length();
                        e.insertPage(j);
                        e.insertText(cachedText[i][j].toString(),tempIndex);

                    }
                }
                myList.add(e);
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
            int a3=i-1;int a2=i-2;int a1=i-3;int a4=i;int a5=i+1;int a6=i+2;int a7=i+2;
            String ss="";
            if(a1>=0){
                ss+=s.indexOf(a1)+s.indexOf(a2)+s.indexOf(a3)+s.indexOf(a4)+s.indexOf(a5)+s.indexOf(a6)+s.indexOf(a7);
            }else
            if(a2>=0){

                ss+=s.indexOf(a2)+s.indexOf(a3)+s.indexOf(a4)+s.indexOf(a5)+s.indexOf(a6)+s.indexOf(a7);
            }else
            if(a3>=0){

                ss+=s.indexOf(a3)+s.indexOf(a4)+s.indexOf(a5)+s.indexOf(a6)+s.indexOf(a7);
            }else
            {

                ss+=s.indexOf(a4)+s.indexOf(a5)+s.indexOf(a6)+s.indexOf(a7);
            }
            textToShow.add(ss);
        }

    }



}
