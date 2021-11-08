package com.example.Algorithms;

public class Functions {
    public static int[] sumTwoArrays(int[] tab1, int[] tab2){
        int[] i = new int[tab1.length];
        for (int j : tab1){
            i[j] = tab1[j] + tab2[j];
        }
        return i;
    }

    public static int[] subtractTwoArrays(int[] tab1, int[] tab2){
        int[] i = new int[tab1.length];
        for (int j : tab1){
            i[j] = tab1[j] - tab2[j];
        }
        return i;
    }

    public static int[][] sumTwoArrays(int[][] tab1, int[][] tab2){
        int[][] i = new int[tab1.length][];
        for (int j = 0; j < tab1.length; j++){
            i[j] = sumTwoArrays(tab1[j], tab2[j]);
            /*
            i[j] = new int[tab1[j].length];
            for (int k = 0; k < tab1[j].length; k++){
                i[j][k] = tab1[j][k] + tab2[j][k];
            }
             */
        }
        return i;
    }

    public static int[][] subtractTwoArrays(int[][] tab1, int[][] tab2){
        int[][] i = new int[tab1.length][];
        for (int j = 0; j < tab1.length; j++){
            i[j] = subtractTwoArrays(tab1[j], tab2[j]);
        }
        return i;
    }
}
