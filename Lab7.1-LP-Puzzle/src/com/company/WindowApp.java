package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WindowApp extends JFrame {
    private ImagePane imagePane;
    private JPanel menuPanel;
    private JPanel mainPanel;
    private JButton openImageButton;
    private JButton showImageButton;

    BufferedImage image=null;

    WindowApp(){
        //mainPanel
        mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //imagePane
        imagePane=new ImagePane();
        //menuPanel
        menuPanel=new JPanel();
        menuPanel.setLayout(new FlowLayout());
        mainPanel.add(menuPanel,BorderLayout.NORTH);
        //scrollPane
        mainPanel.add(imagePane,BorderLayout.CENTER);
        //window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        openImageButton=new JButton("Open image...");
        showImageButton=new JButton("Show sample...");
        menuPanel.add(openImageButton);
        menuPanel.add(showImageButton);
        setContentPane(mainPanel);
        pack();
        //listeners
        showImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(image!=null){
                    JOptionPane.showMessageDialog(null, "", "Sample", JOptionPane.WARNING_MESSAGE, new ImageIcon(BufferedImageUtils.resize(image,ImagePane.WINDOW_SIZE_X,ImagePane.WINDOW_SIZE_Y)));
                } else{
                    JOptionPane.showMessageDialog(null, "No sapmle to show.", "Waring", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        openImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showOpenDialog(null);
                if(ret ==JFileChooser.APPROVE_OPTION) {
                    try {
                        Component[] componentList = mainPanel.getComponents();
                        for(Component c : componentList){
                            if(c instanceof ImagePane){
                                mainPanel.remove(c);
                            }
                        }
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        imagePane=new ImagePane();
                        mainPanel.add(imagePane,BorderLayout.CENTER);
                        image=ImageIO.read(fileOpen.getSelectedFile());
                        imagePane.addImage(image);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "Cannot open image.", "Waring", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }
}