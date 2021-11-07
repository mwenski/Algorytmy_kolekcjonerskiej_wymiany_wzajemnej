package com.example.Algorithms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Algorithm {
    public int numberOfUsers;
    public int numberOfSeries;
    public int numberOfObjects;

    public float Bonus;
    public float[] Prices;

    public Proposition[] Propositions;

    public int[] Flags;
    private final int notChecked = 0;
    private final int Checked = 1;
    private final int Blocked = 2;

    private LinkedList<Integer> Adjacency[];

    //Konstruktor klasu
    public Algorithm(int numberOfUsers, int numberOfSeries, int numberOfObjects){
        this.numberOfUsers = numberOfUsers;
        this.numberOfSeries = numberOfSeries;
        this.numberOfObjects = numberOfObjects;

        Prices = new float[numberOfObjects];
        Propositions = new Proposition[numberOfUsers];

        Adjacency = new LinkedList[numberOfUsers];
        Flags = new int[numberOfUsers];
        for (int i = 0; i < numberOfUsers; i++) Adjacency[i] = new LinkedList();
    }

    //Setter przypisujący ceny
    public void setPrices(float[] Prices, float Bonus){
        this.Prices = Prices.clone();
        this.Bonus = Bonus;
    }

    //Setter przypisujący oferty i potrzeby
    public void setPropositions(Proposition[] Propositions){
        this.Propositions = Propositions.clone();
        for(int i = 0; i < numberOfUsers; i++){
            System.out.println(Arrays.deepToString(Propositions[i].Offer));
            System.out.println(Arrays.deepToString(Propositions[i].Need));
        }
    }

    //Funkcja dodająca krawędzie w grafie
    public void addEdge(int vertex1, int vertex2){ Adjacency[vertex1].add(vertex2); }

    //Funkcja kompletująca graf
    public void completeAdjacency(){
        for (int i = 0; i < numberOfUsers; i++){
            for (int j = 0; j < numberOfUsers; j++){
                if(i != j) addEdge(i, j);
            }
        }
    }

    //Funkcja wyznaczająca wartość kolekcji
    public float computeValue(int[][] Objects, boolean isBonus){
        float value = 0;
        for (int i = 0; i < numberOfSeries; i++){
            float f = 0;
            int fullCollections = 0;
            for(int j = 0; j < numberOfObjects; j++){
                f += Prices[j] * Objects[i][j];
                if(isBonus && (Objects[i][j] > fullCollections)) fullCollections = Objects[i][j];
            }
            f += fullCollections * Bonus;
            value += f;
        }
        return value;
    }

    //TODO: Dokończyć, tylko jak? Czy "participants" są potrzebni? Jak wyciągnąć z tego poszczególne dane?
    public void AnalyzeGraph(int i){
        int s = 0;

        //LinkedList<Integer> participants = new LinkedList<Integer>();

        Flags[i] = Checked;
        //participants.add(i);

        //while(participants.size() != 0){
            //i = participants.poll();

            Iterator<Integer> iterator = Adjacency[i].listIterator();
            while (iterator.hasNext()){
                int j = iterator.next();
                if(Flags[j] == notChecked){
                    Flags[j] = Checked;
                    //participants.add(j);

                    AnalyzeGraph(j);

                }
            }

        //}

    }



}
