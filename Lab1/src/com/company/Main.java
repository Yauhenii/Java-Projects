package com.company;

public class Main {

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Wrong arguments");
            }
            float sum = calcSeries(Float.parseFloat(args[0]), Float.parseFloat(args[1]));
            System.out.println(String.format("Sum of series = %s", sum));
        }
        catch(IllegalArgumentException exception) {
            System.out.println(exception.toString());
        }
    }

    public static float calcSeries(float x,float e) {
        float sum=0;
        float prevElem=-2*x/3; //keep previous element
        for(int k=2;e<Math.abs(prevElem);k++)
        {
            sum+=prevElem;
            prevElem*=-x*(k+1)/(3*k); //newElem=prevElem*c(k,x), c(k,x)=-x*(k+1)/(3*k)
        }
        return sum;
    }
}
