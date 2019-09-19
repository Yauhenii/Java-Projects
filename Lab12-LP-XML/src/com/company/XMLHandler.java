package com.company;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
    private int min,max,n;

    public XMLHandler() {
        n=0;
        min=1000;
        max=0;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getQuantity() {
        return n;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("student")) {
            int id=Integer.parseInt(attributes.getValue("id"));
            n++;
            if(min>id){
                min=id;
            }
            if(max<id){
                max=id;
            }
        }
    }
}
