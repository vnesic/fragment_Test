package com.example.user.fragment_test;

/**
 * Created by Buljoslav on 13/12/2016.
 */

public class UserSettings {

    enum colors {RED,BLACK};
    enum fonts{TIMES,CALIBRI,ARIAL};

    public static boolean isChanged=false;
    public static fonts Font=fonts.ARIAL;
    public static colors Color=colors.BLACK;

    public static int defaultFontSize=20;
    public static int currentFontSize=20;
    public static int defaultFontSizeIncrement;
    public static int displayFontSize=0;
    public static void setDefaultFontSize(int n){

        defaultFontSize=n;
        displayFontSize=defaultFontSize;
        setIncrement((int)(0.1*defaultFontSize));
    }
    public static void setIncrement(int n){
        defaultFontSizeIncrement=n;
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

         displayFontSize=currentFontSize;
     }


    public static void resetToDefault(){

        currentFontSize=displayFontSize;
        isChanged=false;
    }
}

