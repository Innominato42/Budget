import it.unicam.cs.mpgc.jbudget109205.Model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinalTest {
    @Test
    public void testFlussoCompleto() {

        MovimentoManager movimentoManager = new MovimentoManager();
        BudgetManager budgetManager = new BudgetManager(movimentoManager);
        Scadenzario scadenziario = new Scadenzario(movimentoManager);
        Statistiche statistiche = new Statistiche(movimentoManager);


        Category spesa = new Category("Spesa", null);
        Category svago = new Category("Svago", null);


        budgetManager.setBudget(spesa, YearMonth.of(2025, 8), 500.0);
        budgetManager.setBudget(svago, YearMonth.of(2025, 8), 200.0);


        Movimento m1 = new Movimento(-100.0, LocalDate.of(2025, 8, 1), "Supermercato");
        m1.aggiungiCategoria(spesa);
        movimentoManager.aggiungiMovimento(m1);


        MovimentoProgrammato mp = new MovimentoProgrammato(
                LocalDate.of(2025, 8, 10),
                -50.0,
                "Cinema",
                Set.of(svago)
        );
        scadenziario.aggiungiMovimento(mp);


        scadenziario.eseguiMovimenti(LocalDate.of(2025, 8, 10));


        assertEquals(500.0, budgetManager.getBudget(spesa, YearMonth.of(2025, 8)));
        assertEquals(200.0, budgetManager.getBudget(svago, YearMonth.of(2025, 8)));


        Map<ICategory, Double> speseAgosto = statistiche.getSpesePerCategoria(YearMonth.of(2025, 8));
        assertEquals(-100.0, speseAgosto.get(spesa), 0.001);
        assertEquals(-50.0, speseAgosto.get(svago), 0.001);


        assertTrue(scadenziario.getMovimenti().isEmpty());


        assertEquals(spesa, statistiche.getCategoriaPiuCostosa(YearMonth.of(2025, 8)));
    }
}

