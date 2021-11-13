/*****************************
 Klasa służąca do analizy problemu kolekcjonerskiej wymiany wzajemnej
 *****************************/
package com.example.Algorithms;

import java.util.*;

import static com.example.ReadWriteFile.WriteFile.writeExchange;
import static com.example.ReadWriteFile.WriteFile.writeValues;
import static com.example.Simulator.Scene.updateLogArea;

public class Algorithm extends Functions{

    //Zmienne opisujące podstawowe informacje
    public int numberOfUsers;
    public int numberOfSeries;
    public int[] numberOfObjects;

    //Zmienne opisujące ceny
    public float[] Bonus;
    public float[][] Prices;

    //Tablice opisujące to, co dany kolekcjoner posiada
    public Proposition[] Propositions;
    public Possession[] Possessions;
    private final float[] Values;

    public boolean[] Flags;
    int idExchange = 0;

    //private final LinkedList<Integer>[] Adjacency;
    private final LinkedList<Integer> Queue = new LinkedList<>();
    private ArrayList<ExchangeProposition> ExchangePropositions;


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

        //Adjacency = new LinkedList[numberOfUsers];
        for (int i = 0; i < numberOfUsers; i++) {
          //  Adjacency[i] = new LinkedList<Integer>();
            Queue.add(i);
        }
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
    //private void addEdge(int vertex1, int vertex2){ Adjacency[vertex1].add(vertex2); }

    /*
    //Funkcja kompletująca graf
    public void completeAdjacency(){
        for (int i = 0; i < numberOfUsers; i++){
            for (int j = 0; j < numberOfUsers; j++){
                if(i != j) addEdge(i, j);
            }
        }
    }

     */


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
             Possessions[i] = new Possession(Have, sumTwoArrays(Have, Propositions[i].Offer));
             System.out.println(i + ": " + Arrays.deepToString(Possessions[i].HaveAll));
        }
    }

    public void MakeExchange(){
            ExchangeProposition eP = null;
            float v1 = 0;
            float v2 = 0;
            int[][] iNeeds, jNeeds;
            float iValues, jValues;
            int i, j;

            for (ExchangeProposition exchangeProposition : ExchangePropositions) {
                i = exchangeProposition.Participant1;
                j = exchangeProposition.Participant2;
                iNeeds = exchangeProposition.Proposition1.clone();
                jNeeds = exchangeProposition.Proposition2.clone();
                iValues = computeValue(sumTwoArrays(subtractTwoArrays(Possessions[i].HaveAll, jNeeds).clone(), iNeeds).clone()) - computeValue(Possessions[i].HaveAll);
                jValues = computeValue(sumTwoArrays(subtractTwoArrays(Possessions[j].HaveAll, iNeeds).clone(), jNeeds).clone()) - computeValue(Possessions[j].HaveAll);
                if ((iValues + jValues) > (v1 + v2)) {
                    eP = new ExchangeProposition(i, iNeeds, j, jNeeds);
                }
            }

            if (eP != null) {
                i = eP.Participant1;
                j = eP.Participant2;
                iNeeds = eP.Proposition1.clone();
                jNeeds = eP.Proposition2.clone();

                writeExchange(idExchange, i, j, iNeeds);
                writeExchange(idExchange, j, i, jNeeds);

                Possessions[i].HaveAll = sumTwoArrays(subtractTwoArrays(Possessions[i].HaveAll, jNeeds).clone(), iNeeds).clone();
                Possessions[j].HaveAll = sumTwoArrays(subtractTwoArrays(Possessions[j].HaveAll, iNeeds).clone(), jNeeds).clone();
                Propositions[i].Need = subtractTwoArrays(Propositions[i].Need, iNeeds).clone();
                Propositions[j].Need = subtractTwoArrays(Propositions[j].Need, jNeeds).clone();
                Propositions[i].Offer = subtractTwoArrays(Propositions[i].Offer, jNeeds).clone();
                Propositions[j].Offer = subtractTwoArrays(Propositions[j].Offer, iNeeds).clone();

                ComputeValues();

                Queue.remove(Integer.valueOf(i));
                Queue.remove(Integer.valueOf(j));
                Queue.add(i);
                Queue.add(j);
            }
    }

    //Funkcja wyznaczająca wartości początkowe wszystkich kolekcji
    public void ComputeValues(){
        for(int i = 0; i < numberOfUsers; i++){
            Values[i] = computeValue(Possessions[i].HaveAll);
        }
        writeValues(idExchange, Values);
        idExchange++;
    }

    //Funkcja rozpoczynająca analizę
    public void StartAnalyzingGraph(int s){
        Flags = new boolean[numberOfUsers];
        ExchangePropositions = new ArrayList<>();
        AnalyzeGraph(s);
    }

    //TODO: Dokończyć, tylko jak? Czy "participants" są potrzebni? Jak wyciągnąć z tego poszczególne dane?
    public void AnalyzeGraph(int i){
        int[][] iNeeds;
        int[][] jNeeds;
        float iValues, jValues;

        Flags[i] = true;

        updateLogArea(String.valueOf(i));
        //participants.add(i);


        //while(participants.size() != 0){
            //i = participants.poll();

        for (int q : Queue) {
            int j = Queue.get(q);
            if (!Flags[j]) {


                Flags[j] = true;
                //ParticipantId = j;
                updateLogArea(String.valueOf(j));


                System.out.println("###################");
                iNeeds = getCommonObjects(Propositions[i].Need, Propositions[j].Offer).clone();
                jNeeds = getCommonObjects(Propositions[j].Need, Propositions[i].Offer).clone();

                System.out.println(i + " oraz " + j);
                System.out.println(Arrays.deepToString(iNeeds));
                System.out.println(Arrays.deepToString(jNeeds));

                iValues = computeValue(sumTwoArrays(subtractTwoArrays(Possessions[i].HaveAll, jNeeds).clone(), iNeeds).clone());
                jValues = computeValue(sumTwoArrays(subtractTwoArrays(Possessions[j].HaveAll, iNeeds).clone(), jNeeds).clone());

                if(iValues > Values[i] && jValues > Values[j]) {
                    System.out.println(iValues + "a" + Values[i]);
                    System.out.println(jValues + "a" + Values[j]);
                    updateLogArea("Znaleziono możliwość wymiany między " + i + " a " + j);
                    System.out.println("Znaleziono możliwość wymiany między " + i + " a " + j);
                    ExchangePropositions.add(new ExchangeProposition(i, iNeeds, j, jNeeds));
                }
            }
        }


        if(!ExchangePropositions.isEmpty()){
            MakeExchange();
            StartAnalyzingGraph(Queue.size() - 1);
        }else{
            if((i - 1) >= 0) StartAnalyzingGraph(i - 1);
        }



        //}

    }



}
