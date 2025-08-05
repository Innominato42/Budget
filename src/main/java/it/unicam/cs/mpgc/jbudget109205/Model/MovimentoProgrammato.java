package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * Rappresenta un movimento programmato, ovvero un'operazione che verrà eseguita in futuro.
 */
public class MovimentoProgrammato {

    private final UUID id;
    private final LocalDate dataEsecuzione;
    private final double importo;
    private final String descrizione;
    private final Set<ICategory> categorie;

    /**
     * Costruisce un nuovo movimento programmato.
     *
     * @param dataEsecuzione la data in cui il movimento dovrà essere eseguito
     * @param importo        l'importo del movimento (positivo per entrata, negativo per uscita)
     * @param descrizione    la descrizione del movimento
     * @param categorie      le categorie associate al movimento
     */
    public MovimentoProgrammato(LocalDate dataEsecuzione, double importo, String descrizione, Set<ICategory> categorie) {
        this.id = UUID.randomUUID();
        this.dataEsecuzione = dataEsecuzione;
        this.importo = importo;
        this.descrizione = descrizione;
        this.categorie = categorie;
    }

    /**
     * Restituisce l'ID del movimento programmato.
     *
     * @return l'UUID del movimento
     */
    public UUID getId() {
        return id;
    }

    /**
     * Restituisce la data in cui il movimento dovrà essere eseguito.
     *
     * @return la data di esecuzione
     */
    public LocalDate getDataEsecuzione() {
        return dataEsecuzione;
    }

    /**
     * Restituisce l'importo del movimento.
     *
     * @return l'importo
     */
    public double getImporto() {
        return importo;
    }

    /**
     * Restituisce la descrizione del movimento.
     *
     * @return la descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce le categorie associate al movimento.
     *
     * @return l'insieme delle categorie
     */
    public Set<ICategory> getCategorie() {
        return categorie;
    }

    /**
     * Converte il movimento programmato in un movimento reale eseguibile dal sistema.
     *
     * @return un oggetto Movimento
     */
    public Movimento toMovimento() {
        return new Movimento(
                id,
                dataEsecuzione,
                importo,
                descrizione,
                categorie
        );
    }
}