import it.unicam.cs.mpgc.jbudget109205.Model.CategoryManager;
import it.unicam.cs.mpgc.jbudget109205.Model.ICategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CategoryManagerTest {
    private CategoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new CategoryManager();
        String rootName = manager.getRootCategory().getName();

        manager.addCategory("Cibo", rootName);
        manager.addCategory("Utenze", rootName);

        manager.addCategory("Casa", rootName);

        //casa
        manager.addCategory("Arredamento", "Casa");
        manager.addCategory("Ristrutturazione", "Casa");

        //utenze
        manager.addCategory("Luce", "Utenze");
        manager.addCategory("Gas", "Utenze");
    }



    @Test
    void testCategoriaCreata() {
        ICategory casa = manager.getCategory("Casa");
        assertNotNull(casa);
        assertEquals("Casa", casa.getName());
    }

    @Test
    void testGerarchiaCategorie() {
        ICategory luce = manager.getCategory("Luce");
        assertNotNull(luce);
        assertEquals("Utenze", luce.getParent().getName());
    }

    @Test
    void testCategorieFiglie() {
        ICategory utenze = manager.getCategory("Utenze");
        assertEquals(2, utenze.getChildren().size());
    }

    @Test
    void testSubcategorieRicorsive() {
        ICategory casa = manager.getCategory("Casa");
        assertEquals(2, manager.getAllSubcategories(casa).size());
    }
}
