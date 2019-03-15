package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BallDialog extends JDialog {

    //const
    public static int SCREEN_WIDTH=200;
    public static int SCREEN_HEIGHT=200;
    public static int IMAGE_WIDTH=30;
    public static int IMAGE_HEIGHT=30;
    public static String IMAGE_FILE_NAME="img_311846.png";
    public static int TIMER_SPEED=50;
    public static int MIN_SPEED=1;
    public static int MAX_SPEED=50;
    public static int INT_SPEED=1;
    public static int T=10;
    public static String[] items={"Inward","Backward"};
    //gui
    private JSlider slider;
    private JPanel drawPanel;
    private JPanel menuPanel;
    private JPanel mainPanel;
    private JComboBox comboBox;
    private Timer timer;
    //
    private BufferedImage dbImage =null;
    private BufferedImage image;
    private int t=MAX_SPEED/2;
    private int direction=1;

    private double currAngle=0;

    BallDialog(JFrame owner){
        super(owner,true);
        //panel
        mainPanel=new JPanel(new BorderLayout());
        menuPanel=new JPanel(new BorderLayout());
        //slider
        slider=new JSlider(MIN_SPEED,MAX_SPEED,INT_SPEED);
        slider.setValue(t);
        menuPanel.add(slider,BorderLayout.EAST);
        //combobox
        comboBox=new JComboBox(items);
        menuPanel.add(comboBox,BorderLayout.WEST);
        //panel
        drawPanel =new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(dbImage,0,0,this);
            }
        };
        drawPanel.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        drawPanel.setDoubleBuffered(true);
        drawPanel.setFocusable(true);
        drawPanel.setBackground(Color.white);
        //image
        readImage(IMAGE_FILE_NAME);
        //timer
        timer =new Timer(TIMER_SPEED/t, new ActionListener() {
            int elapsedTime=0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(elapsedTime==Integer.MAX_VALUE){
                    elapsedTime=0;
                }
                if(currAngle>=2*Math.PI){
                    currAngle=0;
                }
                int width= drawPanel.getWidth();
                int height= drawPanel.getHeight();
                int diam=Math.min(width,height);
                dbImage = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
                Graphics dbStartGraphics= dbImage.getGraphics();
                dbStartGraphics.setColor(Color.WHITE);
                dbStartGraphics.fillRect(0,0,width,height);
                currAngle+=direction*2*Math.PI*1/1000;
                int x=getCircleX(diam/2,diam/2-IMAGE_WIDTH);
                int y=getCircleY(diam/2,diam/2-IMAGE_HEIGHT);
                dbStartGraphics.drawImage(image,x-IMAGE_WIDTH/2,y-IMAGE_HEIGHT/2,IMAGE_WIDTH,IMAGE_HEIGHT,null);
                elapsedTime++;
                repaint();
            }
        });
        timer.start();
        //window
        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(drawPanel,BorderLayout.CENTER);
        setContentPane(mainPanel);
        setResizable(true);
        setMinimumSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        pack();
        //
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                t=slider.getValue();
                t=t*mainPanel.getWidth()/getScreenWorkingWidth();
                timer.setDelay(TIMER_SPEED/t);
            }
        });
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox.getSelectedIndex()==0){
                    direction=1;
                } else{
                    direction=-1;
                }
            }
        });
    }
    int getCircleX(int o,int r){
        Double x=r*Math.cos(currAngle)+o;
        return x.intValue();
    }
    int getCircleY(int o,int r){
        Double y=r*Math.sin(currAngle)+o;
        return y.intValue();
    }
    void readImage(String filename){
        try{
            File file=new File(filename);
            image = ImageIO.read(file);
        }catch (IOException e){
            JOptionPane.showMessageDialog(this, "Cannot open image.", "Waring", JOptionPane.WARNING_MESSAGE);
        }
    }
    public static int getScreenWorkingWidth() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int getScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }
}
