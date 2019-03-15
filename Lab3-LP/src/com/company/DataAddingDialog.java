package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataAddingDialog extends JDialog {

    JPanel mainPanel;
    JButton addButton;
    JButton okButton;
    JButton chooseImageButton;
    JTextField descriptionField;
    JTextField priceField;
    JTextField countryField;

    DataAddingDialog(WindowApp owner){
        super(owner,"Add row", true);
        //init
        descriptionField=new JTextField();
        priceField=new JTextField();
        countryField=new JTextField();
        addButton=new JButton("Add tour");
        okButton=new JButton("OK");
        chooseImageButton=new JButton("Choose image");
        //add
        mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(4,1));
        mainPanel.add(new JLabel("Country"));
        mainPanel.add(countryField);
        mainPanel.add(new JLabel("Destription"));
        mainPanel.add(descriptionField);
        mainPanel.add(new JLabel("Price"));
        mainPanel.add(priceField);
        mainPanel.add(addButton);
        mainPanel.add(okButton);
        //window
        setResizable(false);
        setContentPane(mainPanel);
        pack();
        //listeners
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int price = Integer.parseInt(priceField.getText());
                    String country =countryField.getText();
                    String description = descriptionField.getText();
                    DefaultTableModel model = (DefaultTableModel) owner.table1.getModel();
                    ImageIcon imageIcon =owner.countriesAndFlagsMap.get(country+" ");
                    if(imageIcon!=null) {
                        model.insertRow(model.getRowCount()-1,new Object[]{country+" ", description, price, false});
                    }
                    else{
                        JOptionPane.showMessageDialog(DataAddingDialog.this,
                                "No such country.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (IllegalArgumentException exception){
                    JOptionPane.showMessageDialog(DataAddingDialog.this,
                            "Check input format.",
                            "Inane warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
