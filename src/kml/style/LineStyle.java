/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kml.style;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 <LineStyle id="ID">
 <!-- inherited from ColorStyle -->
 <color>ffffffff</color>            <!-- kml:color -->
 <colorMode>normal</colorMode>      <!-- colorModeEnum: normal or random -->

 <!-- specific to LineStyle -->
 <width>1</width>                            <!-- float -->
 <gx:outerColor>ffffffff</gx:outerColor>     <!-- kml:color -->
 <gx:outerWidth>0.0</gx:outerWidth>          <!-- float -->
 <gx:physicalWidth>0.0</gx:physicalWidth>    <!-- float -->
 <gx:labelVisibility>0</gx:labelVisibility>  <!-- boolean -->
 </LineStyle>
 */
public class LineStyle extends ColorStyle {

    private String colorMode = "normal";
    private float scale = 1.0f;
    private float width = 1.0f;
    private String outerColor = "ffffffff";
    private float outerWidth = 0.0f;
    private float physicalWidth = 0.0f;
    private int labelVisibility = 0;
    private Element xmlElement;
    private final String type = "LineStyle";

    public String getType() {
        return type;
    }

    public void setXmlElement(Element xmlElement) {
        this.xmlElement = xmlElement;
        if (this.color == null) {
            color = "ffffffff";
        }
        // id attribue
        if (xmlElement.getAttribute("id").length() > 0) {
            this.setId(xmlElement.getAttribute("id"));
        }// id  
        NodeList styleElementList = xmlElement.getElementsByTagNameNS("*","LineStyle");
        for (int j = 0; j < styleElementList.getLength(); j++) {
            Node styleNode = styleElementList.item(j);
            if (styleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element styleElement = (Element) styleNode;
                String styleElementTagName = "color";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    this.color = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                }//color
                styleElementTagName = "colorMode";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    this.colorMode = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                }//colorMode  
                styleElementTagName = "scale";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    String scaleString = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    try {
                        this.scale = Float.parseFloat(scaleString);
                    } catch (Exception ex) {
                        this.scale = 1.0f;
                    }
                }//scale     
                styleElementTagName = "width";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    String widthString = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    try {
                        this.width = Float.parseFloat(widthString);
                    } catch (Exception ex) {
                        this.width = 1.0f;
                    }
                }//width  
                styleElementTagName = "gx:outerWidth";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    String widthString = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    try {
                        this.outerWidth = Float.parseFloat(widthString);
                    } catch (Exception ex) {
                        this.outerWidth = 0.0f;
                    }
                }//gx:outerWidth  
                styleElementTagName = "gx:physicalWidth";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    String widthString = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    try {
                        this.physicalWidth = Float.parseFloat(widthString);
                    } catch (Exception ex) {
                        this.physicalWidth = 0.0f;
                    }
                }//gx:physicalWidth  
                styleElementTagName = "gx:labelVisibility";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    String visString = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    try {
                        this.labelVisibility = Integer.parseInt(visString);
                    } catch (Exception ex) {
                        this.labelVisibility = 0;
                    }
                }//gx:labelVisibility 
            }
        }//for   
    }

    public LineStyle(Element xmlElement) {
        this.xmlElement = xmlElement;
        this.setColor("ffffffff");
        this.setXmlElement(xmlElement);
    }

    public LineStyle() {
    }

    public String getColorMode() {
        return colorMode;
    }

    public void setColorMode(String colorMode) {
        this.colorMode = colorMode;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public String getOuterColor() {
        return outerColor;
    }

    public void setOuterColor(String outerColor) {
        this.outerColor = outerColor;
    }

    public float getOuterWidth() {
        return outerWidth;
    }

    public void setOuterWidth(float outerWidth) {
        this.outerWidth = outerWidth;
    }

    public float getPhysicalWidth() {
        return physicalWidth;
    }

    public void setPhysicalWidth(float physicalWidth) {
        this.physicalWidth = physicalWidth;
    }

    public int getLabelVisibility() {
        return labelVisibility;
    }

    public void setLabelVisibility(int labelVisibility) {
        this.labelVisibility = labelVisibility;
    }

    public String getKml() {
        StringBuilder kml = new StringBuilder();
        kml.append("<LineStyle");
        if (this.getId().length() > 0) {
            kml.append(" id=\"").append(this.getId()).append("\">\n");
        } else {
            kml.append(">\n");
        }
        kml.append("<color>").append(this.color).append("</color>\n");
        kml.append("<colorMode>").append(this.colorMode).append("</colorMode>\n");
        if (scale != 1.0f) {
            kml.append("<scale>").append(scale).append("</scale>\n");
        }
        if (width != 1.0f) {
            kml.append("<width>").append(width).append("</width>\n");
        }
        kml.append("<gx:outerColor>").append(this.outerColor).append("</gx:outerColor>\n");
        if (outerWidth != 0.0f) {
            kml.append("<gx:outerWidth>").append(outerWidth).append("</gx:outerWidth>\n");
        }
        if (physicalWidth != 0.0f) {
            kml.append("<gx:physicalWidth>").append(physicalWidth).append("</gx:physicalWidth>\n");
        }
        kml.append("<gx:labelVisibility>").append(this.labelVisibility).append("</gx:labelVisibility>\n");
        kml.append("</LineStyle>\n");
        return kml.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("id:").append(this.getId()).append("\n");
        out.append("color:").append(color).append("\n");
        out.append("colorMode:").append(colorMode).append("\n");
        out.append("scale:").append(scale).append("\n");
        out.append("width:").append(width).append("\n");
        out.append("gx:outerColor:").append(outerColor).append("\n");
        out.append("gx:outerWidth:").append(outerWidth).append("\n");
        out.append("gx:physicalWidth:").append(physicalWidth).append("\n");
        out.append("gx:labelVisibility:").append(labelVisibility).append("\n");
        return out.toString();
    }

    public String getMnvrXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<lineAlpha>").append(this.getAlphaDecimalAsString()).append("</lineAlpha>\n");
        xml.append("<!-- LineStyle --><lineColor>").append(this.getColorAsMnvrColorString()).append("</lineColor>\n");
        xml.append("<lineWidth>").append(this.getWidth()).append("</lineWidth>\n");
        return xml.toString();
    }

    public String getOvlXml() {
        StringBuilder xml = new StringBuilder();
        int w = Math.round(this.getWidth());
        xml.append("<!-- LineStyle --><LINE_GEOM>1</LINE_GEOM>\n");
        xml.append("<!-- LineStyle --><LINE_WIDTH>").append(w).append("</LINE_WIDTH>\n");
        xml.append("<!-- LineStyle --><LINE_COLOR>").append(this.getColorAsOvlColorString()).append("</LINE_COLOR>\n");
        return xml.toString();
    }
}
