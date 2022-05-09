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
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClientStr cliente = new ClientStr();
        Scanner sc = new Scanner(System.in);
        int full = 0;
        int i = 0;
        int end = 0;
        int pronto = 0;

        cliente.connetti();
        cliente.comunica();

        System.out.println("Fase 1: inserisci tutti i pezzi sulla griglia.");
        while (full == 0) {
        

            cliente.stampaGriglia();
            cliente.stampaPezziRimanenti();
            System.out.println("");
            System.out.println("Pezzo numero " + (i++));
            System.out.println("Direzione del pezzo: ");
            String direzione = sc.next();
            System.out.println("Numero riga: ");
            int riga = sc.nextInt();
            System.out.println("Numero colonna: ");
            int colonna = sc.nextInt();
            System.out.println("Lunghezza pezzo: ");
            int lunghezzaPezzo = sc.nextInt();
            cliente.inviaPezzo(riga, colonna, lunghezzaPezzo, direzione);
            full = cliente.riceviFull();
        }
        
        System.out.println("--Attendi che il server sia pronto--");
        
        cliente.riceviPronto();
        
        

        System.out.println("Fase 2: attacca il Server.");
        while (end == 0) {
            System.out.println("--Ora puoi attaccare--");
            System.out.println("Inserisci la riga da attaccare");
            int riga = sc.nextInt();
            System.out.println("Inserisci la colonna da attaccare");
            int colonna = sc.nextInt();
            cliente.attaccaServer(riga, colonna);
            cliente.riceviRapportoAttaccoServer();
            System.out.println("--Attendi l'attacco del Server--");
            cliente.stampaGriglia();System.out.println("");
            end = cliente.riceviEnd();
            
            

            if (end == 1) {
                System.out.println("Bravo client hai vinto!");
                break;
            } else if (end == 2) {
                System.out.println("Hai perso, ha vinto il Server!");
                break;
            }

        }

    }

}
