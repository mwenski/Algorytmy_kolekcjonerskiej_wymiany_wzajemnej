/*****************************
 Klasa zawierająca informacje na temat posiadanych
 przez kolekcjonera przedmiotów
 *****************************/

package com.example.Algorithms;

public class Possession {
    public int[][] HaveAll;
    public int[][] Have;

    public Possession(int[][] Have, int[][] HaveAll){
        this.Have = Have.clone();
        this.HaveAll = HaveAll.clone();
    }
}
