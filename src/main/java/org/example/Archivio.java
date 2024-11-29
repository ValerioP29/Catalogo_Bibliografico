package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Archivio {
    final Map<String,  ElementoCatalogo> catalogo = new HashMap<>();

    //Metodo per AGGIUNGERE
    public void aggiungiElemento (ElementoCatalogo elemento) {
        if (catalogo.containsKey(elemento.getCodiceISBN())) {
            System.out.println("ISBN già presnte.");
        } else {
            catalogo.put(elemento.getCodiceISBN(), elemento);
            System.out.println("Elemento aggiunto al catalogo.");
        }
    }
    // RICERCA tramite ISBN

    public ElementoCatalogo ricercaPerISBN( String isbn) throws ISBNNonTrovata {

        if (!catalogo.containsKey(isbn)) {
            throw new ISBNNonTrovata("Elemento con ISBN " + isbn + " non trovato.");
        }
        return catalogo.get(isbn);
    }

    //Metodo per RIMUOVERE

public void rimuoviElemento (String isbn ) {
        if (catalogo.remove(isbn) != null) {
            System.out.println("Elemento rimosso con successo.");
        } else{
            System.out.println("Nessun elemento trovato con questo ISBN: " + isbn);
        }
}

     //Metodi di ricerca (anno e autore)

    public List<ElementoCatalogo> ricercaPerAnno(int anno) {
        List<ElementoCatalogo> risultati = catalogo.values().stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());

        if (risultati.isEmpty()) {
            System.out.println("Nessun elemento trovato");
        } else{
            System.out.println("ecco gli elementi relativi all'anno: " + anno);
            risultati.forEach(System.out::println);
        }
        return risultati;

    }
    public List<Libro> ricercaPerAutore (String autore) {
        List<Libro> risultati = catalogo.values().stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter( libro -> libro.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());

        if (risultati.isEmpty()) {
            System.out.println("Nessun elemento trovato");
        } else{
            System.out.println("ecco gli elementi relativi all'autore: " + autore);
            risultati.forEach(System.out::println);
        }
        return risultati;
    }

    //Metodo per AGGIORNARE

    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) {
        if (catalogo.containsKey(isbn)) {
            catalogo.put(isbn, nuovoElemento);
            System.out.println("Elemento aggiornato.");
        } else {
            System.out.println("Elemento con ISBN " + isbn + " non trovato.");
        }
    }

    // metodo per le STATISTICHE
    public void statistiche() {
        long totaleLibri = catalogo.values().stream()
                .filter(e -> e instanceof Libro)
                .count();

        long totaleRiviste = catalogo.values().stream()
                .filter(e -> e instanceof Rivista)
                .count();

        Optional<ElementoCatalogo> elementoConPiuPagine = catalogo.values().stream()
                .max(Comparator.comparingInt(ElementoCatalogo::getNumeroPagine));

        double mediaPagine = catalogo.values().stream()
                .mapToInt(ElementoCatalogo::getNumeroPagine)
                .average()
                .orElse(0);

        System.out.println("Statistiche del catalogo:");
        System.out.println("Numero totale di libri: " + totaleLibri);
        System.out.println("Numero totale di riviste: " + totaleRiviste);

        if (elementoConPiuPagine.isPresent()) {
            System.out.println("Elemento con il maggior numero di pagine: " + elementoConPiuPagine.get());
        } else {
            System.out.println("Nessun elemento nel catalogo.");
        }

        System.out.println("Media delle pagine di tutti gli elementi: " + mediaPagine);
    }

}


