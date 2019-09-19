package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class WindowApp extends JFrame {
    private final static int WINDOW_X = 300;
    private final static int WINDOW_Y = 700;
    public static final Object[] tableHeader = new String[]{"ID","Surname","Course","Group","City"};
    public static final String SCHEMA_FILE_NAME= "schema.xsd";

    JMenu menu;
    JMenu menuStudents;
    JMenuBar menuBar;
    JMenuItem menuItemAddStudent;
    JMenuItem menuItemSaveAsXML;
    JMenuItem menuItemOpenXMLDOM;
    JMenuItem menuItemOpenXMLSAX;
    JMenuItem menuItemSaveAsBinary;
    JMenuItem menuItemOpenBinary;
    JMenuItem menuItemCheck;

    JScrollPane scrollPane;

    JPanel startPanel;
    JButton startButton;

    JPanel mainPanel;
    JPanel northPanel;
    JPanel southPanel;
    JButton addDataButton;
    JButton deleteDataButton;

    JTextArea minArea;
    JTextArea maxArea;

    private JTable table;

    public ArrayList<Student> getModelStudents() {
        return studentArrayList;
    }

    ArrayList<Student> studentArrayList;

    WindowApp() {
        //start panel
        startPanel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage image = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("texture.jpg"));
                    g.drawImage(image,0,0,null);
                } catch (Exception e){
                    System.out.println(e.getStackTrace());
                }
            }
        };
        startPanel.setLayout(null);
        //start buttons
        startButton=new JButton("Start!");
        startButton.setBounds(WINDOW_X/2-WINDOW_X/4,WINDOW_Y/8-WINDOW_Y/16,WINDOW_X /2,WINDOW_X /16);
        startPanel.add(startButton);
        //panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //buttons
        addDataButton = new JButton("Add new students...");
        deleteDataButton = new JButton("Delete selected student");
        //
        northPanel = new JPanel(new GridLayout(1,2));
        northPanel.add(addDataButton);
        northPanel.add(deleteDataButton);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        //areas
        minArea=new JTextArea();
        maxArea=new JTextArea();
        //
        southPanel= new JPanel(new GridLayout(2,2));
        mainPanel.add(southPanel,BorderLayout.SOUTH);
        southPanel.add(new JLabel("Min ID"));
        southPanel.add(new JLabel("Max ID"));
        southPanel.add(minArea);
        southPanel.add(maxArea);
        //model
        studentArrayList = new ArrayList<>();
        //table
        table = new JTable();
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        //add header
        DefaultTableModel model = new DefaultTableModel(tableHeader,0);
        table.setModel(model);
        //menu bar
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuStudents = new JMenu(("Students"));
        menuItemAddStudent = new JMenuItem("Add student...");
        menuItemSaveAsXML = new JMenuItem("Save as XML file...");
        menuItemOpenXMLDOM = new JMenuItem("Open XML file...");
        menuItemOpenXMLSAX = new JMenuItem("Min and max ID...");
        menuItemSaveAsBinary = new JMenuItem("Save as binary file...");
        menuItemOpenBinary = new JMenuItem("Open binary file...");
        menuItemCheck = new JMenuItem("Check XML correctness");
        menuStudents.add(menuItemAddStudent);
        menu.add(menuItemOpenXMLDOM);
        menu.add(menuItemOpenXMLSAX);
        menu.add(menuItemOpenBinary);
        menu.addSeparator();
        menu.add(menuItemSaveAsXML);
        menu.add(menuItemSaveAsBinary);
        menu.addSeparator();
        menu.add(menuItemCheck);
        menuBar.add(menu);
        menuBar.add(menuStudents);

        //window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(startPanel);
        setPreferredSize(new Dimension(WINDOW_X,WINDOW_Y/4));
        pack();
        //listeners
        addDataButton.addActionListener(new AddDataListener());
        menuItemAddStudent.addActionListener(new AddDataListener());
        menuItemSaveAsXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        writeToXMLFile(file);
                    }
                    catch (IOException exception){

                    }
                }
            }
        });
        menuItemSaveAsBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(studentArrayList);
                        oos.flush();
                        oos.close();
                        fos.close();
                    }
                    catch (IOException exception){

                    }
                }
            }
        });
        menuItemOpenXMLDOM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //parse xml file (DOM)
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file=fileOpen.getSelectedFile();
                        if(!isXMLValid(file)){
                            JOptionPane.showMessageDialog(WindowApp.this,
                                    "Illegal XML format",
                                    "Parser error.",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        studentArrayList=new ArrayList<>();
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.parse(file);

                        NodeList studentElements = document
                                .getDocumentElement()
                                .getElementsByTagName("student");

                        for (int i = 0; i < studentElements.getLength(); i++) {
                            Node student = studentElements.item(i);
                            if (student.getNodeType() == Node.ELEMENT_NODE) {
                                Element element = (Element) student;
                                int recBookNum = Integer.parseInt(element.getAttribute("id"));
                                String surname =
                                        element
                                                .getElementsByTagName("surname")
                                                .item(0)
                                                .getTextContent();
                                int course = Integer.parseInt(
                                        element
                                                .getElementsByTagName("course")
                                                .item(0)
                                                .getTextContent());
                                int group = Integer.parseInt(
                                        element
                                                .getElementsByTagName("group")
                                                .item(0)
                                                .getTextContent());
                                String city= element.getAttribute("city");
                                studentArrayList.add(new Student(recBookNum,surname,course,group,city));

                            }
                        }
                        updateList();
                    }
                    catch (ParserConfigurationException exception){
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "Parser error.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    catch (SAXException exception){
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "SAX error.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    catch (IOException exception){
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "Cannot open the file.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        menuItemOpenXMLSAX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //parse xml file (SAX)
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file=fileOpen.getSelectedFile();
                        if(!isXMLValid(file)){
                            JOptionPane.showMessageDialog(WindowApp.this,
                                    "Illegal XML format",
                                    "Parser error.",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                        SAXParser parser = factory.newSAXParser();

                        XMLHandler handler=new XMLHandler();
                        parser.parse(file,handler);

                        if(handler.getQuantity()!=0){
                            minArea.setText(Integer.toString(handler.getMin()));
                            maxArea.setText(Integer.toString(handler.getMax()));
                        } else{
                            minArea.setText("No elements");
                            maxArea.setText("No elements");
                        }

                    }
                    catch (ParserConfigurationException exception){
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "Parser error.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    catch (SAXException exception){
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "SAX error.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    catch (IOException exception){
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "SAX error.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        menuItemOpenBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //parse binary
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        ObjectInputStream oin = new ObjectInputStream(fis);
                        studentArrayList = (ArrayList<Student>) oin.readObject();
                        updateList();
                    }
                    catch (ClassNotFoundException exception){
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "Cannot open the file. Class not found.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    catch (IOException exception){
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "Cannot open the file.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        menuItemCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //parse binary
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    if (isXMLValid(file)) {
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "XML file is correct",
                                "Parser.",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else{
                        JOptionPane.showMessageDialog(WindowApp.this,
                                "Illegal XML format",
                                "Parser error.",
                                JOptionPane.WARNING_MESSAGE);

                    }
                }
            }
        });
        deleteDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int[] rows = table.getSelectedRows();
                for(int i=0;i<rows.length;i++){
                    model.removeRow(rows[i]-i);
                    studentArrayList.remove(rows[i]-i);
                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                removeAll();
                setContentPane(mainPanel);
                setJMenuBar(menuBar);
                setPreferredSize(new Dimension(2*WINDOW_X,WINDOW_Y));
                revalidate();
                repaint();
                pack();
            }
        });
    }

    public boolean isXMLValid(File XMLFile) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(Thread.currentThread().getContextClassLoader().getResource(SCHEMA_FILE_NAME));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(XMLFile));
            return true;
        } catch (SAXException | IOException e) {
            return false;
        }
    }


    public void updateList() {
        DefaultTableModel model = new DefaultTableModel(tableHeader,0);
        Iterator<Student> iterator = studentArrayList.iterator();
        Student student;
        while (iterator.hasNext()) {
            student=iterator.next();
            model.addRow(new Object[]{student.getRecBookNum(),student.getSurname(),student.getCourse(),student.getGroup(),student.getCity()});
        }
        table.setModel(model);
    }


    public void writeToXMLFile(File file) throws IOException {
        file.createNewFile();
        FileWriter writer=new FileWriter(file);
        writer.write(this.toXMLString());
        writer.flush();
        writer.close();
    }

    public String toXMLString(){
        StringBuffer resultXML=new StringBuffer();
        resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        resultXML.append("<students>\n");
        Iterator<Student> iterator = studentArrayList.iterator();
        while (iterator.hasNext()){
            resultXML.append(iterator.next().toXMLString());
        }
        resultXML.append("</students>\n");
        return resultXML.toString();
    }
    class AddDataListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            AddDataDialog dialog = new AddDataDialog(WindowApp.this);
            dialog.setVisible(true);
        }
    }
}



//                FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                        "TXT files", "txt");
//                fileOpen.setFileFilter(filter);