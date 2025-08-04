package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MovimentoManager {

    // Mappa dei movimenti, indicizzati per ID univoco
    private final Map<UUID, IMovimento> movimenti = new HashMap<>();

    /**
     * Aggiunge un movimento al manager.
     * Se un movimento con lo stesso ID esiste già, viene sovrascritto.
     * @param movimento il movimento da aggiungere
     */
    public void aggiungiMovimento(IMovimento movimento) {
        movimenti.put(movimento.getId(), movimento);
    }

    /**
     * Rimuove un movimento dato il suo ID.
     * @param id l'ID del movimento da rimuovere
     * @return true se il movimento è stato trovato e rimosso, false altrimenti
     */
    public boolean rimuoviMovimento(UUID id) {
        return movimenti.remove(id) != null;
    }

    /**
     * Recupera un movimento dato il suo ID.
     * @param id l'ID del movimento
     * @return il movimento corrispondente, oppure null se non esiste
     */
    public IMovimento getMovimento(UUID id) {
        return movimenti.get(id);
    }

    /**
     * Restituisce tutti i movimenti registrati.
     * @return lista di tutti i movimenti
     */
    public List<IMovimento> getAllMovimenti() {
        return new ArrayList<>(movimenti.values());
    }

    /**
     * Restituisce tutti i movimenti effettuati in una certa data.
     * @param data la data da filtrare
     * @return lista di movimenti avvenuti nella data specificata
     */
    public List<IMovimento> getMovimentiPerData(LocalDate data) {
        return movimenti.values().stream()
                .filter(m -> m.getDate().equals(data))
                .collect(Collectors.toList());
    }

    /**
     * Restituisce tutti i movimenti associati a una certa categoria.
     * @param categoria la categoria da filtrare
     * @return lista di movimenti che appartengono alla categoria
     */
    public List<IMovimento> getMovimentiPerCategoria(ICategory categoria) {
        return movimenti.values().stream()
                .filter(m -> m.getCategories().contains(categoria))
                .collect(Collectors.toList());
    }

    /**
     * Calcola il saldo totale di tutti i movimenti.
     * Le uscite sono rappresentate con importi negativi.
     * @return il saldo risultante
     */
    public double calcolaSaldoTotale() {
        return movimenti.values().stream()
                .mapToDouble(IMovimento::getAmount)
                .sum();
    }

    /**
     * Restituisce i movimenti compresi tra due date (incluse).
     * @param inizio data di inizio
     * @param fine data di fine
     * @return lista di movimenti nell'intervallo
     */
    public List<IMovimento> getMovimentiInIntervallo(LocalDate inizio, LocalDate fine) {
        return movimenti.values().stream()
                .filter(m -> !m.getDate().isBefore(inizio) && !m.getDate().isAfter(fine))
                .collect(Collectors.toList());
    }
}
