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
public class ColorStyle {

    String color;
    private String id;
    private final String NO_ID = "NO_ID";
    public String styleMapTemplate = "template_StyleMap";

    public String getId() {
        if (id != null) {
            return id;
        } else {
            return NO_ID;
        }
    }

    public void setId(String id) {
        if (id.length() < 1 || id == null) {
            id = this.NO_ID;
        } else {
            this.id = id;
        }
    }

    public ColorStyle() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlpha() {
        return KmlColor.getAAfromKmlColor(this.color);
    }

    public String getAlphaDecimalAsString() {
        return KmlColor.getAlphaDecimalStringFromKmlColor(this.color);
    }

    public Double getAlphaDecimal() {
        return KmlColor.getAlphaDecimalFromKmlColor(this.color);
    }

    public String getColorAsMnvrColorString() {
        return KmlColor.getMNVRColorFromKmlColor(this.color);
    }

    public String getColorAsOvlColorString() {
        return KmlColor.getOvlColorfromKmlColor(this.color);
    }

    public String getContrastingMnvrColor() {
        Long contrast = KmlColor.getContrastingBackGroundAsLong(this.color);
        return contrast.toString();
    }

}
