package catalogoBibliotecario;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Archivio archivio = new Archivio();
        Scanner scanner = new Scanner(System.in);

        boolean continua = true;

        while (continua) {
            System.out.println("\n--- MENU CATALOGO BIBLIOTECARIO ---");
            System.out.println("1. Aggiungi Libro");
            System.out.println("2. Aggiungi Rivista");
            System.out.println("3. Cerca per ISBN");
            System.out.println("4. Rimuovi per ISBN");
            System.out.println("5. Cerca per anno di pubblicazione");
            System.out.println("6. Cerca per autore");
            System.out.println("7. Aggiorna elemento tramite ISBN");
            System.out.println("8. Visualizza statistiche catalogo");
            System.out.println("9. Stampa tutti gli elementi del catalogo");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            String input = scanner.nextLine();

            try {
                int scelta = Integer.parseInt(input);

                switch (scelta) {
                    case 1 -> aggiungiLibro(scanner, archivio);
                    case 2 -> aggiungiRivista(scanner, archivio);
                    case 3 -> cercaPerISBN(scanner, archivio);
                    case 4 -> rimuoviPerISBN(scanner, archivio);
                    case 5 -> cercaPerAnno(scanner, archivio);
                    case 6 -> cercaPerAutore(scanner, archivio);
                    case 7 -> aggiornaElemento(scanner, archivio);
                    case 8 -> archivio.stampaStatistiche();
                    case 9 -> archivio.stampaCatalogo();
                    case 0 -> {
                        System.out.println("Uscita dal programma. Arrivederci!");
                        continua = false;
                    }
                    default -> System.out.println("Scelta non valida. Riprova.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            } catch (NoSuchElementException e) {
                System.out.println("x " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("x " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Errore inatteso: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void aggiungiLibro(Scanner scanner, Archivio archivio) {
        try {
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Titolo: ");
            String titolo = scanner.nextLine();
            System.out.print("Anno di pubblicazione: ");
            int anno = Integer.parseInt(scanner.nextLine());
            System.out.print("Numero di pagine: ");
            int pagine = Integer.parseInt(scanner.nextLine());
            System.out.print("Autore: ");
            String autore = scanner.nextLine();
            System.out.print("Genere: ");
            String genere = scanner.nextLine();

            Libro libro = new Libro(isbn, titolo, anno, pagine, autore, genere);
            archivio.aggiungiElemento(libro);
            System.out.println("Libro aggiunto!");
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido per anno o pagine.");
        } catch (IllegalArgumentException e) {
            System.out.println("x " + e.getMessage());
        }
    }

    private static void aggiungiRivista(Scanner scanner, Archivio archivio) {
        try {
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Titolo: ");
            String titolo = scanner.nextLine();
            System.out.print("Anno di pubblicazione: ");
            int anno = Integer.parseInt(scanner.nextLine());
            System.out.print("Numero di pagine: ");
            int pagine = Integer.parseInt(scanner.nextLine());
            System.out.print("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
            Rivista.Periodicita periodicita = Rivista.Periodicita.valueOf(scanner.nextLine().toUpperCase());

            Rivista rivista = new Rivista(isbn, titolo, anno, pagine, periodicita);
            archivio.aggiungiElemento(rivista);
            System.out.println("Rivista aggiunta!");
        } catch (IllegalArgumentException e) {
            System.out.println(" Periodicità non valida o altro errore: " + e.getMessage());
        }
    }

    private static void cercaPerISBN(Scanner scanner, Archivio archivio) {
        System.out.print("ISBN da cercare: ");
        String isbn = scanner.nextLine();
        ElementoCatalogo trovato = archivio.cercaPerIsbn(isbn);
        System.out.println("Trovato:\n" + trovato);
    }

    private static void rimuoviPerISBN(Scanner scanner, Archivio archivio) {
        System.out.print("ISBN da rimuovere: ");
        String isbn = scanner.nextLine();
        archivio.rimuoviPerIsbn(isbn);
        System.out.println("Elemento rimosso (se esisteva).");
    }

    private static void cercaPerAnno(Scanner scanner, Archivio archivio) {
        try {
            System.out.print("Anno di pubblicazione: ");
            int anno = Integer.parseInt(scanner.nextLine());
            var risultati = archivio.cercaPerAnno(anno);
            if (risultati.isEmpty()) {
                System.out.println("Nessun elemento trovato per l'anno " + anno);
            } else {
                risultati.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido per l'anno.");
        }
    }

    private static void cercaPerAutore(Scanner scanner, Archivio archivio) {
        System.out.print("Autore: ");
        String autore = scanner.nextLine();
        var risultati = archivio.cercaPerAutore(autore);
        if (risultati.isEmpty()) {
            System.out.println("Nessun libro trovato per l'autore " + autore);
        } else {
            risultati.forEach(System.out::println);
        }
    }

    private static void aggiornaElemento(Scanner scanner, Archivio archivio) {
        System.out.print("ISBN dell'elemento da aggiornare: ");
        String isbn = scanner.nextLine();

        ElementoCatalogo esistente = archivio.cercaPerIsbn(isbn);

        if (esistente instanceof Libro) {
            System.out.println("Aggiorna Libro:");
            try {
                System.out.print("Nuovo Titolo: ");
                String titolo = scanner.nextLine();
                System.out.print("Nuovo Anno di pubblicazione: ");
                int anno = Integer.parseInt(scanner.nextLine());
                System.out.print("Nuovo Numero di pagine: ");
                int pagine = Integer.parseInt(scanner.nextLine());
                System.out.print("Nuovo Autore: ");
                String autore = scanner.nextLine();
                System.out.print("Nuovo Genere: ");
                String genere = scanner.nextLine();

                Libro libroAggiornato = new Libro(isbn, titolo, anno, pagine, autore, genere);
                archivio.aggiornaElemento(isbn, libroAggiornato);
                System.out.println("Libro aggiornato con successo!");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido per anno o pagine.");
            }

        } else if (esistente instanceof Rivista) {
            System.out.println("Aggiorna Rivista:");
            try {
                System.out.print("Nuovo Titolo: ");
                String titolo = scanner.nextLine();
                System.out.print("Nuovo Anno di pubblicazione: ");
                int anno = Integer.parseInt(scanner.nextLine());
                System.out.print("Nuovo Numero di pagine: ");
                int pagine = Integer.parseInt(scanner.nextLine());
                System.out.print("Nuova Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                Rivista.Periodicita periodicita = Rivista.Periodicita.valueOf(scanner.nextLine().toUpperCase());

                Rivista rivistaAggiornata = new Rivista(isbn, titolo, anno, pagine, periodicita);
                archivio.aggiornaElemento(isbn, rivistaAggiornata);
                System.out.println("Rivista aggiornata con successo!");
            } catch (IllegalArgumentException e) {
                System.out.println("Periodicità non valida o altro errore: " + e.getMessage());
            }
        } else {
            System.out.println("Nessun elemento trovato con ISBN: " + isbn);
        }
    }
}



