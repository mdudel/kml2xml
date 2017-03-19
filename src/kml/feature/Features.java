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
import kml.geometry.Polygon;
import kml.style.StyleMap;
import kml.style.Styles;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author marty
 */
public class Features {

    private Document doc;
    private Map<String, ArrayList<Object>> featureMap = new HashMap<String, ArrayList<Object>>();
    private final String NO_ID = "NO_ID";
    Styles style = new Styles();
    public String styleUrlTemplate = "template_styleUrl";
    public String styleMapTemplate = "template_StyleMap";
    private String title = "";
    private Placemark orphanPlacemark = new Placemark("orphan");

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Features(Document doc) {
        System.out.println("KML Document count: " + this.countDocuments(doc));
        this.setDoc(doc);
    }

    private int countDocuments(Document kmlDoc) {
        kmlDoc.getDocumentElement().normalize();
        NodeList documentList = kmlDoc.getElementsByTagNameNS("*", "Document");
        return documentList.getLength();
    }

    private void addFeature(String id, Object featureObject) {
        // validate id
        if (1 > id.length()) {
            id = NO_ID;
        }
        // Check for existance of key in styleMap
        if (!this.featureMap.containsKey(id)) {
            this.featureMap.put(id, new ArrayList<>());
        }
        // Add the styleObject to the key's array list
        this.featureMap.get(id).add(featureObject);
    }

    public void setDoc(Document doc) {
        this.doc = doc;
        //  check for CPOF style kml
        // Why does  doc.getElementsByTagNameNS("*", "Placemark") not
        // work if there is NO namespace

        boolean excludeStylesInPlacemark = true;
        this.style = new Styles(doc, excludeStylesInPlacemark);
        doc.getDocumentElement().normalize();

        NodeList childNodes = doc.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("Node: " + node.getNodeName() + " " + node.getLocalName());
            }
        }

        //NodeList placemarkList = doc.getElementsByTagNameNS("*","Placemark");
        NodeList placemarkList = doc.getElementsByTagNameNS("*", "Placemark");
        for (int i = 0; i < placemarkList.getLength(); i++) {
            Node node = placemarkList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Placemark placemark = new Placemark(element);
                this.addFeature(placemark.getId(), placemark);
                //System.out.println("+++ Adding Placemark: " + placemark.getId());
            }
        }// for placemarkList   
        NodeList syleMapList = doc.getElementsByTagNameNS("*", "StyleMap");
        for (int i = 0; i < syleMapList.getLength(); i++) {
            Node node = syleMapList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                boolean excludeStyleMapInPlacemark = true;
                StyleMap styleMap = new StyleMap(element, excludeStyleMapInPlacemark);
                this.addFeature(styleMap.getId(), styleMap);
                //System.out.println("+++ Adding StyleMap: " + styleMap.getId());
            }
        }// for syleMapList
        // Check for orphaned geometries (ie bad KML where geometries are
        // placed in a folder an NOT a Placemark)
        // This accounts for malformed KML this is still renderable in Google Earth
        // Point, LineString, Polygon
        int orphanKnt = 0;
        NodeList pointList = doc.getElementsByTagNameNS("*", "Point");
        for (int i = 0; i < pointList.getLength(); i++) {
            Node node = pointList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String parentNode = element.getParentNode().getNodeName();
                if (!(parentNode.contains("Placemark") || parentNode.contains("MultiGeometry"))) {
                    orphanPlacemark.setPlacemarkElement(element);
                    System.out.println("Possible orphan " + element.getTagName() + " of " + parentNode);
                    orphanKnt++;
                }
            }
        }// for orphaned pointList  
        NodeList lineList = doc.getElementsByTagNameNS("*", "LineString");
        for (int i = 0; i < lineList.getLength(); i++) {
            Node node = lineList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String parentNode = element.getParentNode().getNodeName();
                if (!(parentNode.contains("Placemark") || parentNode.contains("MultiGeometry"))) {
                    orphanPlacemark.setPlacemarkElement(element);
                    System.out.println("Possible orphan " + element.getTagName() + " of " + parentNode);
                    orphanKnt++;
                }
            }
        }// for orphaned lineList  
        NodeList polyList = doc.getElementsByTagNameNS("*", "Polygon");
        for (int i = 0; i < polyList.getLength(); i++) {
            Node node = polyList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String parentNode = element.getParentNode().getNodeName();
                if (!(parentNode.contains("Placemark") || parentNode.contains("MultiGeometry"))) {
                    orphanPlacemark.setPlacemarkElement(element);
                    orphanKnt++;
                }
            }
        }// for orphaned polyList  
        if (orphanKnt > 0) {
            System.out.println("parsing malformed KML with " + orphanKnt + " orphaned geometries");
            this.addFeature(orphanPlacemark.getId(), orphanPlacemark);
        }
        // TODO: Parse network links
        // TODO: Check for circular network links (ie network links that refer 
        // to themselves)
    }

    public String listChildIds() {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, ArrayList<Object>> entry : this.featureMap.entrySet()) {
            String id = entry.getKey();
            out.append(id).append("\n");
        }//for all id's   
        return out.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, ArrayList<Object>> entry : this.featureMap.entrySet()) {
            String id = entry.getKey();
            out.append(this.toString(id));
        }//for all id's   
        return out.toString();
    }

    public String toCsv() {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, ArrayList<Object>> entry : this.featureMap.entrySet()) {
            String id = entry.getKey();
            out.append(this.toCsv(id));
        }//for all id's   
        return out.toString();
    }

    public String getCsvHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name").append(",");
        sb.append("Id").append(",");
        sb.append("Description").append(",");
        sb.append("Atom_author").append(",");
        sb.append("Address").append(",");
        sb.append("Atom_author").append(",");
        //sb.append("Atom_link").append(",");
        sb.append("ExtendedData").append(",");
        sb.append("Metadata").append(",");
        sb.append("Parent").append(",");
        sb.append("PhoneNumber").append(",");
        sb.append("Type").append(",");
        sb.append("Visibility").append(",");
        sb.append("KmlCoordinates");
        sb.append("\n");
        return sb.toString();
    }

    public String toCsv(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList features = featureMap.get(id);
        for (Object feature : features) {
            if (feature instanceof Placemark) {
                Placemark thisFeature = (Placemark) feature;
                sb.append(thisFeature.getCsvName()).append(",");
                sb.append(thisFeature.getId()).append(",");
                sb.append(thisFeature.getCsvSafeDescription()).append(",");
                //sb.append(thisFeature.getDescription()).append(",");
                sb.append(thisFeature.getAtom_author()).append(",");
                sb.append(thisFeature.getAddress()).append(",");
                sb.append(thisFeature.getAtom_author()).append(",");
                //sb.append(thisFeature.getAtom_link()).append(",");
                sb.append(thisFeature.getExtendedData()).append(",");
                sb.append(thisFeature.getMetadata()).append(",");
                sb.append(thisFeature.getParent()).append(",");
                sb.append(thisFeature.getPhoneNumber()).append(",");
                sb.append(thisFeature.getType()).append(",");
                sb.append(thisFeature.getVisibility()).append(",");
                sb.append(thisFeature.getKmlCoordinates());
                sb.append("\n");
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
        sb.append(style.toString());
        ArrayList features = featureMap.get(id);
        for (Object feature : features) {
            if (feature instanceof StyleMap) {
                StyleMap thisFeature = (StyleMap) feature;
                sb.append("START ").append(thisFeature.getType()).append(" id:").append(thisFeature.getId()).append("\n");
                sb.append(thisFeature.toString());
                sb.append("END ").append(thisFeature.getType()).append(" id:").append(thisFeature.getId()).append("\n");
            }
            if (feature instanceof Placemark) {
                Placemark thisFeature = (Placemark) feature;
                sb.append("START ").append(thisFeature.getType()).append(" id:").append(thisFeature.getId()).append("\n");
                sb.append(thisFeature.toString());
                sb.append("END ").append(thisFeature.getType()).append(" id:").append(thisFeature.getId()).append("\n");
            }
        }
        return sb.toString();
    }

    public String getMnvrXml() {
        StringBuilder out = new StringBuilder();
        out.append("<Effort>\n");
        out.append("<Type dataType=\"String\">effort</Type>\n");
        out.append("<Name dataType=\"String\">").append(this.title).append("</Name>\n");
        out.append("<Description dataType=\"String\">TODO: FINISH THIS in kml.feature.Features</Description>\n");
        for (Map.Entry<String, ArrayList<Object>> entry : this.featureMap.entrySet()) {
            String id = entry.getKey();
            out.append(this.getMnvrXml(id));
        }//for all id's   
        out.append("</Effort>\n");
        return out.toString();
    }

    public String getMnvrXml(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList features = featureMap.get(id);
        for (Object feature : features) {
            if (feature instanceof Placemark) {
                Placemark thisPlacemarkFeature = (Placemark) feature;
                ArrayList mnvrXmlArray = thisPlacemarkFeature.getMnvrXmlArray();
                for (Object mnvrXmlObject : mnvrXmlArray) {
                    if (mnvrXmlObject instanceof String) {
                        String mnvrXml = (String) mnvrXmlObject;
                        // Check for styles in parent (defined outside of Placemark)
                        // Need to replace styleUrl tempates with the MNVR XML
                        if (!thisPlacemarkFeature.getStyleUrl().contentEquals("null")) {
                            String template = this.styleUrlTemplate + ":" + thisPlacemarkFeature.getStyleUrl();
                            //--- System.out.println(">>>>> StyleUrl template: " + template);//DEBUG
                            String styleUrlMnvrXml = style.getMnvrXml(thisPlacemarkFeature.getStyleUrl());
                            //--- System.out.println(">>>>> " + styleUrlMnvrXml);//DEBUG
                            if (styleUrlMnvrXml.startsWith(this.styleMapTemplate)) {
                                String styleMapNormalUrl = styleUrlMnvrXml.split(":")[1].replaceAll("\\n", "");
                                //--- System.out.println(">>>>> " + styleMapNormalUrl);//DEBUG
                                //styleUrlMnvrXml = styleUrlMnvrXml + "\nNeed to replace with " + styleMapNormalUrl + " object\nBEGIN:\n" + style.getMnvrXml(styleMapNormalUrl) + "END\n";
                                // TODO: fix.this
                                styleUrlMnvrXml = style.getMnvrXml(styleMapNormalUrl);
                            }
                            // If this is a point, remove the line styles, otherwise
                            // remove the icon specific styles
                            if (mnvrXml.contains("<geometry>point</geometry>")) {
                                styleUrlMnvrXml = styleUrlMnvrXml.replaceAll("<!-- LineStyle --><lineColor>.*?</lineColor>", "");
                                styleUrlMnvrXml = styleUrlMnvrXml.replaceAll("<!-- DEFAULT SYMBOLID --><symbolID>.*?</symbolID>", "");
                            } else {
                                styleUrlMnvrXml = styleUrlMnvrXml.replaceAll("<!-- IconStyle --><lineColor>.*?</lineColor>", "");
                                styleUrlMnvrXml = styleUrlMnvrXml.replaceAll("<!-- KML to MNVR MAPPING --><symbolID>.*?</symbolID>", "");
                                //<!-- KML to MNVR MAPPING --><symbolID>GFGPGPPC------X</symbolID>
                            }
                            //System.out.println("replace template with:\n" + styleUrlMnvrXml + "\n"); // DEBUG
                            mnvrXml = mnvrXml.replace(template, styleUrlMnvrXml);

                        }
                        if (mnvrXml.contains("<geometry>point</geometry>")) {
                            mnvrXml = mnvrXml.replaceAll("<!-- DEFAULT SYMBOLID --><symbolID>.*?</symbolID>", "");
                        } else {
                            mnvrXml = mnvrXml.replaceAll("<!-- KML to MNVR MAPPING --><symbolID>.*?</symbolID>", "");
                        }
                        //TODO: replace any remaining templates such as styleMaps
                        //System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\nMNVRXML:\n" + mnvrXml + "\n");
                        sb.append(mnvrXml);
                    }//
                }//each mnvrXmlObject in ArrayList

            }
        }
        return sb.toString();
    }

    public String getOvlXml() {
        StringBuilder out = new StringBuilder();
        out.append("<?xml version=\"1.0\"?>\n");
        out.append("<MODEL>\n");
        for (Map.Entry<String, ArrayList<Object>> entry : this.featureMap.entrySet()) {
            String id = entry.getKey();
            out.append(this.getOvlXml(id));
        }//for all id's   
        out.append("<NAME>").append(this.title).append("</NAME>\n");
        out.append("<DESCRIPTION>TODO: FINISH THIS in kml.feature.Features</DESCRIPTION>\n");
        out.append("<SOURCE></SOURCE>\n");
        out.append("<REMARKS></REMARKS>\n");
        out.append("</MODEL>\n");
        return out.toString();
    }

    public String getOvlXml(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList features = featureMap.get(id);
        for (Object feature : features) {
            if (feature instanceof Placemark) {
                Placemark thisPlacemarkFeature = (Placemark) feature;
                ArrayList ovlXmlArray = thisPlacemarkFeature.getOvlXmlArray();
                for (Object ovlXmlObject : ovlXmlArray) {
                    if (ovlXmlObject instanceof String) {
                        String ovlXml = (String) ovlXmlObject;
                        // Check for styles in parent (defined outside of Placemark)
                        // Need to replace styleUrl tempates with the OVL XML
                        if (!thisPlacemarkFeature.getStyleUrl().contentEquals("null")) {
                            String template = this.styleUrlTemplate + ":" + thisPlacemarkFeature.getStyleUrl();
                            String styleUrlOvlXml = style.getOvlXml(thisPlacemarkFeature.getStyleUrl());
                            if (styleUrlOvlXml.startsWith(this.styleMapTemplate)) {
                                String styleMapNormalUrl = styleUrlOvlXml.split(":")[1].replaceAll("\\n", "");
                                //styleUrlOvlXml = styleUrlOvlXml + "\n<!-- Need to replace with " + styleMapNormalUrl + " object\nBEGIN:\n" + style.getOvlXml(styleMapNormalUrl) + "END -->\n";
                                styleUrlOvlXml = style.getOvlXml(styleMapNormalUrl);
                            }
                            // If this is a point, remove the line styles, otherwise
                            // remove the icon specific styles
                            if (ovlXml.contains("milbobject")) {
                                styleUrlOvlXml = styleUrlOvlXml.replaceAll("<!-- LineStyle --><LINE_COLOR>.*?</LINE_COLOR>", "");
                                styleUrlOvlXml = styleUrlOvlXml.replaceAll("<!-- LineStyle --><LINE_GEOM>.*?</LINE_GEOM>", "");
                                styleUrlOvlXml = styleUrlOvlXml.replaceAll("<!-- LineStyle --><LINE_WIDTH>.*?</LINE_WIDTH>", "");
                            } else {
                                styleUrlOvlXml = styleUrlOvlXml.replaceAll("<!-- IconStyle --><LINE_COLOR>.*?</LINE_COLOR>", "");
                                styleUrlOvlXml = styleUrlOvlXml.replaceAll("<!-- IconStyle --><LINE_GEOM>.*?</LINE_GEOM>", "");
                                styleUrlOvlXml = styleUrlOvlXml.replaceAll("<!-- IconStyle --><MIL_ID>.*?</MIL_ID>", "");
                                //<!-- KML to MNVR MAPPING --><symbolID>GFGPGPPC------X</symbolID>
                            }
                            styleUrlOvlXml = styleUrlOvlXml.replaceAll("\\n\\n", "\n");
                            //System.out.println("Replace " + template + " with\n" + styleUrlOvlXml + "-------------------------\n");
                            ovlXml = ovlXml.replace(template, styleUrlOvlXml);

                        }
                        if (ovlXml.contains("milbobject")) {
                            ovlXml = ovlXml.replaceAll("<!-- DEFAULT SYMBOLID --><symbolID>.*?</symbolID>", "");
                        } else {
                            ovlXml = ovlXml.replaceAll("<!-- IconStyle --><MIL_ID>.*?</MIL_ID>", "");
                        }
                        //TODO: replace any remaining templates such as styleMaps
                        //System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ovlXml:\n" + ovlXml + "\n");
                        //sb.append("<!-- process id ").append(id).append(" -->\n");
                        sb.append(ovlXml);
                    }//
                }//each ovlXmlObject in ArrayList

            }
        }
        return sb.toString();
    }

}
