package com.company;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyRadioButton extends JRadioButton implements MouseListener {

    public MyRadioButton() {
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setText("CLicked!");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setText("");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setSelected(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setSelected(false);
    }

}
