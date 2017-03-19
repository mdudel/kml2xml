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
 <BalloonStyle id="ID">
 <!-- specific to BalloonStyle -->
 <bgColor>ffffffff</bgColor>            <!-- kml:color -->
 <textColor>ff000000</textColor>        <!-- kml:color -->
 <text>...</text>                       <!-- string -->
 <displayMode>default</displayMode>     <!-- kml:displayModeEnum -->
 </BalloonStyle>
 */
public class BalloonStyle extends ColorStyle {

    private String bgColor = "ffffffff";
    private String textColor = "ff000000";
    private String text = "";
    private String displayMode = "default";
    private Element xmlElement;
    private final String type = "BalloonStyle";

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
        NodeList styleElementList = xmlElement.getElementsByTagNameNS("*","BalloonStyle");
        for (int j = 0; j < styleElementList.getLength(); j++) {
            Node styleNode = styleElementList.item(j);
            if (styleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element styleElement = (Element) styleNode;
                String styleElementTagName = "color";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    this.color = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                }//color
                styleElementTagName = "bgColor";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    this.bgColor = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                }//bgColor  
                styleElementTagName = "textColor";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    this.textColor = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                }//textColor
                styleElementTagName = "text";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    this.text = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                }//text  
                styleElementTagName = "displayMode";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    this.displayMode = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                }//displayMode  

            }
        }//for   
    }

    public BalloonStyle(Element xmlElement) {
        this.xmlElement = xmlElement;
        this.setColor("ffffffff");
        this.setXmlElement(xmlElement);
    }

    public BalloonStyle() {
        this.setColor("ffffffff");
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    public String getKml() {
        StringBuilder kml = new StringBuilder();
        kml.append("<BalloonStyle");
        if (this.getId().length() > 0) {
            kml.append(" id=\"").append(this.getId()).append("\">\n");
        } else {
            kml.append(">\n");
        }
        kml.append("<bgColor>").append(this.bgColor).append("</bgColor>\n");
        kml.append("<textColor>").append(this.textColor).append("</textColor>\n");
        kml.append("<text>").append(this.text).append("</text>\n");
        kml.append("<displayMode>").append(this.displayMode).append("</displayMode>\n");
        kml.append("</BalloonStyle>\n");
        return kml.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("id:").append(this.getId()).append("\n");
        out.append("bgColor:").append(bgColor).append("\n");
        out.append("textColor:").append(textColor).append("\n");
        out.append("text:").append(text).append("\n");
        out.append("displayMode:").append(displayMode).append("\n");
        return out.toString();
    }

    public String getMnvrXml() {
        // No balloon in MNVR schema
        return "";
    }
}
