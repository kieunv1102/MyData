package com.entertaiment.photo.christmassticker.iconsticker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSI on 29/07/2015.
 */
public class Sticker3 {
    public static final int NUMBER_STICKER1 =20 ;
    //format 01,02,....
    public static DecimalFormat formatter = new DecimalFormat("00");

    public static List<String> getSticker3() {
        List<String> listSticker = new ArrayList<String>();
        for (int i = 0; i < NUMBER_STICKER1; i++) {
            String aFormatted = formatter.format(i + 1);
            listSticker.add("sticker/sticker3/sticker3_" + aFormatted + ".png");
        }
        return listSticker;
    }
}
