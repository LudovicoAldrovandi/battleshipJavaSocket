/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_es_1;

import java.util.Scanner;

/**
 *
 * @author aldro
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServerStr servente = new ServerStr();
        Griglia gClient = new Griglia();
        Griglia gServer = new Griglia();
        String appoggio[][];
        String s[] = new String[4];
        int fullClient = 0;
        int fullServer = 0;
        int p = 0;
        int end = 0;
        servente.attendi();
        servente.comunica();
        System.out.println("Stringa inviata e ricevuta");

        gClient.riempiGriglia();

        while (fullClient == 0) {
        
            servente.attendi();
            servente.inviaGriglia(gClient.getGriglia());
            servente.inviaPezziClient(gClient);

            s = servente.riceviPezzo();

            gClient.aggiungiPezzo(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]), s[3]);
            fullClient = gClient.isFull();
            servente.inviaFull(fullClient);
        }
        servente.attendi();
        gServer.riempiGriglia();
        
        while (fullServer == 0) {
        

            int pezzi[][] = gServer.getPezzi();
            gServer.stampaGriglia();
            System.out.println("Numero x Grandezza");
            for (int j = 0; j < 4; j++) {
                System.out.print(pezzi[0][j] + " x ");
                System.out.println(pezzi[1][j]);
            }

            System.out.println("Pezzo numero " + (p++));
            System.out.println("Direzione del pezzo: ");
            String direzione = sc.next();
            System.out.println("Numero riga: ");
            int riga = sc.nextInt();
            System.out.println("Numero colonna: ");
            int colonna = sc.nextInt();
            System.out.println("Lunghezza pezzo: ");
            int lunghezzaPezzo = sc.nextInt();

            gServer.aggiungiPezzo(lunghezzaPezzo, colonna, riga, direzione);
            fullServer = gServer.isFull();
        }
        servente.pronto();

        System.out.println("Fase 2: attacca il Client.");

        while (gServer.vittoria() == 0 && gClient.vittoria() == 0) {
            System.out.println("--Attendi l'attacco del Client--");
            servente.inviaRapportoAttaccoClient(servente.riceviAttaccoClient(gServer));
            gServer.stampaGriglia();

            System.out.println("--Ora puoi attaccare--");
            servente.attendi();
            System.out.println("Inserisci la riga da attaccare");

            int riga = sc.nextInt();

            System.out.println("Inserisci la colonna da attaccare");

            int colonna = sc.nextInt();

            servente.attaccaClient(riga, colonna, gClient);
            appoggio = gClient.getGriglia();
            servente.inviaGriglia(appoggio);
            
            servente.end(gClient, gServer);

        }
        if (gServer.vittoria() == 1) {
            System.out.println("Hai perso, ha vinto il Client!");

        } else if (gClient.vittoria() == 1) {
            System.out.println("Bravo Server hai vinto!");

        }

    }

}
