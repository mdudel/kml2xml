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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 <Point id="ID">
 <!-- specific to Point -->
 <extrude>0</extrude>                        <!-- boolean -->
 <altitudeMode>clampToGround</altitudeMode>
 <!-- kml:altitudeModeEnum: clampToGround, relativeToGround, or absolute -->
 <!-- or, substitute gx:altitudeMode: clampToSeaFloor, relativeToSeaFloor -->
 <coordinates>...</coordinates>              <!-- lon,lat[,alt] -->
 </Point>
 */
public class Point extends Geometry {

    private int extrude = 0;
    private String altitudeMode = "clampToGround";
    private Element xmlElement;
    private final String type = "Point";

    public String getType() {
        return type;
    }

    private void extractPointProperties(Element pointElement) {
        String pointElementTagName = "altitudeMode";
        if (pointElement.getElementsByTagNameNS("*",pointElementTagName).getLength() > 0) {
            this.altitudeMode = pointElement.getElementsByTagNameNS("*",pointElementTagName).item(0).getTextContent();
        }//altitudeMode
        pointElementTagName = "coordinates";
        if (pointElement.getElementsByTagNameNS("*",pointElementTagName).getLength() > 0) {
            try {
                this.setCoordinates(pointElement.getElementsByTagNameNS("*",pointElementTagName).item(0).getTextContent());
            } catch (Exception ex) {
                System.out.println("Error: " + this.getType());
                System.out.println(ex.getMessage() + "\nError getting coordinates...");
                System.out.println("START:\n" + pointElement.getTextContent() + "\nEND");
            }
        }//coordinates  
        pointElementTagName = "extrude";
        if (pointElement.getElementsByTagNameNS("*",pointElementTagName).getLength() > 0) {
            String extrudeString = pointElement.getElementsByTagNameNS("*",pointElementTagName).item(0).getTextContent();
            try {
                this.extrude = Integer.parseInt(extrudeString);
            } catch (Exception ex) {
                this.extrude = 0;
            }
        }//extrude      
    }

    public void setXmlElement(Element xmlElement) {
        this.xmlElement = xmlElement;
        try {
            this.setParent(xmlElement.getParentNode().getNodeName());
        } catch (Exception ex) {
            this.setParent("UNKNOWN");
        }
        // id attribute
        if (xmlElement.getAttribute("id").length() > 0) {
            String id = xmlElement.getAttribute("id");
            this.setId(id);
        }// id  
        NodeList pointElementList = xmlElement.getElementsByTagNameNS("*","Point");
        if (pointElementList.getLength() > 0) {
            for (int j = 0; j < pointElementList.getLength(); j++) {
                Node pointNode = pointElementList.item(j);
                if (pointNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pointElement = (Element) pointNode;
                    this.extractPointProperties(pointElement);
                }

            }//for  
        } else {
            this.extractPointProperties(xmlElement);
        }
    }

    public Point(Element xmlElement) {
        this.xmlElement = xmlElement;
        this.setXmlElement(xmlElement);
    }

    public Point() {

    }

    public int getExtrude() {
        return extrude;
    }

    public void setExtrude(int extrude) {
        this.extrude = extrude;
    }

    public String getAltitudeMode() {
        return altitudeMode;
    }

    public void setAltitudeMode(String altitudeMode) {
        this.altitudeMode = altitudeMode;
    }

    public String getKml() {
        StringBuilder kml = new StringBuilder();
        kml.append("<Point>\n");
        kml.append("<extrude>").append(this.extrude).append("</extrude>\n");
        kml.append("<altitudeMode>").append(this.altitudeMode).append("</altitudeMode>\n");
        kml.append("<coordinates>").append(this.getCoordinates()).append("</coordinates>\n");
        kml.append("</Point>\n");
        return kml.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("id:").append(this.getId()).append("\n");
        out.append("extrude:").append(extrude).append("\n");
        out.append("altitudeMode:").append(altitudeMode).append("\n");
        out.append("coordinates:").append(this.getCoordinates()).append("\n");
        return out.toString();
    }

    public String getMnvrXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<!-- DEFAULT SYMBOLID --><symbolID>GFGPGPRI------X</symbolID>\n");
        xml.append("<geometry>point</geometry>\n");
        xml.append(this.getCoordinatesAsMnvrXML());
        xml.append("\n<type>GRAPHIC</type>\n");
        return xml.toString();
    }

    public String getOvlXml() {
        return this.getCoordinatesAsOvlXML();
    }
}
