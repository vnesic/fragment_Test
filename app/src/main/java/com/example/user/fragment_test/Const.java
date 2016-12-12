package com.example.user.fragment_test;

/**
 * Created by Buljoslav on 27/11/2016.
 */

public final class Const {

    public static final int SUBTITLE=0;
    public static final int TEXT=1;
    public static final int MaxNumOfTexts=50;
    public static final int MaxNumOfSubTexts=50;
    public static final int BEGINNING=0;
    public static final int END=1;
    public static final String[] TEXT_DELIMITERS={"<text>","</text>"};
    public static final String[] SUBTITLE_DELIMITERS={"<subtitle>","</subtitle>"};
    public static final String[] TITLE_DELIMITERS={"<title>","</title>"};
    public static final String[] FOOTNOTE_DELIMITERS={"<f>","</f>"};

    public static final String NOTIFICATION_SUB ="subtitle_intent" ;
    public static final String NOTIFICATION_TEXT ="text_intent" ;

    public static final String[] TITLES =
            {
                    "О аутору",
                    "ПРАВОСЛАВНО УЧЕЊЕ О СПАСЕЊУ",
                    "О РАСКОЛИМА",
                    "МОНОФИЗИТСТВО",
                    "РИМОКАТОЛИЦИЗАМ ",
                    "ПРОТЕСТАНТИЗАМ",
                    "ЈЕХОВИНИ СВЕДОЦИ",
                    "ИСЛАМ",
                    "О НЕОПАГАНИЗМУ",
                    "ШТА ОДГОВОРИТИ АТЕИСТИ? \n" +
                            "СВЕШТЕНИК ГЕОРГИЈЕ МАКСИМОВ",
                    "ПРАВОСЛАВНИ ОДНОС ПРЕМА ЧУДИМА",
                    "ЈОГА",
                    "РЕИНКАРНАЦИЈА"
            };

    public static int MaxNumberofFootnotes=20;
}
