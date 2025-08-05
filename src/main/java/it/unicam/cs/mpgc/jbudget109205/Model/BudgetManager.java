package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.YearMonth;
import java.util.*;

public class BudgetManager implements IBudgetManager {
    // Associazioni tra una categoria e un mese specifico a un budget (double)
    private final Map<ICategory, Map<YearMonth, Double>> budgets = new HashMap<>();

    // Riferimento al gestore dei movimenti per accedere alle spese effettive
    private final MovimentoManager movimentoManager;

    /**
     * Costruttore della classe BudgetManager.
     *
     * @param movimentoManager il gestore dei movimenti da cui ottenere le spese
     */
    public BudgetManager(MovimentoManager movimentoManager) {
        this.movimentoManager = movimentoManager;
    }

    /**
     * Restituisce il budget di una categoria per un certo mese
     * @param category la categoria a cui associare il budget
     * @param month il mese di riferimento
     * @return il budget
     */
    @Override
    public double getBudget(ICategory category, YearMonth month) {
        return budgets.getOrDefault(category, Collections.emptyMap()).getOrDefault(month, 0.0);
    }
    /**
     * Imposta un budget per una determinata categoria e mese.
     *
     * @param category la categoria a cui associare il budget
     * @param month    il mese di riferimento
     * @param amount   l'importo del budget
     */
    @Override
    public void setBudget(ICategory category, YearMonth month, double amount) {
        budgets.computeIfAbsent(category, k -> new HashMap<>()).put(month, amount);
    }
    /**
     * Restituisce il budget impostato per una categoria in un determinato mese.
     *
     * @param category la categoria di interesse
     * @param month    il mese di interesse
     * @return il budget, oppure 0.0 se non impostato
     */
    @Override
    public double getBudgetLimit(ICategory category, YearMonth month) {
        return budgets.getOrDefault(category, Collections.emptyMap()).getOrDefault(month, 0.0);
    }
    /**
     * Calcola quanto è stato speso effettivamente per una categoria in un determinato mese.
     * I movimenti vengono filtrati per data e per appartenenza alla categoria.
     *
     * @param category la categoria di spesa
     * @param month    il mese di riferimento
     * @return la somma delle spese per quella categoria nel mese
     */

    @Override
    public double getTotalSpent(ICategory category, YearMonth month) {
        return movimentoManager.getAllMovimenti().stream()
                .filter(m -> m.getDate() != null &&
                        YearMonth.from(m.getDate()).equals(month) &&
                        m.getCategories().contains(category))
                .mapToDouble(IMovimento::getAmount)
                .sum();
    }
    /**
     * Restituisce la differenza tra il budget e la spesa effettiva per una categoria in un mese.
     * Se il valore è negativo, indica che si è superato il budget.
     *
     * @param category la categoria di spesa
     * @param month    il mese di riferimento
     * @return il valore rimanente (positivo o negativo)
     */

    @Override
    public boolean isOverBudget(ICategory category, YearMonth month) {
        return getTotalSpent(category, month) > getBudget(category, month);
    }

    /**
     * Restituisce la differenza tra il budget e la spesa effettiva per una categoria in un mese.
     * Se il valore è negativo, indica che si è superato il budget.
     *
     * @param category la categoria di spesa
     * @param month    il mese di riferimento
     * @return il valore rimanente (positivo o negativo)
     */
    @Override
    public double getRemainingBudget(ICategory category, YearMonth month) {
        return getBudget(category, month) - getTotalSpent(category, month);
    }

    /**
     * Restituisce tutte le voci di budget registrate come oggetti Budget.
     *
     * @return lista di tutte le voci di budget
     */
    @Override
    public List<Budget> getAllBudgetEntries() {
        List<Budget> result = new ArrayList<>();

        for (Map.Entry<ICategory, Map<YearMonth, Double>> categoryEntry : budgets.entrySet()) {
            ICategory category = categoryEntry.getKey();
            Map<YearMonth, Double> monthlyBudgets = categoryEntry.getValue();

            for (Map.Entry<YearMonth, Double> monthEntry : monthlyBudgets.entrySet()) {
                YearMonth month = monthEntry.getKey();
                double amount = monthEntry.getValue();

                result.add(new Budget(category, month, amount));
            }
        }

        return result;
    }

}
