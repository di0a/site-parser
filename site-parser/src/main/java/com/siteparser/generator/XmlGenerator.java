package com.siteparser.generator;

import com.siteparser.domain.Item;
import org.jdom2.Document;
import org.jdom2.Element;

import org.jdom2.output.XMLOutputter;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.jdom2.output.Format.*;

/**
 * Created by sundial on 17.04.2017.
 */
public class XmlGenerator {


    private void saveToFile(XMLOutputter output, Document doc) {
        output.setFormat(getPrettyFormat());

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        Date date = new Date();

        String fileName = "items-" + dateFormat.format(date);

        try {

            File file = new File(fileName + ".xml");
            file.createNewFile();

            FileOutputStream outFile = new FileOutputStream(file);

            output.output(doc, outFile);

            outFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void generateXml(List<Item> parsedItemRepo) {

        System.out.println("Generating xml report...");

        Element root = new Element("offers");
        Document doc = new Document();

        for (Item item : parsedItemRepo) {

            Element offerNode = new Element("offer");

            Element id = new Element("id");
            id.addContent(item.getId());

            Element name = new Element("name");
            name.addContent(item.getName());

            Element brand = new Element("brand");
            brand.addContent(item.getBrand());

            Element color = new Element("color");
            color.addContent(item.getColor());

            Element price = new Element("price");
            price.addContent(item.getPrice());

            Element actual = new Element("initial-price");
            actual.addContent(item.getActualPrice());

            Element description = new Element("description");
            description.addContent(item.getDescription());

            offerNode.addContent(id);
            offerNode.addContent(name);
            offerNode.addContent(brand);
            offerNode.addContent(color);
            offerNode.addContent(price);
            offerNode.addContent(actual);
            offerNode.addContent(description);

            root.addContent(offerNode);
        }
        doc.setRootElement(root);
        XMLOutputter output = new XMLOutputter();
        saveToFile(output, doc);
        System.out.println("Xml report generated successfully!");

    }
}
