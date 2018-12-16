package com.company;

public class Geometric extends Progression {
    public Geometric(double b, double q){
        setfirstElem(b);
        setCoefficient(q);
    }
    @Override
    public double getElem(int j) throws IllegalArgumentException{
        if(j<=0){
            throw new IllegalArgumentException();
        }
        return (getFirstElem()*Math.pow(getCoefficient(),j-1));
    }
}

