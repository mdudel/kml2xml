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
package kml2xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import kml.feature.Features;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author marty
 */
public class Kml2Xml {

    static String helpText = "\n\tKml2Xml"
            + "\n\tConverts a KML file to a Command Web XML file for import as an Effort into"
            + "\n\tthe Manuever Widget."
            + "\n"
            + "\n\tOptions"
            + "\n\t\t? or help: Print this message"
            + "\n\t\tIN=path/to/input/filename (required)"
            + "\n\t\tOUT=path/to/output/filename (optional)"
            + "\n\t\tTITLE=TITLE OF EFFORT/OVERLAY (optional, use quotes if the title has spaces)"
            + "\n\t\tFORMAT=the output schema, default is MNVR XML, use OVL to obtain GCCS-J OVL [MNVR,OVL,CSV](optional)";

    public static void main(String[] args) throws IOException, SAXException {
        String inFile = "";
        String outFile = "";
        SimpleDateFormat ft = new SimpleDateFormat("E MMM dd hh:mm:ss zzz yyyy");
        Date date = new Date();
        String owner = "USAREUR";
        String effortName = "KML to XML " + ft.format(date);
        boolean titleSet = false;
        boolean skip = false;
        String format = "MNVR";
        try {
            if (args.length == 0) {
                System.out.println(helpText);
                System.exit(0);
            }
            for (String s : args) {
                if (s.toUpperCase().equals("HELP") || s.equals("?")) {
                    System.out.println(helpText);
                    System.exit(0);
                }
                String[] arg = s.split("=");
                if (arg[0].toUpperCase().startsWith("IN")) {
                    inFile = arg[1];
                    System.out.println("INPUT FILE SET TO: " + inFile);
                }
                if (arg[0].toUpperCase().startsWith("OUT")) {
                    outFile = arg[1];
                    System.out.println("OUTPUT FILE SET TO: " + outFile);
                }
                if (arg[0].toUpperCase().startsWith("T")) {
                    effortName = arg[1];
                    System.out.println("EFFORT TITLE SET TO: " + effortName);
                    titleSet = true;
                }
                if (arg[0].toUpperCase().startsWith("F")) {
                    String value = arg[1];
                    if (value.toUpperCase().startsWith("M")) {
                        format = "MNVR";
                    }
                    if (value.toUpperCase().startsWith("G")) {
                        format = "OVL";
                    }
                    if (value.toUpperCase().startsWith("O")) {
                        format = "OVL";
                    }
                    if (value.toUpperCase().startsWith("C")) {
                        format = "CSV";
                    }
                    System.out.println("OUT FORMAT SET TO: " + format);
                }
                if (arg[0].toUpperCase().startsWith("S")) {
                    skip = true;
                    System.out.println("Skipping output file generation ");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR reading arguments: " + e.getMessage());
        }// try catch reading arguments
        if (!titleSet) {
            effortName = inFile.toUpperCase().replaceAll(".KML", "").replaceAll(".KMZ", "");
            effortName = effortName.replaceAll("_", " ") + " " + ft.format(date);
        }
        if (outFile.length() < 1) {
            String ext = ".xml";
            if (format.toUpperCase().startsWith("O")) {
                ext = ".ovl";
            }
            if (format.toUpperCase().startsWith("C")) {
                ext = ".csv";
            }
            outFile = inFile + ext;
            outFile = outFile.replace(" ", "_");
        }
        //======================================================================
        // Build string representing input file
        StringBuilder sbXml = new StringBuilder();
        String line = null;
        File fin = new File(inFile);
        File fout = new File(outFile);
        if (!fout.exists()) {
            fout.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(fout.getAbsoluteFile()));
        try {
            //    ==============================================================
            long start_ms = System.currentTimeMillis();

            if (ZipUtils.isZip(fin)) {
                System.out.println("Zip file detected.");
                //get the zip file content
                ZipInputStream zis = new ZipInputStream(new FileInputStream(fin));
                //get the zipped file list entry
                ZipEntry ze;
                while ((ze = zis.getNextEntry()) != null) {
                    String fileName = ze.getName();
                    System.out.println("Zipped file: " + fileName);
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    if (extension.equalsIgnoreCase("kml")) { // ================
                        System.out.println("Extension: " + extension);
                        int bufferSize = 1;
                        int knt;
                        StringBuilder kml = new StringBuilder();
                        byte kmlBytes[] = new byte[bufferSize];
                        while ((knt = zis.read(kmlBytes, 0, bufferSize)) != -1) {
                            //Read in all the kml files bytes in data
                            kml.append(new String(kmlBytes));
                            kmlBytes = new byte[bufferSize];
                        }
                        //System.out.println("KML ===>\n" + kml + "\n<=== KML");
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        dbf.setNamespaceAware(true); //necessary if you want to getLocalName
                        DocumentBuilder db;
                        db = dbf.newDocumentBuilder();
                        Document doc = db.parse(new InputSource(new StringReader(kml.toString())));
                        Features features = new Features(doc);
                        features.setTitle(effortName);
//                        System.out.println("Start CSV");
//                        System.out.println(features.getCsvHeader());
//                        System.out.println(features.toCsv());
//                        System.out.println("End CSV");
                        if (format.toUpperCase().startsWith("O")) {
                            bw.write(features.getOvlXml());
                        } else if (format.toUpperCase().startsWith("C")) {
                            bw.write(features.getCsvHeader());
                            bw.write(features.toCsv());
                        } else {
                            bw.write(features.getMnvrXml());
                        }
                        //bw.flush();
                    } // =======================================================
                }
                zis.closeEntry();
                zis.close();
            } else {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true); //necessary if you want to getLocalName
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(fin);
                Features features = new Features(doc);
                features.setTitle(effortName);
//                System.out.println("Start CSV");
//                System.out.println(features.getCsvHeader());
//                System.out.println(features.toCsv());
//                System.out.println("End CSV");
                if (format.toUpperCase().startsWith("O")) {
                    bw.write(features.getOvlXml());
                } else if (format.toUpperCase().startsWith("C")) {
                    bw.write(features.getCsvHeader());
                    bw.write(features.toCsv());
                } else {
                    bw.write(features.getMnvrXml());
                }
                //bw.flush();
            }
            //
            long end_ms = System.currentTimeMillis();
            double processing_s = (end_ms - start_ms) / 1000.0;
            System.out.println("\nProcessing time: " + processing_s + " (s)");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Kml2Xml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Kml2Xml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Kml2Xml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            String error = ex.getMessage();
            if (error.indexOf("is not bound") > -1) {
                String prefix = error.substring(error.indexOf("prefix") + 8, error.indexOf("for attribute") - 2);
                System.out.println("Namespace prefix not defined:" + prefix + " in " + inFile);
            } else {
                Logger.getLogger(Kml2Xml.class.getName()).log(Level.SEVERE, null, ex);
            }

        }// try catch file input
        finally {
            bw.flush();
            bw.close();
            System.out.println("KML parsed to " + outFile);
        }
    }//main

}

// http://stackoverflow.com/questions/12749236/unable-to-create-kmz-file-using-java-util-zip
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//import org.apache.commons.io.IOUtils;
//import java.io.*;
//
//public class TestKmz {
//
//    public static void main(String[] args) throws IOException {     
//        createKMZ();
//        System.out.println("file out.kmz created");
//    }
//
//    public static void createKMZ()  throws IOException  {
//        FileOutputStream fos = new FileOutputStream("out.kmz");
//        ZipOutputStream zoS = new ZipOutputStream(fos);     
//        ZipEntry ze = new ZipEntry("doc.kml");
//        zoS.putNextEntry(ze);
//        PrintStream ps = new PrintStream(zoS);          
//        ps.println("<?xml version='1.0' encoding='UTF-8'?>");
//        ps.println("<kml xmlns='http://www.opengis.net/kml/2.2'>");     
//        // write out contents of KML file ...
//        ps.println("<Placemark>");
//        // add reference to image via inline style
//        ps.println("  <Style><IconStyle>");
//        ps.println("    <Icon><href>image.png</href></Icon>");
//        ps.println("  </IconStyle></Style>");
//        ps.println("</Placemark>");
//        ps.println("</kml>");
//        ps.flush();                 
//        zoS.closeEntry(); // close KML entry
//
//        // now add image file entry to KMZ
//        FileInputStream is = null;
//        try {                   
//            is = new FileInputStream("image.png");
//            ZipEntry zEnt = new ZipEntry("image.png");
//            zoS.putNextEntry(zEnt);
//            // copy image input to KMZ output
//            // write contents to entry within compressed KMZ file
//            IOUtils.copy(is, zoS);
//        } finally {
//            IOUtils.closeQuietly(is);
//        }
//        zoS.closeEntry();
//        zoS.close();
//    }   
//}   
