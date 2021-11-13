package com.example.Algorithms;

public class ExchangeProposition {
    int Participant1, Participant2;
    int[][] Proposition1, Proposition2;

    ExchangeProposition(int Participant1, int[][] Proposition1, int Participant2, int[][] Proposition2){
        this.Participant1 = Participant1;
        this.Proposition1 = Proposition1.clone();

        this.Participant2 = Participant2;
        this.Proposition2 = Proposition2.clone();
    }
}
