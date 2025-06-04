package Eccezioni;


public class ElementoNonTrovato extends Exception {
    public ElementoNonTrovato(String isbn) {
        super("Elemento con ISBN " + isbn + " non trovato.");
    }
}
