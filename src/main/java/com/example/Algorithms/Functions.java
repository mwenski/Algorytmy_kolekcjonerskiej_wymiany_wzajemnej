package com.example.Algorithms;

public class Functions {
    public static int[] sumTwoArrays(int[] tab1, int[] tab2){
        int[] i = new int[tab1.length];
        for (int j = 0; j < tab1.length; j++){
            i[j] = tab1[j] + tab2[j];
        }
        return i;
    }

    public static int[] subtractTwoArrays(int[] tab1, int[] tab2){
        int[] i = new int[tab1.length];
        for (int j = 0; j < tab1.length; j++){
            i[j] = tab1[j] - tab2[j];
        }
        return i;
    }

    public static int[][] sumTwoArrays(int[][] tab1, int[][] tab2){
        int[][] i = new int[tab1.length][];
        for (int j = 0; j < tab1.length; j++){
            i[j] = new int[tab1[j].length];
            for (int k = 0; k < tab1[j].length; k++){
                i[j][k] = tab1[j][k] + tab2[j][k];
            }
        }
        return i;
    }

    public static int[][] subtractTwoArrays(int[][] tab1, int[][] tab2){
        int[][] i = new int[tab1.length][];
        for (int j = 0; j < tab1.length; j++){
            i[j] = new int[tab1[j].length];
            for (int k = 0; k < tab1[j].length; k++){
                i[j][k] = tab1[j][k] - tab2[j][k];
            }
        }
        return i;
    }

    public static void increaseInArray(int[] tab1, int[] tab2, int i){
        tab2[i]++;
        if (tab2[i] > tab1[i]){
            tab2[i] = 0;
            increaseInArray(tab1, tab2, i+1);
        }
    }

    //Wartość wyznaczająca największą liczbę w tablicy
    public int getMaximumValue(int[] tab){
        int max = 0;
        for (int i = 0; i < tab.length; i++){
            if (tab[i] > max) max = tab[i];
        }
        return max;
    }

    //Wartość wyznaczająca największą liczbę w tablicy
    public int getMinimumValue(int[] tab){
        int min = tab[0];
        for (int i = 0; i < tab.length; i++){
            if (tab[i] < min) min = tab[i];
        }
        return min;
    }


}
