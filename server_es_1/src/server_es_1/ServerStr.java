/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_es_1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aldro
 */
public class ServerStr {

    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public Socket attendi() {
        try {
            // creo un server sulla porta 6789 
            server = new ServerSocket(6789);
            // rimane in attesa di un client 
            client = server.accept();
            // chiudo il server per inibire altri client
            server.close();
            //associo due oggetti al socket del client per effettuare la scrittura e la lettura 
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server !");
            System.exit(1);
        }
        return client;
    }

    public void comunica() {
        try {
            System.out.println("Clinet connesso!");
            client.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione...");
            System.exit(2);
        }
    }

    public void inviaGriglia(String griglia[][]) {

        try {

            String toClient = "";
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    toClient += griglia[i][j] + ",";
                }
            }

            outVersoClient.writeBytes(toClient);
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerStr.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("eccezione");
        }
    }

    public String[] riceviPezzo() {

        try {
            attendi();
            stringaRicevuta = inDalClient.readLine();
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerStr.class.getName()).log(Level.SEVERE, null, ex);
        }
        String toReturn[] = stringaRicevuta.split(",");
        for (int i = 0; i < 4; i++) {
            System.out.println(toReturn[i]);
        }
        return toReturn;

    }

    public void inviaFull(int full) {
        attendi();
        try {
            outVersoClient.writeBytes(Integer.toString(full));
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerStr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String riceviAttaccoClient(Griglia g) {

        try {
            attendi();
            stringaRicevuta = inDalClient.readLine();

            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerStr.class.getName()).log(Level.SEVERE, null, ex);
        }
        String attacco[] = stringaRicevuta.split(",");
        return g.attacca(Integer.parseInt(attacco[0]), Integer.parseInt(attacco[1]));
    }

    public void inviaRapportoAttaccoClient(String rapporto) {
        try {
            attendi();
            outVersoClient.writeBytes(rapporto);
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerStr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void end(Griglia gClient, Griglia gServer) {
        int vittoria = 0;
        attendi();
        if (gServer.vittoria() == 0 && gClient.vittoria() == 0) {
            vittoria = 0;
        } else if (gServer.vittoria() == 1) {
            vittoria = 1;
        } else if (gClient.vittoria() == 1) {
            vittoria = 2;
        }
        try {
            outVersoClient.writeBytes(Integer.toString(vittoria));
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerStr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void attaccaClient(int riga, int colonna, Griglia gClient) {
        String a = gClient.attacca(riga, colonna);
        System.out.println(a);
    }

    public void pronto() {
        try {

            outVersoClient.writeBytes("pronto");
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerStr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inviaPezziClient(Griglia g) {
        attendi();
        String toClient = "";
        int pezzi[][] = g.getPezzi();
        try {

            for (int i = 0; i < 4; i++) {
                toClient += Integer.toString(pezzi[0][i]) + ",";
                toClient += Integer.toString(pezzi[1][i]) + ",";
            }
            outVersoClient.writeBytes(toClient);
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerStr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
