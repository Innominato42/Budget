package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.YearMonth;
import java.util.Map;

/**
 * Interfaccia che definisce i metodi per ottenere statistiche sulle spese.
 */
public interface IStatistiche {

    /**
     * Restituisce l'ammontare totale delle spese per ciascuna categoria
     * in un determinato mese.
     *
     * @param month il mese di riferimento
     * @return una mappa categoria → somma delle spese
     */
    Map<ICategory, Double> getSpesePerCategoria(YearMonth month);

    /**
     * Restituisce la percentuale di spesa per ogni categoria rispetto
     * al totale del mese specificato.
     *
     * @param month il mese di riferimento
     * @return una mappa categoria → percentuale (tra 0.0 e 100.0)
     */
    Map<ICategory, Double> getPercentualiPerCategoria(YearMonth month);

    /**
     * Restituisce l'andamento delle spese totali mese per mese.
     *
     * @return una mappa mese → totale spese
     */
    Map<YearMonth, Double> getAndamentoMensile();

    /**
     * Restituisce la categoria in cui si è speso di più nel mese indicato.
     *
     * @param month il mese di riferimento
     * @return la categoria con maggiore spesa, o null se non ci sono dati
     */
    ICategory getCategoriaPiuCostosa(YearMonth month);

    /**
     * Restituisce la media mensile delle spese complessive.
     *
     * @return la media calcolata su tutti i mesi presenti nei movimenti
     */
    double getMediaMensile();
}
