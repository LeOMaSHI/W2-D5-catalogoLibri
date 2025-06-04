package Catalogo;


import Eccezioni.ElementoNonTrovato;
import ents.ElementoCatalogo;
import ents.Libro;
import ents.Rivista;

import java.util.*;
import java.util.stream.Collectors;

public class Archivio {
    private Map<String, ElementoCatalogo> elementi = new HashMap<>();

    public void aggiungiElemento(ElementoCatalogo e) {
        if (elementi.containsKey(e.getIsbn())) {
            System.out.println("Elemento già presente!");
        } else {
            elementi.put(e.getIsbn(), e);
        }
    }

    public ElementoCatalogo cercaPerIsbn(String isbn) throws ElementoNonTrovato {
        if (!elementi.containsKey(isbn)) throw new ElementoNonTrovato(isbn);
        return elementi.get(isbn);
    }

    public void rimuoviPerIsbn(String isbn) {
        elementi.remove(isbn);
    }

    public List<ElementoCatalogo> cercaPerAnno(int anno) {
        return elementi.values().stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .toList();
    }

    public List<Libro> cercaPerAutore(String autore) {
        return elementi.values().stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(libro -> libro.getAutore().equalsIgnoreCase(autore))
                .toList();
    }

    public void aggiornaElemento(String isbn, ElementoCatalogo nuovo) throws ElementoNonTrovato {
        if (!elementi.containsKey(isbn)) throw new ElementoNonTrovato(isbn);
        elementi.put(isbn, nuovo);
    }

    public void statistiche() {
        long numLibri = elementi.values().stream().filter(e -> e instanceof Libro).count();
        long numRiviste = elementi.values().stream().filter(e -> e instanceof Rivista).count();
        Optional<ElementoCatalogo> maxPagine = elementi.values().stream()
                .max(Comparator.comparingInt(ElementoCatalogo::getNumeroPagine));
        double mediaPagine = elementi.values().stream()
                .mapToInt(ElementoCatalogo::getNumeroPagine)
                .average().orElse(0);

        System.out.println("Totale libri: " + numLibri);
        System.out.println("Totale riviste: " + numRiviste);
        maxPagine.ifPresent(e -> System.out.println("Elemento con più pagine: " + e));
        System.out.println("Media pagine: " + mediaPagine);
    }

    public Collection<ElementoCatalogo> getTutti() {
        return elementi.values();
    }
}
