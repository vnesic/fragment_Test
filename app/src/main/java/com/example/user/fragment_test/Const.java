package com.example.user.fragment_test;

/**
 * Created by Buljoslav on 27/11/2016.
 */

public final class Const {

    public static final String[] authorBio={"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Свештеник Георгије Максимов " +
            "је рођен 1979. године. \n" +
            "2001. године завршио је православни Свето-тихоновски " +
            "универзитет Апостола Јована Богослова. 2009. године заштитио је" +
            " дисертацију у истоименој институцији. Од 2002. до 2012. године " +
            "предавао на Московској духовној семинарији, од 2012. године до данас" +
            " предаје на Сретењској духовној семинарији. \n" +
            "22. маја 2010. рукоположен у чин ђакона, 6. јануара 2015. године" +
            " рукоположен у чин свештеника. Свештеник је московске епархије, " +
            "члан је међусаборног присуства Руске Православне Цркве. Руководилац " +
            "сектора апологетске мисије Синодалног мисионарског одељења " +
            "Руске Православне Цркве. Одговорно лице за мисионарски и " +
            "катехизаторски рад Северног викаријатства града Москве. Аутор " +
            "је више од 30 књига и брошура, преко 2000 радова. Учесник низа " +
            "међународних конференција. \n" +
            "Један од најистакнутијих мисионара Руске Православне Цркве, " +
            "уопште Православне Цркве у целини данас, са искуством мисије у" +
            " Русији, али и у земљама Азије: Тајланд, Филипини, Камбоџа, Вијетнам" +
            ", Индонезија. \n" +
            "Материјали представљају транскрипт његових емисија 'Апологет' на" +
            " московском радију Радоњеж, уз изузетак његове две књиге о атеизму и " +
            "'православном' еволуционизму.\n"};

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

                    "ПРАВОСЛАВНО УЧЕЊЕ О СПАСЕЊУ",
                    "РАСКОЛИ",
                    "МОНОФИЗИТСТВО",
                    "РИМОКАТОЛИЦИЗАМ ",
                    "ПРОТЕСТАНТИЗАМ",
                    "ЈЕХОВИНИ СВЕДОЦИ",
                    "ИСЛАМ",
                    "НЕОПАГАНИЗАМ",
                    "ШТА ОДГОВОРИТИ АТЕИСТИ? \n" +
                            "СВЕШТЕНИК ГЕОРГИЈЕ МАКСИМОВ",
                    "ПРАВОСЛАВНИ ОДНОС ПРЕМА ЧУДИМА",
                    "ЈОГА",
                    "РЕИНКАРНАЦИЈА",
                    "                                     †",//losa praksa ali nemam bolje resenje
                    "О аутору"
            };

    public static int MaxNumberofFootnotes=20;


    public static boolean day_night=true;
}
