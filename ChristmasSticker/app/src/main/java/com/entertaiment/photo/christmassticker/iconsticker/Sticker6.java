package com.entertaiment.photo.christmassticker.iconsticker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSI on 29/07/2015.
 */
public class Sticker6 {
    public static final int NUMBER_STICKER1 =13 ;
    //format 01,02,....
    public static DecimalFormat formatter = new DecimalFormat("00");

    public static List<String> getSticker6() {
        List<String> listSticker = new ArrayList<String>();
        for (int i = 0; i < NUMBER_STICKER1; i++) {
            String aFormatted = formatter.format(i + 1);
            listSticker.add("sticker/sticker6/sticker6_" + aFormatted + ".png");
        }
        return listSticker;
    }
}
