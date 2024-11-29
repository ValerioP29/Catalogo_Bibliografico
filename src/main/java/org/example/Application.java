package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Archivio archivio = new Archivio();
        Scanner scanner = new Scanner(System.in);
        boolean esci = false;

        while (!esci) {
            System.out.println("""
                    Scegli un'opzione:
                    1. Aggiungi elemento
                    2. Ricerca per ISBN
                    3. Rimuovi elemento
                    4. Ricerca per anno pubblicazione
                    5. Ricerca per autore
                    6. Aggiorna elemento
                    7. Statistiche catalogo
                    8. Esci
                    """);

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1 -> {
                    System.out.println("Inserisci il tipo (libro o rivista): ");
                    String tipo;
                    while (true) {
                        tipo = scanner.nextLine();
                        if (tipo.equalsIgnoreCase("libro") || tipo.equalsIgnoreCase("rivista")) {
                            break;
                        } else {
                            System.out.println("Errore: inserisci libro o rivista.");
                        }
                    }
                    System.out.println("ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.println("Titolo:");
                    String titolo = scanner.nextLine();
                    System.out.println("Anno pubblicazione:");
                    int anno;
                    while (true) {
                        try {
                            anno = scanner.nextInt();
                            if (anno > 0) {
                                break;
                            } else {
                                System.out.println("Inserisci  un anno valido.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Inserisci un numero valido");
                            scanner.nextLine();
                        }
                    }
                    System.out.println("Numero pagine:");
                    int pagine;
                    while (true) {
                        try {
                            pagine = scanner.nextInt();
                            if (pagine > 0) {
                                break;
                            } else {
                                System.out.println("inserisci un numero di pagine valido.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Errore: inserisci numeri validi.");
                            scanner.nextLine();
                        }
                    }


                    if (tipo.equalsIgnoreCase("libro")) {
                        System.out.println("Autore: ");
                        String autore = scanner.nextLine();
                        System.out.println("Genere: ");
                        String genere = scanner.nextLine();
                        archivio.aggiungiElemento(new Libro(isbn, titolo, anno, pagine, autore, genere));

                    } else if
                    (tipo.equalsIgnoreCase("rivista")) {

                        Periodicita periodicitaEnum = null;
                        scanner.nextLine();
                        while (true) {
                            try {
                                System.out.println("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                                String periodicita = scanner.nextLine().toUpperCase();
                                periodicitaEnum = Periodicita.valueOf(periodicita);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Errore: inserisci una periodicità valida (SETTIMANALE, MENSILE, SEMESTRALE).");
                            }
                        }
                        archivio.aggiungiElemento(new Rivista(isbn, titolo, anno, pagine, periodicitaEnum));
                    }

                }
                case 2 -> {
                    System.out.println("Inserisci ISBN: ");
                    String isbn = scanner.nextLine();
                    try {
                        System.out.println(archivio.ricercaPerISBN(isbn));
                    } catch (ISBNNonTrovata e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("Inserisci ISBN: ");
                    String isbn = scanner.nextLine();
                    archivio.rimuoviElemento(isbn);
                }
                case 4 -> {
                    System.out.println("Inserisci anno di pubblicazione: ");
                    int anno = scanner.nextInt();
                    scanner.nextLine();
                    archivio.ricercaPerAnno(anno);
                }
                case 5 -> {
                    System.out.println("Inserisci autore: ");
                    String autore = scanner.nextLine();
                    archivio.ricercaPerAutore(autore);

                }
                case 6 -> {
                    System.out.println("Inserisci ISBN dell'elemento da aggiornare:");
                    String isbn = scanner.nextLine();

                    if (!archivio.catalogo.containsKey(isbn)) {
                        System.out.println("Nessun elemento trovato con ISBN: " + isbn);
                    } else {
                        System.out.println("Nuovo titolo:");
                        String titolo = scanner.nextLine();
                        System.out.println("Nuovo anno pubblicazione:");
                        int anno = scanner.nextInt();
                        System.out.println("Nuovo numero pagine:");
                        int pagine = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("È un libro o una rivista?");
                        String tipo = scanner.nextLine();

                        if (tipo.equalsIgnoreCase("libro")) {
                            System.out.println("Nuovo autore:");
                            String autore = scanner.nextLine();
                            System.out.println("Nuovo genere:");
                            String genere = scanner.nextLine();
                            archivio.aggiornaElemento(isbn, new Libro(isbn, titolo, anno, pagine, autore, genere));
                        } else if (tipo.equalsIgnoreCase("rivista")) {
                            System.out.println("Nuova periodicità (SETTIMANALE, MENSILE, SEMESTRALE):");
                            String periodicita = scanner.nextLine().toUpperCase();
                            archivio.aggiornaElemento(isbn, new Rivista(isbn, titolo, anno, pagine, Periodicita.valueOf(periodicita)));
                        }
                        try {
                            System.out.println("Elemento aggiornato: " + archivio.ricercaPerISBN(isbn));
                        } catch (ISBNNonTrovata e) {
                            System.out.println("Aggiornamento non riuscito.");
                        }
                    }
                }

                case 7 -> archivio.statistiche();
                case 8 -> esci = true;
                default -> System.out.println("Scelta non valida.");
            }
            if (!esci) {
                System.out.println("\n premi invio per tornare al menu principale: ");
                scanner.nextLine();
            }

        }

        scanner.close();
        }
    }


