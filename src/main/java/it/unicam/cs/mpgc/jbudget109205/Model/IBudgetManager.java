package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.YearMonth;
import java.util.List;

public interface IBudgetManager {
    /**
     * Aggiunge o aggiorna un budget per una categoria in un determinato mese.
     *
     * @param category la categoria a cui è assegnato il budget
     * @param month il mese di riferimento
     * @param limit il limite di spesa
     */
    void setBudget(ICategory category, YearMonth month, double limit);

    /**
     * Restituisce il limite di spesa per una categoria in un determinato mese.
     *
     * @param category la categoria da cercare
     * @param month il mese di riferimento
     * @return il limite di spesa, o 0.0 se non impostato
     */
    double getBudgetLimit(ICategory category, YearMonth month);

    /**
     * Calcola la spesa totale in una categoria per un determinato mese.
     *
     * @param category la categoria da analizzare
     * @param month il mese di riferimento
     * @return la somma delle spese nella categoria
     */
    double getTotalSpent(ICategory category, YearMonth month);

    /**
     * Verifica se la spesa per una categoria in un mese ha superato il budget.
     *
     * @param category la categoria da verificare
     * @param month il mese di riferimento
     * @return true se la spesa ha superato il budget, false altrimenti
     */
    boolean isOverBudget(ICategory category, YearMonth month);

    /**
     * Restituisce tutte le voci di budget presenti.
     *
     * @return una lista di voci di budget
     */
    List<Budget> getAllBudgetEntries();

    double getBudget(ICategory category, YearMonth month);

    double getRemainingBudget(ICategory category, YearMonth month);
}
