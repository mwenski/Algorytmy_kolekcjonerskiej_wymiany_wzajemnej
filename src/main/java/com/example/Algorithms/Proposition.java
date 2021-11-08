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

class ExchangePossibility{
    public int GiverId;
    public int GetterId;
    public Proposition GivenObjects;

    public ExchangePossibility(int GiverId, int GetterId, Proposition GivenObjects){
        this.GiverId = GiverId;
        this.GetterId = GetterId;
        this.GivenObjects = GivenObjects;
    }
}
