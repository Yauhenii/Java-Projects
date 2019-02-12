package com.company;

import java.util.Comparator;

public class MyComparator1 implements Comparator<Cinema> {

    public int compare(Cinema e1, Cinema e2) {
        return e1.getName().compareTo(e2.getName());
    }
}