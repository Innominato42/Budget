import it.unicam.cs.mpgc.jbudget109205.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScadenzarioTest {

    private MovimentoManager movimentoManager;
    private Scadenzario scadenzario;
    private Category category;

    @BeforeEach
    public void setup() {
        movimentoManager = new MovimentoManager();
        scadenzario = new Scadenzario(movimentoManager);
        category = new Category("Casa", null);
    }

    @Test
    public void testAggiungiEMuoviMovimento() {
        MovimentoProgrammato mp = new MovimentoProgrammato(
                LocalDate.of(2025, 8, 10),
                100.0,
                "Bollette",
                Set.of(category)
        );

        scadenzario.aggiungiMovimento(mp);
        assertTrue(scadenzario.getMovimenti().contains(mp));
        scadenzario.eseguiMovimenti(LocalDate.of(2025, 8, 10));
        assertFalse(scadenzario.getMovimenti().contains(mp));
        assertTrue(movimentoManager.getAllMovimenti().stream()
                .anyMatch(m -> m.getDescription().equals("Bollette") && m.getAmount() == 100.0));
    }

    @Test
    public void testRimuoviMovimento() {
        MovimentoProgrammato mp = new MovimentoProgrammato(
                LocalDate.of(2025, 8, 11),
                50.0,
                "Abbonamento",
                Set.of(category)
        );

        scadenzario.aggiungiMovimento(mp);
        assertTrue(scadenzario.getMovimenti().contains(mp));

        scadenzario.rimuoviMovimento(mp);
        assertFalse(scadenzario.getMovimenti().contains(mp));
    }

    @Test
    public void testGetMovimentiPerData() {
        MovimentoProgrammato mp1 = new MovimentoProgrammato(
                LocalDate.of(2025, 8, 12),
                30.0,
                "Rata mutuo",
                Set.of(category)
        );
        MovimentoProgrammato mp2 = new MovimentoProgrammato(
                LocalDate.of(2025, 8, 12),
                40.0,
                "Spesa",
                Set.of(category)
        );
        MovimentoProgrammato mp3 = new MovimentoProgrammato(
                LocalDate.of(2025, 8, 13),
                20.0,
                "Altro",
                Set.of(category)
        );

        scadenzario.aggiungiMovimento(mp1);
        scadenzario.aggiungiMovimento(mp2);
        scadenzario.aggiungiMovimento(mp3);

        var movimenti12 = scadenzario.getMovimentiPerData(LocalDate.of(2025, 8, 12));
        assertEquals(2, movimenti12.size());
        assertTrue(movimenti12.contains(mp1));
        assertTrue(movimenti12.contains(mp2));

        var movimenti13 = scadenzario.getMovimentiPerData(LocalDate.of(2025, 8, 13));
        assertEquals(1, movimenti13.size());
        assertTrue(movimenti13.contains(mp3));
    }
}
