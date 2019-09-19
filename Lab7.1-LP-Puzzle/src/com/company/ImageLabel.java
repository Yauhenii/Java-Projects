package com.company;

import javax.swing.*;

public class ImageLabel extends JLabel {

    private int i;
    private int j;

    private int ci;
    private int cj;

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public int getCj() {
        return cj;
    }

    public void setCj(int cj) {
        this.cj = cj;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public ImageLabel(Icon image, int i, int j) {
        super(image);
        this.i=i;
        this.j=j;
        ci=i;
        cj=j;
    }

    public boolean isIn(){
        return (i==ci && j==cj);
    }
}
