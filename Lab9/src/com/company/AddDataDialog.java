package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDataDialog extends JDialog {
    JPanel mainPanel;
    JButton exitButton;
    JButton addStudentButton;

    JTextArea recBookNumArea;
    JTextArea surnameArea;
    JTextArea courseArea;
    JTextArea groupArea;

    AddDataDialog(WindowApp owner){
        super(owner,"Add students", true);
        //main panel
        mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(10,1));
        //text areas
        mainPanel.add(new JLabel("RecBook number"));
        recBookNumArea=new JTextArea();
        mainPanel.add(recBookNumArea);
        mainPanel.add(new JLabel("Surname"));
        surnameArea=new JTextArea();
        mainPanel.add(surnameArea);
        mainPanel.add(new JLabel("Course number"));
        courseArea=new JTextArea();
        mainPanel.add(courseArea);
        mainPanel.add(new JLabel("Group number"));
        groupArea=new JTextArea();
        mainPanel.add(groupArea);
        //buttons
        addStudentButton=new JButton("Add student");
        mainPanel.add(addStudentButton);
        exitButton=new JButton("Exit");
        mainPanel.add(exitButton);
        //window
        setResizable(false);
        setContentPane(mainPanel);
        pack();
        //listeners
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int recBookNum = Integer.parseInt(recBookNumArea.getText());
                    String surname = surnameArea.getText();
                    int course = Integer.parseInt(courseArea.getText());
                    int group = Integer.parseInt(groupArea.getText());
                    owner.getModelStudents().add(new Student(recBookNum, surname, course, group));
                    owner.updateList();
                }
                catch (IllegalArgumentException exception){
                    JOptionPane.showMessageDialog(AddDataDialog.this,
                            "Check input format.",
                            "Inane warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}
