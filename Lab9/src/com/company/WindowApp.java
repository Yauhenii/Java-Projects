package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WindowApp extends JFrame {
    private final static int LIST_X = 300;
    private final static int LIST_Y = 500;

    JMenu menu;
    JMenuBar menuBar;
    JMenuItem menuItem1;
    JMenuItem menuItem2;
    JMenuItem menuItem3;
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
        mainPanel.add(chooseFileButton, BorderLayout.SOUTH);
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
        menuItem1 = new JMenuItem("Add student...");
        menuItem2 = new JMenuItem("Choose file...");
        menuItem3 = new JMenuItem("Sort");
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menuBar.add(menu);
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

    class ChooseFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            int ret = fileOpen.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    int recBookNum;
                    String surname;
                    int course;
                    int group;
                    Scanner scanner = new Scanner(new File(fileOpen.getSelectedFile().getAbsolutePath()));
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
                studentTempArrayList=new ArrayList<>(studentArrayList);
                studentSortedArrayList=new ArrayList<>();
                MySort2 mySort2=new MySort2();
                Collections.sort(studentTempArrayList,mySort2);
                Iterator<Student> iterator=studentTempArrayList.iterator();
                Student student1=iterator.next();
                Student student2;
                while (iterator.hasNext()){
                        student2=iterator.next();
                        if (mySort2.compare(student1, student2)==0) {
                            studentSortedArrayList.add(student1);
                            while (iterator.hasNext() && mySort2.compare(student1, student2)==0){
                                studentSortedArrayList.add(student2);
                                student1=student2;
                                student2=iterator.next();
                            }
                        }
                        else{
                            student1 = student2;
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
        //if(a==b) return -1;
        return a.getSurname().compareTo(b.getSurname());
    }
}


//                MySort2 mySort2 = newMySort2();
//                studentTempArrayList = new ArrayList<>();
//                Iterator<Student> interalIt;
//                Iterator<Student> externalIt = studentArrayList.iterator();
//                while (externalIt.hasNext()) {
//                    Student studentExternal = externalIt.next();
//                    interalIt = studentArrayList.iterator();
//                    while (interalIt.hasNext()) {
//                        Student internalStudent = interalIt.next();
//                        if (mySort2.compare(internalStudent, studentExternal) == 0) {
//                            studentTempArrayList.add(internalStudent);
//                        }
//                    }
//                }
//                HashSet<Student> tempSet = new HashSet<>(studentTempArrayList);
//                studentTempArrayList = new ArrayList<>(tempSet);
//                MySort1 mySort1 = new MySort1();
//                Collections.sort(studentTempArrayList, mySort1);
//                updateTaskList();
