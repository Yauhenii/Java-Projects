package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ImagePane extends JLayeredPane {

    public final static int WINDOW_SIZE_X=900;
    public final static int WINDOW_SIZE_Y=600;

    private final static int IMAGES_NUM_X=2;
    private final static int IMAGES_NUM_Y=3;
    private final static int IMAGES_NUM=IMAGES_NUM_X*IMAGES_NUM_Y;

    private int w;
    private int h;

    boolean[][] imagesPos;

    public ImagePane(){
        setSize(getPreferredSize());
    }

    public void addImage(BufferedImage image){
        image=BufferedImageUtils.resize(image,WINDOW_SIZE_X,WINDOW_SIZE_Y);
        w=WINDOW_SIZE_X/IMAGES_NUM_X;
        h=WINDOW_SIZE_Y/IMAGES_NUM_Y;
        Dimension dimension=new Dimension(w,h);

        imagesPos=new boolean[IMAGES_NUM_Y][IMAGES_NUM_X];
        for(int i=0;i<IMAGES_NUM_Y;i++){
            imagesPos[i]=new boolean[IMAGES_NUM_X];
            for(int j=0;j<IMAGES_NUM_X;j++){
                imagesPos[i][j]=true;
            }
        }

        ImagePane.ImageHandler handler=new ImagePane.ImageHandler();

        for(int j=0;j<IMAGES_NUM_Y;j++){
            for(int i=0;i<IMAGES_NUM_X;i++){
                ImageLabel label=new ImageLabel(new ImageIcon(image.getSubimage(i*w,j*h,w,h)), i,j);
                label.setSize(dimension);
                label.setLocation(new Point(i*w,j*h));
                label.addMouseListener(handler);
                label.addMouseMotionListener(handler);
                add(label);
            }
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_SIZE_X,WINDOW_SIZE_Y);
    }
    public class ImageHandler extends MouseAdapter {

        private Point offset;
        private boolean dragging;

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel label = (JLabel) e.getComponent();
            moveToFront(label);
            offset = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getPoint().x - offset.x;
            int y = e.getPoint().y - offset.y;
            Component component = e.getComponent();
            Point location = component.getLocation();
            location.x += x;
            location.y += y;
            component.setLocation(location);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Component component = e.getComponent();
            ImageLabel imageLabel=(ImageLabel)component;
            Point location = component.getLocation();
            location.setLocation(new Point( (int)(location.getX()+w/2),(int)(location.getY()+h/2)));
            boolean trigger=true;
            for(int j=0;j<IMAGES_NUM_X;j++) {
                if(!trigger){
                    break;
                }
                for (int i = 0; i < IMAGES_NUM_Y; i++) {
                    if (w * i <= location.getX() && w * (i + 1) >= location.getX() && h * j <= location.getY() && h * (j + 1) >= location.getY()) {
                        component.setLocation(new Point(w * i, h * j));
                        imageLabel.setCi(i);
                        imageLabel.setCj(j);
                        if (imageLabel.isIn()) {
                            imagesPos[imageLabel.getJ()][imageLabel.getI()] = true;
                        } else {
                            imagesPos[imageLabel.getJ()][imageLabel.getI()] = false;
                        }
                        trigger=false;
                        break;
                    }
                }
            }

            if(isValid()){
                JOptionPane.showMessageDialog(null, "Puzzle is completed!", "Image", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        public boolean isValid(){
            for(int i=0;i<IMAGES_NUM_Y;i++){
                for(int j=0;j<IMAGES_NUM_X;j++){
                    if(!imagesPos[i][j]){
                        return false;
                    }
                }
            }
            return true;
        }
    }
}

