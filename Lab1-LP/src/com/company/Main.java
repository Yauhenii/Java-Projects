package com.company;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void readXMLFile(String fileName, ArrayList<Cinema> arrayList){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File(fileName);
            Document document = builder.parse(file);

            NodeList cinemasNodeList = document
                    .getDocumentElement()
                    .getElementsByTagName("cinema");

            if (cinemasNodeList.getLength() != 0) {

                for (int i = 0; i < cinemasNodeList.getLength(); i++) {

                    Node cinemaNode = cinemasNodeList.item(i);

                    if (cinemaNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element cinemaElement = (Element) cinemaNode;

                        String cinemaName = cinemaElement.getAttribute("name");
                        int cinemaSeats = Integer.parseInt(cinemaElement.getAttribute("seats"));

                        NodeList filmsNodeList = cinemaElement.getElementsByTagName("film");

                        ArrayList<String> filmsList=new ArrayList<>();

                        if (filmsNodeList.getLength() != 0) {


                            for (int j = 0; j < filmsNodeList.getLength(); j++) {

                                Node filmNode = filmsNodeList.item(j);

                                if (cinemaNode.getNodeType() == Node.ELEMENT_NODE) {

                                    Element filmElement = (Element) filmNode;

                                    String filmName = filmElement.getAttribute("name");

                                    filmsList.add(filmName);

                                }

                            }
                        }

                        arrayList.add(new Cinema(cinemaName, cinemaSeats,filmsList));
                    }
                }
            }
        }
        catch (ParserConfigurationException exception) {

            System.out.println(exception.getMessage());

        } catch (SAXException exception) {

            System.out.println(exception.getMessage());

        } catch (IOException exception) {

            System.out.println(exception.getMessage());
        }
    }

    public static void main(String[] args) {
        ArrayList<Cinema> cinemasList=new ArrayList<>();
        String fileName="Data";
        readXMLFile(fileName,cinemasList);

        System.out.println("Raw list:");
        printContent(cinemasList);

        cinemasList.sort(Comparator.comparing(Cinema::getName));
        System.out.println("Sorted list:");
        printContent(cinemasList);

        HashSet<String> filmsSet=new HashSet<>();
        for(Cinema cinema: cinemasList){
            for(String film : cinema.getCurrentFilmsList()){
                filmsSet.add(film);
            }
        }
        System.out.println("    Films:");
        printContent(filmsSet);

        System.out.println("    Cinema with max number of seats:");
        System.out.println(Collections.max(cinemasList,Comparator.comparing(Cinema::getSeats)).getName());

        System.out.println("    Binary search:");
        cinemasList.sort(Comparator.comparing(Cinema::getSeats));
        int index=Collections.binarySearch(cinemasList, new Cinema("",110),Comparator.comparing(Cinema::getSeats));
        if(index<0){
            System.out.println("No such cinema");
        }else{
            System.out.println(cinemasList.get(index).getName());
        }

    }

    public static void printContent(ArrayList<Cinema> arrayList){
        for(Cinema cinema : arrayList){
            System.out.println("    "+cinema.getName());
            System.out.println(cinema.getSeats());
            for(String film : cinema.getCurrentFilmsList()){
                System.out.print(film+" ");
            }
            System.out.println();
        }
    }

    public static void printContent(HashSet<String> hashSet){
        for(String string: hashSet){

            System.out.println(string);

        }
    }

}
