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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 <PolyStyle id="ID">
 <!-- inherited from ColorStyle -->
 <color>ffffffff</color>            <!-- kml:color -->
 <colorMode>normal</colorMode>      <!-- kml:colorModeEnum: normal or random -->

 <!-- specific to PolyStyle -->
 <fill>1</fill>                     <!-- boolean -->
 <outline>1</outline>               <!-- boolean -->
 </PolyStyle>

 Elements Specific to PolyStyle
 <fill>
 Boolean value. Specifies whether to fill the polygon. 
 <outline>
 Boolean value. Specifies whether to outline the polygon. 
 Polygon outlines use the current LineStyle. 
 */
public class PolyStyle extends ColorStyle {

    private String colorMode = "normal";
    private int fill = 1;
    private int outline = 1;
    private Element xmlElement;
    private final String type = "PolyStyle";

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
        NodeList styleElementList = xmlElement.getElementsByTagNameNS("*","PolyStyle");
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
                styleElementTagName = "fill";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    String fillString = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    try {
                        this.fill = Integer.parseInt(fillString);
                    } catch (Exception ex) {
                        this.fill = 1;
                    }
                }//fill 
                styleElementTagName = "outline";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    String olString = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    try {
                        this.outline = Integer.parseInt(olString);
                    } catch (Exception ex) {
                        this.outline = 1;
                    }
                }//outline                 
            }
        }//for   
    }

    public PolyStyle(Element xmlElement) {
        this.xmlElement = xmlElement;
        this.setColor("ffffffff");
        this.setXmlElement(xmlElement);
    }

    public PolyStyle() {
    }

    public String getColorMode() {
        return colorMode;
    }

    public void setColorMode(String colorMode) {
        this.colorMode = colorMode;
    }

    public int getFill() {
        return fill;
    }

    public void setFill(int fill) {
        this.fill = fill;
    }

    public int getOutline() {
        return outline;
    }

    public void setOutline(int outline) {
        this.outline = outline;
    }

    public String getKml() {
        StringBuilder kml = new StringBuilder();
        kml.append("<PolyStyle");
        if (this.getId().length() > 0) {
            kml.append(" id=\"").append(this.getId()).append("\">\n");
        } else {
            kml.append(">\n");
        }
        kml.append("<color>").append(this.getColor()).append("</color>\n");
        kml.append("<colorMode>").append(this.colorMode).append("</colorMode>\n");
        kml.append("<fill>").append(this.fill).append("</fill>\n");
        kml.append("<outline>").append(this.outline).append("</outline>\n");
        kml.append("</PolyStyle>\n");
        return kml.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("id:").append(this.getId()).append("\n");
        out.append("color:").append(color).append("\n");
        out.append("colorMode:").append(colorMode).append("\n");
        out.append("fill:").append(fill).append("\n");
        out.append("outline:").append(outline).append("\n");
        return out.toString();
    }

    public String getMnvrXml() {
        StringBuilder xml = new StringBuilder();
        if (this.fill < 1) {
            //No fill
            xml.append("<fillAlpha>0</fillAlpha>\n");
            xml.append("<fillStyle>none</fillStyle>\n");
        } else {
            //Use fill
            xml.append("<fillAlpha>").append(this.getAlphaDecimalAsString()).append("</fillAlpha>\n");
            xml.append("<fillColor>").append(this.getColorAsMnvrColorString()).append("</fillColor>\n");
            xml.append("<fillStyle>solid</fillStyle>\n");
        }
        return xml.toString();
    }

    public String getOvlXml() {
        StringBuilder xml = new StringBuilder();
        if (this.fill < 1) {
            //No fill
            return "";
        } else {
            //Use fill
//                0 - FILL_SOLID
//                1 - FILL_EMPTY
//                2 - STIPPLE_50 less transparent
//                25 - STIPPLE_25
//                10 - STIPPLE_10 more transparent
//                <FILL_TYPE>10</FILL_TYPE>
            int fillType = 0;
            double alpha = this.getAlphaDecimal();
            if (alpha > .90) {
                fillType = 0;
            } else if (alpha > .66) {
                fillType = 2;
            } else if (alpha > .33) {
                fillType = 25;
            } else {
                fillType = 10;
            }
            xml.append("<!-- PolyStyle --><FILL_TYPE>").append(fillType).append("</FILL_TYPE>\n");
            xml.append("<!-- PolyStyle --><FILL_COLOR>").append(this.getColorAsOvlColorString()).append("</FILL_COLOR>\n");
        }
        return xml.toString();
    }

}
