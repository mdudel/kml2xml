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
package kml.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kml.geometry.LineString;
import kml.geometry.LinearRing;
import kml.geometry.Point;
import kml.geometry.Polygon;
import kml.style.StyleMap;
import kml.style.Styles;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 <Placemark id="ID">
 <!-- inherited from Feature element -->
 <name>...</name>                      <!-- string -->
 <visibility>1</visibility>            <!-- boolean -->
 <open>0</open>                        <!-- boolean -->
 <atom:author>...<atom:author>         <!-- xmlns:atom -->
 <atom:link href=" "/>                <!-- xmlns:atom -->
 <address>...</address>                <!-- string -->
 <xal:AddressDetails>...</xal:AddressDetails>  <!-- xmlns:xal -->
 <phoneNumber>...</phoneNumber>        <!-- string -->
 <Snippet maxLines="2">...</Snippet>   <!-- string -->
 <description>...</description>        <!-- string -->
 <AbstractView>...</AbstractView>      <!-- Camera or LookAt -->
 <TimePrimitive>...</TimePrimitive>
 <styleUrl>...</styleUrl>              <!-- anyURI -->
 <StyleSelector>...</StyleSelector>
 <Region>...</Region>
 <Metadata>...</Metadata>              <!-- deprecated in KML 2.2 -->
 <ExtendedData>...</ExtendedData>      <!-- new in KML 2.2 -->

 <!-- specific to Placemark element -->
 <Geometry>...</Geometry>
 </Placemark>
 */
public class Placemark extends Feature {

    // 
    private Document doc;
    private Map<String, ArrayList<Object>> geometryMap = new HashMap<String, ArrayList<Object>>();
    private final String NO_ID = "NO_ID";
    private final String type = "Placemark";
    Styles style = new Styles();
    StyleMap styleMap = new StyleMap();

    public String getType() {
        return type;
    }

    public Placemark(String id) {
        this.setId(id);
    }

    public Placemark(Document doc) {
        this.setDoc(doc);
    }

    public Placemark(Element element) {
        this.setPlacemarkElement(element);
    }

    public void setPlacemarkElement(Element element) {
        try {
            this.setParent(element.getParentNode().getNodeName());
        } catch (Exception ex) {
            this.setParent("UNKNOWN");
        }

        // id attribute
        if (element.getAttribute("id").length() > 0) {
            String id = element.getAttribute("id");
            this.setId(id);
        }// id
        // name element
        if (element.getElementsByTagNameNS("*", "name").getLength() > 0) {
            String name = element.getElementsByTagNameNS("*", "name").item(0).getTextContent();
            this.setName(name);
        }// name
        if (element.getElementsByTagNameNS("*", "description").getLength() > 0) {
            String description = element.getElementsByTagNameNS("*", "description").item(0).getTextContent();
            this.setDescription(description);
        }// description     
        if (element.getElementsByTagNameNS("*", "Style").getLength() > 0) {
            this.style.setStyleElement(element);
        }// Style   
        if (element.getElementsByTagNameNS("*", "StyleMap").getLength() > 0) {
            this.styleMap.setElement(element);
        }// StyleMap        
        int suKnt = element.getElementsByTagNameNS("*", "styleUrl").getLength();
        if (element.getElementsByTagNameNS("*", "styleUrl").getLength() > 0) {
            //String styleUrl = element.getElementsByTagNameNS("*","styleUrl").item(0).getTextContent();
            //pm.append("styleUrl: ").append(styleUrl).append("\n");
            for (int knt = 0; knt < suKnt; knt++) {
                String styleUrl = element.getElementsByTagNameNS("*", "styleUrl").item(knt).getTextContent();
                this.setStyleUrl(styleUrl);
            }
            //System.out.println("element styleUrl : " + element.getElementsByTagNameNS("*","styleUrl").item(0).getTextContent());
        }// styleUrl    

        // IGNORE MultiGeometry because the child elements are geometris that are parsed anyway
//                if (element.getElementsByTagNameNS("*","MultiGeometry").getLength() > 0) {
//                    //TODO
//                }// MultiGeometry    
        int polyKnt = element.getElementsByTagNameNS("*", "Polygon").getLength();
        if (polyKnt > 0) {
            NodeList polyNodes = element.getElementsByTagNameNS("*", "Polygon"); //NodeList
            if (polyNodes != null) {
                for (int i = 0; i < polyNodes.getLength(); i++) {
                    if (polyNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element polyElement = (Element) polyNodes.item(i);
                        Polygon polygon = new Polygon(polyElement);
                        //System.out.println("Adding Polygon[" + i + "] id:" + polygon.getId());
                        this.addGeometry(polygon.getId(), polygon);
                    }
                }
            }
//            for (int knt = 0; knt < polyKnt; knt++) {
//                Element polyElement = (Element) element.getElementsByTagNameNS("*","Polygon").item(knt);
//                Polygon polygon = new Polygon(polyElement);
//                System.out.println("Adding Polygon[" + knt + "] id:" + polygon.getId() + " " + polygon.toString());
//                this.addGeometry(polygon.getId(), polygon);
//
//            }
        }// Polygon ====================================================
        //System.out.println("      Placemark.setPlacemarkElement element.getTagName() " + element.getTagName());
        if (element.getTagName().equals("Polygon")) {
            Polygon polygon = new Polygon(element);
            System.out.println("Adding Polygon[ORPHAN] id:" + polygon.getId());
            this.addGeometry(polygon.getId(), polygon);
        }// Polygon Orphan ====================================================
        int pointKnt = element.getElementsByTagNameNS("*", "Point").getLength();
        if (pointKnt > 0) {
            for (int knt = 0; knt < pointKnt; knt++) {
                Point point = new Point(element);
                //System.out.println("Adding LineString[" + knt + "] id:" + lineString.getId() + "\n" + lineString.getCoordinates());
                this.addGeometry(point.getId(), point);
            }
        }// Point ====================================================== 
        if (element.getTagName().equals("Point")) {
            Point point = new Point(element);
            System.out.println("Adding Point[ORPHAN] id:" + point.getId());
            this.addGeometry(point.getId(), point);
        }// Point Orphan ====================================================        
        int lsKnt = element.getElementsByTagNameNS("*", "LineString").getLength();
        if (lsKnt > 0) {
            for (int knt = 0; knt < lsKnt; knt++) {
                LineString lineString = new LineString(element);
                //System.out.println("Adding LineString[" + knt + "] id:" + lineString.getId() + "\n" + lineString.getCoordinates());
                this.addGeometry(lineString.getId(), lineString);
            }
        }// LineString =================================================
//            if (lsKnt > 0) {
//                NodeList lsNodes = element.getElementsByTagNameNS("*","LineString"); //NodeList
//                if (lsNodes != null) {
//                    for (int knt = 0; knt < lsKnt; knt++) {
//                        if (lsNodes.item(knt).getNodeType() == Node.ELEMENT_NODE) {
//                            LineString lineString = new LineString((Element) lsNodes.item(knt));
//                            //System.out.println("Adding LineString[" + knt + "] id:" + lineString.getId() + "\n" + lineString.getCoordinates());
//                            this.addGeometry(lineString.getId(), lineString);
//                        }
//                    }
//                }
//            }        
        if (element.getTagName().equals("LineString")) {
            LineString lineString = new LineString(element);
            System.out.println("Adding LineString[ORPHAN] id:" + lineString.getId());
            this.addGeometry(lineString.getId(), lineString);
        }// LineString Orphan ====================================================         
        // Is this necessary since it is part of a Polygon?
//                int lrKnt = element.getElementsByTagNameNS("*","LinearRing").getLength();
//                if (element.getElementsByTagNameNS("*","LinearRing").getLength() > 0) {
//                    for (int knt = 0; knt < lrKnt; knt++) {
//                        System.out.println("Adding LinearRing[" + knt + "] ");
//                        LinearRing linearRing = new LinearRing(element);
//                        this.addGeometry(linearRing.getId(), linearRing);
//                    }
//                }// LinearRing ================================================= 
    }

    public void setDoc(Document doc) {
        this.doc = doc;
        boolean excludeStylesInPlacemark = true;
        this.style = new Styles(doc, excludeStylesInPlacemark);
        doc.getDocumentElement().normalize();
        // Get placemarks 
        NodeList nl = doc.getElementsByTagNameNS("*", "Placemark");
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            //System.out.println("Current Element [" + i + "] " + node.getNodeName());
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                this.setPlacemarkElement(element);
            }//node
        }//for nodes
    }

    private void addGeometry(String id, Object styleObject) {
        // validate id
        if (id == null) {
            id = NO_ID;
        } else if (id.length() < 1) {
            id = NO_ID;
        }
        // Check for existance of key in styleMap
        if (!this.geometryMap.containsKey(id)) {
            this.geometryMap.put(id, new ArrayList<>());
        }
        // Add the styleObject to the key's array list
        this.geometryMap.get(id).add(styleObject);
    }

// <StyleSelector>...</StyleSelector>
// <Metadata>...</Metadata>              <!-- deprecated in KML 2.2 -->
// <ExtendedData>...</ExtendedData>      <!-- new in KML 2.2 -->
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("TODO: Finish Placemark style handler...\n");
        out.append("Style:\n").append(this.style.toString());
        out.append("StyleMap:\n").append(this.styleMap.toString());
        out.append("styleUrl:").append(this.getStyleUrl()).append("\n");
        out.append("id:").append(this.getId()).append("\n");
        out.append("name:").append(this.getName()).append("\n");
        out.append("visibility:").append(this.getVisibility()).append("\n");
        out.append("open:").append(this.getOpen()).append("\n");
        out.append("description:").append(this.getDescription()).append("\n");
        out.append("TODO: Finish feature properties\n");

        for (Map.Entry<String, ArrayList<Object>> entry : geometryMap.entrySet()) {
            String id = entry.getKey();
            out.append(this.toString(id));
        }//for all id's

        return out.toString();
    }

    public String getKmlCoordinates() {
        StringBuilder kml = new StringBuilder();
        for (Map.Entry<String, ArrayList<Object>> entry : geometryMap.entrySet()) {
            String id = entry.getKey();
            kml.append(this.getKmlCoordinates(id));
        }//for all id's
        return kml.toString();
    }

    public String getKmlCoordinates(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList geometries = geometryMap.get(id);
        for (Object geometry : geometries) {
            if (geometry instanceof LineString) {
                LineString thisGeo = (LineString) geometry;
                sb.append(thisGeo.getCoordinates());
            }
            if (geometry instanceof LinearRing) {
                LinearRing thisGeo = (LinearRing) geometry;
                sb.append(thisGeo.getCoordinates());
            }
            if (geometry instanceof Point) {
                Point thisGeo = (Point) geometry;
                sb.append(thisGeo.getCoordinates());
            }
            if (geometry instanceof Polygon) {
                Polygon thisGeo = (Polygon) geometry;
                sb.append(thisGeo.getCoordinates());
            }
        }
        return sb.toString();
    }

    public String toString(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList geometries = geometryMap.get(id);
        for (Object geometry : geometries) {
            if (geometry instanceof LineString) {
                LineString thisGeo = (LineString) geometry;
                sb.append("START ").append(thisGeo.getType()).append(" id:").append(thisGeo.getId()).append("\n");
                sb.append(thisGeo.toString());
                sb.append("END ").append(thisGeo.getType()).append(" id:").append(thisGeo.getId()).append("\n");
            }
            if (geometry instanceof LinearRing) {
                LinearRing thisGeo = (LinearRing) geometry;
                sb.append("START ").append(thisGeo.getType()).append(" id:").append(thisGeo.getId()).append("\n");
                sb.append(thisGeo.toString());
                sb.append("END ").append(thisGeo.getType()).append(" id:").append(thisGeo.getId()).append("\n");
            }
            if (geometry instanceof Point) {
                Point thisGeo = (Point) geometry;
                sb.append("START ").append(thisGeo.getType()).append(" id:").append(thisGeo.getId()).append("\n");
                sb.append(thisGeo.toString());
                sb.append("END ").append(thisGeo.getType()).append(" id:").append(thisGeo.getId()).append("\n");
            }
            if (geometry instanceof Polygon) {
                Polygon thisGeo = (Polygon) geometry;
                sb.append("START ").append(thisGeo.getType()).append(" id:").append(thisGeo.getId()).append("\n");
                sb.append(thisGeo.toString());
                sb.append("END ").append(thisGeo.getType()).append(" id:").append(thisGeo.getId()).append("\n");
            }
        }
        return sb.toString();
    }

    public String getOvlDescription() {
        String desc = this.getDescription();
        //desc = Normalizer.normalize(desc, Normalizer.Form.NFD);
        //desc = desc.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        desc = StringEscapeUtils.escapeHtml(desc);
        desc = desc.replaceAll("\\P{InBasic_Latin}", "*"); //WORKS
        desc = StringEscapeUtils.escapeXml(desc);
        desc = desc.replaceAll("<", "[").replaceAll(">", "]");
        return desc;
    }

    public String getOvlName() {
        String desc = this.getName().toUpperCase();
        //desc = Normalizer.normalize(desc, Normalizer.Form.NFD);
        //desc = desc.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        desc = desc.replaceAll("\\P{InBasic_Latin}", "*"); //WORKS
        desc = StringEscapeUtils.escapeXml(desc);
        desc = desc.replaceAll("<", "[").replaceAll(">", "]");
        return desc;
    }

    private String getStyleString() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(this.style.toString());
        } catch (Exception ex) {
            return "";
        }
        return sb.toString();
    }

    public String getMnvrXml(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList geometries = geometryMap.get(id);
        int total = geometries.size();
        int count = 0;
        for (Object geometry : geometries) {
            count++;
            sb.append("<MilStdSymbol>\n");
            if (this.getStyleUrl().equals("null")) {
                sb.append(this.style.getMnvrXml());
            } else {
                sb.append(this.styleUrlTemplate).append(":").append(this.getStyleUrl()).append("\n");
            }
            if (total > 1) {
                sb.append("<name>").append(this.getName()).append(" ").append(count).append(" of ").append(total).append("</name>\n");
            } else {
                sb.append("<name>").append(this.getName()).append("</name>\n");
            }
            sb.append("<uniqueDesignation1_T>").append(this.getName()).append("</uniqueDesignation1_T>\n");
            sb.append("<description>").append(this.getDescription(true)).append("</description>\n");
            if (geometry instanceof LineString) {
                LineString thisGeo = (LineString) geometry;
                sb.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sb.append(thisGeo.getMnvrXml());
            }
            if (geometry instanceof LinearRing) {
                LinearRing thisGeo = (LinearRing) geometry;
                sb.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sb.append(thisGeo.getMnvrXml());
            }
            if (geometry instanceof Point) {
                Point thisGeo = (Point) geometry;
                sb.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sb.append(thisGeo.getMnvrXml());
            }
            if (geometry instanceof Polygon) {
                Polygon thisGeo = (Polygon) geometry;
                sb.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sb.append(thisGeo.getMnvrXml());
            }
            sb.append("\n</MilStdSymbol>\n");
        }
        return sb.toString();
    }

    public String getMnvrXml() {
        StringBuilder xml = new StringBuilder();
        for (Map.Entry<String, ArrayList<Object>> entry : geometryMap.entrySet()) {
            String id = entry.getKey();
            xml.append(this.getMnvrXml(id));
        }//for all id's
        return xml.toString();
    }

    public ArrayList getMnvrXmlArray(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        ArrayList xml = new ArrayList();
        ArrayList geometries = geometryMap.get(id);
        int total = geometries.size();
        int count = 0;
        for (Object geometry : geometries) {
            count++;
            StringBuilder sb = new StringBuilder();
            sb.append("<MilStdSymbol>\n");
            if (this.getStyleUrl().equals("null")) {
                sb.append("<!-- Style  -->\n");
                sb.append(this.style.getMnvrXml());
            } else {
                sb.append("<!-- StyleUrl id: ").append(this.getStyleUrl()).append(" -->\n"); //DEBUG
                sb.append(this.styleUrlTemplate).append(":").append(this.getStyleUrl()).append("\n");
            }
            if (total > 1) {
                sb.append("<name>").append(this.getName()).append(" ").append(count).append(" of ").append(total).append("</name>\n");
            } else {
                sb.append("<name>").append(this.getName()).append("</name>\n");
            }
            sb.append("<uniqueDesignation1_T>").append(this.getName()).append("</uniqueDesignation1_T>\n");
            sb.append("<description>").append(this.getDescription(true)).append("</description>\n");
            if (geometry instanceof LineString) {
                LineString thisGeo = (LineString) geometry;
                sb.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sb.append(thisGeo.getMnvrXml());
            }
            if (geometry instanceof LinearRing) {
                LinearRing thisGeo = (LinearRing) geometry;
                sb.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sb.append(thisGeo.getMnvrXml());
            }
            if (geometry instanceof Point) {
                Point thisGeo = (Point) geometry;
                sb.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sb.append(thisGeo.getMnvrXml());
            }
            if (geometry instanceof Polygon) {
                Polygon thisGeo = (Polygon) geometry;
                sb.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sb.append(thisGeo.getMnvrXml());
            }
            sb.append("\n</MilStdSymbol>\n");
            xml.add(sb.toString());
        }
        return xml;
    }

    public ArrayList getMnvrXmlArray() {
        ArrayList xmlArray = new ArrayList();
        for (Map.Entry<String, ArrayList<Object>> entry : geometryMap.entrySet()) {
            String id = entry.getKey();
            ArrayList xmlItems = getMnvrXmlArray(id);
            for (Object xmlItem : xmlItems) {
                xmlArray.add(xmlItem);
            }
        }//for all id's
        return xmlArray;
    }

    public ArrayList getOvlXmlArray(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        ArrayList xml = new ArrayList();
        ArrayList geometries = geometryMap.get(id);
        int total = geometries.size();
        int count = 0;
        for (Object geometry : geometries) {
            count++;
            // Build the style
            StringBuilder sbStyle = new StringBuilder();
            if (this.getStyleUrl().equals("null")) {
                sbStyle.append(this.style.getOvlXml());
            } else {
                sbStyle.append("<!-- STYLEURL:").append(this.getStyleUrl()).append(" -->\n");
                sbStyle.append(this.styleUrlTemplate).append(":").append(this.getStyleUrl()).append("\n");
            }
            if (total > 1) {
                sbStyle.append("<NAME>").append(this.getOvlName()).append(" ").append(count).append(" of ").append(total).append("</NAME>\n");
            } else {
                sbStyle.append("<NAME>").append(this.getOvlName()).append("</NAME>\n");
            }
            sbStyle.append("<DESCRIPTION>").append(this.getOvlDescription()).append("</DESCRIPTION>\n");

            // Build the ovl objects
            StringBuilder sbOvl = new StringBuilder();
            if (geometry instanceof LineString) {
                sbOvl.append("<polygon>\n");
                sbOvl.append(sbStyle);
                LineString thisGeo = (LineString) geometry;
                sbOvl.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sbOvl.append(thisGeo.getOvlXml());
                sbOvl.append("</polygon>\n");
            }
            if (geometry instanceof LinearRing) {
                sbOvl.append("<polygon>\n");
                sbOvl.append(sbStyle);
                sbOvl.append("<CLOSED>true</CLOSED>\n");
                LinearRing thisGeo = (LinearRing) geometry;
                sbOvl.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sbOvl.append(thisGeo.getOvlXml());
                sbOvl.append("</polygon>\n");
            }
            if (geometry instanceof Point) {
                sbOvl.append("<milbobject>\n");
                sbOvl.append(sbStyle);
                Point thisGeo = (Point) geometry;
                sbOvl.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sbOvl.append(thisGeo.getOvlXml());
                sbOvl.append("<CLOSED>false</CLOSED>\n"
                        + "<T_VIS>true</T_VIS>\n");
                sbOvl.append("</milbobject>\n");
            }
            if (geometry instanceof Polygon) {
                sbOvl.append("<polygon>\n");
                sbOvl.append(sbStyle);
                sbOvl.append("<CLOSED>true</CLOSED>\n");
                Polygon thisGeo = (Polygon) geometry;
                sbOvl.append("<!-- KML GEOMETRY TYPE: ").append(thisGeo.getType()).append(" -->\n");
                sbOvl.append(thisGeo.getOvlXml());
                sbOvl.append("</polygon>\n");
            }
            xml.add(sbOvl.toString());
        }
        return xml;
    }

    public ArrayList getOvlXmlArray() {
        ArrayList xmlArray = new ArrayList();
        for (Map.Entry<String, ArrayList<Object>> entry : geometryMap.entrySet()) {
            String id = entry.getKey();
            ArrayList xmlItems = getOvlXmlArray(id);
            for (Object xmlItem : xmlItems) {
                xmlArray.add(xmlItem);
            }
        }//for all id's
        return xmlArray;
    }

}
