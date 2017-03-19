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

/**
 * <Polygon id="ID">
 * <!-- specific to Polygon -->
 * <extrude>0</extrude>                       <!-- boolean -->
 * <tessellate>0</tessellate>                 <!-- boolean -->
 * <altitudeMode>clampToGround</altitudeMode>
 * <!-- kml:altitudeModeEnum: clampToGround, relativeToGround, or absolute -->
 * <!-- or, substitute gx:altitudeMode: clampToSeaFloor, relativeToSeaFloor -->
 * <outerBoundaryIs>
 * <LinearRing>
 * <coordinates>...</coordinates>         <!-- lon,lat[,alt] -->
 * </LinearRing>
 * </outerBoundaryIs>
 * <innerBoundaryIs>
 * <LinearRing>
 * <coordinates>...</coordinates>         <!-- lon,lat[,alt] -->
 * </LinearRing>
 * </innerBoundaryIs>
 * </Polygon>
 */
public class Polygon extends Geometry {

    private int extrude = 0;
    private int tessellate = 0;
    private String altitudeMode = "clampToGround";
    private Element xmlElement;
    private final String type = "Polygon";
    private LinearRing outerBoundaryIs;
    private LinearRing innerBoundaryIs;

    public String getType() {
        return type;
    }

    private void extractPolygonProperties(Element element) {

        String stringElementTagName = "extrude";
        if (element.getElementsByTagNameNS("*", stringElementTagName).getLength() > 0) {
            String extrudeString = element.getElementsByTagNameNS("*", stringElementTagName).item(0).getTextContent();
            try {
                this.extrude = Integer.parseInt(extrudeString);
            } catch (Exception ex) {
                this.extrude = 0;
            }
            //System.out.println("Setting extrude: " + this.getExtrude());
        }//extrude    
        stringElementTagName = "tessellate";
        if (element.getElementsByTagNameNS("*", stringElementTagName).getLength() > 0) {
            String tessellateString = element.getElementsByTagNameNS("*", stringElementTagName).item(0).getTextContent();
            try {
                this.tessellate = Integer.parseInt(tessellateString);
            } catch (Exception ex) {
                this.tessellate = 0;
            }
            //System.out.println("Setting tessellate: " + this.getTessellate());
        }//tessellate   
        stringElementTagName = "outerBoundaryIs";
        if (element.getElementsByTagNameNS("*", stringElementTagName).getLength() > 0) {
            NodeList outerList = element.getElementsByTagNameNS("*", stringElementTagName);
            Node outerNode = outerList.item(0);
            if (outerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element boundary = (Element) outerNode;
                outerBoundaryIs = new LinearRing(boundary);
                //System.out.println("Setting tessellate: " + this.outerBoundaryIs.getCoordinates());
            }
        }//outerBoundaryIs  
        // TODO: A polygon can have multiple inner boundaries
        stringElementTagName = "innerBoundaryIs";
        if (element.getElementsByTagNameNS("*", stringElementTagName).getLength() > 0) {
            NodeList outerList = element.getElementsByTagNameNS("*", stringElementTagName);
            Node outerNode = outerList.item(0);
            if (outerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element boundary = (Element) outerNode;
                innerBoundaryIs = new LinearRing(boundary);
                //System.out.println("Setting tessellate: " + this.innerBoundaryIs.getCoordinates());
            }
        }//innerBoundaryIs         
    }

    private void setXmlElement(Element xmlElement) {
        //System.out.println("Setting Polygon element: " + xmlElement.getTagName());
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
            //System.out.println("Setting id: " + this.getId());
        }// id  
        NodeList elementList = xmlElement.getElementsByTagNameNS("*", "Polygon");
        //System.out.println("Number of <Polygon> tags: " + elementList.getLength());
        if (elementList.getLength() > 0) {
            for (int j = 0; j < elementList.getLength(); j++) {
                Node node = elementList.item(j);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    this.extractPolygonProperties(element);
                }// is ELEMENT_NODE
            }//for  
        } else {
            this.extractPolygonProperties(xmlElement);
        }
    }

    public Polygon() {
    }

    public Polygon(Element xmlElement) {
        this.setXmlElement(xmlElement);
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

    public LinearRing getOuterBoundaryIs() {
        return outerBoundaryIs;
    }

    public void setOuterBoundaryIs(LinearRing outerBoundaryIs) {
        this.outerBoundaryIs = outerBoundaryIs;
    }

    public LinearRing getInnerBoundaryIs() {
        return innerBoundaryIs;
    }

    public void setInnerBoundaryIs(LinearRing innerBoundaryIs) {
        this.innerBoundaryIs = innerBoundaryIs;
    }

    public String getKml() {
        StringBuilder kml = new StringBuilder();
        kml.append("<Polygon");
        if (this.getId().length() > 0) {
            kml.append(" id=\"").append(this.getId()).append("\">\n");
        } else {
            kml.append(">\n");
        }
        if (this.extrude != 0) {
            kml.append("<extrude>").append(this.extrude).append("</extrude>\n");
        }
        if (this.tessellate != 0) {
            kml.append("<tessellate>").append(this.tessellate).append("</tessellate>\n");
        }
        kml.append("<altitudeMode>").append(this.altitudeMode).append("</altitudeMode>\n");
        if (outerBoundaryIs != null) {
            kml.append(outerBoundaryIs.getKml());
        }
        if (innerBoundaryIs != null) {
            kml.append(innerBoundaryIs.getKml());
        }
        kml.append("</Polygon>\n");
        return kml.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("id:").append(this.getId()).append("\n");
        out.append("extrude:").append(extrude).append("\n");
        out.append("tessellate:").append(tessellate).append("\n");
        out.append("altitudeMode:").append(altitudeMode).append("\n");
        if (outerBoundaryIs != null) {
            out.append("Polygon outerBoundaryIs\n").append(outerBoundaryIs.toString());
        }
        if (innerBoundaryIs != null) {
            out.append("Polygon innerBoundaryIs\n").append(innerBoundaryIs.toString());
        }
        return out.toString();
    }

    public String getCoordinates() {
        StringBuilder out = new StringBuilder();
        if (outerBoundaryIs != null) {
            out.append(outerBoundaryIs.getCoordinates());
        }
        return out.toString();
    }

    public String getMnvrXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<!-- DEFAULT SYMBOLID --><symbolID>GFGPGAG-------X</symbolID>\n");
        xml.append("<geometry>area</geometry>\n");
        xml.append(this.outerBoundaryIs.getCoordinatesAsMnvrXML());
        xml.append("\n<type>GRAPHIC</type>\n");
        return xml.toString();
    }

    public String getOvlXml() {
        return this.outerBoundaryIs.getCoordinatesAsOvlXML();
    }
}
