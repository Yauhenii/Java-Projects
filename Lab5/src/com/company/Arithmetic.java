package com.company;

public class Arithmetic extends Progression {
    public Arithmetic(double a, double d){
        setfirstElem(a);
       setCoefficient(d);
    }
    @Override
    public double getElem(int j) throws IllegalArgumentException{
        if(j<=0){
            throw new IllegalArgumentException();
        }
        return (getFirstElem()+getCoefficient()*(j-1));
    }
}

