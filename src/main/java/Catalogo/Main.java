package Catalogo;



import Eccezioni.ElementoNonTrovato;
import ents.*;

import java.util.Scanner;

public class Main {
    private static final Archivio archivio = new Archivio();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean esegui = true;

        while (esegui) {
            stampaMenu();
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> aggiungiLibro();
                case "2" -> aggiungiRivista();
                case "3" -> cercaPerIsbn();
                case "4" -> rimuoviPerIsbn();
                case "5" -> cercaPerAnno();
                case "6" -> cercaPerAutore();
                case "7" -> aggiornaElemento();
                case "8" -> archivio.statistiche(); // separato 8 e 9  xche piu pulito da vede (?)
                case "9" -> mostraTutti();
                case "0" -> {
                    System.out.println("Uscita dal programma.");
                    esegui = false;
                }
                default -> System.out.println("Scelta non valida.");
            }
        }

        scanner.close();
    }

    private static void stampaMenu() {
        System.out.println("\n===== MENU CATALOGO BIBLIOTECARIO =====");
        System.out.println("1. Aggiungi libro");
        System.out.println("2. Aggiungi rivista");
        System.out.println("3. Cerca per ISBN");
        System.out.println("4. Rimuovi per ISBN");
        System.out.println("5. Cerca per anno di pubblicazione");
        System.out.println("6. Cerca libri per autore");
        System.out.println("7. Aggiorna elemento");
        System.out.println("8. Statistiche");
        System.out.println("9. Mostra tutti gli elementi");
        System.out.println("0. Esci");
        System.out.print("Scelta: ");
    }

    private static void aggiungiLibro() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("Anno pubblicazione: ");
        int anno = Integer.parseInt(scanner.nextLine());
        System.out.print("Numero pagine: ");
        int pagine = Integer.parseInt(scanner.nextLine());
        System.out.print("Autore: ");
        String autore = scanner.nextLine();
        System.out.print("Genere: ");
        String genere = scanner.nextLine();                        //mettere msg output "libro aggiunto" (?)

        archivio.aggiungiElemento(new Libro(isbn, titolo, anno, pagine, autore, genere));
    }

    private static void aggiungiRivista() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("Anno pubblicazione: ");
        int anno = Integer.parseInt(scanner.nextLine());
        System.out.print("Numero pagine: ");
        int pagine = Integer.parseInt(scanner.nextLine());
        System.out.print("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
        Periodicita periodicita = Periodicita.valueOf(scanner.nextLine().toUpperCase());

        archivio.aggiungiElemento(new Rivista(isbn, titolo, anno, pagine, periodicita));
    }

    private static void cercaPerIsbn() {
        System.out.print("Inserisci ISBN da cercare: ");
        String isbn = scanner.nextLine();
        try {
            ElementoCatalogo elemento = archivio.cercaPerIsbn(isbn);
            System.out.println(elemento);
        } catch (ElementoNonTrovato e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rimuoviPerIsbn() {
        System.out.print("ISBN da rimuovere: ");
        String isbn = scanner.nextLine();
        archivio.rimuoviPerIsbn(isbn);
        System.out.println("Elemento rimosso (se esisteva).");
    }

    private static void cercaPerAnno() {
        System.out.print("Anno di pubblicazione: ");
        int anno = Integer.parseInt(scanner.nextLine());
        archivio.cercaPerAnno(anno).forEach(System.out::println);
    }

    private static void cercaPerAutore() {
        System.out.print("Nome autore: ");
        String autore = scanner.nextLine();
        archivio.cercaPerAutore(autore).forEach(System.out::println);
    }

    private static void aggiornaElemento() {
        System.out.print("ISBN dell’elemento da aggiornare: ");
        String isbn = scanner.nextLine();

        try {
            ElementoCatalogo esistente = archivio.cercaPerIsbn(isbn);
            System.out.println("Elemento attuale: " + esistente);
            System.out.println("Inserisci nuovi dati (sovrascriverà quello esistente):");

            if (esistente instanceof Libro) {
                aggiungiLibro();  // aggiunge con stesso ISBN
            } else {
                aggiungiRivista();  // stesso
            }

        } catch (ElementoNonTrovato e) {
            System.out.println(e.getMessage());
        }
    }

    private static void mostraTutti() {
        archivio.getTutti().forEach(System.out::println);
    }
}
