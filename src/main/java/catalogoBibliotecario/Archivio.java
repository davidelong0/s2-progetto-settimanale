package catalogoBibliotecario;

import java.util.*;
import java.util.stream.Collectors;

public class Archivio {
    private List<ElementoCatalogo> catalogo = new ArrayList<>();


    public void aggiungiElemento(ElementoCatalogo elemento) throws IllegalArgumentException {
        if (catalogo.stream().anyMatch(e -> e.getIsbn().equals(elemento.getIsbn()))) {
            throw new IllegalArgumentException("Elemento con ISBN già presente.");
        }
        catalogo.add(elemento);
        System.out.println("Elemento aggiunto correttamente.");
    }


    public ElementoCatalogo cercaPerIsbn(String isbn) throws NoSuchElementException {
        return catalogo.stream()
                .filter(e -> e.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Elemento con ISBN non trovato."));
    }


    public void rimuoviPerIsbn(String isbn) {
        boolean rimosso = catalogo.removeIf(e -> e.getIsbn().equals(isbn));
        if (rimosso) {
            System.out.println("Elemento rimosso correttamente.");
        } else {
            System.out.println("Nessun elemento trovato con questo ISBN da rimuovere.");
        }
    }


    public List<ElementoCatalogo> cercaPerAnno(int anno) {
        return catalogo.stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }


    public List<Libro> cercaPerAutore(String autore) {
        return catalogo.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(l -> l.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }


    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) throws NoSuchElementException {
        for (int i = 0; i < catalogo.size(); i++) {
            if (catalogo.get(i).getIsbn().equals(isbn)) {
                catalogo.set(i, nuovoElemento);
                System.out.println("✏️ Elemento aggiornato con successo.");
                return;
            }
        }
        throw new NoSuchElementException("Elemento da aggiornare non trovato.");
    }


    public void stampaStatistiche() {
        long numLibri = catalogo.stream().filter(e -> e instanceof Libro).count();
        long numRiviste = catalogo.stream().filter(e -> e instanceof Rivista).count();
        Optional<ElementoCatalogo> maxPagine = catalogo.stream()
                .max(Comparator.comparingInt(ElementoCatalogo::getNumeroPagine));
        double mediaPagine = catalogo.stream()
                .mapToInt(ElementoCatalogo::getNumeroPagine)
                .average()
                .orElse(0);

        System.out.println("STATISTICHE CATALOGO:");
        System.out.println("Numero totale di libri: " + numLibri);
        System.out.println("Numero totale di riviste: " + numRiviste);
        System.out.println("Elemento con più pagine: " + maxPagine.map(Object::toString).orElse("Nessuno"));
        System.out.printf("Media pagine: %.2f%n", mediaPagine);
    }


    public void stampaCatalogo() {
        if (catalogo.isEmpty()) {
            System.out.println("Il catalogo è vuoto.");
        } else {
            System.out.println("CONTENUTO DEL CATALOGO:");
            catalogo.forEach(System.out::println);
        }
    }
}
