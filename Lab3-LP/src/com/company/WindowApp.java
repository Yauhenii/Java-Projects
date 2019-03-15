package com.company;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

public class WindowApp extends JFrame {

    public static final int SCREEN_WIDTH=800;
    public static final int SCREEN_HEIGHT=500;
    JPanel mainPanel1;
    JPanel mainPanel2;
    JScrollPane scrollPane1;
    JScrollPane scrollPane2;
    JList countriesList;
    JTable table1;
    JButton button1;
    JTabbedPane tabbedPane;
    //data
    public static final String flagsFolderName="plain";
    public static final String capitalsFileName="capitals.txt";
    public static final Object[] table1Header = new String[]{"Flag","Description","Price","Select tour"};
    public HashMap<String, ImageIcon> countriesAndFlagsMap;
    public HashMap<String, String> countriesAndCapitalsMap;
    public HashMap<String, String> countryAndDescriptionMap;
    public HashMap<String, Integer> countryAndPriceMap;

    WindowApp(){
        //data
        readFlags(flagsFolderName);
        readCapitals(capitalsFileName);
        readDescription();
        readPrice();
        //tabbed pane
        tabbedPane=new JTabbedPane();
        initFirstTab();
        initSecondTab();
        //window
        setContentPane(tabbedPane);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }
    public void initFirstTab(){
        //main panel
        mainPanel1 =new JPanel();
        mainPanel1.setLayout(new BorderLayout());
        countriesList=new JList(countriesAndFlagsMap.keySet().toArray());
        countriesList.setCellRenderer(new FlagsListRenderer());
        //scroll pane
        scrollPane1=new JScrollPane(countriesList);
        mainPanel1.add(scrollPane1, BorderLayout.CENTER);
        //
        tabbedPane.add(mainPanel1);
    }
    public void initSecondTab(){
        //main panel
        mainPanel2=new JPanel();
        mainPanel2.setLayout(new BorderLayout());
        //table
        table1=new MyTable();
        table1.setFillsViewportHeight(true);
        table1.setModel(toTableModel(countryAndDescriptionMap,countryAndPriceMap));
        //renderers
        table1.getColumnModel().getColumn(0).setCellRenderer(new FlagsTableCellRenderer());
        table1.getColumnModel().getColumn(1).setCellRenderer(new TextTableCellRenderer());
        table1.getColumnModel().getColumn(2).setCellRenderer(new TextTableCellRenderer());
        //editors
        table1.getColumnModel().getColumn(0).setCellEditor(null);
        table1.getColumnModel().getColumn(1).setCellEditor(new TextCellEditor());
        table1.getColumnModel().getColumn(2).setCellEditor(new TextCellEditor());
        table1.setFocusable(false);
        table1.setRowHeight(70);
        JPanel panel=new JPanel(new BorderLayout());
        //button
        button1=new JButton("Add tour");
        panel.add(button1,BorderLayout.EAST);
        //scroll pane
        scrollPane2=new JScrollPane(table1);
        scrollPane2.setPreferredSize(new Dimension(SCREEN_WIDTH,3*SCREEN_HEIGHT/4));
        mainPanel2.add(scrollPane2,BorderLayout.NORTH);
        mainPanel2.add(panel,BorderLayout.SOUTH);
        //
        tabbedPane.add(mainPanel2);
        //
        DefaultTableModel tableModel= (DefaultTableModel) table1.getModel();
        tableModel.addTableModelListener(new TableModelListener() {
            boolean trigger=false;
            @Override
            public void tableChanged(TableModelEvent e) {
                    int sum = 0;
                    if (!trigger) {
                        for (int i = 0; i < table1.getRowCount(); i++) {
                            if (Boolean.valueOf(table1.getValueAt(i, 3).toString())) {
                                sum += Integer.parseInt(table1.getValueAt(i, 2).toString());
                            }
                        }
                        trigger = true;
                        tableModel.setValueAt(sum, tableModel.getRowCount() - 1, 2);
                        trigger = false;
                    }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataAddingDialog dialog = new DataAddingDialog(WindowApp.this);
                dialog.setVisible(true);
            }
        });
    }
    public TableModel toTableModel(HashMap<?,?> map1,HashMap<?,?> map2){
        DefaultTableModel model = new DefaultTableModel(table1Header,0);
        for(HashMap.Entry<?,?> entry : map1.entrySet()){
            model.addRow(new Object[]{entry.getKey(),entry.getValue(), map2.get((String)entry.getKey()),false});
        }
        model.addRow(new Object[]{null,"TOTAL:","0",false});
        return model;
    }
    public void readFlags(String folderName){
        File folder=new File(folderName);
        countriesAndFlagsMap =new HashMap<>();
        for(File file : folder.listFiles()){
            String countryName=file.getName();
            countryName=parseCountryName(countryName);
            countriesAndFlagsMap.put(countryName, createImageIcon(file.getPath(),countryName));
        }
    }
    protected ImageIcon createImageIcon(String path,
                                        String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    public String parseCountryName(String countryName){
        String countryNameParsed=countryName.substring(5,countryName.length()-4);
        StringBuffer contryNameBuffer=new StringBuffer();
        StringTokenizer tokenizer=new StringTokenizer(countryNameParsed,"_");
        String upperCaseString;
        while(tokenizer.hasMoreElements()){
            upperCaseString=tokenizer.nextElement().toString();
            upperCaseString=upperCaseString.substring(0,1).toUpperCase()+upperCaseString.substring(1);
            contryNameBuffer.append(upperCaseString);
            contryNameBuffer.append(" ");
        }
        return contryNameBuffer.toString();
    }
    public class FlagsTableCellRenderer extends DefaultTableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column){
            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table,
                    "",
                    isSelected,
                    hasFocus,
                    row,
                    column);
            label.setIcon(countriesAndFlagsMap.get((String)value));
            return label;
        }
    }
    public class TextTableCellRenderer extends DefaultTableCellRenderer{

        Font font = new Font("helvitica", Font.BOLD, 24);

        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column){
            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column);
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }
    public class FlagsListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 24);

        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list,
                    value,
                    index,
                    isSelected,
                    cellHasFocus);
            if(isSelected) {
                String capitalName=countriesAndCapitalsMap.get((String) value);
                if(capitalName!=null) {
                    label.setText(value + capitalName);
                }else{
                    label.setText(value+" No data about capital");
                }
            }
            label.setIcon(countriesAndFlagsMap.get((String)value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }
    class TextCellEditor extends AbstractCellEditor implements TableCellEditor {

        Component textField;

        TextCellEditor(){
            textField = new JTextField();
        }
        public Component getTableCellEditorComponent(JTable table,
                                                     Object value,
                                                     boolean isSelected,
                                                     int row,
                                                     int column){
            ((JTextField)textField).setText(value.toString());
            return textField;
        }
        public Object getCellEditorValue() {
            return ((JTextField)textField).getText();
        }
    }
    public void readPrice(){
        countryAndPriceMap=new HashMap<>();
        countryAndPriceMap.put("Belarus ",100);
        countryAndPriceMap.put("Germany ",500);
        countryAndPriceMap.put("Netherlands ",600);
        countryAndPriceMap.put("Russia ",150);
        countryAndPriceMap.put("Ukraine ",200);
        countryAndPriceMap.put("Spain ",600);
        countryAndPriceMap.put("Italy ",500);
        countryAndPriceMap.put("Egypt ",300);
        countryAndPriceMap.put("Yemen ",30);
    }
    public void readDescription(){
        countryAndDescriptionMap=new HashMap<>();
        countryAndDescriptionMap.put("Belarus ","Beautiful capital");
        countryAndDescriptionMap.put("Germany ","Rivers");
        countryAndDescriptionMap.put("Netherlands ","Canals");
        countryAndDescriptionMap.put("Russia ","Forests");
        countryAndDescriptionMap.put("Ukraine ","Fields");
        countryAndDescriptionMap.put("Spain ","Sun");
        countryAndDescriptionMap.put("Italy ","Beach");
        countryAndDescriptionMap.put("Yemen ", "War");
        countryAndDescriptionMap.put("Egypt ", "Sand");
    }
    public void readCapitals(String capitalsFileName){
        countriesAndCapitalsMap=new HashMap<>();
        countriesAndCapitalsMap.put("Belarus ","Minsk");
        countriesAndCapitalsMap.put("Germany ","Berlin");
        countriesAndCapitalsMap.put("Netherlands ","Amsterdam");
        countriesAndCapitalsMap.put("Cuba ","Havana");
    }
}
