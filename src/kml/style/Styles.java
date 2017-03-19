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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author marty
 */
public class Styles {

    private Document doc;
    private Map<String, ArrayList<Object>> styleHashMap = new HashMap<String, ArrayList<Object>>();
    private final String NO_ID = "NO_ID";
    private boolean excludeStylesInPlacemark = false;

    public Styles() {
    }

    private void addStyle(String id, Object styleObject) {
        // validate id
        if (1 > id.length()) {
            id = NO_ID;
        }
        //System.out.println("Adding style id:" + id);
        // Check for existance of key in styleHashMap
        if (!styleHashMap.containsKey(id)) {
            styleHashMap.put(id, new ArrayList<>());
            //System.out.println("ooo New style id " + id);
        }
        // Add the styleObject to the key's array list
        // Need to check for duplicates
        if (!styleHashMap.get(id).contains(styleObject)) {
            styleHashMap.get(id).add(styleObject);
            //System.out.println("+++ Adding style object to id " + id + "\n    ==============================");
        } else {
            System.out.println("!!! Duplicate style object... id " + id);
        }
//        // Creating a set form the list will remnove duplicates
//        Set set = new HashSet(styleHashMap.get(id));
//        // Converting the set back to the list...
//        styleHashMap.put(id, new ArrayList(set));

    }

    public Styles(Document doc) {
        this.setDoc(doc);
    }

    public Styles(Document doc, boolean excludeStylesInPlacemark) {
        // This will only ignore placemarks when submitting this doc if set to true
        this.excludeStylesInPlacemark = excludeStylesInPlacemark;
        this.setDoc(doc);
        this.excludeStylesInPlacemark = false;
    }

    public Styles(Element element) {
        this.setStyleElement(element);
    }

    public Styles(Element element, boolean excludeStylesInPlacemark) {
        // This will only ignore placemarks when submitting this element if set to true
        this.excludeStylesInPlacemark = excludeStylesInPlacemark;
        this.setStyleElement(element);
        this.excludeStylesInPlacemark = false;
    }

    public void setStyleElement(Element element, boolean excludeStylesInPlacemark) {
        // This will only ignore placemarks when submitting this element if set to true
        this.excludeStylesInPlacemark = excludeStylesInPlacemark;
        this.setStyleElement(element);
        this.excludeStylesInPlacemark = false;
    }

    public void setStyleElement(Element element) {
        //System.out.println(">>> Setting style for " + element.getParentNode().getNodeName());
        if (this.excludeStylesInPlacemark) {
            if (element.getParentNode().getNodeName().equalsIgnoreCase("Placemark")) {
                return;
            }
        }
        if (element.getAttribute("id").length() > 0) {
            String id = element.getAttribute("id");
        }// id   
        // iconStyleString element [x] supporting Class written ====
        if (element.getElementsByTagNameNS("*","IconStyle").getLength() > 0) {
            IconStyle iconStyle = new IconStyle(element);
            this.addStyle(iconStyle.getId(), iconStyle);
            //System.out.println("Adding IconStyle:\n" + iconStyle.toString());
        }// iconStyleString    
        // LabelStyle element [x] supporting Class written =========
        if (element.getElementsByTagNameNS("*","LabelStyle").getLength() > 0) {
            LabelStyle labelStyle = new LabelStyle(element);
            this.addStyle(labelStyle.getId(), labelStyle);
            //System.out.println("Adding LabelStyle:\n" + labelStyle.toString());
        }// LabelStyle 
        // LineStyle element [x] supporting Class written ==========
        if (element.getElementsByTagNameNS("*","LineStyle").getLength() > 0) {
            LineStyle lineStyle = new LineStyle(element);
            this.addStyle(lineStyle.getId(), lineStyle);
            //System.out.println("Adding LineStyle:\n" + lineStyle.toString());
        }// LineStyle 
        // PolyStyle element [x] supporting Class written
        if (element.getElementsByTagNameNS("*","PolyStyle").getLength() > 0) {
            PolyStyle polyStyle = new PolyStyle(element);
            this.addStyle(polyStyle.getId(), polyStyle);
            //System.out.println("Adding PolyStyle:\n" + polyStyle.toString());
        }// PolyStyle 
        // BalloonStyle element  [x] supporting Class written
        if (element.getElementsByTagNameNS("*","BalloonStyle").getLength() > 0) {
            BalloonStyle balloonStyle = new BalloonStyle(element);
            this.addStyle(balloonStyle.getId(), balloonStyle);
        }// BalloonStyle 
        // ListStyle element  [ ] supporting Class written TODO
        if (element.getElementsByTagNameNS("*","ListStyle").getLength() > 0) {
            ListStyle listStyle = new ListStyle(element);
            this.addStyle(listStyle.getId(), listStyle);
        }// ListStyle 
        //System.out.println("=== ==============================");
    }

    public void setDoc(Document doc) {
        this.doc = doc;
        doc.getDocumentElement().normalize();
        NodeList styleList = doc.getElementsByTagNameNS("*","Style");
        for (int i = 0; i < styleList.getLength(); i++) {
            Node node = styleList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                this.setStyleElement(element);
            }
        }// for styleList   
        styleList = doc.getElementsByTagNameNS("*","StyleMap");
        for (int i = 0; i < styleList.getLength(); i++) {
            Node node = styleList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                StyleMap styleMap = new StyleMap(element, true);
                this.addStyle(styleMap.getId(), styleMap);
            }
        }// for styleList           
    }

    public String toString(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList styles = styleHashMap.get(id);
        for (Object style : styles) {
            if (style instanceof BalloonStyle) {
                BalloonStyle thisStyle = (BalloonStyle) style;
                sb.append("START ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
                sb.append(thisStyle.toString());
                sb.append("END ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
            }
            if (style instanceof IconStyle) {
                IconStyle thisStyle = (IconStyle) style;
                sb.append("START ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
                sb.append(thisStyle.toString());
                sb.append("END ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
            }
            if (style instanceof LabelStyle) {
                LabelStyle thisStyle = (LabelStyle) style;
                sb.append("START ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
                sb.append(thisStyle.toString());
                sb.append("END ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
            }
            if (style instanceof LineStyle) {
                LineStyle thisStyle = (LineStyle) style;
                sb.append("START ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
                sb.append(thisStyle.toString());
                sb.append("END ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
            }
            if (style instanceof ListStyle) {
                ListStyle thisStyle = (ListStyle) style;
                sb.append("START ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
                sb.append(thisStyle.toString());
                sb.append("END ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
            }
            if (style instanceof PolyStyle) {
                PolyStyle thisStyle = (PolyStyle) style;
                sb.append("START ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
                sb.append(thisStyle.toString());
                sb.append("END ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
            }
            if (style instanceof StyleMap) {
                StyleMap thisStyle = (StyleMap) style;
                sb.append("START ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
                sb.append(thisStyle.toString());
                sb.append("END ").append(thisStyle.getType()).append(" id:").append(thisStyle.getId()).append("\n");
            }

        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ArrayList<Object>> entry : styleHashMap.entrySet()) {
            String id = entry.getKey();
            sb.append(this.toString(id));
        }//for all id's
        return sb.toString();
    }

    public String getMnvrXml(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList styles = styleHashMap.get(id);
        if (styles == null) {
            return "";
        }
        for (Object style : styles) {
// No ListStyle in MNVR XML            
//            if (style instanceof BalloonStyle) {
//                BalloonStyle thisStyle = (BalloonStyle) style;
//                sb.append(thisStyle.getMnvrXml());
//            }
            if (style instanceof IconStyle) {
                IconStyle thisStyle = (IconStyle) style;
                sb.append(thisStyle.getMnvrXml());
            }
            if (style instanceof LabelStyle) {
                LabelStyle thisStyle = (LabelStyle) style;
                sb.append(thisStyle.getMnvrXml());
            }
            if (style instanceof LineStyle) {
                LineStyle thisStyle = (LineStyle) style;
                sb.append(thisStyle.getMnvrXml());
            }
// No ListStyle in MNVR XML
//            if (style instanceof ListStyle) {
//                ListStyle thisStyle = (ListStyle) style;
//                sb.append(thisStyle.getMnvrXml());
//            }
            if (style instanceof PolyStyle) {
                PolyStyle thisStyle = (PolyStyle) style;
                sb.append(thisStyle.getMnvrXml());
            }
            if (style instanceof StyleMap) {
                StyleMap thisStyle = (StyleMap) style;
                String xml = thisStyle.getMnvrXml();
                if (!sb.toString().contains(xml)) {
                    sb.append(thisStyle.getMnvrXml());
                }
            }

        }// for all style by id
        return sb.toString();
    }

    public String getMnvrXml() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ArrayList<Object>> entry : styleHashMap.entrySet()) {
            String id = entry.getKey();
            sb.append(this.getMnvrXml(id));
        }//for all id's
        return sb.toString();
    }

    public String getOvlXml(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList styles = styleHashMap.get(id);
        if (styles == null) {
            return "";
        }
        for (Object style : styles) {
// No BalloonStyle in OVL XML            
//            if (style instanceof BalloonStyle) {
//                BalloonStyle thisStyle = (BalloonStyle) style;
//                sb.append(thisStyle.getOvlXml());
//            }
            if (style instanceof IconStyle) {
                IconStyle thisStyle = (IconStyle) style;
                sb.append(thisStyle.getOvlXml());
            }
            if (style instanceof LabelStyle) {
                LabelStyle thisStyle = (LabelStyle) style;
                sb.append(thisStyle.getOvlXml());
            }
            if (style instanceof LineStyle) {
                LineStyle thisStyle = (LineStyle) style;
                sb.append(thisStyle.getOvlXml());
            }
// No ListStyle in OVL XML
//            if (style instanceof ListStyle) {
//                ListStyle thisStyle = (ListStyle) style;
//                sb.append(thisStyle.getOvlXml());
//            }
            if (style instanceof PolyStyle) {
                PolyStyle thisStyle = (PolyStyle) style;
                sb.append(thisStyle.getOvlXml());
            }
            if (style instanceof StyleMap) {
                StyleMap thisStyle = (StyleMap) style;
                sb.append(thisStyle.getOvlXml());
            }

        }// for all style by id
        // Remove duplicates
        StringBuilder result = new StringBuilder();
        Set<String> set = new HashSet<String>();
        set.addAll(Arrays.asList(sb.toString().split("\n")));
        for (String s : set) {
            result.append(s).append("\n");
        }
        return result.toString();
    }

    public String getOvlXml() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ArrayList<Object>> entry : styleHashMap.entrySet()) {
            String id = entry.getKey();
            sb.append(this.getOvlXml(id));
        }//for all id's
        return sb.toString();
    }

    public String toType(String id) {
        // validate id
        if (id.length() < 1) {
            id = NO_ID;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList styles = styleHashMap.get(id);
        for (Object style : styles) {
            if (style instanceof BalloonStyle) {
                BalloonStyle thisStyle = (BalloonStyle) style;
                sb.append(thisStyle.getType());
            }
            if (style instanceof IconStyle) {
                IconStyle thisStyle = (IconStyle) style;
                sb.append(thisStyle.getType());
            }
            if (style instanceof LabelStyle) {
                LabelStyle thisStyle = (LabelStyle) style;
                sb.append(thisStyle.getType());
            }
            if (style instanceof LineStyle) {
                LineStyle thisStyle = (LineStyle) style;
                sb.append(thisStyle.getType());
            }
            if (style instanceof ListStyle) {
                ListStyle thisStyle = (ListStyle) style;
                sb.append(thisStyle.getType());
            }
            if (style instanceof PolyStyle) {
                PolyStyle thisStyle = (PolyStyle) style;
                sb.append(thisStyle.getType());
            }
            if (style instanceof StyleMap) {
                StyleMap thisStyle = (StyleMap) style;
                sb.append(thisStyle.getType());
            }
        }
        return sb.toString();
    }

}
