package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementazione della classe Scadenziario, che gestisce movimenti futuri da eseguire.
 */

public class Scadenzario implements IScadenzario {

    private final List<MovimentoProgrammato> movimentiProgrammati = new ArrayList<>();
    private final MovimentoManager movimentoManager;

    /**
     * Costruttore del Scadenzario.
     *
     * @param movimentoManager il gestore dei movimenti reali in cui salvare i movimenti eseguiti
     */
    public Scadenzario(MovimentoManager movimentoManager) {
        this.movimentoManager = movimentoManager;
    }

    /**
     * Aggiunge un nuovo movimento programmato allo scadenziario.
     *
     * @param movimento il movimento programmato da aggiungere
     */
    @Override
    public void aggiungiMovimento(MovimentoProgrammato movimento) {
        movimentiProgrammati.add(movimento);
    }

    /**
     * Rimuove un movimento programmato dallo scadenziario.
     *
     * @param movimento il movimento da rimuovere
     */
    @Override
    public void rimuoviMovimento(MovimentoProgrammato movimento) {

        movimentiProgrammati.remove(movimento);
    }

    /**
     * Restituisce tutti i movimenti programmati.
     *
     * @return lista dei movimenti programmati
     */
    @Override
    public List<MovimentoProgrammato> getMovimenti() {
        return  new ArrayList<>(movimentiProgrammati);
    }
    /**
     * Restituisce tutti i movimenti programmati con data di esecuzione uguale alla data specificata.
     *
     * @param data la data per cui cercare i movimenti
     * @return lista dei movimenti previsti per quella data
     */
    @Override
    public List<MovimentoProgrammato> getMovimentiPerData(LocalDate data) {
        return movimentiProgrammati.stream()
                .filter(m -> m.getDataEsecuzione().equals(data))
                .collect(Collectors.toList());
    }

    /**
     * Esegue tutti i movimenti programmati previsti per la data specificata,
     * convertendoli in movimenti reali e salvandoli tramite il MovimentoManager.
     *
     * @param data la data di esecuzione
     */
    @Override
    public void eseguiMovimenti(LocalDate data) {
        List<MovimentoProgrammato> daEseguire = getMovimentiPerData(data);
        for (MovimentoProgrammato mp : daEseguire) {
            movimentoManager.aggiungiMovimento(mp.toMovimento());
        }
        movimentiProgrammati.removeAll(daEseguire);
    }


}

