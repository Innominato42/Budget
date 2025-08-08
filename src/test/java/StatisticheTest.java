import it.unicam.cs.mpgc.jbudget109205.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticheTest {

    private MovimentoManager movimentoManager;
    private Statistiche statistiche;
    private Category cibo;
    private Category trasporti;

    @BeforeEach
    void setUp() {
        movimentoManager = new MovimentoManager();
        statistiche = new Statistiche(movimentoManager);

        cibo = new Category("Cibo", null);
        trasporti = new Category("Trasporti", null);

        Movimento m1= new Movimento(
                UUID.randomUUID(),
                LocalDate.of(2025, 8, 1),
                -50.0,
                "Spesa",
                new HashSet<>(List.of(cibo))
        );
        m1.aggiungiCategoria(cibo);
        movimentoManager.aggiungiMovimento(m1);

        Movimento m2 = new Movimento(
                UUID.randomUUID(),
                LocalDate.of(2025, 8, 5),
                -30.0,
                "Cena",
                new HashSet<>(List.of(cibo))
        );
        m2.aggiungiCategoria(cibo);
        movimentoManager.aggiungiMovimento(m2);

        Movimento m3 = new Movimento(
                UUID.randomUUID(),
                LocalDate.of(2025, 8, 10),
                -20.0,
                "Benzina",
                new HashSet<>(List.of(trasporti))
        );
        m3.aggiungiCategoria(trasporti);
        movimentoManager.aggiungiMovimento(m3);

        Movimento m4 = new Movimento(
                UUID.randomUUID(),
                LocalDate.of(2025, 7, 10),
                -10.0,
                "Autobus",
                new HashSet<>(List.of(trasporti))
        );
        m4.aggiungiCategoria(trasporti);
        movimentoManager.aggiungiMovimento(m4);
    }

    @Test
    void testGetSpesePerCategoria() {
        Map<ICategory, Double> spese = statistiche.getSpesePerCategoria(YearMonth.of(2025, 8));
        assertEquals(2, spese.size());
        assertEquals(-80.0, spese.get(cibo));
        assertEquals(-20.0, spese.get(trasporti));
    }

    @Test
    void testGetPercentualiPerCategoria() {
        Map<ICategory, Double> percentuali = statistiche.getPercentualiPerCategoria(YearMonth.of(2025, 8));
        assertEquals(2, percentuali.size());
        assertEquals(80.0, percentuali.get(cibo), 0.001);
        assertEquals(20.0, percentuali.get(trasporti), 0.001);
    }

    @Test
    void testGetAndamentoMensile() {
        Map<YearMonth, Double> andamento = statistiche.getAndamentoMensile();
        assertEquals(2, andamento.size());
        assertEquals(-100.0, andamento.get(YearMonth.of(2025, 8)));
        assertEquals(-10.0, andamento.get(YearMonth.of(2025, 7)));
    }

    @Test
    void testGetCategoriaPiuCostosa() {
        ICategory piuCostosa = statistiche.getCategoriaPiuCostosa(YearMonth.of(2025, 8));
        assertEquals(cibo, piuCostosa);
    }

    @Test
    void testGetMediaMensile() {
        double media = statistiche.getMediaMensile();
        assertEquals(-55.0, media);
    }
}
