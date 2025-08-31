package it.unicam.cs.mpgc.jbudget109205.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * La classe Category rappresenta una categoria di spesa all'interno del sistema di gestione del budget
 */
public class Category implements ICategory {
    private String name;
    private ICategory parent;
    private List<ICategory> children = new ArrayList<>();

    /**
     * Costruisce una nuova Categoria
     * @param name il nome della nuova categoria
     * @param parent la categoria padre a cui questa appartiene, oppure {@code null} se si tratta di una categoria di primo livello.
     * Ogni categoria può quindi essere organizzata in una gerarchia ad albero, ad esempio "Casa" potrebbe contenere "Affitto" e "Luce"
     */
    public Category(String name, ICategory parent) {
        this.name = name;
        this.parent = parent;
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public ICategory getParent() {
        return this.parent;
    }

    @Override
    public List<ICategory> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category other = (Category) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name; // così in GUI vedrai solo il nome
    }
}
