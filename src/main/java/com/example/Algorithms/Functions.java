package com.example.Algorithms;

public class Functions {

    //Funkcja służąca do dodawania tablic dwuwymiarowych
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

    //Funkcja służąca do odejmowania tablic dwuwymierowych
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
