package it.unicam.cs.mpgc.jbudget109205.Model;


import java.time.LocalDate;
import java.util.List;

/**
 * Interfaccia per la gestione dello scadenziario dei movimenti programmati.
 */
public interface IScadenzario {

    /**
     * Aggiunge un movimento programmato allo scadenziario.
     *
     * @param movimento il movimento programmato da aggiungere
     */
    void aggiungiMovimento(MovimentoProgrammato movimento);

    /**
     * Rimuove un movimento programmato dallo scadenziario.
     *
     * @param movimento il movimento da rimuovere
     */
    void rimuoviMovimento(MovimentoProgrammato movimento);

    /**
     * Restituisce tutti i movimenti programmati.
     *
     * @return lista dei movimenti programmati
     */
    List<MovimentoProgrammato> getMovimenti();

    /**
     * Restituisce i movimenti programmati per una data specifica.
     *
     * @param data la data di esecuzione
     * @return lista dei movimenti programmati in quella data
     */
    List<MovimentoProgrammato> getMovimentiPerData(LocalDate data);

    /**
     * Esegue tutti i movimenti programmati previsti per la data specificata.
     * I movimenti vengono trasformati in movimenti reali e registrati nel sistema.
     *
     * @param data la data di esecuzione
     */
    void eseguiMovimenti(LocalDate data);
}

