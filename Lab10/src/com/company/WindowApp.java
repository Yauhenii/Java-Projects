package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.List;

public class WindowApp extends JFrame {
    private final static int LIST_X = 300;
    private final static int LIST_Y = 500;

    JMenu menu;
    JMenu menuStudents;
    JMenuBar menuBar;
    JMenuItem menuItem1;
    JMenuItem menuItem2;
    JMenuItem menuItem3;
    JMenuItem menuItem4;
    JMenuItem menuItem5;
    JScrollPane scrollPaneStudents;
    JScrollPane scrollPaneTask;
    JPanel mainPanel;
    JPanel northPanel;
    JButton addDataButton;
    JButton chooseFileButton;
    JButton sortButton;
    JList<Student> listStudents;
    JList<Student> listTask;

    public ArrayList<Student> getModelStudents() {
        return studentArrayList;
    }

    DefaultListModel<Student> modelStudents;
    ArrayList<Student> studentArrayList;
    ArrayList<Student> studentTempArrayList;
    ArrayList<Student> studentSortedArrayList;

    WindowApp() {
        //panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //buttons
        addDataButton = new JButton("Add new students...");
        chooseFileButton = new JButton("Choose text file...");
        sortButton = new JButton("Sort");
        addDataButton.setPreferredSize(new Dimension(LIST_X, 20));
        sortButton.setPreferredSize(new Dimension(LIST_X, 20));
        //
        northPanel = new JPanel();
        northPanel.add(addDataButton, BorderLayout.WEST);
        //mainPanel.add(chooseFileButton, BorderLayout.SOUTH);
        northPanel.add(sortButton, BorderLayout.EAST);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        //model
        studentArrayList = new ArrayList<>();
        //lists
        listStudents = new JList<>();
        scrollPaneStudents = new JScrollPane(listStudents);
        scrollPaneStudents.setPreferredSize(new Dimension(LIST_X, LIST_Y));
        mainPanel.add(scrollPaneStudents, BorderLayout.WEST);
        listTask = new JList<>();
        scrollPaneTask = new JScrollPane(listTask);
        scrollPaneTask.setPreferredSize(new Dimension(LIST_X, LIST_Y));
        mainPanel.add(scrollPaneTask, BorderLayout.EAST);
        //menu bar
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuStudents = new JMenu(("Students"));
        menuItem1 = new JMenuItem("Add student...");
        menuItem2 = new JMenuItem("Open text file...");
        menuItem3 = new JMenuItem("Sort");
        menuItem4 = new JMenuItem("Save as XML file...");
        menuItem5 = new JMenuItem("Open XML file...");
        menuStudents.add(menuItem3);
        menuStudents.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem5);
        menu.addSeparator();
        menu.add(menuItem4);
        menuBar.add(menu);
        menuBar.add(menuStudents);
        setJMenuBar(menuBar);

        //window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(mainPanel);
        pack();
        //listeners
        sortButton.addActionListener(new SortListener());
        addDataButton.addActionListener(new AddDataListener());
        chooseFileButton.addActionListener(new ChooseFileListener());
        menuItem1.addActionListener(new AddDataListener());
        menuItem2.addActionListener(new ChooseFileListener());
        menuItem3.addActionListener(new SortListener());
        menuItem4.addActionListener(new ActionListener() {
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
        menuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
//                FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                        "TXT files", "txt");
//                fileOpen.setFileFilter(filter);
                int ret = fileOpen.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    try {
                        studentArrayList=new ArrayList<>();
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        File file=fileOpen.getSelectedFile();
                        Document document = builder.parse(file);

                        NodeList studentElements = document
                                .getDocumentElement()
                                .getElementsByTagName("student");

                        for (int i = 0; i < studentElements.getLength(); i++) {
                            Node student = studentElements.item(i);
                            if (student.getNodeType() == Node.ELEMENT_NODE) {
                                Element element = (Element) student;
                                int recBookNum = Integer.parseInt(
                                        element
                                        .getElementsByTagName("recBookNum")
                                        .item(0)
                                        .getTextContent());
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
                                studentArrayList.add(new Student(recBookNum,surname,course,group));

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
    }

    public void updateList() {
        modelStudents = new DefaultListModel<>();
        Iterator<Student> iterator = studentArrayList.iterator();
        while (iterator.hasNext()) {
            modelStudents.addElement(iterator.next());
        }
        listStudents.setModel(modelStudents);
    }

    public void updateTaskList() {
        modelStudents = new DefaultListModel<>();
        Iterator<Student> iterator = studentSortedArrayList.iterator();
        while (iterator.hasNext()) {
            modelStudents.addElement(iterator.next());
        }
        listTask.setModel(modelStudents);
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
        resultXML.append("<students>\n");
        Iterator<Student> iterator = studentArrayList.iterator();
        while (iterator.hasNext()){
            resultXML.append(iterator.next().toXMLString());
        }
        resultXML.append("</students>\n");
        return resultXML.toString();
    }

    class ChooseFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "TXT files", "txt");
            fileOpen.setFileFilter(filter);
            int ret = fileOpen.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    studentArrayList=new ArrayList<>();
                    int recBookNum;
                    String surname;
                    int course;
                    int group;
                    Scanner scanner = new Scanner(fileOpen.getSelectedFile());
                    while (scanner.hasNext()) {
                        recBookNum = scanner.nextInt();
                        surname = scanner.next();
                        course = scanner.nextInt();
                        group = scanner.nextInt();
                        studentArrayList.add(new Student(recBookNum, surname, course, group));
                        updateList();
                    }
                } catch (FileNotFoundException exception) {
                    JOptionPane.showMessageDialog(WindowApp.this,
                            "Cannot open the file.",
                            "Inane warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    class AddDataListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            AddDataDialog dialog = new AddDataDialog(WindowApp.this);
            dialog.setVisible(true);
        }
    }
    class SortListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            studentSortedArrayList=new ArrayList<>();
            HashMap<String,Integer> hashMap=new HashMap<>();
            for(Student student : studentArrayList){
                hashMap.put(student.getSurname(),0);
            }
            for(Student student : studentArrayList){
                Integer integer = hashMap.get(student.getSurname());
                hashMap.put(student.getSurname(),++integer);
            }
            for(Student student : studentArrayList){
                if(hashMap.get(student.getSurname())>1){
                    studentSortedArrayList.add(student);
                }
            }
            Collections.sort(studentSortedArrayList, new MySort1());
            updateTaskList();
        }
    }
}
class MySort1 implements Comparator<Student> {

    public int compare(Student a, Student b) {
        if (a.getCourse() > b.getCourse()) {
            return 1;
        } else if (a.getCourse() < b.getCourse()) {
            return -1;
        } else {
            if (a.getGroup() > b.getGroup()) {
                return 1;
            } else if (a.getGroup() < b.getGroup()) {
                return -1;
            } else {
                return a.getSurname().compareTo(b.getSurname());
            }
        }
    }
}
class MySort2 implements Comparator<Student> {

    public int compare(Student a, Student b) {
        return a.getSurname().compareTo(b.getSurname());
    }
}

//{
//        studentTempArrayList=new ArrayList<>(studentArrayList);
//        studentSortedArrayList=new ArrayList<>();
//        MySort2 mySort2=new MySort2();
//        if(!studentTempArrayList.isEmpty()) {
//        Collections.sort(studentTempArrayList, mySort2);
//        Iterator<Student> iterator = studentTempArrayList.iterator();
//        Student student1 = iterator.next();
//        Student student2;
//        while (iterator.hasNext()) {
//        student2 = iterator.next();
//        if (mySort2.compare(student1, student2) == 0) {
//        studentSortedArrayList.add(student1);
//        while (iterator.hasNext() && mySort2.compare(student1, student2) == 0) {
//        studentSortedArrayList.add(student2);
//        student1 = student2;
//        student2 = iterator.next();
//        }
//        } else {
//        student1 = student2;
//        }
//        }
//        Collections.sort(studentSortedArrayList, new MySort1());
//        updateTaskList();
//        }
//        }