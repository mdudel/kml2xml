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
 <LabelStyle id="ID">
 <!-- inherited from ColorStyle -->
 <color>ffffffff</color>            <!-- kml:color -->
 <colorMode>normal</colorMode>      <!-- kml:colorModeEnum: normal or random -->

 <!-- specific to LabelStyle -->
 <scale>1</scale>                   <!-- float -->
 </LabelStyle>
 */
public class LabelStyle extends ColorStyle {

    private String colorMode = "normal";
    private float scale = 1.0f;
    private Element xmlElement;
    private final String type = "LabelStyle";

    public String getType() {
        return type;
    }

    public void setXmlElement(Element xmlElement) {
        this.xmlElement = xmlElement;
        if (this.color == null) {
            color = "ffffffff";
        }
        // id attribute
        if (xmlElement.getAttribute("id").length() > 0) {
            this.setId(xmlElement.getAttribute("id"));
        }// id  
        NodeList styleElementList = xmlElement.getElementsByTagNameNS("*","LabelStyle");
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
            }
        }//for            
    }

    public LabelStyle(Element xmlElement) {
        this.color = "ffffffff";
        this.xmlElement = xmlElement;
        this.setXmlElement(xmlElement);
    }

    public LabelStyle() {
        scale = 1.0f;
        color = "ffffffff";
        colorMode = "normal";
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

    public String getKml() {
        StringBuilder kml = new StringBuilder();
        kml.append("<LabelStyle");
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
        kml.append("</LabelStyle>\n");
        return kml.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("id:").append(this.getId()).append("\n");
        out.append("color:").append(color).append("\n");
        out.append("colorMode:").append(colorMode).append("\n");
        out.append("scale:").append(scale).append("\n");
        return out.toString();
    }

    public String getMnvrXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<labelFontColor>").append(this.getColorAsMnvrColorString()).append("</labelFontColor>\n");
        xml.append("<labelBackColor>").append(this.getContrastingMnvrColor()).append("</labelBackColor >\n");
        return xml.toString();
    }

    public String getOvlXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<!-- LabelStyle --><LABEL_TYPE>Name</LABEL_TYPE>\n");
        xml.append("<!-- LabelStyle --><TEXTCOLOR>").append(this.getColorAsOvlColorString()).append("</TEXTCOLOR>\n");
        return xml.toString();
    }
}
