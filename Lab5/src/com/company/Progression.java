package com.company;

import java.io.FileWriter;
import java.io.IOException;

public abstract class Progression {
    public abstract double getElem(int j);
    public double getSum() throws IllegalArgumentException{
        if(n<=0){
            throw new IllegalArgumentException();
        }
        double sum=0;
        for (int i=1;i<=n;i++){
            sum+=getElem(i);
        }
        return sum;
    }
    public String toString() throws IllegalArgumentException{
        if(n<=0){
            throw new IllegalArgumentException();
        }
        StringBuffer stringBuffer=new StringBuffer("");
        for (int i=1;i<=n;i++){
            stringBuffer.append(Double.toString(getElem(i)));
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }
    public void writeToFile(String fileName) throws IOException {
        FileWriter fileWriter=new FileWriter(fileName);
        fileWriter.write(getSum()+"\n"+toString()+"\n");
        fileWriter.close();

    }

    public double getFirstElem() {
        return firstElem;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setfirstElem(double setfirstElem) {
        this.firstElem=setfirstElem;
    }
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    private double firstElem, coefficient;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    private int n;
}

