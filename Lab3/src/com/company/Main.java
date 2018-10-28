package com.company;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            double matrix[][] = readMatrix("matrixFile.txt");
            int p=10;
            matrix = powMatrix(matrix,p);
            int size=matrix[0].length;
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    System.out.print(matrix[i][j]);
                    System.out.print(" ");
                }
                System.out.println();
            }

        }
        catch(FileNotFoundException exception){
            //exception.printStackTrace();
            System.out.println("No such file found");
        }
        catch(NoSuchElementException exception){
            //exception.printStackTrace();
            System.out.println("Quantity of elements error");
        }
        catch(ArrayIndexOutOfBoundsException exception){
            //exception.printStackTrace();
            System.out.println("Size of matrix must be more than 1");
        }

    }


    public static double[][] readMatrix(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int size = scanner.nextInt();
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = scanner.nextFloat();
            }
        }
        return matrix;
    }
    public static double[][] powMatrix(double matrix[][],int power) {
        int size=matrix[0].length;
        double resMatrix[][]=new double[size][size];
        for(int i=0;i<size;i++) {
            for(int j=0;j<size;j++){
                resMatrix[i][j]=matrix[i][j];
            }
        }
        for(int c=1; c<power; c++){
            double temp[][]=new double [size][size];
            for(int i=0; i<size; i++){
                for(int j=0;j<size;j++){
                    temp[i][j]=0;
                    for(int k=0;k<size; k++){
                        temp[i][j]+=resMatrix[i][k]*matrix[k][j];
                    }
                }
            }
            for(int i=0;i<size;i++) {
                for(int j=0;j<size;j++){
                    resMatrix[i][j]=temp[i][j];
                }
            }
        }
        return resMatrix;
    }
}
