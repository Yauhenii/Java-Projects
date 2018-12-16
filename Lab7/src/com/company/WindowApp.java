package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WindowApp extends JFrame {
    private final static int BUTTONS_QUANTITY=3;
    private final static int WINDOW_SIZE_X=1200;
    private final static int WINDOW_SIZE_Y=800;

    private JScrollPane scrollPane;
    private JPanel drawPanel;
    private JPanel menuPanel;
    private JPanel mainPanel;
    private JLabel label;
    private JButton openImageButton, saveImageButton;
    private JRadioButton colorRadioButtons[];
    private ButtonGroup colorGroup;

    private int prevMouseCoordX;
    private int prevMouseCoordY;

    private BufferedImage dbImage=null;

    WindowApp(){
        //mainPanel
        mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //drawPanel
        drawPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(dbImage,0,0,this);
            }
        };
        drawPanel.setDoubleBuffered(true);
        drawPanel.setFocusable(true);
        drawPanel.setBackground(Color.white);
        //menuPanel
        menuPanel=new JPanel();
        menuPanel.setLayout(new FlowLayout());
        mainPanel.add(menuPanel,BorderLayout.NORTH);
        //scrollPane
        scrollPane=new JScrollPane(drawPanel);
        mainPanel.add(scrollPane,BorderLayout.CENTER);
        //label
        label=new JLabel("Select color: ");
        menuPanel.add(label,BorderLayout.NORTH);
        //window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_SIZE_X,WINDOW_SIZE_Y));
        setResizable(false);
        //setMinimumSize(new Dimension(MIN_WINDOW_SIZE_X,MIN_WINDOW_SIZE_Y));
        //buttons
        colorRadioButtons=new JRadioButton[BUTTONS_QUANTITY];
        colorGroup=new ButtonGroup();
        for(int i=0;i<BUTTONS_QUANTITY;i++){
            colorRadioButtons[i]=new JRadioButton(getColorString(i));
            colorGroup.add(colorRadioButtons[i]);
            menuPanel.add(colorRadioButtons[i]);
        }
        colorRadioButtons[0].setSelected(true);
        openImageButton=new JButton("Open image...");
        saveImageButton=new JButton("Save image...");
        menuPanel.add(openImageButton);
        menuPanel.add(saveImageButton);
        //
        setContentPane(mainPanel);
        pack();
        //listeners
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                prevMouseCoordX=e.getX();
                prevMouseCoordY=e.getY();
            }
        });
        drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(dbImage==null) {
                    dbImage = new BufferedImage(drawPanel.getWidth(),drawPanel.getHeight(),BufferedImage.TYPE_3BYTE_BGR);//createImage(WINDOW_SIZE_X, WINDOW_SIZE_Y);
                    Graphics dbStartGraphics=dbImage.getGraphics();
                    dbStartGraphics.setColor(Color.WHITE);
                    dbStartGraphics.fillRect(0,0,drawPanel.getWidth(),drawPanel.getHeight());
                }
                Graphics dbGraphics=dbImage.getGraphics();
                dbGraphics.setColor(getColorButtonString());
                dbGraphics.drawLine(prevMouseCoordX, prevMouseCoordY, e.getX(), e.getY());
                prevMouseCoordX=e.getX();
                prevMouseCoordY=e.getY();
                repaint();
            }
        });
        openImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //File chooser
                JFileChooser fileOpen = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG Images", "jpg");
                fileOpen.setFileFilter(filter);
                int ret = fileOpen.showOpenDialog(null);
                if(ret ==JFileChooser.APPROVE_OPTION) {
                    try {
                        dbImage = ImageIO.read(fileOpen.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(openImageButton, "Cannot open image.", "Waring", JOptionPane.WARNING_MESSAGE);
                    }
                    drawPanel.setPreferredSize(new Dimension(dbImage.getWidth(), dbImage.getHeight()));
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    repaint();
                }
            }
        });
        saveImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileSave = new JFileChooser();
                if(fileSave.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                    try {
                        File file = new File(fileSave.getSelectedFile().getPath()+".jpg");
                        if(dbImage!=null) {
                            ImageIO.write( dbImage, "jpg", file);
                        }
                        else{
                            JOptionPane.showMessageDialog(openImageButton, "Cannot save empty image.","Waring", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    catch (IOException e1){
                        JOptionPane.showMessageDialog(openImageButton, "Cannot save image.","Waring", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }
    private String getColorString(int i){
        if(i==0)
            return "red";
        if(i==1)
            return "green";
        if(i==2)
            return "blue";
        return "black";
    }
    private Color getColorButtonString(){
        if(colorRadioButtons[0].isSelected())
            return Color.red;
        if(colorRadioButtons[1].isSelected())
            return Color.green;
        if(colorRadioButtons[2].isSelected())
            return Color.blue;
        return Color.black;
    }
}

