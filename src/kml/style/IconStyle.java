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
 <IconStyle id="ID">
 <!-- inherited from ColorStyle -->
 <color>ffffffff</color>            <!-- kml:color -->
 <colorMode>normal</colorMode>      <!-- kml:colorModeEnum:normal or random -->

 <!-- specific to IconStyle -->
 <scale>1</scale>                   <!-- float -->
 <heading>0</heading>               <!-- float -->
 <Icon>
 <href>...</href>
 </Icon>
 <hotSpot x="0.5"  y="0.5"
 xunits="fraction" yunits="fraction"/>    <!-- kml:vec2 -->
 </IconStyle>
 */
public class IconStyle extends ColorStyle {

    private String colorMode;
    private float scale;
    private float heading;
    private String Icon; // Actually the href or URL to the icon
    private String hotSpot = "";
    private float hotSpotX = 0.5f;
    private float hotSpotY = 0.5f;
    private String xunits = "fraction";
    private String yunits = "fraction";
    private Element xmlElement;
    private final String type = "IconStyle";
    private boolean colorSet = false;

    public String getType() {
        return type;
    }

    public void setXmlElement(Element xmlElement) {
        this.xmlElement = xmlElement;
        if (this.color == null) {
            color = "ffffffff";
            this.colorSet = false;
        }
        // id attribute
        if (xmlElement.getAttribute("id").length() > 0) {
            this.setId(xmlElement.getAttribute("id"));
        }// id  
        NodeList styleElementList = xmlElement.getElementsByTagNameNS("*","IconStyle");
        for (int j = 0; j < styleElementList.getLength(); j++) {
            Node styleNode = styleElementList.item(j);
            if (styleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element styleElement = (Element) styleNode;
                String styleElementTagName = "color";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    this.color = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    this.colorSet = true;
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
                styleElementTagName = "heading";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    String hdgString = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    try {
                        this.heading = Float.parseFloat(hdgString);
                    } catch (Exception ex) {
                        this.heading = 1.0f;
                    }
                }//heading     
                styleElementTagName = "Icon";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    String iconString = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    this.Icon = iconString.trim();
                }//Icon      
                styleElementTagName = "hotSpot";
                if (styleElement.getElementsByTagNameNS("*",styleElementTagName).getLength() > 0) {
                    this.hotSpot = styleElement.getElementsByTagNameNS("*",styleElementTagName).item(j).getTextContent();
                    System.out.println("TODO: Parse hotSpot " + this.hotSpot);
                }//hotSpot     
            }

        }//for     
    }

    public IconStyle(Element xmlElement) {
        this.xmlElement = xmlElement;
        //Set defaults
        scale = 1.0f;
        this.setColor("ffffffff");
        colorMode = "normal";
        Icon = "http://maps.google.com/mapfiles/kml/pal3/icon21.png";
        heading = 0.0f;
        hotSpotX = 0.5f;
        hotSpotY = 0.5f;
        xunits = "fraction";
        yunits = "fraction";
        this.setXmlElement(xmlElement);
    }

    public IconStyle() {
        scale = 1.0f;
        color = "ffffffff";
        colorMode = "normal";
        Icon = "http://maps.google.com/mapfiles/kml/pal3/icon21.png";
        heading = 0.0f;
        hotSpotX = 0.5f;
        hotSpotY = 0.5f;
        xunits = "fraction";
        yunits = "fraction";
    }

    public float getHotSpotX() {
        return hotSpotX;
    }

    public void setHotSpotX(float hotSpotX) {
        this.hotSpotX = hotSpotX;
    }

    public float getHotSpotY() {
        return hotSpotY;
    }

    public void setHotSpotY(float hotSpotY) {
        this.hotSpotY = hotSpotY;
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

    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        this.heading = heading;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }

    public String getKml() {
        StringBuilder kml = new StringBuilder();
        kml.append("<IconStyle");
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
        if (heading != 0.0f) {
            kml.append("<heading>").append(heading).append("</heading>\n");
        }
        if (Icon != null) {
            kml.append("<Icon><href>").append(Icon).append("</href></Icon>\n");
        }
        kml.append("<hotSpot x=\"").append(hotSpotX).append("\" y=\"").append(hotSpotY).append("\" xunits=\"").append(xunits).append("\" yunits=\"").append(yunits).append("\"/>\n");
        kml.append("</IconStyle>\n");
        return kml.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("id:").append(this.getId()).append("\n");
        out.append("color:").append(color).append("\n");
        out.append("colorMode:").append(colorMode).append("\n");
        out.append("scale:").append(scale).append("\n");
        out.append("heading:").append(heading).append("\n");
        out.append("Icon:").append(Icon).append("\n");
        out.append("hotSpotX:").append(hotSpotX).append("\n");
        out.append("hotSpotY:").append(hotSpotY).append("\n");
        //out.append("hotSpot:").append(hotSpot).append("\n");
        out.append("xunits:").append(xunits).append("\n");
        out.append("yunits:").append(yunits).append("\n");
        return out.toString();
    }

    public String getMnvrXmlIcon() {
        String icon = "GFGPGPRI------X";
        if (this.getIcon().toUpperCase().contains("PUSHPIN")) {
            icon = "GFGPGPPC------X";
        }
        if ((this.getIcon().toUpperCase().contains("PADDLE")) && (this.getIcon().toUpperCase().contains("-LV"))) {
            icon = "WAS-GND-NCP----";
        }
        if (this.getIcon().contains("broken_link")) {
            icon = "GFGPGPPC------X";
        }
        if (this.getIcon().contains("triangle")) {
            icon = "WOS-IBMG--P----";
        }
        if (this.getIcon().contains("star")) {
            icon = "GFGPGPPD------X";
        }
        if (this.getIcon().contains("target")) {
            icon = "GFGPGPWA------X";
        }
        if (this.getIcon().contains("/square")) {
            icon = "GFGPGPRN------X";
        }
        if (this.getIcon().contains("donut")) {
            icon = "WAS-WC----P----";
        }
        if (this.getIcon().contains("shaded_dot")) {
            icon = "WAS-CCCSOCP----";
        }
        if (this.getIcon().contains("marina")) {
            icon = "WOS-HPBA--P----";
        }
        if (this.getIcon().contains("mountains")) {
            icon = "GFOPSBM-------X";
        }
        if (this.getIcon().contains("campground")) {
            icon = "WAS-TM----P----";
        }
//        if (this.getIcon().contains("circle")) {
//            icon = "GFMPOMT-------X";
//        }
        if (this.getIcon().contains("arrow")) {
            icon = "WAS-WST-SQP----";
        }
        // TODO: Regular expression that can determine 2525B/C symbol codes...
        String mnvrIcon = "<!-- KML to MNVR MAPPING --><symbolID>" + icon + "</symbolID>\n";
        return mnvrIcon;
    }

    private void setDefaultIconColor() {
        if (this.getIcon().contains("paddle")) {
            this.setColor("FF1400FF");
        }
        if (this.getIcon().contains("wht-")) {
            this.setColor("FFFFFFFF");
        } else if (this.getIcon().contains("ltblu-")) {
            this.setColor("FFF0FF14");
        } else if (this.getIcon().contains("blu-")) {
            this.setColor("FFF05A14");
        } else if (this.getIcon().contains("blue")) {
            this.setColor("FFF05A14");
        } else if (this.getIcon().contains("grn-")) {
            this.setColor("FF14F000");
        } else if (this.getIcon().contains("pink-")) {
            this.setColor("FFFF78F0");
        } else if (this.getIcon().contains("purple-")) {
            this.setColor("FF780078");
        } else if (this.getIcon().contains("red-")) {
            this.setColor("FF1400FF");
        } else if (this.getIcon().contains("ylw-")) {
            this.setColor("FF14F0FF");
        }
    }

    public String getMnvrXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<angleOfRotation>").append(this.getHeading()).append("</angleOfRotation>\n");
        // Need to deconflict linecolor with other styles, this is valid for POINTS only
        if (!this.colorSet) {
            this.setDefaultIconColor();
        }
        xml.append("<!-- IconStyle --><lineColor>").append(this.getColorAsMnvrColorString()).append("</lineColor>\n");
        xml.append(this.getMnvrXmlIcon());
        return xml.toString();
    }

    public String getOvlXmlIcon() {
        String icon = "GFGPGPRI------X";
        if (this.getIcon().toUpperCase().contains("PUSHPIN")) {
            icon = "GFGPGPPC------X";
        }
        if ((this.getIcon().toUpperCase().contains("PADDLE")) && (this.getIcon().toUpperCase().contains("-LV"))) {
            icon = "GFGPGPRI------X";
        }
        if (this.getIcon().contains("broken_link")) {
            icon = "GFGPGPPC------X";
        }
        if (this.getIcon().contains("triangle")) {
            icon = "WOS-IBMG--P----";
        }
        if (this.getIcon().contains("star")) {
            icon = "GFGPGPPD------X";
        }
        if (this.getIcon().contains("target")) {
            icon = "GFGPGPWA------X";
        }
        if (this.getIcon().contains("/square")) {
            icon = "GFGPGPRN------X";
        }
        if (this.getIcon().contains("donut")) {
            icon = "GFGPGPWA------X";
        }
        if (this.getIcon().contains("shaded_dot")) {
            icon = "WAS-CCCSOCP----";
        }
        if (this.getIcon().contains("marina")) {
            icon = "WOS-HPBA--P----";
        }
        if (this.getIcon().contains("mountains")) {
            icon = "GFOPSBM-------X";
        }
        if (this.getIcon().contains("campground")) {
            icon = "WAS-TM----P----";
        }
//        if (this.getIcon().contains("circle")) {
//            icon = "GFMPOMT-------X";
//        }
        if (this.getIcon().contains("arrow")) {
            icon = "WAS-WST-SQP----";
        }
        // TODO: Regular expression that can determine 2525B/C symbol codes...
        String ovlIcon = "<!-- IconStyle --><MIL_ID>" + icon + "</MIL_ID>\n";
        return ovlIcon;
    }

    public String getOvlXml() {
        StringBuilder xml = new StringBuilder();
        if (!this.colorSet) {
            this.setDefaultIconColor();
        }
        xml.append("<!-- IconStyle --><LINE_COLOR>").append(this.getColorAsOvlColorString()).append("</LINE_COLOR>\n<!-- IconStyle --><LINE_GEOM>1</LINE_GEOM>\n");
        xml.append(this.getOvlXmlIcon());
        return xml.toString();
    }
}
