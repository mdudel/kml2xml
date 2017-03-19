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
package kml.geometry;

/*
 <!-- abstract element; do not create -->
 <!-- Geometry id="ID" -->
 <!-- Point,LineString,LinearRing,
 Polygon,MultiGeometry,Model,
 gx:Track -->
 <!-- /Geometry -->
 */
public class Geometry {

    private String id;
    private final String NO_ID = "NO_ID";
    private String coordinates;
    private String parent = "";

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getCoordinatesAsMnvrXML() {
        StringBuilder xml = new StringBuilder();
        String[] coords = this.getCoordinates().split(" ");
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
        return xml.toString();
    }

    public String getCoordinatesAsOvlXML() {
        StringBuilder xml = new StringBuilder();
        String[] coords = this.getCoordinates().split(" ");
        int knt = coords.length;
        for (int i = 0; i < knt; i++) {
            //System.out.println("[" + i + "] " + coords[i]);
            String[] coord = coords[i].split(",");
            if (coord.length >= 2) {
                xml.append("<POSITION>");
                xml.append(coord[1]).append(" ");
                xml.append(coord[0]);
                xml.append("</POSITION>\n");
            } else {
                StringBuilder error = new StringBuilder();
                error.append("Invalid KML Coordinate:  [").append(coord).append("] coordinate length: ").append(coord.length);
                System.out.println(error.toString());
            }
        }
        return xml.toString();
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates.trim();
    }

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

}
