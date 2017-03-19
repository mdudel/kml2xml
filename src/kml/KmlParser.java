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
package kml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author marty
 */
public class KmlParser {

    private String kmlFileName; //Name and path to KML file
    private File kmlFile;
    private ArrayList placemarks;
    //private HashMap<String, Style> styleMap;
    private Document kmlDocument;
    String parseStatus = "NOT INITIALIZED";

    public KmlParser(String kmlFileName) {
        try {
            this.kmlFileName = kmlFileName;
            this.kmlFile = new File(kmlFileName);
            this.placemarks = new ArrayList();
            // this.styleMap = new HashMap<>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.kmlDocument = db.parse(this.kmlFile);
            kmlDocument.getDocumentElement().normalize();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(KmlParser.class.getName()).log(Level.SEVERE, null, ex);
            parseStatus = "ERROR: " + ex.getMessage();
        } catch (SAXException ex) {
            Logger.getLogger(KmlParser.class.getName()).log(Level.SEVERE, null, ex);
            parseStatus = "ERROR: " + ex.getMessage();
        } catch (IOException ex) {
            Logger.getLogger(KmlParser.class.getName()).log(Level.SEVERE, null, ex);
            parseStatus = "ERROR: " + ex.getMessage();
        }
    }//KmlParser

    public String getParseStatus() {
        return parseStatus;
    }

}
