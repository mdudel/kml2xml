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
 <StyleMap id="ID">
 <!-- extends StyleSelector -->
 <!-- elements specific to StyleMap -->
 <Pair id="ID">
 <key>normal</key>              <!-- kml:styleStateEnum:  normal or highlight -->
 <styleUrl>...</styleUrl> or <Style>...</Style>
 </Pair>
 </StyleMap>
 */
public class StyleMap extends ColorStyle {

    public String normal = "null";
    public String highlight = "null";
    private Element element;
    private boolean excludeStyleMapInPlacemark = false;
    private final String type = "StyleMap";

    public StyleMap() {

    }

    public StyleMap(Element element) {
        this.setElement(element);

    }

    public StyleMap(Element element, boolean excludeStyleMapInPlacemark) {
        this.setElement(element, excludeStyleMapInPlacemark);
    }

    public String getType() {
        return type;
    }

    public void setElement(Element element, boolean excludeStyleMapInPlacemark) {
        this.excludeStyleMapInPlacemark = excludeStyleMapInPlacemark;
        this.setElement(element);
        excludeStyleMapInPlacemark = false;
    }

    public void setElement(Element element) {
        if (this.excludeStyleMapInPlacemark) {
            if (element.getParentNode().getNodeName().equalsIgnoreCase("Placemark")) {
                return;
            }
        }
        // id attribute
        if (element.getAttribute("id").length() > 0) {
            String id = element.getAttribute("id");
            this.setId(id);
        }// id
        NodeList pairs = element.getElementsByTagNameNS("*","Pair");
        int tagKnt = pairs.getLength();
        if (tagKnt > 0) {
            for (int knt = 0; knt < tagKnt; knt++) {
                Node pair = pairs.item(knt);
                String styleUrlKey = "";
                String styleUrl = "";
                if (pair.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) pair;
                    String pairTagName = "key";
                    if (el.getElementsByTagNameNS("*",pairTagName).getLength() > 0) {
                        styleUrlKey = el.getElementsByTagNameNS("*",pairTagName).item(0).getTextContent();
                    }//key
                    pairTagName = "styleUrl";
                    if (el.getElementsByTagNameNS("*",pairTagName).getLength() > 0) {
                        styleUrl = el.getElementsByTagNameNS("*",pairTagName).item(0).getTextContent();
                    }//styleUrl  
                    if (styleUrlKey.equalsIgnoreCase("normal")) {
                        normal = styleUrl;
                    } else if (styleUrlKey.equalsIgnoreCase("highlight")) {
                        highlight = styleUrl;
                    }
                }//node
            }
            //System.out.println("element styleUrl : " + element.getElementsByTagNameNS("*","styleUrl").item(0).getTextContent());
        }// styleUrl        
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("id:").append(this.getId()).append("\n");
        out.append("StyleMap normal:").append(this.normal).append("\n");
        out.append("StyleMap highlight:").append(this.highlight).append("\n");
        return out.toString();
    }

    public String getMnvrXml() {
        StringBuilder xml = new StringBuilder();
        // MNVR XML has no highlight so only the normal reference is returned
        String norm = this.getNormal();
        if (norm.startsWith("#")) {
            norm = norm.substring(1, norm.length());
        }
        xml.append(this.styleMapTemplate).append(":").append(norm).append("\n");
        //System.out.println("In StyleMap.getMnvrXml, normal URL: " + norm + " returning " + xml.toString());//DEBUG
        return xml.toString();
    }

    public String getOvlXml() {
        StringBuilder xml = new StringBuilder();
        // OVL XML has no highlight so only the normal reference is returned
        String norm = this.getNormal();
        if (norm.startsWith("#")) {
            norm = norm.substring(1, norm.length());
        }
        xml.append(this.styleMapTemplate).append(":").append(norm).append("\n");
        return xml.toString();
    }

}
