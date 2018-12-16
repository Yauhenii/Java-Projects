package com.company;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;

public class WindowApp extends JFrame {
    private JPanel panel;
    private JLabel label;
    private JButton button;

    final static int WINDOW_SIZE_X = 500;
    final static int WINDOW_SIZE_Y = 500;
    final static int BUTTON_SIZE_X = 100;
    final static int BUTTON_SIZE_Y = 50;

    WindowApp() {
        //window
        setResizable(false);
        setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //panel
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel, BorderLayout.CENTER);
        //label
        label = new JLabel("Move the cursor");
        this.add(label, BorderLayout.SOUTH);
        //button
        button = new JButton("");
        button.setBounds(0, 0, BUTTON_SIZE_X, BUTTON_SIZE_Y);
        panel.add(button);
        //Listeners
        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                label.setText("X:" + e.getX() + " Y:" + e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                label.setText("X:" + e.getX() + " Y:" + e.getY());
            }
        });

        button.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

                label.setText("X:" + Integer.toString(e.getX()+button.getX()) + " Y:" + Integer.toString(e.getY()+button.getY()));
                if(e.isControlDown()){
                    button.setLocation(e.getX()+button.getX(),e.getY()+button.getY());
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                label.setText("X:" + Integer.toString(e.getX()+button.getX()) + " Y:" + Integer.toString(e.getY()+button.getY()));
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setLocation(new Point(e.getX(),e.getY()));
            }
        });
        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){
                if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE){
                    StringBuffer buttonText = new StringBuffer(button.getText());
                    if (buttonText.length() != 0) {
                        buttonText.deleteCharAt(buttonText.length() - 1);
                        button.setText(buttonText.toString());
                    }
                }
                else {
                    StringBuffer buttonText = new StringBuffer(button.getText());
                    buttonText.append(e.getKeyChar());
                    button.setText(buttonText.toString());
                }
            }
        });
    }
}



