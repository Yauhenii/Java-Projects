package com.company;

//chart
import com.google.gson.stream.JsonReader;
import org.knowm.xchart.*;
import org.knowm.xchart.style.*;
import org.knowm.xchart.internal.*;
//gson
import com.google.gson.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static final String FILE_NAME="file.json";
    public static void main(String[] args) {
        //pie chart
        PieChart chart = new PieChartBuilder()
                .width(800)
                .height(600)
                .title("City(Stations)")
                .build();
        // Customize Chart
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndValue);
        chart.getStyler().setAnnotationDistance(1.15);
        chart.getStyler().setPlotContentSize(.7);
        chart.getStyler().setStartAngleInDegrees(90);
        //read data
        City[] cities;
        cities=readData();
        // fill chart with data
        if (cities != null) {
            fillData(chart,cities);
        }
        // Show it
        new SwingWrapper(chart).displayChart();
        //tabbed pane
    }
    static void fillData(PieChart chart,City[] cities){
        for(City city : cities){
            // Series
            if(city.getQuantity()>0 && !isInteger(city.getCity())) {
                chart.addSeries(city.getCity(), city.getQuantity());
            }
        }
    }
    static City[] readData(){
        Gson gson=new Gson();
        City[]cities=null;
        try{
            JsonReader reader = new JsonReader(new FileReader(FILE_NAME));
            cities=gson.fromJson(reader,City[].class);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found.");
        } catch (JsonSyntaxException e){
            JOptionPane.showMessageDialog(null, "Illegal input data.");
        }
        return cities;
    }
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        if (str.isEmpty()) {
            return false;
        }
        int i = 0;
        int length=str.length();
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
