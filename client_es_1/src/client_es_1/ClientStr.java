/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_es_1;

/**
 *
 * @author aldro
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientStr {

    String nomeServer = "localhost";                  // indirizzo server locale  
    int portaServer = 6789;                        // porta x servizio data e ora
    Socket miosocket;
    BufferedReader tastiera;                         // buffer per l'input da tastiera
    String stringaUtente = "";                            // stringa inserita da utente
    String stringaRicevutaDalServer = "";                 // stringa ricevuta dal server
    DataOutputStream outVersoServer;                 // stream di output
    BufferedReader inDalServer;                      // stream di input 

    public void comunica() {
        try {
            System.out.println("Server connesso!");
            miosocket.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server!");
            System.exit(1);
        }
    }

    public Socket connetti() {
        try {
            // per l'input da tastiera
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            // creo un socket  
            miosocket = new Socket(nomeServer, portaServer);
            // miosocket = new Socket(InetAddress.getLocalHost(), 6789);
            // associo due oggetti al socket per effettuare la scrittura e la lettura 
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return miosocket;
    }

    public void stampaGriglia() {
        connetti();
        try {

            String griglia[] = inDalServer.readLine().split(",");
            for (int i = 0; i < griglia.length; i++) {
                if (i % 10 == 0) {
                    System.out.println("");
                }
                System.out.print(griglia[i]);
            }

            miosocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server!");
            System.exit(1);
        }

    }

    public void inviaPezzo(int riga, int colonna, int lunghezzaPezzo, String direzione) {
        try {
            connetti();
            String toServer = "";
            toServer = Integer.toString(riga) + "," + Integer.toString(colonna) + "," + Integer.toString(lunghezzaPezzo) + "," + direzione;
            outVersoServer.writeBytes(toServer);
            miosocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientStr.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int riceviFull() {
        int toReturn = 0;
        try {
            connetti();

            toReturn = Integer.parseInt(inDalServer.readLine());
            miosocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientStr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn;
    }

    public void attaccaServer(int riga, int colonna) {
        try {
            connetti();
            String toServer = Integer.toString(riga) + "," + Integer.toString(colonna);
            outVersoServer.writeBytes(toServer);
            miosocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientStr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void riceviRapportoAttaccoServer() {
        try {
            connetti();
            stringaRicevutaDalServer = inDalServer.readLine();
            miosocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientStr.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(stringaRicevutaDalServer);

    }

    public int riceviEnd() {
        int toReturn = 0;
        connetti();
        try {
            
            
            toReturn = Integer.parseInt(inDalServer.readLine());
            miosocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientStr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn;
    }

    public int riceviPronto() {
        int toReturn = 0;
        try {
            connetti();

            stringaRicevutaDalServer = inDalServer.readLine();

            miosocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientStr.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (stringaRicevutaDalServer == "pronto") {
            toReturn = 1;
        }
        return toReturn;
    }

    public void stampaPezziRimanenti() {
        try {
            connetti();
            String pezzi[] = inDalServer.readLine().split(",");
            System.out.println("");
            System.out.println("Numero x Grandezza");
            for (int i = 0; i < pezzi.length; i++) {
                if (i % 2 == 0 && i != 0) {
                    System.out.println("");
                }
                System.out.print(pezzi[i] + " ");
            }
            miosocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientStr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
