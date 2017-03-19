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

/*
 <ListStyle id="ID">
 <!-- specific to ListStyle -->
 <listItemType>check</listItemType> <!-- kml:listItemTypeEnum:check,
 checkOffOnly,checkHideChildren,
 radioFolder -->
 <bgColor>ffffffff</bgColor>        <!-- kml:color -->
 <ItemIcon>                         <!-- 0 or more ItemIcon elements -->
 <state>open</state>
 <!-- kml:itemIconModeEnum:open, closed, error, fetching0, fetching1, or fetching2 -->
 <href>...</href>                 <!-- anyURI -->
 </ItemIcon>
 </ListStyle>
 */
public class ListStyle extends ColorStyle {

    private String listItemType = "check";
    private String bgColor = "ffffffff";
    private final String type = "ListStyle";
    private Element xmlElement;

    public ListStyle(Element xmlElement) {
        this.xmlElement = xmlElement;
    }

    public ListStyle() {
    }

    public String getType() {
        return type;
    }
    //TODO Finish... not sure this is useful in decomposing KML

}
