package com.company;

public class Earthquake implements Comparable<Earthquake> {
    private String name;
    private float amplitude;
    Earthquake(String n,float a){
        name=n;
        amplitude=a;
    }
    public String toString(){
        String result=name+" "+ Float.toString(amplitude);
        return result;
    }
    @Override
    public int compareTo(Earthquake earthquake) {
        if(amplitude<earthquake.amplitude){
            return -1;
        }
        else if(amplitude>earthquake.amplitude){
            return 1;
        }
        else{
            return name.compareTo(earthquake.name);
        }
    }
}
