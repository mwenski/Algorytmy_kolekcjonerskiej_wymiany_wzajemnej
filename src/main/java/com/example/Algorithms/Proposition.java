/*****************************
 Klasa zawierająca informacje na temat wyszukiwanych
 i oferowanych przedmiotów kolekcjonera
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


