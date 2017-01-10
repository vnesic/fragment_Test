package com.example.user.fragment_test;

import android.support.annotation.NonNull;
import android.text.Spanned;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Buljoslav on 13/12/2016.
 */

public  class UserSettings {

    enum colors {RED,BLACK,BLUE,YELLOW,WHITE};
    enum fonts{TIMES,CALIBRI,ARIAL};
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

    public static fonts defaultFont=fonts.TIMES;
    public static fonts currentFont=fonts.TIMES;
    public static fonts displayFont=fonts.TIMES;

    public static colors defaultFontColor=colors.BLACK;
    public static colors currentFontColor=colors.BLACK;
    public static colors displayFontColor=colors.BLACK;

    public static colors defaultBackgroundColor=colors.YELLOW;
    public static colors currentBackgroundColor=colors.YELLOW;
    public static colors displayBackgroundColor=colors.YELLOW;

    public static boolean[] bookmarkArray=new boolean[Const.MaxNumOfTexts];

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

