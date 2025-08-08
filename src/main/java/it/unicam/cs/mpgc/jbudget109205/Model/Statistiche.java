package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe che implementa l'interfaccia IStatistiche per analizzare le spese.
 */
public class Statistiche implements IStatistiche {

    private final MovimentoManager movimentoManager;

    /**
     * Costruttore che richiede un MovimentoManager per accedere ai movimenti.
     *
     * @param movimentoManager il gestore dei movimenti da analizzare
     */
    public Statistiche(MovimentoManager movimentoManager) {
        this.movimentoManager = movimentoManager;
    }

    /**
     * Restituisce il totale speso per ciascuna categoria in un dato mese.
     */
    @Override
    public Map<ICategory, Double> getSpesePerCategoria(YearMonth month) {
        Map<ICategory, Double> spese = new HashMap<>();

        for (IMovimento movimento : movimentoManager.getAllMovimenti()) {
            if (YearMonth.from(movimento.getDate()).equals(month)) {
                for (ICategory category : movimento.getCategories()) {
                    spese.merge(category, movimento.getAmount(), Double::sum);
                }
            }
        }

        return spese;
    }

    /**
     * Calcola la percentuale di spesa per ogni categoria in un determinato mese.
     */
    @Override
    public Map<ICategory, Double> getPercentualiPerCategoria(YearMonth month) {
        Map<ICategory, Double> spesePerCategoria = getSpesePerCategoria(month);
        double totale = spesePerCategoria.values().stream().mapToDouble(Double::doubleValue).sum();

        Map<ICategory, Double> percentuali = new HashMap<>();
        for (Map.Entry<ICategory, Double> entry : spesePerCategoria.entrySet()) {
            double percentuale = (entry.getValue() / totale) * 100.0;
            percentuali.put(entry.getKey(), percentuale);
        }

        return percentuali;
    }

    /**
     * Calcola il totale speso per ogni mese.
     */
    @Override
    public Map<YearMonth, Double> getAndamentoMensile() {
        Map<YearMonth, Double> andamento = new HashMap<>();

        for (IMovimento movimento : movimentoManager.getAllMovimenti()) {
            YearMonth mese = YearMonth.from(movimento.getDate());
            andamento.merge(mese, movimento.getAmount(), Double::sum);
        }

        return andamento;
    }

    /**
     * Restituisce la categoria con la maggiore spesa in un determinato mese.
     */
    @Override
    public ICategory getCategoriaPiuCostosa(YearMonth month) {
        return getSpesePerCategoria(month).entrySet().stream()
                .max((e1, e2) -> Double.compare(Math.abs(e1.getValue()), Math.abs(e2.getValue())))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Calcola la media mensile delle spese totali.
     */
    @Override
    public double getMediaMensile() {
        Map<YearMonth, Double> andamento = getAndamentoMensile();
        if (andamento.isEmpty()) return 0.0;
        return andamento.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
}
