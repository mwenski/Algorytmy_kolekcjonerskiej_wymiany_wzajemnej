/*****************************
 Klasa służąca do analizy problemu kolekcjonerskiej wymiany wzajemnej
 *****************************/
package com.example.Algorithms;

import java.util.*;

import static com.example.ReadWriteFile.WriteFile.writeValues;
import static com.example.Simulator.Scene.updateLogArea;

public class Algorithm extends Functions{

    public int numberOfUsers;
    public int numberOfSeries;
    public int[] numberOfObjects;

    public float[] Bonus;
    public float[][] Prices;

    public Proposition[] Propositions;
    public Possession[] Possessions;
    private float[] Values;

    public boolean[] Flags;
    int id = 0;

    private final LinkedList<Integer>[] Adjacency;

    private int ParticipantId = 0;

    //Konstruktor klasy
    public Algorithm(int numberOfUsers, int numberOfSeries, int[] numberOfObjects){
        this.numberOfUsers = numberOfUsers;
        this.numberOfSeries = numberOfSeries;
        this.numberOfObjects = numberOfObjects.clone();

        Prices = new float[numberOfSeries][];
        Bonus = new float[numberOfSeries];
        Propositions = new Proposition[numberOfUsers];
        Possessions = new Possession[numberOfUsers];
        Values = new float[numberOfUsers];

        Adjacency = new LinkedList[numberOfUsers];
        for (int i = 0; i < numberOfUsers; i++) Adjacency[i] = new LinkedList<Integer>();
    }

    //Setter przypisujący ceny
    public void setPrices(float[][] Prices, float[] Bonus){
        this.Prices = Prices.clone();
        this.Bonus = Bonus.clone();
    }

    //Setter przypisujący oferty i potrzeby
    public void setPropositions(Proposition[] Propositions){
        this.Propositions = Propositions.clone();
    }

    //Funkcja dodająca krawędzie w grafie
    private void addEdge(int vertex1, int vertex2){ Adjacency[vertex1].add(vertex2); }

    //Funkcja kompletująca graf
    public void completeAdjacency(){
        for (int i = 0; i < numberOfUsers; i++){
            for (int j = 0; j < numberOfUsers; j++){
                if(i != j) addEdge(i, j);
            }
        }
    }


    //Funkcja sprawdzająca jakie przedmioty można wymienić między dwoma uczestnikami wymiany
    private int[][] getCommonObjects(int[][] Need, int[][] Offer){
        int[][] commonObjects = new int[numberOfSeries][];
        for(int i = 0; i < numberOfSeries; i++){
            commonObjects[i] = new int[numberOfObjects[i]];
            for(int j = 0; j < numberOfObjects[i]; j++){
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
    private float computeValue(int[][] Objects){
        float value = 0;
        for (int i = 0; i < numberOfSeries; i++){
            float f = 0;
            for(int j = 0; j < numberOfObjects[i]; j++){
                f += Prices[i][j] * Objects[i][j];
            }
            f += getMinimumValue(Objects[i]) * Bonus[i];
            value += f;
        }
        return value;
    }

    public void computeWhatDoesTheyHave(){
        int toComplete;
        for (int i = 0; i < numberOfUsers; i++){
             int[][] Have = new int[numberOfSeries][];
             for (int j = 0; j < numberOfSeries; j++) {
                 Have[j] = new int[numberOfObjects[j]];
                 toComplete = getMaximumValue(Propositions[i].Need[j]);
                 for(int k = 0; k < numberOfObjects[j]; k++) Have[j][k] = toComplete - Propositions[i].Need[j][k];
             }
             //Possessions[i].setHave(Have);
             //Possessions[i].setHaveAll();
            Possessions[i] = new Possession(Have, sumTwoArrays(Have, Propositions[i].Offer));
             System.out.println(i + ": " + Arrays.deepToString(Have));
        }
    }

    //Funkcja rozpoczynająca analizę
    public void StartAnalyzingGraph(){
        Flags = new boolean[numberOfUsers];
        for(int i = 0; i < numberOfUsers; i++){
            Values[i] = computeValue(Possessions[i].HaveAll);
        }
        writeValues(id, Values);

        AnalyzeGraph(ParticipantId);
    }

    //TODO: Dokończyć, tylko jak? Czy "participants" są potrzebni? Jak wyciągnąć z tego poszczególne dane?
    public void AnalyzeGraph(int i){

        //LinkedList<Integer> participants = new LinkedList<Integer>();
        int[][] iNeeds;
        int[][] jNeeds;

        Flags[i] = true;

        updateLogArea(String.valueOf(i));
        //participants.add(i);


        //while(participants.size() != 0){
            //i = participants.poll();

        for (int j : Adjacency[i]) {
            if (!Flags[j]) {
                //Flags[j] = Checked;
                ParticipantId = j;
                System.out.println("###################");
                iNeeds = getCommonObjects(Propositions[i].Need, Propositions[j].Offer).clone();
                jNeeds = getCommonObjects(Propositions[j].Need, Propositions[i].Offer).clone();

                System.out.println(Arrays.deepToString(iNeeds));
                System.out.println(Arrays.deepToString(jNeeds));

                //participants.add(j);


                AnalyzeGraph(j);

            }
        }

        //}

    }



}
