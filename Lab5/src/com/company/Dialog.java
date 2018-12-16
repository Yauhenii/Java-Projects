package com.company;

import javax.swing.*;
import java.awt.event.*;

public class Dialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton writeToFileButton;
    private JRadioButton arithmeticRadioButton;
    private JRadioButton geometricRadioButton;
    private JButton printNElementsButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton resetFormButton;
    private JButton printIElementButton;
    private JButton setButton;
    private JTextField textField6;
    private JTextField textField7;

    int index = 0;
    Progression[] progression = {new Arithmetic(2,2),new Geometric(2,2)};

    public Dialog() {
        //default
        double firstElem = progression[index].getFirstElem();
        double coefficient = progression[index].getCoefficient();
        textField6.setText(Double.toString(firstElem));
        textField7.setText(Double.toString(coefficient));
        arithmeticRadioButton.setSelected(true);
        textField2.setText("1");
        textField3.setText("1");
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        writeToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = 1;
                try {
                    n = Integer.parseInt(textField2.getText());
                }
                catch(NumberFormatException exception) {
                    textField1.setText("Can't convert n to integer type");
                }
                String fileName=textField1.getText();
                try {
                    progression[index].setN(n);
                    progression[index].writeToFile(fileName);
                }
                catch (Exception exception){
                    textField1.setText("Invalid file name");
                }
            }
        });
        resetFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("1");
                textField3.setText("1");
                textField4.setText("");
                textField5.setText("");
            }
        });
        arithmeticRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index=0;
                textField6.setText(Double.toString(progression[index].getFirstElem()));
                textField7.setText(Double.toString(progression[index].getCoefficient()));
                setPritButtonsEnabled(true);
            }
        });
        geometricRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index=1;
                textField6.setText(Double.toString(progression[index].getFirstElem()));
                textField7.setText(Double.toString(progression[index].getCoefficient()));
                setPritButtonsEnabled(true);
            }
        });
        printNElementsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = 1;
                try {
                    textField6.setText(Double.toString(progression[index].getFirstElem()));
                    textField7.setText(Double.toString(progression[index].getCoefficient()));
                    n = Integer.parseInt(textField2.getText());
                    progression[index].setN(n);
                    textField5.setText(progression[index].toString());

                }
                catch(NumberFormatException exception) {
                    textField5.setText("Can't convert n to integer type");
                }
                catch (IllegalArgumentException exception){
                    textField5.setText("Please, type n, n>0");
                }
            }
        });
        printIElementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=1;
                try{
                    textField6.setText(Double.toString(progression[index].getFirstElem()));
                    textField7.setText(Double.toString(progression[index].getCoefficient()));
                    i = Integer.parseInt(textField3.getText());
                    textField4.setText(Double.toString(progression[index].getElem(i)));
                }
                catch(NumberFormatException exception) {
                    textField4.setText("Can't convert i to integer type");
                }
                catch (IllegalArgumentException exception){
                    textField4.setText("Please, type n, n>0");
                }
            }
        });
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double firstElem = Double.parseDouble(textField6.getText());
                    progression[index].setfirstElem(firstElem);
                    setPritButtonsEnabled(true);
                }
                catch(NumberFormatException exception) {
                    textField6.setText("Can't convert n to integer type");
                    setPritButtonsEnabled(false);
                }
                try {
                    double coefficient = Double.parseDouble(textField7.getText());
                    progression[index].setCoefficient(coefficient);
                    setPritButtonsEnabled(true);
                }
                catch(NumberFormatException exception) {
                    textField7.setText("Can't convert n to integer type");
                    setPritButtonsEnabled(false);
                }
            }
        });
    }
    private void setPritButtonsEnabled(boolean buttonsEnabled){
        printIElementButton.setEnabled(buttonsEnabled);
        printNElementsButton.setEnabled(buttonsEnabled);
    }
    private void onCancel() {
        dispose(); //освобождение ресурсов окна (уничтожение связанного с объектом окна)
    }

    public static void main(String[] args) {
        Dialog dialog = new Dialog();
        dialog.setSize(800,300);
        //dialog.pack();
        dialog.setVisible(true);
    }

}
