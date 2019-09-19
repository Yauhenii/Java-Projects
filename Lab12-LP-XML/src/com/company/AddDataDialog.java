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
    JTextArea cityArea;

    AddDataDialog(WindowApp owner){
        super(owner,"Add students", true);
        //main panel
        mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(12,1));
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
        mainPanel.add(new JLabel("City"));
        cityArea=new JTextArea();
        mainPanel.add(cityArea);
        //buttons
        addStudentButton=new JButton("Add student");
        mainPanel.add(addStudentButton);
        exitButton=new JButton("Close");
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
                    String city = cityArea.getText();

                    if(course<1 || course>4){
                        JOptionPane.showMessageDialog(AddDataDialog.this,
                                "Check input format.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(group<1 || group>2){
                        JOptionPane.showMessageDialog(AddDataDialog.this,
                                "Check input format.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(surname.length()<1 || surname.length()>15){
                        JOptionPane.showMessageDialog(AddDataDialog.this,
                                "Check input format.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }


                    owner.getModelStudents().add(new Student(recBookNum, surname, course, group,city));
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
