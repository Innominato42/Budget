import it.unicam.cs.mpgc.jbudget109205.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MovimentoManagerTest {


    private MovimentoManager manager;
    private IMovimento m1;
    private IMovimento m2;
    private IMovimento m3;
    private ICategory casa;
    private ICategory lavoro;

    @BeforeEach
    public void setup() {
        manager = new MovimentoManager();

        casa = new Category("Casa", null);
        lavoro = new Category("Lavoro", null);

        m1 = new Movimento(UUID.randomUUID(), LocalDate.of(2025, 8, 1), 100.0, "stipendio", Set.of(lavoro));
        m2 = new Movimento(UUID.randomUUID(), LocalDate.of(2025, 8, 2), -50.0, "affitto", Set.of(casa));
        m3 = new Movimento(UUID.randomUUID(), LocalDate.of(2025, 8, 2), -20.0, "bollette", Set.of(casa));

        manager.aggiungiMovimento(m1);
        manager.aggiungiMovimento(m2);
        manager.aggiungiMovimento(m3);
    }

    @Test
    public void testAggiuntaEMovimentoPresente() {
        assertEquals(3, manager.getTuttiIMovimenti().size());
        assertEquals(m1, manager.getMovimento(m1.getId()));
    }

    @Test
    public void testRimozioneMovimento() {
        boolean rimosso = manager.rimuoviMovimento(m2.getId());
        assertTrue(rimosso);
        assertNull(manager.getMovimento(m2.getId()));
    }

    @Test
    public void testGetMovimentiPerData() {
        List<IMovimento> risultati = manager.getMovimentiPerData(LocalDate.of(2025, 8, 2));
        assertEquals(2, risultati.size());
        assertTrue(risultati.contains(m2));
        assertTrue(risultati.contains(m3));
    }

    @Test
    public void testGetMovimentiPerCategoria() {
        List<IMovimento> risultati = manager.getMovimentiPerCategoria(casa);
        assertEquals(2, risultati.size());
        assertTrue(risultati.contains(m2));
        assertTrue(risultati.contains(m3));
    }

    @Test
    public void testCalcolaSaldoTotale() {
        double saldo = manager.calcolaSaldoTotale();
        assertEquals(30.0, saldo); // 100 - 50 - 20
    }

    @Test
    public void testGetMovimentiInIntervallo() {
        List<IMovimento> risultati = manager.getMovimentiInIntervallo(
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2025, 8, 2)
        );
        assertEquals(3, risultati.size());
    }

    @Test
    public void testGetMovimentiInIntervalloVuoto() {
        List<IMovimento> risultati = manager.getMovimentiInIntervallo(
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2025, 7, 31)
        );
        assertTrue(risultati.isEmpty());
    }
}
