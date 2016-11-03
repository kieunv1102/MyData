package com.entertaiment.photo.christmassticker.models;

import android.content.Context;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyDataModel {
	public static final int NUMBER_FONT = 73;

	public static final int NUMBER_FACE = 24;

	public static final int NUMBER_SHAPE = 16;
	public static final int NUMBER_COLOR = 7;
	public static final int NUMBER_FRAME = 69;
	public static final int NUMBER_TOOLS = 13;
	//public static final int NUMBER_SAYS = 64;
	public static final int NUMBER_SYMBOL = 24;
	public static final int NUMBER_FOODs = 25;
	public static final int NUMBER_TET = 24;
	public static final int NUMBER_LOVES = 20;
	public static final int NUMBER_TEXT = 33;
	public static final int NUMBER_STAR = 22;
	public static final int NUMBER_EYES = 28;
	public static final int NUMBER_HAIR = 22;
	public static final int NUMBER_HAT = 28;
	public static final int NUMBER_ROMANCE = 58;
	public static final int NUMBER_MASK = 22;
	public static final int NUMBER_SUMMER = 59;
	public static final int NUMBER_SHAPES = 15;
	public static final int NUMBER_TRAVEL = 21;
	public static final int NUMBER_BIRTHDAY = 10;
	public static final int NUMBER_CHRISTMAS = 15;
	public static final int NUMBER_PATTERN = 18;
	public static final int NUMBER_LOVENEWS=25;

	// format 01,02,....
	public static DecimalFormat formatter = new DecimalFormat("00");

	public static List<String> getTools() {
		List<String> listSticker = new ArrayList<String>();
		for (int i = 0; i < NUMBER_TOOLS; i++) {
			String aFormatted = formatter.format(i + 1);
			listSticker.add("tools/tool" + aFormatted + ".png");
		}
		return listSticker;
	}

	public static List<String> getFonts() {
		List<String> listSticker = new ArrayList<String>();
		for (int i = 0; i < NUMBER_FONT; i++) {
			String aFormatted = formatter.format(i + 1);
			listSticker.add("fonts/font" + aFormatted + ".ttf");
		}
		return listSticker;

	}

	// Sticker
	public static List<String> getFrams() {
		List<String> listSticker = new ArrayList<String>();
		for (int i = 0; i < NUMBER_FRAME; i++) {
			String aFormatted = formatter.format(i + 1);
			listSticker.add("frames/frames" + aFormatted + ".png");
		}
		return listSticker;
	}
	public static List<String> getMask() {
		List<String> listSticker = new ArrayList<String>();
		for (int i = 0; i < NUMBER_MASK; i++) {
			String aFormatted = formatter.format(i + 1);
			listSticker.add("mask/mask" + aFormatted + ".png");
		}
		return listSticker;
	}

	// Sticker
//	public static List<String> getEyes() {
//		List<String> listSticker = new ArrayList<String>();
//		for (int i = 0; i < NUMBER_EYES; i++) {
//			String aFormatted = formatter.format(i + 1);
//			listSticker.add("eye/eye" + aFormatted + ".png");
//		}
//		return listSticker;
//	}

	// Sticker
//	public static List<String> getHair() {
//		List<String> listSticker = new ArrayList<String>();
//		for (int i = 0; i < NUMBER_HAIR; i++) {
//			String aFormatted = formatter.format(i + 1);
//			listSticker.add("hair/hair" + aFormatted + ".png");
//		}
//		return listSticker;
//	}
	// Sticker
		public static List<String> getSummer() {
			List<String> listSticker = new ArrayList<String>();
			for (int i = 0; i < NUMBER_SUMMER; i++) {
				String aFormatted = formatter.format(i + 1);
				listSticker.add("summer/summer" + aFormatted + ".png");
			}
			return listSticker;
		}// Sticker
//		public static List<String> getShape() {
//			List<String> listSticker = new ArrayList<String>();
//			for (int i = 0; i < NUMBER_SHAPES; i++) {
//				String aFormatted = formatter.format(i + 1);
//				listSticker.add("shapes/shape" + aFormatted + ".png");
//			}
//			return listSticker;
//		}// Sticker
		public static List<String> getBithday() {
			List<String> listSticker = new ArrayList<String>();
			for (int i = 0; i < NUMBER_BIRTHDAY; i++) {
				String aFormatted = formatter.format(i + 1);
				listSticker.add("birthday/birthday" + aFormatted + ".png");
			}
			return listSticker;
		}// Sticker
		public static List<String> getTravel() {
			List<String> listSticker = new ArrayList<String>();
			for (int i = 0; i < NUMBER_TRAVEL; i++) {
				String aFormatted = formatter.format(i + 1);
				listSticker.add("travel/travel" + aFormatted + ".png");
			}
			return listSticker;
		}// Sticker
		public static List<String> getChristmas() {
			List<String> listSticker = new ArrayList<String>();
			for (int i = 0; i < NUMBER_CHRISTMAS; i++) {
				String aFormatted = formatter.format(i + 1);
				listSticker.add("christmas/christmas" + aFormatted + ".png");
			}
			return listSticker;
		}// Sticker
//		public static List<String> getPattern() {
//			List<String> listSticker = new ArrayList<String>();
//			for (int i = 0; i < NUMBER_PATTERN; i++) {
//				String aFormatted = formatter.format(i + 1);
//				listSticker.add("pattern/pattern" + aFormatted + ".png");
//			}
//			return listSticker;
//		}

//	public static List<String> getHats() {
//		List<String> listSticker = new ArrayList<String>();
//		for (int i = 0; i < NUMBER_HAT; i++) {
//			String aFormatted = formatter.format(i + 1);
//			listSticker.add("hat/hat" + aFormatted + ".png");
//		}
//		return listSticker;
//	}

//	public static List<String> getROMANCE() {
//		List<String> listSticker = new ArrayList<String>();
//		for (int i = 0; i < NUMBER_ROMANCE; i++) {
//			String aFormatted = formatter.format(i + 1);
//			listSticker.add("romance/romance" + aFormatted + ".png");
//		}
//		return listSticker;
//	}

	// Sticker

	public static List<String> getFaces() {
		List<String> listSticker = new ArrayList<String>();
		for (int i = 0; i < NUMBER_FACE; i++) {
			String aFormatted = formatter.format(i + 1);
			listSticker.add("face/emoticon" + aFormatted + ".png");
		}
		return listSticker;
	}

//	public static List<String> getLoves() {
//		List<String> listSticker = new ArrayList<String>();
//		for (int i = 0; i < NUMBER_LOVES; i++) {
//			String aFormatted = formatter.format(i + 1);
//			listSticker.add("love/love" + aFormatted + ".png");
//		}
//		return listSticker;
//	}

	public static List<String> getSays() {
		List<String> listSticker = new ArrayList<String>();
		for (int i = 0; i < NUMBER_TEXT; i++) {
			String aFormatted = formatter.format(i + 1);
			listSticker.add("text/t" + aFormatted + ".png");
		}
		return listSticker;
	}

	public static List<String> getFoods() {
		List<String> listSticker = new ArrayList<String>();
		for (int i = 0; i < NUMBER_FOODs; i++) {
			String aFormatted = formatter.format(i + 1);
			listSticker.add("foods/tool" + aFormatted + ".png");
		}
		return listSticker;
	}

//	public static List<String> getStar() {
//		List<String> listSticker = new ArrayList<String>();
//		for (int i = 0; i < NUMBER_STAR; i++) {
//			String aFormatted = formatter.format(i + 1);
//			listSticker.add("star/star" + aFormatted + ".png");
//		}
//		return listSticker;
//	}

//	public static List<String> getSymbols() {
//		List<String> listSticker = new ArrayList<String>();
//		for (int i = 0; i < NUMBER_SYMBOL; i++) {
//			String aFormatted = formatter.format(i + 1);
//			listSticker.add("symbol/symbol" + aFormatted + ".png");
//		}
//		return listSticker;
//	}

//	public static List<String> getTet() {
//		List<String> listSticker = new ArrayList<String>();
//		for (int i = 0; i < NUMBER_TET; i++) {
//			String aFormatted = formatter.format(i + 1);
//			listSticker.add("tet/sta" + aFormatted + ".png");
//		}
//		return listSticker;
//	}

	public static int[] getColor(Context context) {
		int[] colorId = new int[NUMBER_COLOR];
		DecimalFormat formatter = new DecimalFormat("00");
		for (int i = 0; i < NUMBER_COLOR; i++) {
			String aFormatted1 = formatter.format(i + 1);
			colorId[i] = context.getResources().getIdentifier(
					"item_color_" + aFormatted1, "color",
					context.getPackageName());
		}
		return colorId;
	}

	public static List<String> getShapes() {
		List<String> listSticker = new ArrayList<String>();
		for (int i = 0; i < NUMBER_SHAPE; i++) {
			String aFormatted = formatter.format(i + 1);
			listSticker.add("shape/shape" + aFormatted + ".png");
		}
		return listSticker;
	}
//	public static List<String> getSay() {
//		List<String> listSticker = new ArrayList<String>();
//		for (int i = 0; i < NUMBER_SAYS; i++) {
//			String aFormatted = formatter.format(i + 1);
//			listSticker.add("say/saying" + aFormatted + ".png");
//		}
//		return listSticker;
//	}
	public static List<String> getLovenew() {
		List<String> listSticker = new ArrayList<String>();
		for (int i = 0; i < NUMBER_LOVENEWS; i++) {
			String aFormatted = formatter.format(i + 1);
			listSticker.add("lovenew/love" + aFormatted + ".png");
		}
		return listSticker;
	}
}
