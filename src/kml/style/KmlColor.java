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

/**
 *
 * @author marty
 */
public final class KmlColor {

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return a long value representing the color in MNVR widget format<br>
     * MS Access uses a long whole number to define a color.<br>
     * The conversion is:<br>
     * MNVR value = (65536*Red) + (256*Green) + (Blue)
     *
     */
    public static long getMNVRColorLongFromKmlColor(String kmlColor) {
        try {
            kmlColor = kmlColor.trim();
            while (kmlColor.length() < 8) {
                kmlColor = "0" + kmlColor;
            }
            long rr = Long.parseLong(kmlColor.substring(6, 8), 16);
            long gg = Long.parseLong(kmlColor.substring(4, 6), 16);
            long bb = Long.parseLong(kmlColor.substring(2, 4), 16);
            // MS Access Long Color value = (65536*Blue) + (256*Green) + (Red)
            long access = (65536 * rr) + (256 * gg) + (bb);
            return access;
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return a string representing the color in MNVR widget format<br>
     * MS Access uses a long whole number to define a color.<br>
     * The conversion is:<br>
     * MNVR value = (65536*Red) + (256*Green) + (Blue)
     */
    public static String getMNVRColorFromKmlColor(String kmlColor) {
        try {
            kmlColor = kmlColor.trim();
            while (kmlColor.length() < 8) {
                kmlColor = "0" + kmlColor;
            }
            long rr = Long.parseLong(kmlColor.substring(6, 8), 16);
            long gg = Long.parseLong(kmlColor.substring(4, 6), 16);
            long bb = Long.parseLong(kmlColor.substring(2, 4), 16);
            // MS Access Long Color value = (65536*Blue) + (256*Green) + (Red)
            Long access = (65536 * rr) + (256 * gg) + (bb);
            //System.out.println("Converting KML Color " + kmlColor + " red:" + rr + " green:" + gg + " blue:" + bb + " to long:" + access);
            return Long.toString(access);
        } catch (Exception ex) {
            return "0";
        }
    }

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return alpha as float decimal value (0.0 .. 1.0)
     */
    public static Double getAlphaDecimalFromKmlColor(String kmlColor) {
        kmlColor = kmlColor.trim();
        while (kmlColor.length() < 8) {
            kmlColor = "0" + kmlColor;
        }
        String aa = kmlColor.substring(0, 2);
        Double alpha = (double) Integer.parseInt(aa, 16);
        return (alpha / 255);
    }

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return alpha as string representation of decimal value (0.00 .. 1.00)
     */
    public static String getAlphaDecimalStringFromKmlColor(String kmlColor) {
        kmlColor = kmlColor.trim();
        while (kmlColor.length() < 8) {
            kmlColor = "0" + kmlColor;
        }
        String aa = kmlColor.substring(0, 2);
        Double alpha = (double) Integer.parseInt(aa, 16);
        alpha = (alpha / 255);
        String sAlpha = alpha.toString();
        if (sAlpha.length() > 4) {
            sAlpha = sAlpha.substring(0, 4);
        }
        return sAlpha;
    }

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return a string of a KML color that represents black or white<br>
     * The return color is calculated to contract with the input color
     */
    public static String getContrastingBackGroundAsKml(String kmlColor) {
        //if (red*0.299 + green*0.587 + blue*0.114) > 186 use #000000 else use #ffffff
        // 
        // White Smoke: FFF5F5F5
        String contrastingColor = "FFF5F5F5";
        try {
            kmlColor = kmlColor.trim();
            while (kmlColor.length() < 8) {
                kmlColor = "0" + kmlColor;
            }
            int rr = Integer.parseInt(kmlColor.substring(6, 8), 16);
            int gg = Integer.parseInt(kmlColor.substring(4, 6), 16);
            int bb = Integer.parseInt(kmlColor.substring(2, 4), 16);
            double contrast = (rr * 0.299) + (gg * 0.587) + (bb * 0.114);
            if (contrast > 186.0) {
                contrastingColor = "00000000";
            }
        } catch (Exception ex) {
            return "FFF5F5F5";
        }
        return contrastingColor;
    }

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return a long color value that represents black or white<br>
     * The return value is calculated to contract with the input color
     */
    public static long getContrastingBackGroundAsLong(String kmlColor) {
        long contrastingColor = 16119285;
        try {
            kmlColor = kmlColor.trim();
            while (kmlColor.length() < 8) {
                kmlColor = "0" + kmlColor;
            }
            int rr = Integer.parseInt(kmlColor.substring(6, 8), 16);
            int gg = Integer.parseInt(kmlColor.substring(4, 6), 16);
            int bb = Integer.parseInt(kmlColor.substring(2, 4), 16);
            double contrast = (rr * 0.299) + (gg * 0.587) + (bb * 0.114);
            if (contrast > 186.0) {
                contrastingColor = 0;
            }
        } catch (Exception ex) {
            return 16119285;
        }
        return contrastingColor;
    }

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return the aa (alpha 00..ff) portion of the string
     */
    public static String getAAfromKmlColor(String kmlColor) {
        kmlColor = kmlColor.trim();
        while (kmlColor.length() < 8) {
            kmlColor = "0" + kmlColor;
        }
        String aa = kmlColor.substring(0, 2);
        return aa;
    }

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return the rr (red 00..ff) portion of the string
     */
    public static String getRRfromKmlColor(String kmlColor) {
        kmlColor = kmlColor.trim();
        while (kmlColor.length() < 8) {
            kmlColor = "0" + kmlColor;
        }
        String rr = kmlColor.substring(6, 8);
        return rr;
    }

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return the gg (green 00..ff) portion of the string
     */
    public static String getGGfromKmlColor(String kmlColor) {
        kmlColor = kmlColor.trim();
        while (kmlColor.length() < 8) {
            kmlColor = "0" + kmlColor;
        }
        String gg = kmlColor.substring(4, 6);
        return gg;
    }

    /**
     *
     * @param kmlColor in string format aabbggrr
     * @return the bb (blue 00..ff) portion of the string
     */
    public static String getBBfromKmlColor(String kmlColor) {
        kmlColor = kmlColor.trim();
        while (kmlColor.length() < 8) {
            kmlColor = "0" + kmlColor;
        }
        String bb = kmlColor.substring(2, 4);
        return bb;
    }

    /**
     *
     * @param kmlColor
     * @return returns the OVL color string in the format RED GREEN BLUE<br>
     * The color components are int values from [0.255]<br>
     * Example red (ff0000ff) = "255 0 0"
     */
    public static String getOvlColorfromKmlColor(String kmlColor) {
        try {
            kmlColor = kmlColor.trim();
            while (kmlColor.length() < 8) {
                kmlColor = "0" + kmlColor;
            }
            String rr = kmlColor.substring(6, 8);
            String gg = kmlColor.substring(4, 6);
            String bb = kmlColor.substring(2, 4);
            Long red = Long.parseLong(rr, 16);
            Long green = Long.parseLong(gg, 16);
            Long blue = Long.parseLong(bb, 16);
            StringBuilder ovlColor = new StringBuilder();
            ovlColor.append(red).append(" ").append(green).append(" ").append(blue);
            return ovlColor.toString();
        } catch (Exception ex) {
            // Return white if there was an error
            System.out.println(ex.getMessage() + "\nkmlColor:" + kmlColor);
            return "255 255 255";
        }
    }

}
