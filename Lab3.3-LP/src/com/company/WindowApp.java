package com.company;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Scanner;

public class WindowApp extends JFrame {
    //gui
    JPanel mainPanel;
    JTree tree;
    JScrollPane scrollPane;
    JButton deleteButton;
    JButton addButton;
    JPanel buttonPanel;
    JButton acceptButton;
    JTextField nameField;
    JTextField surnameField;
    JTextField fathersNameField;
    JTextField courseField;
    JTextField groupField;
    //const
    public static final int SCREEN_WIDTH=600;
    public static final int SCREEN_HEIGHT=700;
    public static final String FILE_TO_READ="data.txt";
    public static final Icon courseIcon = new ImageIcon("courseIcon.png");
    public static final Icon topIcon = new ImageIcon("topIcon.png");
    public static final Icon groupIcon = new ImageIcon("groupIcon.png");
    public static final Icon studentIcon = new ImageIcon("studentIcon.png");
    //data
    ArrayList<Student> students;
    SortedMutableTreeNode top;
    SortedMutableTreeNode selectedNode;
    private DefaultTreeCellEditor editor;

    WindowApp(){
        //main panel
        mainPanel=new JPanel(new BorderLayout());
        //buttonPanel
        buttonPanel=new JPanel(new GridLayout(2,8));
        //fields
        courseField=new JTextField();
        groupField=new JTextField();
        surnameField=new JTextField();
        nameField=new JTextField();
        fathersNameField=new JTextField();
        //buttons
        deleteButton=new JButton("Delete");
        addButton=new JButton("Add");
        acceptButton=new JButton("Edit");
        buttonPanel.add(addButton);
        buttonPanel.add(new JLabel("Course"));
        buttonPanel.add(new JLabel("Group"));
        buttonPanel.add(new JLabel("Surname"));
        buttonPanel.add(new JLabel("Name"));
        buttonPanel.add(new JLabel("Father's name"));
        buttonPanel.add(deleteButton);
        buttonPanel.add(acceptButton);
        buttonPanel.add(courseField);
        buttonPanel.add(groupField);
        buttonPanel.add(surnameField);
        buttonPanel.add(nameField);
        buttonPanel.add(fathersNameField);
        //read data
        readData(FILE_TO_READ);
        //tree model
        top = new SortedMutableTreeNode("Courses");
        //tree
        tree=new JTree(top){
            @Override
            public boolean isEditable() {
                return true;
            }

        };
        tree.setPreferredSize(new Dimension(SCREEN_WIDTH,2*SCREEN_HEIGHT/3));
        setRenderer();
        tree.setCellEditor(editor);
        createNodes();
        //window
        mainPanel.add(tree,BorderLayout.NORTH);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        //
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                selectedNode = (SortedMutableTreeNode)tree.getLastSelectedPathComponent();
                if(selectedNode!=null) {
                    if (selectedNode.isLeaf()) {
                        Student student = (Student) selectedNode.getUserObject();
                        courseField.setText(Integer.toString(student.getCourse()));
                        groupField.setText(Integer.toString(student.getGroup()));
                        surnameField.setText(student.getSurname());
                        nameField.setText(student.getName());
                        fathersNameField.setText(student.getFathersname());
                    }
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudentDialog dialog=new AddStudentDialog(WindowApp.this);
                dialog.setVisible(true);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteNode(selectedNode);
                ((DefaultTreeModel)tree.getModel()).reload();
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedNode!=null){
                    if(selectedNode.isLeaf()){
                        try {
                            int course = Integer.parseInt(courseField.getText());
                            int group = Integer.parseInt(groupField.getText());
                            String surname = surnameField.getText();
                            String name = nameField.getText();
                            String fathersname = fathersNameField.getText();
                            addStudentToTree(new Student(course,group,surname,name,fathersname));
                            deleteNode(selectedNode);
                            ((DefaultTreeModel)tree.getModel()).reload();
                        }catch (IllegalArgumentException exception){
                            JOptionPane.showMessageDialog(WindowApp.this,
                                    "Check input format.",
                                    "Inane warning",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });
    }
    void deleteNode(SortedMutableTreeNode selectedNode){
        if(selectedNode==null){
            return;
        }
        if(selectedNode.getDepth()==0){
            DefaultTreeModel model =(DefaultTreeModel)tree.getModel();
            SortedMutableTreeNode parentNode=(SortedMutableTreeNode) selectedNode.getParent();
            SortedMutableTreeNode parentParentNode=(SortedMutableTreeNode) parentNode.getParent();
            if(parentNode.getChildCount()==1){
                model.removeNodeFromParent(selectedNode);
                if(parentParentNode.getChildCount()==1){
                    model.removeNodeFromParent(parentNode);
                    model.removeNodeFromParent(parentParentNode);
                }
                else{
                    model.removeNodeFromParent(parentNode);
                }
            } else {
                model.removeNodeFromParent(selectedNode);
            }
        }
    }
    void setRenderer(){
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                                                          Object value,
                                                          boolean selected,
                                                          boolean expanded,
                                                          boolean leaf,
                                                          int row,
                                                          boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                SortedMutableTreeNode node=(SortedMutableTreeNode) value;
                Object o = node.getUserObject();
                if (o instanceof Student) {
                    setIcon(studentIcon);
                } else {
                    if(node.getDepth()==3){
                        setIcon(topIcon);
                    }
                    if(node.getDepth()==2){
                        setIcon(courseIcon);
                    }
                    if(node.getDepth()==1){
                        setIcon(groupIcon);
                    }
                }
                return this;
            }
        });
    }
    void createNodes(){
        for(Student student : students){
            addStudentToTree(student);
        }
    }
    void addStudentToTree(Student student){
        int course=student.getCourse();
        int group=student.getGroup();
        SortedMutableTreeNode courseNode;
        SortedMutableTreeNode groupNode;
        if(top.isLeaf()){
            top.add(new SortedMutableTreeNode(course));
            courseNode=(SortedMutableTreeNode)top.getChildAt(0);
            courseNode.add(new SortedMutableTreeNode(group));
            groupNode=(SortedMutableTreeNode)courseNode.getChildAt(0);
            groupNode.add(new SortedMutableTreeNode(student));
        } else{
            boolean trigger=false;
            for(int i=0;i<top.getChildCount();i++){
                courseNode=(SortedMutableTreeNode) top.getChildAt(i);
                if(courseNode.toString().equals(Integer.toString(course))){
                    //add group
                    boolean internalTrigger=false;
                    for(int j=0;j<courseNode.getChildCount();j++){
                        groupNode=(SortedMutableTreeNode)courseNode.getChildAt(j);
                        if(groupNode.toString().equals(Integer.toString(group))){
                            groupNode.add(new SortedMutableTreeNode(student));
                            internalTrigger=true;
                            break;
                        }
                    }
                    if(!internalTrigger){
                        courseNode.add(new SortedMutableTreeNode(group));
                        groupNode=(SortedMutableTreeNode)courseNode.getChildAt(courseNode.getChildCount()-1);
                        groupNode.add(new SortedMutableTreeNode(student));
                        courseNode.sort();
                    }
                    trigger=true;
                    break;
                }
            }
            if(!trigger){
                top.add(new SortedMutableTreeNode(course));
                courseNode=(SortedMutableTreeNode)top.getChildAt(top.getChildCount()-1);
                courseNode.add(new SortedMutableTreeNode(group));
                groupNode=(SortedMutableTreeNode)courseNode.getChildAt(0);
                groupNode.add(new SortedMutableTreeNode(student));
            }
        }
        top.sort();
    }
    void readData(String fileName){
        File file=new File(fileName);
        students=new ArrayList<>();
        int course;
        int group;
        String surname;
        String name;
        String fathersname;

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                course=scanner.nextInt();
                group=scanner.nextInt();
                surname=scanner.next();
                name=scanner.next();
                fathersname=scanner.next();
                students.add(new Student(course,group,surname,name,fathersname));
            }
        }
        catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(WindowApp.this,
                    "No file to read.",
                    "Inane warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    class AddStudentDialog extends JDialog{
        JTextField nameField;
        JTextField surnameField;
        JTextField fathersNameField;
        JTextField courseField;
        JTextField groupField;
        JPanel panel;
        JButton button;

        AddStudentDialog(JFrame owner){
            nameField=new JTextField();
            surnameField=new JTextField();
            fathersNameField=new JTextField();
            courseField=new JTextField();
            groupField=new JTextField();
            button=new JButton("Add");
            panel=new JPanel(new GridLayout(11,1));
            panel.add(new JLabel("Name"));
            panel.add(nameField);
            panel.add(new JLabel("Surname"));
            panel.add(surnameField);
            panel.add(new JLabel("Father's name"));
            panel.add(fathersNameField);
            panel.add(new JLabel("Course"));
            panel.add(courseField);
            panel.add(new JLabel("Group"));
            panel.add(groupField);
            panel.add(button);
            setContentPane(panel);
            setModal(true);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setResizable(false);
            pack();
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int course = Integer.parseInt(courseField.getText());
                        int group = Integer.parseInt(groupField.getText());
                        String surname = surnameField.getText();
                        String name = nameField.getText();
                        String fathersname = fathersNameField.getText();
                        addStudentToTree(new Student(course,group,surname,name,fathersname));
                        ((DefaultTreeModel)tree.getModel()).reload();
                        //((DefaultTreeModel)tree.getModel()).nodeChanged(groupNode);
                    }catch (IllegalArgumentException exception){
                        JOptionPane.showMessageDialog(AddStudentDialog.this,
                                "Check input format.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
        }
    }
}
