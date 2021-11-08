package com.example.Algorithms;

import java.util.*;

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

    private final LinkedList<Integer>[] Adjacency;

    private int ParticipantId = 0;
    private LinkedList<Integer> ParticipantIDs = new LinkedList<Integer>();
    private Map<Integer, int[]> checkedFlags = new TreeMap<Integer, int[]>();
    private Map<Integer, ExchangePossibility> exchange = new TreeMap<Integer, ExchangePossibility>();

    private int step = 0;

    //Konstruktor klasy
    public Algorithm(int numberOfUsers, int numberOfSeries, int numberOfObjects){
        this.numberOfUsers = numberOfUsers;
        this.numberOfSeries = numberOfSeries;
        this.numberOfObjects = numberOfObjects;

        Prices = new float[numberOfObjects];
        Propositions = new Proposition[numberOfUsers];

        Adjacency = new LinkedList[numberOfUsers];
        for (int i = 0; i < numberOfUsers; i++) Adjacency[i] = new LinkedList<Integer>();
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
            //System.out.println(Arrays.deepToString(Propositions[i].Offer));
            //System.out.println(Arrays.deepToString(Propositions[i].Need));
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


    public boolean checkIfEquals0(int[][] Objects){
        for(int i = 0; i < numberOfSeries; i++){
            for (int j = 0; i < numberOfObjects; i++){
                if (Objects[i][j] != 0) return false;
            }
        }
        return true;
    }

    //Funkcja sprawdzająca jakie przedmioty można wymienić między dwoma uczestnikami wymiany
    public int[][] getCommonObjects(int[][] Need, int[][] Offer){
        int[][] commonObjects = new int[numberOfSeries][numberOfObjects];
        for(int i = 0; i < numberOfSeries; i++){
            for(int j = 0; j < numberOfObjects; j++){
                if(Need[i][j] >= Offer[i][j]) {
                    commonObjects[i][j] = Offer[i][j];
                }else if(Need[i][j] <= Offer[i][j]) {
                    commonObjects[i][j] = Need[i][j];
                }
            }
        }
        return commonObjects;
    }

    //Funkcja wyznaczająca wartość kolekcji
    public float computeValue(int[][] Objects, int seriesCompleted){
        float value = 0;
        for (int i = 0; i < numberOfSeries; i++){
            float f = 0;
            for(int j = 0; j < numberOfObjects; j++){
                f += Prices[j] * Objects[i][j];
            }
            f += seriesCompleted * Bonus;
            value += f;
        }
        return value;
    }

    //Wartość wyznaczająca ilość skompletowanych serii
    public int howManySeriesCompleted(int[] Objects){
        int seriesCompleted = 0;
        for (int i = 0; i < numberOfObjects; i++){
            if (Objects[i] > seriesCompleted) seriesCompleted = Objects[i];
        }
        return seriesCompleted;
    }

    //Funkcja rozpoczynająca analizę
    public void StartAnalyzingGraph(){
        ParticipantIDs.add(ParticipantId);

        Flags = new int[numberOfUsers];

        AnalyzeGraph(ParticipantId);
    }

    //TODO: Dokończyć, tylko jak? Czy "participants" są potrzebni? Jak wyciągnąć z tego poszczególne dane?
    public void AnalyzeGraph(int i){
        //LinkedList<Integer> participants = new LinkedList<Integer>();
        int[][] iNeeds;
        int[][] jNeeds;

        Flags[i] = Checked;
        checkedFlags.put(step, Flags.clone());
        //participants.add(i);


        //while(participants.size() != 0){
            //i = participants.poll();

        for (int j : Adjacency[i]) {
            if (Flags[j] == notChecked) {
                //Flags[j] = Checked;
                ParticipantId = j;
                step++;
                iNeeds = getCommonObjects(Propositions[i].Need, Propositions[j].Offer).clone();
                jNeeds = getCommonObjects(Propositions[j].Need, Propositions[i].Offer).clone();

                System.out.println(Arrays.deepToString(iNeeds));
                System.out.println(Arrays.deepToString(jNeeds));
                System.out.println("###################");

                ParticipantIDs.add(ParticipantId);
                //participants.add(j);


                AnalyzeGraph(j);

            }
        }

        //}

    }



}
