/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_es_1;

import java.util.Scanner;

/**
 *
 * @author aldro
 */
public class Griglia {

    String griglia[][] = new String[10][10];
    int pezzi[][] = {{2, 1, 1, 1},
    {2, 3, 4, 6}};

    public void riempiGriglia() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                griglia[i][j] = "x ";
            }
        }
    }

    public String[][] getGriglia() {
        return griglia;
    }

    public int[][] getPezzi() {
        return pezzi;
    }

    public void stampaGriglia() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(griglia[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public boolean presenzaPezzo(int lunghezzaPezzo) {
        boolean toReturn = false;

        for (int i = 0; i < 4; i++) {
            if (lunghezzaPezzo == pezzi[1][i] && pezzi[0][i] > 0) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    public boolean piazzabile(String direzione, int lunghezzaPezzo, int riga, int colonna) {
        boolean toReturn = false;
        if (riga >= 0 && riga <= 10 && colonna >= 0 && colonna <= 10) {
            switch (direzione) {
                case "w":
                    if (riga - lunghezzaPezzo <= 10) {
                        toReturn = true;
                    }
                    break;

                case "d":
                    if (colonna + lunghezzaPezzo <= 10) {
                        toReturn = true;
                    }
                    break;

                case "s":
                    if (riga + lunghezzaPezzo >= 0) {
                        toReturn = true;
                    }
                    break;

                case "a":
                    if (colonna - lunghezzaPezzo >= 0) {
                        toReturn = true;
                    }
                    break;
            }
        }
        return toReturn;
    }
    
    public void rimuoviPezzo(int lunghezzaPezzo){
        for (int i = 0; i < 4; i++) {
            if(pezzi[1][i] == lunghezzaPezzo){
                pezzi[0][i]--;
                break;
            }
        }
    }

    public void aggiungiPezzo(int lunghezzaPezzo, int colonna, int riga, String direzione) {
        if (presenzaPezzo(lunghezzaPezzo) && piazzabile(direzione, lunghezzaPezzo, riga, colonna)) {
            rimuoviPezzo(lunghezzaPezzo);
            switch (direzione) {
                case "w":
                    for (int i = 0; i < lunghezzaPezzo; i++) {
                        griglia[riga - i][colonna] = "O ";
                    }
                    break;
                case "d":
                    for (int i = 0; i < lunghezzaPezzo; i++) {
                        griglia[riga][colonna + i] = "O ";
                    }
                    break;
                case "s":
                    for (int i = 0; i < lunghezzaPezzo; i++) {
                        griglia[riga + i][colonna] = "O ";
                    }
                    break;
                case "a":
                    for (int i = 0; i < lunghezzaPezzo; i++) {
                        griglia[riga][colonna - i] = "O ";
                    }
                    break;
            }

        } else {
            System.out.println("Spiacente non puoi piazzare il pezzo qui!");
        }
    }
    
    public void attacca (int riga, int colonna){
        if(griglia[riga][colonna] == "O "){
            System.out.println("colpito");
            griglia[riga][colonna] = "# ";
        }else{
            System.out.println("Acqua");
        }
    }
    
    public boolean vittoria (){
        boolean toReturn = true;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(griglia[i][j] == "O "){
                    toReturn = false;
                }
            }
        }
        return toReturn;
    }
}
