/*****************************
 Klasa zawierająca informacje na temat oferty i potrzeb kolekcjonera
 *****************************/
package com.example.Algorithms;

public class Proposition {
    public int[][] Offer;
    public int[][] Need;

    public Proposition(int[][] Offer, int[][] Need){
        this.Offer = Offer.clone();
        this.Need = Need.clone();
    }
}


