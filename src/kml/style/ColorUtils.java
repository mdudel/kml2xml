/*
 * The MIT License
 *
 * Copyright 2017 Martin Dudel.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package kml.style;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Java Code to get a color name from rgb/hex value/awt color
 *
 * The part of looking up a color name from the rgb values is edited from
 * https://gist.github.com/nightlark/6482130#file-gistfile1-java (that has some
 * errors) by Ryan Mast (nightlark)
 *
 * @author Xiaoxiao Li
 *
 * Source: https://gist.github.com/XiaoxiaoLi/8031146
 *
 */
public  class ColorUtils {

    /*
     * Added by Marty Dudel
     */
   

    private Map<String, Color> initColorMap() {
        Map<String, Color> colorMap = new HashMap<String, Color>();
        colorMap.put("ALICEBLUE", new Color(0XF0, 0XF8, 0XFF));
        colorMap.put("ANTIQUEWHITE", new Color(0XFA, 0XEB, 0XD7));
        colorMap.put("AQUA", new Color(0X00, 0XFF, 0XFF));
        colorMap.put("AQUAMARINE", new Color(0X7F, 0XFF, 0XD4));
        colorMap.put("AZURE", new Color(0XF0, 0XFF, 0XFF));
        colorMap.put("BEIGE", new Color(0XF5, 0XF5, 0XDC));
        colorMap.put("BISQUE", new Color(0XFF, 0XE4, 0XC4));
        colorMap.put("BLACK", new Color(0X00, 0X00, 0X00));
        colorMap.put("BLANCHEDALMOND", new Color(0XFF, 0XEB, 0XCD));
        colorMap.put("BLUE", new Color(0X00, 0X00, 0XFF));
        colorMap.put("BLUEVIOLET", new Color(0X8A, 0X2B, 0XE2));
        colorMap.put("BROWN", new Color(0XA5, 0X2A, 0X2A));
        colorMap.put("BURLYWOOD", new Color(0XDE, 0XB8, 0X87));
        colorMap.put("CADETBLUE", new Color(0X5F, 0X9E, 0XA0));
        colorMap.put("CHARTREUSE", new Color(0X7F, 0XFF, 0X00));
        colorMap.put("CHOCOLATE", new Color(0XD2, 0X69, 0X1E));
        colorMap.put("CORAL", new Color(0XFF, 0X7F, 0X50));
        colorMap.put("CORNFLOWERBLUE", new Color(0X64, 0X95, 0XED));
        colorMap.put("CORNSILK", new Color(0XFF, 0XF8, 0XDC));
        colorMap.put("CRIMSON", new Color(0XDC, 0X14, 0X3C));
        colorMap.put("CYAN", new Color(0X00, 0XFF, 0XFF));
        colorMap.put("DARKBLUE", new Color(0X00, 0X00, 0X8B));
        colorMap.put("DARKCYAN", new Color(0X00, 0X8B, 0X8B));
        colorMap.put("DARKGOLDENROD", new Color(0XB8, 0X86, 0X0B));
        colorMap.put("DARKGRAY", new Color(0XA9, 0XA9, 0XA9));
        colorMap.put("DARKGREEN", new Color(0X00, 0X64, 0X00));
        colorMap.put("DARKKHAKI", new Color(0XBD, 0XB7, 0X6B));
        colorMap.put("DARKMAGENTA", new Color(0X8B, 0X00, 0X8B));
        colorMap.put("DARKOLIVEGREEN", new Color(0X55, 0X6B, 0X2F));
        colorMap.put("DARKORANGE", new Color(0XFF, 0X8C, 0X00));
        colorMap.put("DARKORCHID", new Color(0X99, 0X32, 0XCC));
        colorMap.put("DARKRED", new Color(0X8B, 0X00, 0X00));
        colorMap.put("DARKSALMON", new Color(0XE9, 0X96, 0X7A));
        colorMap.put("DARKSEAGREEN", new Color(0X8F, 0XBC, 0X8F));
        colorMap.put("DARKSLATEBLUE", new Color(0X48, 0X3D, 0X8B));
        colorMap.put("DARKSLATEGRAY", new Color(0X2F, 0X4F, 0X4F));
        colorMap.put("DARKTURQUOISE", new Color(0X00, 0XCE, 0XD1));
        colorMap.put("DARKVIOLET", new Color(0X94, 0X00, 0XD3));
        colorMap.put("DEEPPINK", new Color(0XFF, 0X14, 0X93));
        colorMap.put("DEEPSKYBLUE", new Color(0X00, 0XBF, 0XFF));
        colorMap.put("DIMGRAY", new Color(0X69, 0X69, 0X69));
        colorMap.put("DODGERBLUE", new Color(0X1E, 0X90, 0XFF));
        colorMap.put("FIREBRICK", new Color(0XB2, 0X22, 0X22));
        colorMap.put("FLORALWHITE", new Color(0XFF, 0XFA, 0XF0));
        colorMap.put("FORESTGREEN", new Color(0X22, 0X8B, 0X22));
        colorMap.put("FUCHSIA", new Color(0XFF, 0X00, 0XFF));
        colorMap.put("GAINSBORO", new Color(0XDC, 0XDC, 0XDC));
        colorMap.put("GHOSTWHITE", new Color(0XF8, 0XF8, 0XFF));
        colorMap.put("GOLD", new Color(0XFF, 0XD7, 0X00));
        colorMap.put("GOLDENROD", new Color(0XDA, 0XA5, 0X20));
        colorMap.put("GRAY", new Color(0X80, 0X80, 0X80));
        colorMap.put("GREEN", new Color(0X00, 0X80, 0X00));
        colorMap.put("GREENYELLOW", new Color(0XAD, 0XFF, 0X2F));
        colorMap.put("HONEYDEW", new Color(0XF0, 0XFF, 0XF0));
        colorMap.put("HOTPINK", new Color(0XFF, 0X69, 0XB4));
        colorMap.put("INDIANRED", new Color(0XCD, 0X5C, 0X5C));
        colorMap.put("INDIGO", new Color(0X4B, 0X00, 0X82));
        colorMap.put("IVORY", new Color(0XFF, 0XFF, 0XF0));
        colorMap.put("KHAKI", new Color(0XF0, 0XE6, 0X8C));
        colorMap.put("LAVENDER", new Color(0XE6, 0XE6, 0XFA));
        colorMap.put("LAVENDERBLUSH", new Color(0XFF, 0XF0, 0XF5));
        colorMap.put("LAWNGREEN", new Color(0X7C, 0XFC, 0X00));
        colorMap.put("LEMONCHIFFON", new Color(0XFF, 0XFA, 0XCD));
        colorMap.put("LIGHTBLUE", new Color(0XAD, 0XD8, 0XE6));
        colorMap.put("LIGHTCORAL", new Color(0XF0, 0X80, 0X80));
        colorMap.put("LIGHTCYAN", new Color(0XE0, 0XFF, 0XFF));
        colorMap.put("LIGHTGOLDENRODYELLOW", new Color(0XFA, 0XFA, 0XD2));
        colorMap.put("LIGHTGRAY", new Color(0XD3, 0XD3, 0XD3));
        colorMap.put("LIGHTGREEN", new Color(0X90, 0XEE, 0X90));
        colorMap.put("LIGHTPINK", new Color(0XFF, 0XB6, 0XC1));
        colorMap.put("LIGHTSALMON", new Color(0XFF, 0XA0, 0X7A));
        colorMap.put("LIGHTSEAGREEN", new Color(0X20, 0XB2, 0XAA));
        colorMap.put("LIGHTSKYBLUE", new Color(0X87, 0XCE, 0XFA));
        colorMap.put("LIGHTSLATEGRAY", new Color(0X77, 0X88, 0X99));
        colorMap.put("LIGHTSTEELBLUE", new Color(0XB0, 0XC4, 0XDE));
        colorMap.put("LIGHTYELLOW", new Color(0XFF, 0XFF, 0XE0));
        colorMap.put("LIME", new Color(0X00, 0XFF, 0X00));
        colorMap.put("LIMEGREEN", new Color(0X32, 0XCD, 0X32));
        colorMap.put("LINEN", new Color(0XFA, 0XF0, 0XE6));
        colorMap.put("MAGENTA", new Color(0XFF, 0X00, 0XFF));
        colorMap.put("MAROON", new Color(0X80, 0X00, 0X00));
        colorMap.put("MEDIUMAQUAMARINE", new Color(0X66, 0XCD, 0XAA));
        colorMap.put("MEDIUMBLUE", new Color(0X00, 0X00, 0XCD));
        colorMap.put("MEDIUMORCHID", new Color(0XBA, 0X55, 0XD3));
        colorMap.put("MEDIUMPURPLE", new Color(0X93, 0X70, 0XDB));
        colorMap.put("MEDIUMSEAGREEN", new Color(0X3C, 0XB3, 0X71));
        colorMap.put("MEDIUMSLATEBLUE", new Color(0X7B, 0X68, 0XEE));
        colorMap.put("MEDIUMSPRINGGREEN", new Color(0X00, 0XFA, 0X9A));
        colorMap.put("MEDIUMTURQUOISE", new Color(0X48, 0XD1, 0XCC));
        colorMap.put("MEDIUMVIOLETRED", new Color(0XC7, 0X15, 0X85));
        colorMap.put("MIDNIGHTBLUE", new Color(0X19, 0X19, 0X70));
        colorMap.put("MINTCREAM", new Color(0XF5, 0XFF, 0XFA));
        colorMap.put("MISTYROSE", new Color(0XFF, 0XE4, 0XE1));
        colorMap.put("MOCCASIN", new Color(0XFF, 0XE4, 0XB5));
        colorMap.put("NAVAJOWHITE", new Color(0XFF, 0XDE, 0XAD));
        colorMap.put("NAVY", new Color(0X00, 0X00, 0X80));
        colorMap.put("OLDLACE", new Color(0XFD, 0XF5, 0XE6));
        colorMap.put("OLIVE", new Color(0X80, 0X80, 0X00));
        colorMap.put("OLIVEDRAB", new Color(0X6B, 0X8E, 0X23));
        colorMap.put("ORANGE", new Color(0XFF, 0XA5, 0X00));
        colorMap.put("ORANGERED", new Color(0XFF, 0X45, 0X00));
        colorMap.put("ORCHID", new Color(0XDA, 0X70, 0XD6));
        colorMap.put("PALEGOLDENROD", new Color(0XEE, 0XE8, 0XAA));
        colorMap.put("PALEGREEN", new Color(0X98, 0XFB, 0X98));
        colorMap.put("PALETURQUOISE", new Color(0XAF, 0XEE, 0XEE));
        colorMap.put("PALEVIOLETRED", new Color(0XDB, 0X70, 0X93));
        colorMap.put("PAPAYAWHIP", new Color(0XFF, 0XEF, 0XD5));
        colorMap.put("PEACHPUFF", new Color(0XFF, 0XDA, 0XB9));
        colorMap.put("PERU", new Color(0XCD, 0X85, 0X3F));
        colorMap.put("PINK", new Color(0XFF, 0XC0, 0XCB));
        colorMap.put("PLUM", new Color(0XDD, 0XA0, 0XDD));
        colorMap.put("POWDERBLUE", new Color(0XB0, 0XE0, 0XE6));
        colorMap.put("PURPLE", new Color(0X80, 0X00, 0X80));
        colorMap.put("RED", new Color(0XFF, 0X00, 0X00));
        colorMap.put("ROSYBROWN", new Color(0XBC, 0X8F, 0X8F));
        colorMap.put("ROYALBLUE", new Color(0X41, 0X69, 0XE1));
        colorMap.put("SADDLEBROWN", new Color(0X8B, 0X45, 0X13));
        colorMap.put("SALMON", new Color(0XFA, 0X80, 0X72));
        colorMap.put("SANDYBROWN", new Color(0XF4, 0XA4, 0X60));
        colorMap.put("SEAGREEN", new Color(0X2E, 0X8B, 0X57));
        colorMap.put("SEASHELL", new Color(0XFF, 0XF5, 0XEE));
        colorMap.put("SIENNA", new Color(0XA0, 0X52, 0X2D));
        colorMap.put("SILVER", new Color(0XC0, 0XC0, 0XC0));
        colorMap.put("SKYBLUE", new Color(0X87, 0XCE, 0XEB));
        colorMap.put("SLATEBLUE", new Color(0X6A, 0X5A, 0XCD));
        colorMap.put("SLATEGRAY", new Color(0X70, 0X80, 0X90));
        colorMap.put("SNOW", new Color(0XFF, 0XFA, 0XFA));
        colorMap.put("SPRINGGREEN", new Color(0X00, 0XFF, 0X7F));
        colorMap.put("STEELBLUE", new Color(0X46, 0X82, 0XB4));
        colorMap.put("TAN", new Color(0XD2, 0XB4, 0X8C));
        colorMap.put("TEAL", new Color(0X00, 0X80, 0X80));
        colorMap.put("THISTLE", new Color(0XD8, 0XBF, 0XD8));
        colorMap.put("TOMATO", new Color(0XFF, 0X63, 0X47));
        colorMap.put("TURQUOISE", new Color(0X40, 0XE0, 0XD0));
        colorMap.put("VIOLET", new Color(0XEE, 0X82, 0XEE));
        colorMap.put("WHEAT", new Color(0XF5, 0XDE, 0XB3));
        colorMap.put("WHITE", new Color(0XFF, 0XFF, 0XFF));
        colorMap.put("WHITESMOKE", new Color(0XF5, 0XF5, 0XF5));
        colorMap.put("YELLOW", new Color(0XFF, 0XFF, 0X00));
        colorMap.put("YELLOWGREEN", new Color(0X9A, 0XCD, 0X32));
        return colorMap;
    }

    /**
     * Initialize the color list that we have.
     */
    private ArrayList<ColorName> initColorList() {
        ArrayList<ColorName> colorList = new ArrayList<ColorName>();
        colorList.add(new ColorName("AliceBlue", 0xF0, 0xF8, 0xFF));
        colorList.add(new ColorName("AntiqueWhite", 0xFA, 0xEB, 0xD7));
        colorList.add(new ColorName("Aqua", 0x00, 0xFF, 0xFF));
        colorList.add(new ColorName("Aquamarine", 0x7F, 0xFF, 0xD4));
        colorList.add(new ColorName("Azure", 0xF0, 0xFF, 0xFF));
        colorList.add(new ColorName("Beige", 0xF5, 0xF5, 0xDC));
        colorList.add(new ColorName("Bisque", 0xFF, 0xE4, 0xC4));
        colorList.add(new ColorName("Black", 0x00, 0x00, 0x00));
        colorList.add(new ColorName("BlanchedAlmond", 0xFF, 0xEB, 0xCD));
        colorList.add(new ColorName("Blue", 0x00, 0x00, 0xFF));
        colorList.add(new ColorName("BlueViolet", 0x8A, 0x2B, 0xE2));
        colorList.add(new ColorName("Brown", 0xA5, 0x2A, 0x2A));
        colorList.add(new ColorName("BurlyWood", 0xDE, 0xB8, 0x87));
        colorList.add(new ColorName("CadetBlue", 0x5F, 0x9E, 0xA0));
        colorList.add(new ColorName("Chartreuse", 0x7F, 0xFF, 0x00));
        colorList.add(new ColorName("Chocolate", 0xD2, 0x69, 0x1E));
        colorList.add(new ColorName("Coral", 0xFF, 0x7F, 0x50));
        colorList.add(new ColorName("CornflowerBlue", 0x64, 0x95, 0xED));
        colorList.add(new ColorName("Cornsilk", 0xFF, 0xF8, 0xDC));
        colorList.add(new ColorName("Crimson", 0xDC, 0x14, 0x3C));
        colorList.add(new ColorName("Cyan", 0x00, 0xFF, 0xFF));
        colorList.add(new ColorName("DarkBlue", 0x00, 0x00, 0x8B));
        colorList.add(new ColorName("DarkCyan", 0x00, 0x8B, 0x8B));
        colorList.add(new ColorName("DarkGoldenRod", 0xB8, 0x86, 0x0B));
        colorList.add(new ColorName("DarkGray", 0xA9, 0xA9, 0xA9));
        colorList.add(new ColorName("DarkGreen", 0x00, 0x64, 0x00));
        colorList.add(new ColorName("DarkKhaki", 0xBD, 0xB7, 0x6B));
        colorList.add(new ColorName("DarkMagenta", 0x8B, 0x00, 0x8B));
        colorList.add(new ColorName("DarkOliveGreen", 0x55, 0x6B, 0x2F));
        colorList.add(new ColorName("DarkOrange", 0xFF, 0x8C, 0x00));
        colorList.add(new ColorName("DarkOrchid", 0x99, 0x32, 0xCC));
        colorList.add(new ColorName("DarkRed", 0x8B, 0x00, 0x00));
        colorList.add(new ColorName("DarkSalmon", 0xE9, 0x96, 0x7A));
        colorList.add(new ColorName("DarkSeaGreen", 0x8F, 0xBC, 0x8F));
        colorList.add(new ColorName("DarkSlateBlue", 0x48, 0x3D, 0x8B));
        colorList.add(new ColorName("DarkSlateGray", 0x2F, 0x4F, 0x4F));
        colorList.add(new ColorName("DarkTurquoise", 0x00, 0xCE, 0xD1));
        colorList.add(new ColorName("DarkViolet", 0x94, 0x00, 0xD3));
        colorList.add(new ColorName("DeepPink", 0xFF, 0x14, 0x93));
        colorList.add(new ColorName("DeepSkyBlue", 0x00, 0xBF, 0xFF));
        colorList.add(new ColorName("DimGray", 0x69, 0x69, 0x69));
        colorList.add(new ColorName("DodgerBlue", 0x1E, 0x90, 0xFF));
        colorList.add(new ColorName("FireBrick", 0xB2, 0x22, 0x22));
        colorList.add(new ColorName("FloralWhite", 0xFF, 0xFA, 0xF0));
        colorList.add(new ColorName("ForestGreen", 0x22, 0x8B, 0x22));
        colorList.add(new ColorName("Fuchsia", 0xFF, 0x00, 0xFF));
        colorList.add(new ColorName("Gainsboro", 0xDC, 0xDC, 0xDC));
        colorList.add(new ColorName("GhostWhite", 0xF8, 0xF8, 0xFF));
        colorList.add(new ColorName("Gold", 0xFF, 0xD7, 0x00));
        colorList.add(new ColorName("GoldenRod", 0xDA, 0xA5, 0x20));
        colorList.add(new ColorName("Gray", 0x80, 0x80, 0x80));
        colorList.add(new ColorName("Green", 0x00, 0x80, 0x00));
        colorList.add(new ColorName("GreenYellow", 0xAD, 0xFF, 0x2F));
        colorList.add(new ColorName("HoneyDew", 0xF0, 0xFF, 0xF0));
        colorList.add(new ColorName("HotPink", 0xFF, 0x69, 0xB4));
        colorList.add(new ColorName("IndianRed", 0xCD, 0x5C, 0x5C));
        colorList.add(new ColorName("Indigo", 0x4B, 0x00, 0x82));
        colorList.add(new ColorName("Ivory", 0xFF, 0xFF, 0xF0));
        colorList.add(new ColorName("Khaki", 0xF0, 0xE6, 0x8C));
        colorList.add(new ColorName("Lavender", 0xE6, 0xE6, 0xFA));
        colorList.add(new ColorName("LavenderBlush", 0xFF, 0xF0, 0xF5));
        colorList.add(new ColorName("LawnGreen", 0x7C, 0xFC, 0x00));
        colorList.add(new ColorName("LemonChiffon", 0xFF, 0xFA, 0xCD));
        colorList.add(new ColorName("LightBlue", 0xAD, 0xD8, 0xE6));
        colorList.add(new ColorName("LightCoral", 0xF0, 0x80, 0x80));
        colorList.add(new ColorName("LightCyan", 0xE0, 0xFF, 0xFF));
        colorList.add(new ColorName("LightGoldenRodYellow", 0xFA, 0xFA, 0xD2));
        colorList.add(new ColorName("LightGray", 0xD3, 0xD3, 0xD3));
        colorList.add(new ColorName("LightGreen", 0x90, 0xEE, 0x90));
        colorList.add(new ColorName("LightPink", 0xFF, 0xB6, 0xC1));
        colorList.add(new ColorName("LightSalmon", 0xFF, 0xA0, 0x7A));
        colorList.add(new ColorName("LightSeaGreen", 0x20, 0xB2, 0xAA));
        colorList.add(new ColorName("LightSkyBlue", 0x87, 0xCE, 0xFA));
        colorList.add(new ColorName("LightSlateGray", 0x77, 0x88, 0x99));
        colorList.add(new ColorName("LightSteelBlue", 0xB0, 0xC4, 0xDE));
        colorList.add(new ColorName("LightYellow", 0xFF, 0xFF, 0xE0));
        colorList.add(new ColorName("Lime", 0x00, 0xFF, 0x00));
        colorList.add(new ColorName("LimeGreen", 0x32, 0xCD, 0x32));
        colorList.add(new ColorName("Linen", 0xFA, 0xF0, 0xE6));
        colorList.add(new ColorName("Magenta", 0xFF, 0x00, 0xFF));
        colorList.add(new ColorName("Maroon", 0x80, 0x00, 0x00));
        colorList.add(new ColorName("MediumAquaMarine", 0x66, 0xCD, 0xAA));
        colorList.add(new ColorName("MediumBlue", 0x00, 0x00, 0xCD));
        colorList.add(new ColorName("MediumOrchid", 0xBA, 0x55, 0xD3));
        colorList.add(new ColorName("MediumPurple", 0x93, 0x70, 0xDB));
        colorList.add(new ColorName("MediumSeaGreen", 0x3C, 0xB3, 0x71));
        colorList.add(new ColorName("MediumSlateBlue", 0x7B, 0x68, 0xEE));
        colorList.add(new ColorName("MediumSpringGreen", 0x00, 0xFA, 0x9A));
        colorList.add(new ColorName("MediumTurquoise", 0x48, 0xD1, 0xCC));
        colorList.add(new ColorName("MediumVioletRed", 0xC7, 0x15, 0x85));
        colorList.add(new ColorName("MidnightBlue", 0x19, 0x19, 0x70));
        colorList.add(new ColorName("MintCream", 0xF5, 0xFF, 0xFA));
        colorList.add(new ColorName("MistyRose", 0xFF, 0xE4, 0xE1));
        colorList.add(new ColorName("Moccasin", 0xFF, 0xE4, 0xB5));
        colorList.add(new ColorName("NavajoWhite", 0xFF, 0xDE, 0xAD));
        colorList.add(new ColorName("Navy", 0x00, 0x00, 0x80));
        colorList.add(new ColorName("OldLace", 0xFD, 0xF5, 0xE6));
        colorList.add(new ColorName("Olive", 0x80, 0x80, 0x00));
        colorList.add(new ColorName("OliveDrab", 0x6B, 0x8E, 0x23));
        colorList.add(new ColorName("Orange", 0xFF, 0xA5, 0x00));
        colorList.add(new ColorName("OrangeRed", 0xFF, 0x45, 0x00));
        colorList.add(new ColorName("Orchid", 0xDA, 0x70, 0xD6));
        colorList.add(new ColorName("PaleGoldenRod", 0xEE, 0xE8, 0xAA));
        colorList.add(new ColorName("PaleGreen", 0x98, 0xFB, 0x98));
        colorList.add(new ColorName("PaleTurquoise", 0xAF, 0xEE, 0xEE));
        colorList.add(new ColorName("PaleVioletRed", 0xDB, 0x70, 0x93));
        colorList.add(new ColorName("PapayaWhip", 0xFF, 0xEF, 0xD5));
        colorList.add(new ColorName("PeachPuff", 0xFF, 0xDA, 0xB9));
        colorList.add(new ColorName("Peru", 0xCD, 0x85, 0x3F));
        colorList.add(new ColorName("Pink", 0xFF, 0xC0, 0xCB));
        colorList.add(new ColorName("Plum", 0xDD, 0xA0, 0xDD));
        colorList.add(new ColorName("PowderBlue", 0xB0, 0xE0, 0xE6));
        colorList.add(new ColorName("Purple", 0x80, 0x00, 0x80));
        colorList.add(new ColorName("Red", 0xFF, 0x00, 0x00));
        colorList.add(new ColorName("RosyBrown", 0xBC, 0x8F, 0x8F));
        colorList.add(new ColorName("RoyalBlue", 0x41, 0x69, 0xE1));
        colorList.add(new ColorName("SaddleBrown", 0x8B, 0x45, 0x13));
        colorList.add(new ColorName("Salmon", 0xFA, 0x80, 0x72));
        colorList.add(new ColorName("SandyBrown", 0xF4, 0xA4, 0x60));
        colorList.add(new ColorName("SeaGreen", 0x2E, 0x8B, 0x57));
        colorList.add(new ColorName("SeaShell", 0xFF, 0xF5, 0xEE));
        colorList.add(new ColorName("Sienna", 0xA0, 0x52, 0x2D));
        colorList.add(new ColorName("Silver", 0xC0, 0xC0, 0xC0));
        colorList.add(new ColorName("SkyBlue", 0x87, 0xCE, 0xEB));
        colorList.add(new ColorName("SlateBlue", 0x6A, 0x5A, 0xCD));
        colorList.add(new ColorName("SlateGray", 0x70, 0x80, 0x90));
        colorList.add(new ColorName("Snow", 0xFF, 0xFA, 0xFA));
        colorList.add(new ColorName("SpringGreen", 0x00, 0xFF, 0x7F));
        colorList.add(new ColorName("SteelBlue", 0x46, 0x82, 0xB4));
        colorList.add(new ColorName("Tan", 0xD2, 0xB4, 0x8C));
        colorList.add(new ColorName("Teal", 0x00, 0x80, 0x80));
        colorList.add(new ColorName("Thistle", 0xD8, 0xBF, 0xD8));
        colorList.add(new ColorName("Tomato", 0xFF, 0x63, 0x47));
        colorList.add(new ColorName("Turquoise", 0x40, 0xE0, 0xD0));
        colorList.add(new ColorName("Violet", 0xEE, 0x82, 0xEE));
        colorList.add(new ColorName("Wheat", 0xF5, 0xDE, 0xB3));
        colorList.add(new ColorName("White", 0xFF, 0xFF, 0xFF));
        colorList.add(new ColorName("WhiteSmoke", 0xF5, 0xF5, 0xF5));
        colorList.add(new ColorName("Yellow", 0xFF, 0xFF, 0x00));
        colorList.add(new ColorName("YellowGreen", 0x9A, 0xCD, 0x32));
        return colorList;
    }

    /**
     * Get the closest color name from our list
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public String getColorNameFromRgb(int r, int g, int b) {
        ArrayList<ColorName> colorList = initColorList();
        ColorName closestMatch = null;
        int minMSE = Integer.MAX_VALUE;
        int mse;
        for (ColorName c : colorList) {
            mse = c.computeMSE(r, g, b);
            if (mse < minMSE) {
                minMSE = mse;
                closestMatch = c;
            }
        }

        if (closestMatch != null) {
            return closestMatch.getName();
        } else {
            return "No matched color name.";
        }
    }

    /**
     * Convert hexColor to rgb, then call getColorNameFromRgb(r, g, b)
     *
     * @param hexColor
     * @return
     */
    public String getColorNameFromHex(int hexColor) {
        int r = (hexColor & 0xFF0000) >> 16;
        int g = (hexColor & 0xFF00) >> 8;
        int b = (hexColor & 0xFF);
        return getColorNameFromRgb(r, g, b);
    }

    public int colorToHex(Color c) {
        return Integer.decode("0x"
                + Integer.toHexString(c.getRGB()).substring(2));
    }

    public String getColorNameFromColor(Color color) {
        return getColorNameFromRgb(color.getRed(), color.getGreen(),
                color.getBlue());
    }

    public Color getColorFromName(String colorName) {
        Color color = Color.BLACK; // default if not color is found
        try {
            Map<String, Color> colorMap = this.initColorMap();
            color = colorMap.get(colorName.toUpperCase());
        } catch (Exception e) {
            //Not found, do nothing
            return null;
        }
        return color;
    }//getColorFromName

    /**
     * SubClass of ColorUtils. In order to lookup color name
     *
     * @author Xiaoxiao Li
     *
     */
    public class ColorName {

        public int r, g, b;
        public String name;

        public ColorName(String name, int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.name = name;
        }

        public int computeMSE(int pixR, int pixG, int pixB) {
            return (int) (((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + (pixB - b)
                    * (pixB - b)) / 3);
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }

        public String getName() {
            return name;
        }

    }

}
