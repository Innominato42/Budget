import it.unicam.cs.mpgc.jbudget109205.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BudgetManagerTest {

    private MovimentoManager movimentoManager;
    private BudgetManager budgetManager;
    private ICategory alimentari;
    private ICategory trasporti;

    @BeforeEach
    void setUp() {
        movimentoManager = new MovimentoManager();
        budgetManager = new BudgetManager(movimentoManager);

        alimentari = new Category("Alimentari", null);
        trasporti = new Category("Trasporti", null);
    }

    @Test
    void testSetAndGetBudget() {
        YearMonth mese = YearMonth.of(2025, 8);
        budgetManager.setBudget(alimentari, mese, 300.0);

        double budget = budgetManager.getBudget(alimentari, mese);
        assertEquals(300.0, budget);
    }

    @Test
    void testGetBudgetLimitDefaultZero() {
        YearMonth mese = YearMonth.of(2025, 8);
        assertEquals(0.0, budgetManager.getBudgetLimit(alimentari, mese));
    }

    @Test
    void testGetTotalSpent() {
        YearMonth mese = YearMonth.of(2025, 8);

        Set<ICategory> categorie = new HashSet<>();
        categorie.add(alimentari);

        movimentoManager.aggiungiMovimento(new Movimento(UUID.randomUUID(), LocalDate.of(2025, 8, 10), 50.0, "Spesa 1", categorie));
        movimentoManager.aggiungiMovimento(new Movimento(UUID.randomUUID(), LocalDate.of(2025, 8, 15), 30.0, "Spesa 2", categorie));
        movimentoManager.aggiungiMovimento(new Movimento(UUID.randomUUID(), LocalDate.of(2025, 7, 15), 99.0, "Fuori mese", categorie));

        assertEquals(80.0, budgetManager.getTotalSpent(alimentari, mese));
    }

    @Test
    void testIsOverBudget() {
        YearMonth mese = YearMonth.of(2025, 8);
        budgetManager.setBudget(alimentari, mese, 70.0);

        Set<ICategory> categorie = new HashSet<>();
        categorie.add(alimentari);

        movimentoManager.aggiungiMovimento(new Movimento(UUID.randomUUID(), LocalDate.of(2025, 8, 1), 50.0, "Spesa", categorie));
        movimentoManager.aggiungiMovimento(new Movimento(UUID.randomUUID(), LocalDate.of(2025, 8, 2), 30.0, "Spesa", categorie));

        assertTrue(budgetManager.isOverBudget(alimentari, mese));
    }

    @Test
    void testGetRemainingBudget() {
        YearMonth mese = YearMonth.of(2025, 8);
        budgetManager.setBudget(alimentari, mese, 100.0);

        Set<ICategory> categorie = new HashSet<>();
        categorie.add(alimentari);

        movimentoManager.aggiungiMovimento(new Movimento(UUID.randomUUID(), LocalDate.of(2025, 8, 5), 40.0, "Spesa", categorie));

        assertEquals(60.0, budgetManager.getRemainingBudget(alimentari, mese));
    }

    @Test
    void testGetAllBudgetEntries() {
        YearMonth mese1 = YearMonth.of(2025, 8);
        YearMonth mese2 = YearMonth.of(2025, 9);

        budgetManager.setBudget(alimentari, mese1, 200.0);
        budgetManager.setBudget(trasporti, mese2, 150.0);

        List<Budget> entries = budgetManager.getAllBudgetEntries();
        assertEquals(2, entries.size());

        Set<ICategory> categorieTrovate = new HashSet<>();
        for (Budget b : entries) {
            categorieTrovate.add(b.getCategory());
        }

        assertTrue(categorieTrovate.contains(alimentari));
        assertTrue(categorieTrovate.contains(trasporti));
    }
}
