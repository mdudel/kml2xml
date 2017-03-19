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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * <!-- abstract element; do not create -->
 * <!-- Feature id="ID" -->                <!-- Document,Folder,
 * NetworkLink,Placemark,
 * GroundOverlay,PhotoOverlay,ScreenOverlay -->
 * <name>...</name>                      <!-- string -->
 * <visibility>1</visibility>            <!-- boolean -->
 * <open>0</open>                        <!-- boolean -->
 * <atom:author>...<atom:author>         <!-- xmlns:atom -->
 * <atom:link href=" "/>            <!-- xmlns:atom -->
 * <address>...</address>                <!-- string -->
 * <xal:AddressDetails>...</xal:AddressDetails>  <!-- xmlns:xal -->
 * <phoneNumber>...</phoneNumber>        <!-- string -->
 * <Snippet maxLines="2">...</Snippet>   <!-- string -->
 * <description>...</description>        <!-- string -->
 * <AbstractView>...</AbstractView>      <!-- Camera or LookAt -->
 * <TimePrimitive>...</TimePrimitive>    <!-- TimeStamp or TimeSpan -->
 * <styleUrl>...</styleUrl>              <!-- anyURI -->
 * <StyleSelector>...</StyleSelector>
 * <Region>...</Region>
 * <Metadata>...</Metadata>              <!-- deprecated in KML 2.2 -->
 * <ExtendedData>...</ExtendedData>      <!-- new in KML 2.2 -->
 * <-- /Feature -->
 */
public class Feature {

    private String id;
    private String name;
    private int visibility = 1;
    private int open = 0;
    private String atom_author;
    private String atom_link;
    private String address;
    private String xal_AddressDetails;
    private String phoneNumber;
    private String Snippet;
    private int maxLines = 2;
    private String description;
    private String styleUrl = "null";
    private String Metadata;
    private String ExtendedData;
    private Object AbstractView;
    private Object TimePrimitive;
    private Object Region;
    public String styleMapTemplate = "template_StyleMap";
    public String styleUrlTemplate = "template_styleUrl";
    private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
    private final String NO_ID = "NO_ID";
    private String parent = "";

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    public String stripHtml(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        Matcher m = REMOVE_TAGS.matcher(string);
        return m.replaceAll("");
    }

    public String getDescription(boolean stripHtml) {
        if (stripHtml) {
            String desc = this.getDescription();
            //String desc = this.getDescription().replaceAll("(?s)</td>.*?<td>", ":");
            //String desc = this.getDescription().replaceAll("</td>.*?<td>", ":");
            //desc = desc.replaceAll("</tr>", "\n");
            //String desc = this.getDescription();
            desc = desc.replaceAll("</td>[\r\n]+", "</td> ");
            desc = this.stripHtml(desc);
            desc = desc.replaceAll("[\r\n]+", "\n");
            desc = StringEscapeUtils.escapeXml(desc);
//            desc = desc.replaceAll("’", "^");
//            desc = desc.replaceAll("‘", "^");
//            desc = desc.replaceAll("\"", "^");
//            desc = desc.replaceAll("£", "L");
            return desc;
        } else {
            return this.getDescription();
        }
    }

    public String getCsvSafeDescription() {
        String desc = this.getDescription();
        //String desc = this.getDescription().replaceAll("(?s)</td>.*?<td>", ":");
        //String desc = this.getDescription().replaceAll("</td>.*?<td>", ":");
        //desc = desc.replaceAll("</tr>", "\n");
        //String desc = this.getDescription();
        desc = desc.replaceAll("</td>[\r\n]+", "</td> ");
        desc = this.stripHtml(desc);
        desc = desc.replaceAll(",", ";");
        desc = desc.replaceAll("\\s+", " ");
        //desc = StringEscapeUtils.escapeXml(desc);
        desc = desc.replaceAll("\\n", " ");
        desc = desc.replaceAll("\\r", " ");
//            desc = desc.replaceAll("’", "^");
//            desc = desc.replaceAll("‘", "^");
//            desc = desc.replaceAll("\"", "^");
//            desc = desc.replaceAll("£", "L");
        return desc;

    }

    public String getId() {
        if (id != null) {
            return id;
        } else {
            return NO_ID;
        }
    }

    public void setId(String id) {
        if (id.length() < 1 || id == null) {
            id = this.NO_ID;
        } else {
            this.id = id;
        }
    }

    public String getName() {
        if (name != null) {
            String desc = name;
            desc = desc.replaceAll("<", "[").replaceAll(">", "]");
            desc = StringEscapeUtils.escapeXml(desc);
            desc = desc.replaceAll("\\n", " ");
            desc = desc.replaceAll("\\r", " ");
            return desc;
        }
        return "";
    }

    public String getCsvName() {
        if (name != null) {
            String desc = name;
            desc = desc.replaceAll("<", "[").replaceAll(">", "]");
            // desc = StringEscapeUtils.escapeXml(desc);
            desc = desc.replaceAll(",", ";");
            desc = desc.replaceAll("\\n", " ");
            desc = desc.replaceAll("\\r", " ");
            return desc;
        }
        return "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public String getAtom_author() {
        return atom_author;
    }

    public void setAtom_author(String atom_author) {
        this.atom_author = atom_author;
    }

    public String getAtom_link() {
        return atom_link;
    }

    public void setAtom_link(String atom_link) {
        this.atom_link = atom_link;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getXal_AddressDetails() {
        return xal_AddressDetails;
    }

    public void setXal_AddressDetails(String xal_AddressDetails) {
        this.xal_AddressDetails = xal_AddressDetails;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSnippet() {
        return Snippet;
    }

    public void setSnippet(String Snippet) {
        this.Snippet = Snippet;
    }

    public int getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    public String getDescription() {
        if (description == null) {
            return "";
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStyleUrl() {
        String url = this.styleUrl;
        if (url.startsWith("#")) {
            url = url.substring(1, url.length());
        }
        return url;
    }

    public void setStyleUrl(String styleUrl) {
        this.styleUrl = styleUrl;
    }

    public String getMetadata() {
        return Metadata;
    }

    public void setMetadata(String Metadata) {
        this.Metadata = Metadata;
    }

    public String getExtendedData() {
        return ExtendedData;
    }

    public void setExtendedData(String ExtendedData) {
        this.ExtendedData = ExtendedData;
    }

    public Object getAbstractView() {
        return AbstractView;
    }

    public void setAbstractView(Object AbstractView) {
        this.AbstractView = AbstractView;
    }

    public Object getTimePrimitive() {
        return TimePrimitive;
    }

    public void setTimePrimitive(Object TimePrimitive) {
        this.TimePrimitive = TimePrimitive;
    }

    public Object getRegion() {
        return Region;
    }

    public void setRegion(Object Region) {
        this.Region = Region;
    }

}
