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
 <LineString id="ID">
 <!-- specific to LineString -->
 <gx:altitudeOffset>0</gx:altitudeOffset>  <!-- double -->
 <extrude>0</extrude>                      <!-- boolean -->
 <tessellate>0</tessellate>                <!-- boolean -->
 <altitudeMode>clampToGround</altitudeMode>
 <!-- kml:altitudeModeEnum: clampToGround, relativeToGround, or absolute -->
 <!-- or, substitute gx:altitudeMode: clampToSeaFloor, relativeToSeaFloor -->
 <gx:drawOrder>0</gx:drawOrder>            <!-- integer -->
 <coordinates>...</coordinates>            <!-- lon,lat[,alt] -->
 </LineString>
 */
public class LineString extends Geometry {

    private double altitudeOffset = 0.0;
    private int extrude = 0;
    private int tessellate = 0;
    private String altitudeMode = "clampToGround";
    private int drawOrder = 0;
    private Element xmlElement;
    private final String type = "LineString";

    public LineString() {
    }

    public LineString(Element xmlElement) {
        this.setXmlElement(xmlElement);
    }

    public String getType() {
        return type;
    }

    private void extractLineStringProperties(Element element) {
        String lineStringElementTagName = "extrude";
        if (element.getElementsByTagNameNS("*",lineStringElementTagName).getLength() > 0) {
            String extrudeString = element.getElementsByTagNameNS("*",lineStringElementTagName).item(0).getTextContent();
            try {
                this.extrude = Integer.parseInt(extrudeString);
            } catch (Exception ex) {
                this.extrude = 0;
            }
        }//extrude  
        lineStringElementTagName = "altitudeOffset";
        if (element.getElementsByTagNameNS("*",lineStringElementTagName).getLength() > 0) {
            String altitudeOffsetString = element.getElementsByTagNameNS("*",lineStringElementTagName).item(0).getTextContent();
            try {
                this.altitudeOffset = Double.parseDouble(altitudeOffsetString);
            } catch (Exception ex) {
                this.altitudeOffset = 0.0;
            }
        }//altitudeOffset   
        lineStringElementTagName = "tessellate";
        if (element.getElementsByTagNameNS("*",lineStringElementTagName).getLength() > 0) {
            String tessellateString = element.getElementsByTagNameNS("*",lineStringElementTagName).item(0).getTextContent();
            try {
                this.tessellate = Integer.parseInt(tessellateString);
            } catch (Exception ex) {
                this.tessellate = 0;
            }
        }//tessellate                 
        lineStringElementTagName = "altitudeMode";
        if (element.getElementsByTagNameNS("*",lineStringElementTagName).getLength() > 0) {
            try {
                this.altitudeMode = element.getElementsByTagNameNS("*",lineStringElementTagName).item(0).getTextContent();
            } catch (Exception ex) {
                this.altitudeMode = "";
            }
        }//altitudeMode
        lineStringElementTagName = "drawOrder";
        if (element.getElementsByTagNameNS("*",lineStringElementTagName).getLength() > 0) {
            String drawOrderString = element.getElementsByTagNameNS("*",lineStringElementTagName).item(0).getTextContent();
            try {
                this.drawOrder = Integer.parseInt(drawOrderString);
            } catch (Exception ex) {
                this.drawOrder = 0;
            }
        }//drawOrder 
        lineStringElementTagName = "coordinates";
        if (element.getElementsByTagNameNS("*",lineStringElementTagName).getLength() > 0) {
            try {
                this.setCoordinates(element.getElementsByTagNameNS("*",lineStringElementTagName).item(0).getTextContent().trim());
            } catch (Exception ex) {
                System.out.println("Error: " + this.getType());
                System.out.println(ex.getMessage() + "\nError getting coordinates...");
                System.out.println("START:\n" + element.getTextContent() + "\nEND");
            }
        }//coordinates  
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
        NodeList elementList = xmlElement.getElementsByTagNameNS("*","LineString");
        if (elementList.getLength() > 0) {
            for (int j = 0; j < elementList.getLength(); j++) {
                Node node = elementList.item(j);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    this.extractLineStringProperties(element);
                }
            }//for  
        } else {
            this.extractLineStringProperties(xmlElement);
        }
    }

    public double getAltitudeOffset() {
        return altitudeOffset;
    }

    public void setAltitudeOffset(double altitudeOffset) {
        this.altitudeOffset = altitudeOffset;
    }

    public int getExtrude() {
        return extrude;
    }

    public void setExtrude(int extrude) {
        this.extrude = extrude;
    }

    public int getTessellate() {
        return tessellate;
    }

    public void setTessellate(int tessellate) {
        this.tessellate = tessellate;
    }

    public String getAltitudeMode() {
        return altitudeMode;
    }

    public void setAltitudeMode(String altitudeMode) {
        this.altitudeMode = altitudeMode;
    }

    public int getDrawOrder() {
        return drawOrder;
    }

    public void setDrawOrder(int drawOrder) {
        this.drawOrder = drawOrder;
    }

    public String getKml() {
        StringBuilder kml = new StringBuilder();
        kml.append("<LineString");
        if (this.getId().length() > 0) {
            kml.append(" id=\"").append(this.getId()).append("\">\n");
        } else {
            kml.append(">\n");
        }
        if (this.extrude != 0) {
            kml.append("<extrude>").append(this.extrude).append("</extrude>\n");
        }
        if (this.altitudeOffset != 0.0) {
            kml.append("<gx:altitudeOffset>").append(this.altitudeOffset).append("</gx:altitudeOffset>\n");
        }
        if (this.tessellate != 0) {
            kml.append("<tessellate>").append(this.tessellate).append("</tessellate>\n");
        }
        kml.append("<altitudeMode>").append(this.altitudeMode).append("</altitudeMode>\n");
        if (this.drawOrder != 0) {
            kml.append("<gx:drawOrder>").append(this.drawOrder).append("</gx:drawOrder>\n");
        }
        kml.append("<coordinates>").append(this.getCoordinates()).append("</coordinates>\n");
        kml.append("</LineString>\n");
        return kml.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("id:").append(this.getId()).append("\n");
        out.append("extrude:").append(extrude).append("\n");
        out.append("altitudeOffset:").append(altitudeOffset).append("\n");
        out.append("tessellate:").append(tessellate).append("\n");
        out.append("drawOrder:").append(drawOrder).append("\n");
        out.append("altitudeMode:").append(altitudeMode).append("\n");
        out.append("coordinates:").append(this.getCoordinates()).append("\n");
        return out.toString();

    }

    public String getMnvrXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<!-- DEFAULT SYMBOLID --><symbolID>GFGPGLB-------X</symbolID>\n");
        xml.append("<geometry>line</geometry>\n");
        xml.append(this.getCoordinatesAsMnvrXML());
        xml.append("\n<type>MilStdSymbol</type>\n");
        xml.append("<runtimeDynamicProperties>highLighted</runtimeDynamicProperties>\n");
        xml.append("<highLighted dataType=\"void\">undefined</highLighted>\n");
        return xml.toString();
    }

    public String getOvlXml() {
        return this.getCoordinatesAsOvlXML();
    }

}
