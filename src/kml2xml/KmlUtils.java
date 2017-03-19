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
package kml2xml;

/**
 *
 * @author marty
 */
public class KmlUtils {

    String kml;
    String abcsXml;
    String effort;

    public String ConvertCoordinates(String kml) {
        //Clean up and extract coordinates
        StringBuilder xml = new StringBuilder();
        String name = "Checkpoint";
        if (kml.indexOf("<name>") > 0) {
            name = kml.substring(kml.indexOf("<name>") + 6, kml.indexOf("</name>"));
        }
        xml.append(" <MilStdSymbol>\n");
        int start = 0, end = 0;
        start = kml.indexOf("<coordinates>") + 13;
        end = kml.indexOf("</coordinates>", start);
        String coordString = kml.substring(start, end);
        String[] coords = coordString.split(" ");
        int knt = coords.length;
        xml.append("<coordinates geometryType=\"null\">\n");
        for (int i = 0; i < knt; i++) {
            //System.out.println("[" + i + "] " + coords[i]);
            String[] coord = coords[i].split(",");
            if (coord.length == 2) {
                xml.append("<coordinate>");
                xml.append("<latitude>").append(coord[1]).append("</latitude>");
                xml.append("<longitude>").append(coord[0]).append("</longitude>");
                xml.append("</coordinate>\n");
            } else if (coord.length == 3) {
                xml.append("<coordinate>");
                xml.append("<latitude>").append(coord[1]).append("</latitude>");
                xml.append("<longitude>").append(coord[0]).append("</longitude>");
                xml.append("<elevation>").append(coord[2]).append("</elevation>");
                xml.append("</coordinate>\n");
            }
        }
        xml.append("</coordinates>\n");
        if (kml.toString().toUpperCase().indexOf("<LINESTRING>") > 0) {
            xml.append("<geometry>line</geometry>\n");
            xml.append("<name>").append(name).append("</name>\n");
            xml.append("<symbolID>GFGPGLB-------X</symbolID>\n");
            xml.append(" <type>GRAPHIC</type>\n");
            xml.append("</MilStdSymbol>\n");
        } else if (kml.toString().toUpperCase().indexOf("<POINT>") > 0) {
            xml.append("<geometry>point</geometry>\n");
            xml.append("<name>").append(name).append("</name>\n");
            xml.append("<symbolID>GFGPGPPK------X</symbolID>\n");
            xml.append(" <type>GRAPHIC</type>\n");
            xml.append("</MilStdSymbol>\n");
        } else if (kml.toString().toUpperCase().indexOf("<POLYGON>") > 0) {
            xml.append("<geometry>area</geometry>\n");
            xml.append("<name>").append(name).append("</name>\n");
            xml.append("<symbolID>GFGPGAG-------X</symbolID>\n");
            xml.append(" <type>GRAPHIC</type>\n");
            xml.append("</MilStdSymbol>\n");
        }

        return xml.toString();
    }//ConvertCoordinates

    public String getXml() {
        return abcsXml;
    }

    public void setKml(String kml) {
        this.kml = kml;
        this.abcsXml = this.ConvertCoordinates(kml);
    }

    public String convertKml(String kml) {
        this.kml = kml;
        this.abcsXml = this.ConvertCoordinates(kml);
        return this.abcsXml;
    }

}
