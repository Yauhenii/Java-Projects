package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class WindowApp extends JFrame {
    //const
    public static final int SCREEN_WIDTH=500;
    public static final int SCREEN_HEIGHT=500;
    public static final int TIMER_SPEED=1000;
    //gui
    private JTabbedPane tabbedPane;
    private JPanel panel1;
    private JPanel panel2;
    //
    private Timer timer1;
    private BufferedImage dbImage1=null;

    WindowApp(){
        //tabbed pane
        tabbedPane=new JTabbedPane();
        initFirstTab();
        initSecondTab();
        //window
        setContentPane(tabbedPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }
    void initFirstTab(){
        //panel
        panel1=new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(dbImage1,0,0,this);
            }
        };
        panel1.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        panel1.setDoubleBuffered(true);
        panel1.setFocusable(true);
        panel1.setBackground(Color.white);
        //tabbed pane
        tabbedPane.add(panel1);
        //timer
        timer1=new Timer(TIMER_SPEED, new ActionListener() {
            int elapsedTime=0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(elapsedTime==60){
                    elapsedTime=0;
                }
                int width=panel1.getWidth();
                int height=panel1.getHeight();
                dbImage1 = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
                Graphics dbStartGraphics= dbImage1.getGraphics();
                dbStartGraphics.setColor(Color.WHITE);
                dbStartGraphics.fillRect(0,0,width,height);
                dbStartGraphics.setColor(Color.BLACK);
                dbStartGraphics.drawOval(0,0,width, height );
                dbStartGraphics.drawLine(width/2, height/2, getCircleX(width/2,width/2,elapsedTime),getCircleY(height/2,height/2,elapsedTime));
                elapsedTime++;
                repaint();
            }
        });
        timer1.start();
    }

  void initSecondTab() {
    // panel
    panel2 = new JPanel(new BorderLayout());
    JButton button = new JButton("PRESS ME");
    panel2.add(button);
    // tabbed pane
    tabbedPane.add(panel2);
    //
    button.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            BallDialog dialog = new BallDialog(WindowApp.this);
            dialog.setVisible(true);
          }
        });
}

    int getCircleX(int o,int r,int time){
        Double x=r*Math.cos(Math.PI/30*time-Math.PI/2)+o;
        return x.intValue();
    }
    int getCircleY(int o,int r,int time){
        Double y=r*Math.sin(Math.PI/30*time-Math.PI/2)+o;
        return y.intValue();
    }
}
