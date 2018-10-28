package com.company;

public class Main {

    public static void main(String[] args) {
        try {
            if(args.length==0)
            {
                throw new IllegalArgumentException("Type at least one string");
            }
            for (String i : args) {
                System.out.println(foo(i));
            }
        }
        catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public static String foo(String string) {
        StringBuffer result=new StringBuffer("");
        int i=0, length=string.length();
        while(i<length) {
            int k = 1;
            while (i<length-1 && string.charAt(i) == string.charAt(i + 1) ) {
                k++;
                i++;
            }
                result.append(string.charAt(i));
            if(k!=1) {
                result.append(k);
            }
                i++;

        }
        return result.toString();
    }
}
