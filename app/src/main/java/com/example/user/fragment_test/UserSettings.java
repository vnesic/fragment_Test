package com.example.user.fragment_test;

import android.support.annotation.NonNull;
import android.text.Spanned;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.example.user.fragment_test.Const.TITLES;

/**
 * Created by Buljoslav on 13/12/2016.
 */

public  class UserSettings {

    enum colors {RED,BLACK,BLUE,YELLOW,WHITE};
    enum fonts{ARIAL,CALIBRI,GABRIOLA,PALATINO,DEFAULT};
    public static boolean firstPass=true;

    public static int authorTextNumber=13;
    public static int crossNumber=authorTextNumber-1;

    public static boolean day_night=false;

    public static Spanned[][] cachedText = new Spanned[Const.MaxNumOfTexts][Const.MaxNumOfTexts];//2nd index on both is currentPage
    public static Spanned[][] cachedSubtitles = new Spanned[Const.MaxNumOfSubTexts][Const.MaxNumOfSubTexts];

    public static String[] footnotesString=new String[Const.MaxNumberofFootnotes];

    public static int currentPageNumber=0;
    public static boolean isChanged=false;
    public static fonts Font=fonts.ARIAL;
    public static colors Color=colors.BLACK;


    public static int defaultFontSize=20;
    public static int currentFontSize=20;
    public static int defaultFontSizeIncrement;
    public static int displayFontSize=0;

    public static fonts defaultFont=fonts.DEFAULT;
    public static fonts currentFont=fonts.DEFAULT;
    public static fonts displayFont=fonts.DEFAULT;

    public static colors defaultFontColor=colors.BLACK;
    public static colors currentFontColor=colors.BLACK;
    public static colors displayFontColor=colors.BLACK;

    public static colors defaultBackgroundColor=colors.YELLOW;
    public static colors currentBackgroundColor=colors.YELLOW;
    public static colors displayBackgroundColor=colors.YELLOW;

    public static boolean[] bookmarkArray=new boolean[Const.MaxNumOfTexts];

    public static ArrayList<BookmarkItem> bookmarkItems=new ArrayList<BookmarkItem>();

    public static void removeElem(int tN,int pN){
       for(int i=0;i<bookmarkItems.size();i++){
           if(bookmarkItems.get(i).pageNum==pN && bookmarkItems.get(i).textNum==tN)bookmarkItems.remove(i);
       }
    }

    public static boolean existElem(int tN,int pN){
        for(int i=0;i<bookmarkItems.size();i++){
            if(bookmarkItems.get(i).pageNum==pN && bookmarkItems.get(i).textNum==tN)return  true;
        }
    return false;
    }


    public static void setDefaultFontSize(int n){

        defaultFontSize=n;
        displayFontSize=defaultFontSize;
        setIncrement((int)(0.1*defaultFontSize));
    }
    public static void setIncrement(int n){
        defaultFontSizeIncrement=n;
    }


    public static void setFontBlack() {
        currentFontColor=colors.BLACK;
    }



    public static void setFontBlue() {
        currentFontColor=colors.BLUE;
    }


    public static void setFontRed() {
        currentFontColor=colors.RED;
    }

    public static void setFontYellow() {
        currentFontColor=colors.YELLOW;
    }



    public static void setBackgroudBlack() {
        currentBackgroundColor=colors.BLACK;
    }



    public static void setBackgroudYellow() {
        currentBackgroundColor=colors.YELLOW;
    }




    public static void setBackgroudWhite() {
        currentBackgroundColor=colors.WHITE;
    }



    ////////////////////////////// fontovi
    public static void setFontDEFAULT() {
        currentFont=fonts.DEFAULT;
    }



    public static void setFontARIAL() {
        currentFont=fonts.ARIAL;
    }


    public static void setFontCALIBRI() {
        currentFont=fonts.CALIBRI;
    }

    public static void setFontGABRIOLA() {
        currentFont=fonts.GABRIOLA;
    }


    public static void setFontPALATINO() {
        currentFont=fonts.PALATINO;
    }








    public static void incrementFont(){
        if((currentFontSize+defaultFontSizeIncrement)>=100){
            currentFontSize=100;
        }else
        {
            currentFontSize+=defaultFontSizeIncrement;
        }
    }

    public static void decrementFont(){
        if((currentFontSize-defaultFontSize) <=0){
            currentFontSize=0;
        }else {
            currentFontSize-=defaultFontSizeIncrement;
        }
    }

    public static void setToDisplay(){
        displayFontColor=currentFontColor;
        displayFont=currentFont;
        displayFontSize=currentFontSize;
        displayBackgroundColor=currentBackgroundColor;
     }


    public static void resetToDefault(){
        currentFontColor=defaultFontColor;
        currentFont=defaultFont;
        currentFontSize=defaultFontSize;
        isChanged=false;
        currentBackgroundColor=defaultBackgroundColor;
    }


















}

    class BookmarkItem{

        public int textNum;

        public int pageNum;

        public String bookmarkName;

        BookmarkItem(int tN,int pN){

            textNum=tN;
            pageNum=pN;
            bookmarkName=TITLES[textNum]+" | страница : "+pageNum;
        }

    }

