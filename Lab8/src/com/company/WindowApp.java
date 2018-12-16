package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowApp extends JFrame {
    private final static int WINDOW_SIZE_X=700;
    private final static int WINDOW_SIZE_Y=400;
    private final static int LIST_SIZE_X=100;
    private final static int LIST_SIZE_Y=400;

    private final static int BUTTONS_QUANTITY=25;
    private final static int RADIOBUTTONS_QUANTITY=3;

    private final static int GRIDLAYOUT_ROWS=5;
    private final static int GRIDLAYOUT_COLS=5;

    private final static int GRIDLAYOUT_RADIO_ROWS=3;
    private final static int GRIDLAYOUT_RADIO_COLS=1;

    private enum PanelColors {RED,GREEN,BLUE};
    private Color [] colors = {Color.RED,Color.GREEN,Color.BLUE};
    JTabbedPane tabbedPane;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel;
    JButton leftButton;
    JButton rightButton;
    JList<String> list1;
    JList<String> list2;

    JButton [] buttons;

    JPanel radioButtonsPanel;
    JPanel colorPanel;
    JRadioButton [] radioButtons;
    ButtonGroup buttonGroup;

    DefaultListModel<String> model1;
    DefaultListModel<String> model2;

    WindowApp(){
        //tabbed pane
        tabbedPane=new JTabbedPane();
        //first tab
            initFirstTab();
        //second tab
            initSecondTab();
        //third tab
            initThirdTab();
        //window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(tabbedPane);
        pack();
    }
    private void initThirdTab(){
        //main panel
        panel3 = new JPanel();
        //panel with radiobuttons
        radioButtonsPanel=new JPanel();
        radioButtonsPanel.setLayout(new GridLayout(GRIDLAYOUT_RADIO_ROWS,GRIDLAYOUT_RADIO_COLS));
        radioButtons = new JRadioButton[RADIOBUTTONS_QUANTITY];
        for(int i=0;i<RADIOBUTTONS_QUANTITY;i++){
            radioButtons[i]=new JRadioButton(PanelColors.values()[i].toString());
            radioButtonsPanel.add(radioButtons[i]);
        }
        panel3.add(radioButtonsPanel,BorderLayout.WEST);
        //one group
        buttonGroup=new ButtonGroup();
        for(int i=0;i<RADIOBUTTONS_QUANTITY;i++){
            buttonGroup.add(radioButtons[i]);
        }
        //color panel
        colorPanel=new JPanel();
        colorPanel.setPreferredSize(new Dimension(2*LIST_SIZE_X,LIST_SIZE_Y));
        panel3.add(colorPanel,BorderLayout.EAST);
        //add icons
        for(int i=0;i<RADIOBUTTONS_QUANTITY;i++){
            radioButtons[i].setIcon(new ImageIcon("default.png"));
            radioButtons[i].setSelectedIcon(new ImageIcon("selected.png"));
            radioButtons[i].setPressedIcon(new ImageIcon("pressed.png"));
            radioButtons[i].setRolloverIcon(new ImageIcon("over.png"));
        }
        //default button
        radioButtons[0].setSelected(true);
        colorPanel.setBackground(colors[0]);
        //add panel to tabbed pane
        tabbedPane.add(panel3);
        //listener
        for(int i=0;i<RADIOBUTTONS_QUANTITY;i++) {
            radioButtons[i].addActionListener(new MyActionListener(i));
        }

    }
    private class MyActionListener implements ActionListener{

        private int i;

        MyActionListener(int i){
            this.i=i;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            colorPanel.setBackground(colors[i]);
        }
    }
    private void initSecondTab(){
        //main panel
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(GRIDLAYOUT_ROWS,GRIDLAYOUT_COLS));
        //buttons
        buttons=new JButton[BUTTONS_QUANTITY];
        for(int i=0; i<BUTTONS_QUANTITY; i++){
            buttons[i]=new MyButton();
        }
        for(int i=0; i<BUTTONS_QUANTITY; i++){
            panel2.add(buttons[i]);
        }
        //add panel to tabbed pane
        tabbedPane.add(panel2);
    }
    private void initFirstTab(){
        //main panel
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        //init list
        String [] stringArray1 = {"Trump","Obama","Bush","Reagan","Clinton","Kennedy"};
        String [] stringArray2 = {"Washington","Franklin","Lincoln", "Hoover"};
        model1 = new DefaultListModel<>();
        model2 = new DefaultListModel<>();
        for(String string : stringArray1){
            model1.addElement(string);
        }
        for(String string : stringArray2){
            model2.addElement(string);
        }
        list1=new JList<>(model1);
        list2=new JList<>(model2);
        list1.setPreferredSize(new Dimension(LIST_SIZE_X, LIST_SIZE_Y));
        list2.setPreferredSize(new Dimension(LIST_SIZE_X, LIST_SIZE_Y));
        panel1.add(list1,BorderLayout.WEST);
        panel1.add(list2,BorderLayout.EAST);
        panel = new JPanel();
        leftButton = new JButton("<");
        rightButton = new JButton(">");
        leftButton.setPreferredSize(new Dimension(100,50));
        rightButton.setPreferredSize(new Dimension(100,50));
        panel.add(leftButton, BorderLayout.SOUTH);
        panel.add(rightButton, BorderLayout.NORTH);
        panel1.add(panel,BorderLayout.CENTER);
        //add panel to tabbed pane
        tabbedPane.add(panel1);
        //listeners
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0; i<model2.size(); i++){
                    if(list2.isSelectedIndex(i)) {
                        model1.addElement(model2.get(i));
                    }
                }
                for(int i=model2.size(); i>-1; i--){
                    if(list2.isSelectedIndex(i)) {
                        model2.remove(i);
                    }
                }
            }
        });
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0; i<model1.size(); i++){
                    if(list1.isSelectedIndex(i)) {
                        model2.addElement(model1.get(i));
                    }
                }
                for(int i=model1.size(); i>-1; i--){
                    if(list1.isSelectedIndex(i)) {
                        model1.remove(i);
                    }
                }
            }
        });
    }
}
